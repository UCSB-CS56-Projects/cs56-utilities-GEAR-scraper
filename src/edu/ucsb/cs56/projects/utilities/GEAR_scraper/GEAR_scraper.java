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
    String textToParse, textToParse2, textToParse3,textToParse4;
    /**
     * default constructor - uses '12-'13 GEAR 
    */

  public GEAR_scraper(){
	try{
	is = new URL(defaultURL).openStream();
	is2 = new URL(defaultURL).openStream();
	is3 = new URL(defaultURL).openStream();
	is4 = new URL(defaultURL).openStream();

	PDFTextParser myTester = new PDFTextParser();
	PDFTextParser myTester2 = new PDFTextParser();
	PDFTextParser myTester3 = new PDFTextParser();
	PDFTextParser myTester4 = new PDFTextParser();

	textToParse = myTester.pdftoText(is,12,13);
	textToParse2 = myTester2.pdftoText(is2,14,15);
	textToParse3 = myTester3.pdftoText(is3,16,17);
	textToParse4 = myTester4.pdftoText(is4,18,19);



    }catch(IOException e){
	e.printStackTrace();}
   }
    /**
     * constructor takes custom url, startPage, and endPage 
     */
    public GEAR_scraper(String url,int startPage,int endPage){
	try{
	is = new URL(url).openStream();
	PDFTextParser myTester = new PDFTextParser();
	textToParse = myTester.pdftoText(is,startPage,endPage);
    }catch(IOException e){
	e.printStackTrace();
    }
    }

    public ArrayList<GECourse> createArrayList() {
	String area = "D";
	ArrayList<GECourse> x = new ArrayList<GECourse>();
	for(String s: textToParse.split("\n")){
	    if(s.contains("Area E")){
		area = "E";
		break;}
	    if(s.contains("Area F")||s.contains("Area f")){
		area = "F";
		break;}
	    if(s.contains("Area G")){
		area = "G";
		break;}
	    if( s.contains("0") || s.contains("1") || s.contains("2") || s.contains("3") || s.contains("4") || s.contains("5") || s.contains("6") || s.contains("7") || s.contains("8") || s.contains("9"))
	    x.add(new GECourse(s,area));
	}

	for(String s: textToParse2.split("\n")){
	    if(s.contains("Area E")){
		area = "E";
		break;}
	    if(s.contains("Area F")||s.contains("Area f")){
		area = "F";
		break;}
	    if(s.contains("Area G")){
		area = "G";
		break;}
	    if( s.contains("0") || s.contains("1") || s.contains("2") || s.contains("3") || s.contains("4") || s.contains("5") || s.contains("6") || s.contains("7") || s.contains("8") || s.contains("9"))
	    x.add(new GECourse(s,area));
	}

	for(String s: textToParse3.split("\n")){
	    if(s.contains("Area E")){
		area = "E";
		break;}
	    if(s.contains("Area F")||s.contains("Area f")){
		area = "F";
		break;}
	    if(s.contains("Area G")){
		area = "G";
		break;}
	    if( s.contains("0") || s.contains("1") || s.contains("2") || s.contains("3") || s.contains("4") || s.contains("5") || s.contains("6") || s.contains("7") || s.contains("8") || s.contains("9"))
	    x.add(new GECourse(s,area));
	}

	for(String s: textToParse4.split("\n")){
	    if(s.contains("Area E")){
		area = "E";
		break;}
	    if(s.contains("Area F")||s.contains("Area f")){
		area = "F";
		break;}
	    if(s.contains("Area G")){
		area = "G";
		break;}
	    if( s.contains("0") || s.contains("1") || s.contains("2") || s.contains("3") || s.contains("4") || s.contains("5") || s.contains("6") || s.contains("7") || s.contains("8") || s.contains("9"))
	    x.add(new GECourse(s,area));
	}

	return x;
    }

}
