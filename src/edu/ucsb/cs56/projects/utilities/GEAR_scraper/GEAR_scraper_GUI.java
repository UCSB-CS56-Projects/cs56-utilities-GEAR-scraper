package edu.ucsb.cs56.projects.utilities.GEAR_scraper;

import java.net.URL;
import java.net.MalformedURLException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
 * GUI Interface class
 *
 * @author Alan Buzdar
 *         View the classes that are in the GEAR GE section for the '15-'16 catalog.
 *         Can also input custom URL
 */


public class GEAR_scraper_GUI implements ItemListener {
    private String[] commands = {"Area D", "Area E", "Area F", "Area G", "Area H", "Special Area Requirements", "Ethnicity", "American Hist Inst", "European", "Writing"};
    private GEAR_scraper scraper;

    private int MIN_WINDOW_WIDTH;
    private int MIN_WINDOW_HEIGHT;
    private int WINDOW_WIDTH;
    private int WINDOW_HEIGHT;
    private int ALLGE_SCROLLPANE_WIDTH;
    private int SEARCH_PANEL_WIDTH;
    private int CHECKBOX_PANEL_HEIGHT;

    private JPanel titleWrapper;
    private JLabel title;

    private ArrayList<GECourse> allGEList;       // ArrayList of GE Course Objects, pulled directly from the scraper
    private GECourse[] allGEArray;               // Created from the allGEList ArrayList
    private JList<GECourse> allGEArr;            // This is the scrollable page for all of the GE courses


    private ArrayList<GECourse> filteredGEs;     // displayed when the checkboxes are clicked to narrow the search
    private GECourse[] filteredGEList;           // displayed    when the checkboxes are clicked to narrow the search

    private JPanel SearchWrapper;                // Holds the prett much everything on the right of the GUI
    private JPanel searchBarWrapper;             // used to hold the seachBarTitle and the seachBar
    private JLabel searchBarTitle;               // "Search for a Course"
    private JTextField searchBar;                // searchBar for the GUI

    private JPanel checkBoxPanel;                // JPanel for the GE Area checkboxes
    private ArrayList<JCheckBox> cboxes;         // ArrayList of checkboxes


    private JPanel BottomSearchPanel;


    private JFrame frame;
    private JScrollPane scrollPane;              // the scrollable pane that displays the courses
    private JScrollPane addedCoursePanel;         // scollable pane for the courses that the user adds

    private JButton customURLButton;             // Custom url button for the GUI
    private JButton clearCoursesButton;                // Clear Courses button for the GUI


    private ArrayList<String> foundCourseList;   // used to add to the Found Courses List
    private JList<String> addedCourseList;       // Jlist of the saved courses
    private DefaultListModel listModel;
    private DefaultListModel startListModel;

