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
    private JList list;
    private GECourse[] courseArray;
    private void go(){
	x = new GEAR_scraper();
	p = x.createArrayList();
	courseArray = new GECourse[p.size()];
	courseArray = p.toArray(courseArray);
	list = new JList(courseArray);
	JFrame frame = new JFrame();
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
	frame.getContentPane().add(BorderLayout.EAST,new JScrollPane(list));
	frame.setSize(600,500);
	frame.getContentPane().add(BorderLayout.WEST,checkBoxPanel);
       	frame.setVisible(true);

    }
    
    public void itemStateChanged(ItemEvent e){
	//so i can reuse some code ;)
	String showString = "show";
	for( JCheckBox checkbox: cboxes){
	    if(checkbox.isSelected()){
		showString += " " +checkbox.getText();
	    }
	}
	show(showString);
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
	for(GECourse f: temp){
	    System.out.println(f);}
	System.out.println("done");
   }

    public static void main(String... args){
	GEAR_scraper_GUI gui = new GEAR_scraper_GUI();
	gui.go();

    }


}