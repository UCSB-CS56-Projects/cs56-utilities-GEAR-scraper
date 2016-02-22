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
    private ArrayList<GECourse> p;
    private ArrayList<JCheckBox> cboxes;
    private JList<GECourse> list;
    private GECourse[] courseArray;
    private JFrame frame;
    private JScrollPane scrollPane;
    private JButton customURLButton;
    private JButton clearCourses;
    private JTextField searchBar;

    private ArrayList<String> foundCourseList;
    private JTextField foundCourses;
    private boolean firstSearch = true;


    private void go() {

        //initialize variables
        MyButtonListener mbl = new MyButtonListener();
        MySearchBarListener msbl = new MySearchBarListener();
        customURLButton = new JButton("Custom URL");
        scraper = new GEAR_scraper(); // uses the default url
        p = scraper.createArrayList(); // p is an ArrayList of GECourse objects
        courseArray = new GECourse[p.size()];
		courseArray = p.toArray(courseArray); // use p to create an array, courseArray

        searchBar = new JTextField("Search for a Course: ", 20);
        foundCourses = new JTextField("Found Courses: ", 20);
        foundCourseList = new ArrayList<String>();
        clearCourses = new JButton("Clear Course Schedule");
        clearCourses.setActionCommand("clear");

        // A graphical list of GECouse objects
        list = new JList<GECourse>(courseArray);


        // Builds the Checkbox panel, checkbox ArrayList
        JPanel checkBoxPanel = new JPanel();

        checkBoxPanel.add(searchBar);
        checkBoxPanel.add(foundCourses);
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

        searchBar.addActionListener(msbl);
        clearCourses.addActionListener(mbl);

        scrollPane = new JScrollPane(list);
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
            String query = searchBar.getText().substring(21);
            searchBar.setText("Search for a Course: ");


            for (int i = p.size() - 1; i >= 0; i--) {
                String course = p.get(i).toString();

                if (course.equalsIgnoreCase(query) && !foundCourseList.contains(course)) {
                    if (firstSearch) {
                        foundCourses.setText(foundCourses.getText() + "\n" + course);
                        firstSearch = false;
                    } else {
                        foundCourses.setText(foundCourses.getText() + ", " + course);
                    }
                    foundCourseList.add(course);


                }
            }


        }

    }





    /* reacts to customURL Button being pressed.
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
                    p = scraper.createArrayList();
                    show("all");


                case "clear": // CODE SMELL

                    foundCourses.setText("Found Courses: ");
                    foundCourseList.clear();
                    firstSearch = true;

            }

        }
    }




    // stolen from my CLI interface
    public void show(String line) {

        GECourse a;
        ArrayList<GECourse> temp = new ArrayList<GECourse>(p);

        for (int i = temp.size() - 1; i >= 0; i--) {
            a = temp.get(i);
            if (a.getCourseNum() == null) {
                temp.remove(i);
                continue;
            }
            if (line.contains("Area D")) {
                if (!a.isD()) {
                    temp.remove(i);
                    continue;
                }
            }
            if (line.contains("Area E")) {
                if (!a.isE()) {
                    temp.remove(i);
                    continue;
                }
            }
            if (line.contains("Area F")) {
                if (!a.isF()) {
                    temp.remove(i);
                    continue;
                }
            }
            if (line.contains("Area G")) {
                if (!a.isG()) {
                    temp.remove(i);
                    continue;
                }
            }
            if (line.contains("Area H")) {
                if (!a.isH()) {
                    temp.remove(i);
                    continue;
                }
            }
            if (line.contains("Special Area")) {
                if (!a.isS()) {
                    temp.remove(i);
                    continue;
                }
            }
            if (line.contains("Writing")) {
                if (!a.isWriting()) {
                    temp.remove(i);
                    continue;
                }
            }
            if (line.contains("American")) {
                if (!a.isAmHistInst()) {
                    temp.remove(i);
                    continue;
                }
            }
            if (line.contains("Ethnicity")) {
                if (!a.isEthnicity()) {
                    temp.remove(i);
                    continue;
                }
            }
            if (line.contains("European")) {
                if (!a.isEuroTrad()) {
                    temp.remove(i);
                    continue;
                }
            }
        }

        //remake the list
        courseArray = new GECourse[temp.size()];
        courseArray = temp.toArray(courseArray);

        list = new JList<GECourse>(courseArray);
        scrollPane.setVisible(false);

        frame.getContentPane().remove(scrollPane);
        scrollPane = new JScrollPane(list);

        scrollPane.setPreferredSize(new Dimension(250, 500));
        frame.getContentPane().add(BorderLayout.EAST, scrollPane);

        frame.invalidate();
        frame.validate();
    }




    public static void main(String... args) {
        GEAR_scraper_GUI gui = new GEAR_scraper_GUI();
        gui.go();

    }


}
