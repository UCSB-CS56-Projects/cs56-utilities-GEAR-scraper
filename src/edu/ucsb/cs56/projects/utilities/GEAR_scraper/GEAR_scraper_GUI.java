package edu.ucsb.cs56.projects.utilities.GEAR_scraper;

import java.net.URL;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/** GUI Interface class
 * @author Alan Buzdar
 */


public class GEAR_scraper_GUI implements ItemListener{
    private String[] commands = {"Area D","Area E", "Area F", "Area G", "Area H", "Special Area Requirements", "Ethnicity", "American Hist Inst", "European", "Writing"};
    private GEAR_scraper x;
    private ArrayList<GECourse> p;
    private ArrayList<JCheckBox> cboxes;
    private JList<GECourse> list;
    private GECourse[] courseArray;
    private JFrame frame;
    private JScrollPane scrollPane;
    private JButton customURLButton;
    private void go(){
	MyButtonListener mbl = new MyButtonListener();
	customURLButton = new JButton("Custom URL");
	customURLButton.setActionCommand("url");
	x = new GEAR_scraper();
	p = x.createArrayList();
	courseArray = new GECourse[p.size()];
	courseArray = p.toArray(courseArray);
	list = new JList<GECourse>(courseArray);
	frame = new JFrame();
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	cboxes = new ArrayList<JCheckBox>();
	JCheckBox temp;
	JPanel checkBoxPanel = new JPanel();
	checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
	for( String cmd: commands){
	    temp = new JCheckBox(cmd);
	    cboxes.add(temp);
	    temp.addItemListener(this);
	    checkBoxPanel.add(temp);
	}
	customURLButton.addItemListener(mbl);
	checkBoxPanel.add(customURLButton);
	scrollPane = new JScrollPane(list);
	scrollPane.setPreferredSize(new Dimension(250,500));
	frame.getContentPane().add(BorderLayout.EAST,scrollPane);
	frame.setSize(600,500);
	frame.getContentPane().add(BorderLayout.WEST,checkBoxPanel);
       	frame.setVisible(true);
	show("show all");

    }
    
    public void itemStateChanged(ItemEvent e){


	if(e.getSource() instanceof JCheckBox){
	    //so i can reuse some code ;)
	    String showString = "show";
	    for( JCheckBox checkbox: cboxes){
		if(checkbox.isSelected()){
		    showString += " " +checkbox.getText();
		}
	    }
	    show(showString);}


    }
    
    public class MyButtonListener implements ActionListener { 

	public void actionPerformed(ActionEvent e) { 
	    String action = e.getActionCommand();
	    System.out.println(action+ " Button was pressed!");

	    switch (action) {

	    case "url": 
		;

	    case "save": 
		System.out.println("I'm a savin'!");
		break;

	    }

	}

    // stolen from my CLI interface
   public void show(String line){

	GECourse a;
	ArrayList<GECourse> temp = new ArrayList<GECourse>(p);
	
	for(int i = temp.size()-1;i>=0; i--){
	    a = temp.get(i);
	    if (a.getCourseNum() == null){
		temp.remove(i);
		continue;
	    }
	    if(line.contains("Area D")){
		if(!a.isD()){
		    temp.remove(i);
		    continue;
		}
	    }
	    if(line.contains("Area E")){
		if(!a.isE()){
		    temp.remove(i);
		    continue;
		}
	    }
	    if(line.contains("Area F")){
		if(!a.isF()){
		    temp.remove(i); continue; }}
	    if(line.contains("Area G")){
		if(!a.isG()){
		    temp.remove(i); continue; }}
	    if(line.contains("Area H")){
		if(!a.isH()){
		    temp.remove(i); continue; }}
	    if(line.contains("Special Area")){
		if(!a.isS()){
		    temp.remove(i); continue; }}
	    if(line.contains("Writing")){
		if(!a.isWriting()){
		    temp.remove(i); continue; }}
	    if(line.contains("American")){
		if(!a.isAmHistInst()){
		    temp.remove(i); continue; }}
	    if(line.contains("Ethnicity")){
		if(!a.isEthnicity()){
		    temp.remove(i); continue; }}
	    if(line.contains("European")){
		if(!a.isEuroTrad()){
		    temp.remove(i); continue; }}
	}
	courseArray = new GECourse[temp.size()];
	courseArray = temp.toArray(courseArray);
	list = new JList<GECourse>(courseArray);
	scrollPane.setVisible(false);
	frame.getContentPane().remove(scrollPane);
	scrollPane = new JScrollPane(list);
	scrollPane.setPreferredSize(new Dimension(250,500));
	frame.getContentPane().add(BorderLayout.EAST,scrollPane);
	frame.invalidate();
	frame.validate();
   }

    public static void main(String... args){
	GEAR_scraper_GUI gui = new GEAR_scraper_GUI();
	gui.go();

    }


}
