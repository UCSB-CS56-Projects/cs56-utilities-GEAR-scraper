package edu.ucsb.cs56.projects.utilities.GEAR_scraper;

import java.net.URL;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/** Command Line Interface class
 * modeled on Phill Conrad lecture notes at http://www.cs.ucsb.edu/~pconrad/cs56/14W/lect/02.06/CLIExample.java
 * @author Alan Buzdar
 */

public class GEAR_scraper_Interface{

    private String prompt = "> ";
    private GEAR_scraper x;
    private Scanner sc = new Scanner(System.in);
    private ArrayList<GECourse> p;
    private String[] commands = {"help","quit","show all","customURL","show areaD","show areaE", "show areaF", "show areaG", "show areaH", "show specialArea", "show ethnicity", "show american", "show euro", "show writing", "You may also string together show commands for more complex queries: \n show ethnicity areaD \n show areaG american writing"};

    public void go(String... args) {
	System.out.println("Starting Gear Scraping...");

	if(args.length==0)
	    x = new GEAR_scraper();
	else{
	    try{
		x = new GEAR_scraper(new URL(args[0]),Integer.parseInt(args[1]),Integer.parseInt(args[2]));}
	    catch(Exception e){
		System.out.println("error following url");
		return;}

	}
	p = x.createArrayList();
	System.out.println("Done Scraping Gear! Type help for help");

	boolean done = false;
	
	while (!done) {
	    // Prompt for input
	    System.out.print(prompt);

	    // Get next line input, minus leading and trailing whitespace
	    String line  = sc.nextLine().trim();
	 
	    if (line.contains("quit") ) {
		done = true;
	    } else if (line.contains("show") ) {
		this.show(line);
	    } else if (line.contains("help")) {
		this.help(line);
	    } else if (line.equals("")) {
		System.out.println("Type \"quit\" to quit");
	    } else if(line.contains("customURL")){
		customURL();
	    } else {
		System.out.println("Unknown command: " + line);
		System.out.println("Try \"help\" or type \"quit\" to quit");
	    }
	} // end while

    } // end go

    public void customURL(){
	System.out.print("Please type in the URL \n" +prompt);
	String line = sc.nextLine().trim();
	System.out.println("Type in the Start Page");
	String start = sc.nextLine().trim();
	System.out.println("Type in the End Page");
	String end = sc.nextLine().trim();
	try{
	    x = new GEAR_scraper(new URL(line),Integer.parseInt(start),Integer.parseInt(end));
	    p = x.createArrayList();
	}catch(Exception e){
	    System.out.println("URL: " + line + "\n Start Page: " +start +" End Page: " + end);
	    System.out.println("Gear Scraping failed. Please check the URL and Page numbers and try again");
	    customURL();
	}
    }
    /** prints out all the classes that have the parameters you list. Note: it is an "and" relationship, not an "or". So show areaG areaF wouldnt display any results because a class can't fulfill both requirements
     */
    public void show(String line){
	//check if command formatted properly
	if(!( line.contains("all") || line.contains("euro") || line.contains("ethnicity") || line.contains("american") || line.contains("writing") || line.contains("specialArea") || line.contains("areaH") || line.contains("areaG") || line.contains("areaF") || line.contains("areaE") || line.contains("areaD"))){
	    System.out.println(line+" not recognized. possible show commands include: \n ");
	    for(String cmd: commands){
		if(cmd.contains("show"))
		   System.out.println(cmd);
	    }
	    return;
	 }

	GECourse a;
	ArrayList<GECourse> temp = new ArrayList<GECourse>(p);
	
	for(int i = temp.size()-1;i>=0; i--){
	    a = temp.get(i);
	    if (a.getCourseNum() == null){
		temp.remove(i);
		continue;
	    }
	    if(line.contains("areaD")){
		if(!a.isD()){
		    temp.remove(i);
		    continue;
		}
	    }
	    if(line.contains("areaE")){
		if(!a.isE()){
		    temp.remove(i);
		    continue;
		}
	    }
	    if(line.contains("areaF")){
		if(!a.isF()){
		    temp.remove(i); continue; }}
	    if(line.contains("areaG")){
		if(!a.isG()){
		    temp.remove(i); continue; }}
	    if(line.contains("areaH")){
		if(!a.isH()){
		    temp.remove(i); continue; }}
	    if(line.contains("specialArea")){
		if(!a.isS()){
		    temp.remove(i); continue; }}
	    if(line.contains("writing")){
		if(!a.isWriting()){
		    temp.remove(i); continue; }}
	    if(line.contains("american")){
		if(!a.isAmHistInst()){
		    temp.remove(i); continue; }}
	    if(line.contains("ethnicity")){
		if(!a.isEthnicity()){
		    temp.remove(i); continue; }}
	    if(line.contains("euro")){
		if(!a.isEuroTrad()){
		    temp.remove(i); continue; }}
	}
	for(GECourse f: temp){
	    System.out.println(f);}
	
    }
    
    public void help(String line){
	System.out.println("Possible Commands:");
	for(String cmd: commands)
	    System.out.println(cmd);
    }

    public static void main(String... args){

	if(args.length==0 || args.length==3){
	    GEAR_scraper_Interface n = new GEAR_scraper_Interface();
	    n.go(args);    
	}
	else
	    System.out.println("Improperly formatted arguments. \n This program takes either no arguments, or all three of the following: URLofGEAR StartPage EndPage");
	
	
    }




}
