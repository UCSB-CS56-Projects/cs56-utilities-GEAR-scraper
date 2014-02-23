package edu.ucsb.cs56.projects.utilities.GEAR_scraper;

import java.net.URL;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.awt.Rectangle;
import java.util.ArrayList;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.PDFTextStripperByArea;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;

/**
 * Scrapes Gear and creates ArrayList
 *
 * @author Alan Buzdar
 * @version CS56, Winter 2014
 * 
 */



public class GEAR_scraper  {
    InputStream is,is2,is3,is4;
    String defaultURL = "http://engineering.ucsb.edu/current_undergraduates/pdf/GEAR-12-13.pdf";
    ArrayList<String> textToParse = new ArrayList<String>();
    /**
     * default constructor - uses '12-'13 GEAR 
    */

  public GEAR_scraper(){
	try{
	    int startPage = 12;
	    for(int i = 0;i<8;i++){
		is = new URL(defaultURL).openStream();
		PDFTextParser myTester = new PDFTextParser();
		String x = myTester.pdftoText(is,startPage+i);
		textToParse.add(x);}
	}catch(IOException e){
	    e.printStackTrace();}
  }
    /**
     * constructor takes custom url, startPage, and endPage 
     */
    public GEAR_scraper(String url,int startPage,int endPage){
	try{
	    
	    for(int i = 0;i<(endPage+1-startPage);i++){
		is = new URL(defaultURL).openStream();
		PDFTextParser myTester = new PDFTextParser();
		String x = myTester.pdftoText(is,startPage+i);
		textToParse.add(x);}


    }catch(IOException e){
	e.printStackTrace();
    }
    }

    private boolean isCourse(String s){
	if (( s.contains("0") || s.contains("1") || s.contains("2") || s.contains("3") || s.contains("4") || s.contains("5") || s.contains("6") || s.contains("7") || s.contains("8") || s.contains("9")) && ( s.contains("Anthropology")) || s.contains("Art") || s.contains("Science") || s.contains("Exercise") || s.contains("Astronomy") || s.contains("Biology") || s.contains("Biomolecular") || s.contains("Black Studies") || s.contains("Chemical") || s.contains("Engineering") || s.contains("French") || s.contains("Studies") || s.contains("Chinese") || s.contains("Classic") || s.contains("Communication") || s.contains("Literature") || s.contains("Counseling") || s.contains("Dance") || s.contains("Dynamical")  || s.contains("Ecology")|| s.contains("Economics") || s.contains("Education") || s.contains("German") || s.contains("English") || s.contains("Geography") || s.contains("Global") || s.contains("Greek") || s.contains("Hebrew") || s.contains("History") || s.contains("Interdisciplinary") || s.contains("Italian") || s.contains("Japanese") || s.contains("Latin") || s.contains("Linguistics") || s.contains("Literature") || s.contains("Materials") || s.contains("Math") || s.contains("Molecular") || s.contains("Music") || s.contains("Philosophy") || s.contains("Physics") || s.contains("Portuguese") || s.contains("Psychology") || s.contains("Slavic") || s.contains("Sociology") || s.contains("Spanish") || s.contains("Statistics") || s.contains("Technology") || s.contains("Theater") || s.contains("Writing") )
	    return true;
	return false;

	    }

    public ArrayList<GECourse> createArrayList() {
	String area = "D";
	ArrayList<GECourse> x = new ArrayList<GECourse>();
	for(String page: textToParse){

	    for(String s: page.split("\n")){
		if(s.contains("provide a perspective on world cultures")){
		    area = "E";
		    break;}
		if(s.contains("Area F") || s.contains("Area f")){
		    area = "F";
		    break;}
		if(s.contains("develop an appreciation of literature")){
		    area = "G";
		    break;}
		if(s.contains("Special Subject Area")){
		    area = "S";
		    break;}
		if(isCourse(s))
		    x.add(new GECourse(s,area));
	}
	}


	return x;
    }

}