    private void go() {

        // Default Window and JPanel Dimension Variables
        MIN_WINDOW_WIDTH = 600;
        MIN_WINDOW_HEIGHT = 500;
        WINDOW_WIDTH = 600;
        WINDOW_HEIGHT = 500;
        ALLGE_SCROLLPANE_WIDTH = 250;
        SEARCH_PANEL_WIDTH = 650;
        CHECKBOX_PANEL_HEIGHT = 700;

        //initialize Listeners
        CustomURLButtonListener customURLBL = new CustomURLButtonListener();
        MySearchBarListener msbl = new MySearchBarListener();
        clearCoursesButtonScheduleListener clearCoursesButtonScheduleBL = new clearCoursesButtonScheduleListener();

        customURLButton = new JButton("Custom URL");
        scraper = new GEAR_scraper();                                 // uses the default url


        // set the title of the window
        title = new JLabel("GEAR Scraper for " + scraper.getCurrentYears() + "!");
        title.setFont(new Font(title.getFont().getFontName(), 1, 20)); // creates a new font, but uses the current default font, 1 = BOLD (Java Constant field values), 20 = size
        title.setHorizontalAlignment(JLabel.CENTER);
        // adds the title text to a JPanel and creates some space at the top
        titleWrapper = new JPanel(new GridBagLayout());               // GridBagLayout is used to center the title in a "Wrapper"
        titleWrapper.setPreferredSize(new Dimension(WINDOW_WIDTH, 40));
        titleWrapper.add(title);                                      // title is added to the wrapper and the wrapper is added to the top of the GUI


        // Array of GE Classes
        allGEList = scraper.createArrayList();                        // allGEList is an ArrayList of GECourse objects
        allGEArray = new GECourse[allGEList.size()];
        allGEArray = allGEList.toArray(allGEArray);                   // use allGEList to create an array, allGEArray
        filteredGEs = new ArrayList<GECourse>(allGEList);             // used in the show method and the searchBar Listener
        allGEArr = new JList<GECourse>(allGEArray);                   // A graphical list of GECouse objects


        // Search Bar
        searchBarWrapper = new JPanel();
        searchBarWrapper.setLayout(new BoxLayout(searchBarWrapper, BoxLayout.X_AXIS));
        // Used for GUI debugging
        //searchBarWrapper.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        searchBarWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        searchBarWrapper.setMaximumSize(new Dimension(SEARCH_PANEL_WIDTH, 25));
        // adds the title and search field to the search Bar Wrapper
        searchBarTitle = new JLabel("Search for a Course:");
        searchBar = new JTextField();
        searchBarWrapper.add(BorderLayout.WEST, searchBarTitle);
        searchBarWrapper.add(BorderLayout.EAST, searchBar);


        // Found Course List to keep track of duplicate searches
        foundCourseList = new ArrayList<String>();


        addedCourseList = new JList<String>();
        startListModel = new DefaultListModel<String>();
        startListModel.addElement("Add Courses by Searching!");
        startListModel
                .addElement("You can also Clear by typing 'clear' into the search box");
        addedCourseList.setModel(startListModel);


        // makes the DefaultListModel for the addedCOurseList
        listModel = new DefaultListModel<String>();


        // Clear Courses Button
        clearCoursesButton = new JButton("Clear Course Schedule");


        // Builds the search panel
        SearchWrapper = new JPanel();
        checkBoxPanel = new JPanel();
        checkBoxPanel.setMaximumSize(new Dimension(SEARCH_PANEL_WIDTH, CHECKBOX_PANEL_HEIGHT));
        SearchWrapper.setMaximumSize(new Dimension(SEARCH_PANEL_WIDTH, WINDOW_HEIGHT));
        SearchWrapper.setLayout(new BoxLayout(SearchWrapper, BoxLayout.Y_AXIS));
        // used for GUI debugging
        //SearchWrapper.setBorder(BorderFactory.createLineBorder(Color.BLACK));


        // Builds the Found Courses Panel
        // empty JScrollPane for the courses that have been searched and added
        addedCoursePanel = new JScrollPane(addedCourseList);
        addedCoursePanel.setMaximumSize(new Dimension(SEARCH_PANEL_WIDTH, 400));


        frame = new JFrame("GEAR Scraper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // Setting up all of the Checkboxes
        JCheckBox temp;
        cboxes = new ArrayList<JCheckBox>();
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
        // Used for GUI debugging
        //checkBoxPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
        for (String cmd : commands) {
            temp = new JCheckBox(cmd);
            cboxes.add(temp);
            temp.addItemListener(this);
            checkBoxPanel.add(temp);
        }


        // Adding Action Listeners
        customURLButton.addActionListener(customURLBL);                    // custom URL Button Listener
        searchBar.addActionListener(msbl);                                 // my search bar listener
        clearCoursesButton.addActionListener(clearCoursesButtonScheduleBL); // clear Course Schedule Button Listener


        // Add course schedule, checkboxPanel, Custom URL
        BottomSearchPanel = new JPanel();
        BottomSearchPanel.setLayout(new BoxLayout(BottomSearchPanel, BoxLayout.Y_AXIS));
        // Used for GUI debugging
        // BottomSearchPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        BottomSearchPanel.add(clearCoursesButton);
        BottomSearchPanel.add(checkBoxPanel);
        BottomSearchPanel.add(customURLButton);
        SearchWrapper.add(searchBarWrapper);
        SearchWrapper.add(addedCoursePanel);

        // left justfies everything underneath the addedCourse list.
        BottomSearchPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        SearchWrapper.add(BottomSearchPanel);


        // Adding the ALLGE Courses List
        scrollPane = new JScrollPane(allGEArr);
        scrollPane.setPreferredSize(new Dimension(ALLGE_SCROLLPANE_WIDTH, WINDOW_HEIGHT));


        // Finally add everything to the frame
        frame.getContentPane().add(BorderLayout.NORTH, titleWrapper);
        frame.getContentPane().add(BorderLayout.EAST, scrollPane);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setMinimumSize(new Dimension(MIN_WINDOW_WIDTH, MIN_WINDOW_HEIGHT));
        frame.getContentPane().add(BorderLayout.WEST, SearchWrapper);
        frame.setVisible(true);
        show("show all");

    }




    /* reacts to checkboxes being clicked
     */

    public void itemStateChanged(ItemEvent e) {


        if (e.getSource() instanceof JCheckBox) {
            //so i can reuse some code ;)
            String showString = "show";
            for (JCheckBox checkbox : cboxes) {
                if (checkbox.isSelected()) {
                    showString += " " + checkbox.getText();
                }
            }
            show(showString);
        }
    }




    /* reacts to searches
     */

    private class MySearchBarListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            // want to search the array of courses for the entered course
            // only gets the course being searched, text after "Search for a Course: "
            String query = searchBar.getText();
            // reset the text in the search bar
            searchBar.setText("");

            if (query.equals("clear")) {
                foundCourseList.clear();
                listModel.clear();
                addedCourseList.setModel(startListModel);
                return;
            } else {

                // want to search for courses in the filteredGE list so that the checkboxes can narrow down the search before hand
                for (int i = filteredGEs.size() - 1; i >= 0; i--) {
                    GECourse course = filteredGEs.get(i);
                    String courseName = course.toString();
                    // don't care about first letter caps, checks for adding the same course multiple times
                    if (courseName.equalsIgnoreCase(query) && !foundCourseList.contains(courseName)) {
                        // add the course to the foundCourseList
                        foundCourseList.add(courseName);

                        String courseAreasSatisfied = areasSatisfied(course, courseName);

                        listModel.addElement(courseAreasSatisfied);
                    }
                }

                // remakes the model, should show the updated data in the JScrollpane
                // adds the listModel back to the addedCourseList
                addedCourseList.setModel(listModel);
            }

        }

    }


    /* reacts to Clear Course Schedule Button being pressed

    */
    private class clearCoursesButtonScheduleListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            foundCourseList.clear();

            // remakes the entire addedCoursesPane
            // clears the exising listModel and then adds it back to the addedCOurselist
            listModel.clear();
            addedCourseList.setModel(startListModel);
        }
    }


    /* reacts to Custom URL Button being pressed
     */
    private class CustomURLButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String line;
            String start;
            String end;
            int startPage;
            int endPage;
            URL url;
            line = JOptionPane.showInputDialog("Enter the URL");

            if (!line.equals("")) {
                try {
                    url = new URL(line);
                } catch (MalformedURLException ex) {

                    JOptionPane.showMessageDialog(null, "There was an error following the URL.");
                    return;
                }
                start = JOptionPane.showInputDialog("Enter the page in GEAR the GE's start on");
                end = JOptionPane.showInputDialog("Enter the page in GEAR the GE's end on");
                try {
                    startPage = Integer.parseInt(start);
                    endPage = Integer.parseInt(end);
                    if (endPage - startPage > 10)
                        throw new NumberFormatException();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Those weren't properly formatted numbers. Make sure to only put the page numbers the GE's are on.");
                    return;
                }
                scraper = new GEAR_scraper(url, startPage, endPage);
                allGEList = scraper.createArrayList();
                show("all");
            }

        }
    }


    // stolen from my CLI interface
    public void show(String line) {

        GECourse a;
        // resets the filteredGEs array on every checkbox clicked to update the scrollPane
        filteredGEs = new ArrayList<GECourse>(allGEList);

        for (int i = filteredGEs.size() - 1; i >= 0; i--) {
            a = filteredGEs.get(i);
            if (a.getCourseNum() == null) {
                filteredGEs.remove(i);
                continue;
            }
            if (line.contains("Area D")) {
                if (!a.isD()) {
                    filteredGEs.remove(i);
                    continue;
                }
            }
            if (line.contains("Area E")) {
                if (!a.isE()) {
                    filteredGEs.remove(i);
                    continue;
                }
            }
            if (line.contains("Area F")) {
                if (!a.isF()) {
                    filteredGEs.remove(i);
                    continue;
                }
            }
            if (line.contains("Area G")) {
                if (!a.isG()) {
                    filteredGEs.remove(i);
                    continue;
                }
            }
            if (line.contains("Area H")) {
                if (!a.isH()) {
                    filteredGEs.remove(i);
                    continue;
                }
            }
            if (line.contains("Special Area")) {
                if (!a.isS()) {
                    filteredGEs.remove(i);
                    continue;
                }
            }
            if (line.contains("Writing")) {
                if (!a.isWriting()) {
                    filteredGEs.remove(i);
                    continue;
                }
            }
            if (line.contains("American")) {
                if (!a.isAmHistInst()) {
                    filteredGEs.remove(i);
                    continue;
                }
            }
            if (line.contains("Ethnicity")) {
                if (!a.isEthnicity()) {
                    filteredGEs.remove(i);
                    continue;
                }
            }
            if (line.contains("European")) {
                if (!a.isEuroTrad()) {
                    filteredGEs.remove(i);
                    continue;
                }
            }
        }

        // filteredGEs is now an array of all of the GECourses that the user wants to diplay

        // creates a new array to pass to the JList
        allGEArray = new GECourse[filteredGEs.size()];
        allGEArray = filteredGEs.toArray(allGEArray);

        // creates the new JList
        allGEArr = new JList<GECourse>(allGEArray);

        // removes the JList from the frame, makes a new one and then adds it back
        scrollPane.setVisible(false);
        frame.getContentPane().remove(scrollPane);
        scrollPane = new JScrollPane(allGEArr);
        scrollPane.setPreferredSize(new Dimension(ALLGE_SCROLLPANE_WIDTH, WINDOW_HEIGHT));
        frame.getContentPane().add(BorderLayout.EAST, scrollPane);

        // updates the frame to display the changes
        frame.invalidate();
        frame.validate();
    }

    private String areasSatisfied(GECourse course, String courseName) {

        String Line = courseName + " satisfies: ";
        ArrayList<String> areas = new ArrayList<String>();
        if (course.isD()) areas.add("D");
        if (course.isE()) areas.add("E");
        if (course.isF()) areas.add("F");
        if (course.isG()) areas.add("G");
        if (course.isH()) areas.add("H");
        if (course.isS()) areas.add("S");
        if (course.isWriting()) areas.add("W");
        if (course.isAmHistInst()) areas.add("AmHistInst");
        if (course.isEthnicity()) areas.add("Ethnicity");
        if (course.isEuroTrad()) areas.add("Euro");

        if (areas.size() == 0) {
            //this means that a course doesnt satify any areas
            Line += "nada, don't take it!";
        } else {
            // fenceposting for commas
            for (int j = 0; j < areas.size() - 1; j++) {
                Line += areas.get(j) + ", ";
            }
            Line += areas.get(areas.size() - 1);
        }

        return Line;
    }

    public static void main(String... args) {
        GEAR_scraper_GUI gui = new GEAR_scraper_GUI();
        gui.go();

    }


}
