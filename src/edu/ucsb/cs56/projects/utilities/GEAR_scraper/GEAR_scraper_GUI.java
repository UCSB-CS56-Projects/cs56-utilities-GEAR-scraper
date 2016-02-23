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

    private ArrayList<GECourse> allGEs;          // ArrayList of GE Course Objects, pulled directly from the scraper
    private GECourse[] allGEArray;               // Created from the allGEs ArrayList

    private ArrayList<GECourse> filteredGEs;     // displayed when the checkboxes are clicked to narrow the search
    private GECourse[] filteredGEList;           // displayed when the checkboxes are clicked to narrow the search

    private JList<GECourse> allGEList;           // This is the scrollable page for all of the GE courses

    private ArrayList<JCheckBox> cboxes;

    private JPanel checkBoxPanel;

    private JFrame frame;
    private JScrollPane scrollPane;              // the scrollable pane that displays the courses
    private JScrollPane addedCoursePane;

    private JButton customURLButton;
    private JButton clearCourses;
    private JTextField searchBar;

    private ArrayList<String> foundCourseList;   // used to add to the Found Courses List


    private ArrayList<String> addedCourseArr; // should only need this to save the searched classes
    private JList<String> addedCourseList;    // Jlist of the saved coruses

    private JTextField foundCourses;


    private void go() {

        //initialize variables
        MyButtonListener mbl = new MyButtonListener();
        MySearchBarListener msbl = new MySearchBarListener();
        customURLButton = new JButton("Custom URL");
        scraper = new GEAR_scraper();                              // uses the default url
        allGEs = scraper.createArrayList();                        // allGEs is an ArrayList of GECourse objects
        allGEArray = new GECourse[allGEs.size()];
		allGEArray = allGEs.toArray(allGEArray);                   // use allGEs to create an array, allGEArray

        filteredGEs = new ArrayList<GECourse>(allGEs);             // used in the show method and the searchBar Listener

        searchBar = new JTextField("Search for a Course: ", 20);
        foundCourses = new JTextField("Found Courses: ", 20);
        foundCourseList = new ArrayList<String>();
        clearCourses = new JButton("Clear Course Schedule");
        clearCourses.setActionCommand("clear");

        // A graphical list of GECouse objects
        allGEList = new JList<GECourse>(allGEArray);


        // USed for the saved Course JList
        addedCourseArr = new ArrayList<String>();
        addedCourseList = new JList<String>(addedCourseArr.toArray(new String[addedCourseArr.size()]));



        // Builds the Checkbox panel, checkbox ArrayList
        checkBoxPanel = new JPanel();

        checkBoxPanel.add(searchBar);
        //checkBoxPanel.add(foundCourses);

        addedCoursePane = new JScrollPane(); // empty JScrollPane for the courses that have been searched and added
        // set the dimensions of the JScrollPane
        addedCoursePane.setPreferredSize(new Dimension(100, 100));
        checkBoxPanel.add(addedCoursePane);

        checkBoxPanel.add(clearCourses);

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JCheckBox temp;
        cboxes = new ArrayList<JCheckBox>();


        //GUI CRAP
        customURLButton.setActionCommand("url");
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
        for (String cmd : commands) {
            temp = new JCheckBox(cmd);
            cboxes.add(temp);
            temp.addItemListener(this);
            checkBoxPanel.add(temp);
        }


        // set up everything to add to the frame

        customURLButton.addActionListener(mbl);
        checkBoxPanel.add(customURLButton);

        searchBar.addActionListener(msbl);   // my search bar listener
        clearCourses.addActionListener(mbl); // my button listener

        scrollPane = new JScrollPane(allGEList);
        scrollPane.setPreferredSize(new Dimension(250, 500));


        // Add everything to the frame

        frame.getContentPane().add(BorderLayout.EAST, scrollPane);
        frame.setSize(600, 500);
        frame.getContentPane().add(BorderLayout.WEST, checkBoxPanel);
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
            String query = searchBar.getText().substring(21); // only gets the course being searched
            searchBar.setText("Search for a Course: ");

            // want to search for courses in the filteredGE list so that the checkboxes can narrow down the search before hand
            for (int i = filteredGEs.size() - 1; i >= 0; i--) {
                String course = filteredGEs.get(i).toString();


                if (course.equalsIgnoreCase(query) && !foundCourseList.contains(course)) {
                    // add the course to the addedCourseArr

                    addedCourseArr.add(course);
                    foundCourseList.add(course);

                }
            }

            // the addedCourseArr now contains a new item, need to update the JList and update the frame

            // want to add to the array and then add to the course list
            // need to convert the ArrayList to an array to pass to the JList

            // creates the new JLIst
            addedCourseList = new JList<String>(addedCourseArr.toArray(new String[addedCourseArr.size()]));

            // removes the old addedCoursePane from the frame, makes a new one and then adds it back
            addedCoursePane.setVisible(false);
            // removes the scrollapne from the jpanel
            checkBoxPanel.remove(addedCoursePane);
            addedCoursePane = new JScrollPane(addedCourseList);
            scrollPane.setPreferredSize(new Dimension(100, 100));
            checkBoxPanel.add(addedCoursePane, 1); // want to add it back into the same place, index 1

            frame.invalidate();
            frame.validate();

        }

    }





    /* reacts to Buttons being pressed, this can be fixed as it is a procedural action listener
    Would be better to implement another one for the Clear Courses button separately
     */
    private class MyButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String action = e.getActionCommand();
            switch (action) {

                case "url": // CODE SMELL

                    String line;
                    String start;
                    String end;
                    int startPage;
                    int endPage;
                    URL url;
                    line = JOptionPane.showInputDialog("Enter the URL");
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
                    allGEs = scraper.createArrayList();
                    show("all");


                case "clear": // CODE SMELL
                    // will need to be adapted for the scrollable text box

                    //foundCourses.setText("Found Courses: ");
                    foundCourseList.clear();
                    addedCourseArr.clear();

                    // remakes the entire addedCoursesPane

                    // need to clear the addedCoursesList and then update the JList
                    addedCourseList = new JList<String>(addedCourseArr.toArray(new String[addedCourseArr.size()]));

                    // removes the old addedCoursePane from the frame, makes a new one and then adds it back
                    addedCoursePane.setVisible(false);
                    // removes the scrollapne from the jpanel
                    checkBoxPanel.remove(addedCoursePane);
                    addedCoursePane = new JScrollPane(addedCourseList);
                    scrollPane.setPreferredSize(new Dimension(100, 100));
                    checkBoxPanel.add(addedCoursePane, 1); // want to add it back into the same place, index 1

                    frame.invalidate();
                    frame.validate();


            }

        }
    }




    // stolen from my CLI interface
    public void show(String line) {

        GECourse a;
        // resets the filteredGEs array on every checkbox clicked to update the scrollPane
        filteredGEs = new ArrayList<GECourse>(allGEs);

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
        allGEList = new JList<GECourse>(allGEArray);

        // removes the JList from the frame, makes a new one and then adds it back
        scrollPane.setVisible(false);
        frame.getContentPane().remove(scrollPane);
        scrollPane = new JScrollPane(allGEList);
        scrollPane.setPreferredSize(new Dimension(250, 500));
        frame.getContentPane().add(BorderLayout.EAST, scrollPane);

        // updates the frame to display the changes
        frame.invalidate();
        frame.validate();
    }




    public static void main(String... args) {
        GEAR_scraper_GUI gui = new GEAR_scraper_GUI();
        gui.go();

    }


}
