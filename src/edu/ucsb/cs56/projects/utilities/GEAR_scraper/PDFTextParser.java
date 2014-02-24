package edu.ucsb.cs56.projects.utilities.GEAR_scraper;

import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.awt.Rectangle;
import java.util.List;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.PDFTextStripperByArea;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
 

/**
 * PDFTextParser  -- processes the PDFs into text with apache pdf box
 *
 * class PDFTextParser adapted from http://thottingal.in/blog/2009/06/24/pdfbox-extract-text-from-pdf/ 
 * 
 * @author Alex Mousavi and Kyle Jorgensen
 * @author Alan Buzdar
 * @version CS56, Winter 2014
 * @see PDFTextParserTest
 */

public class PDFTextParser {
 
    
     /** Extract text from PDF Document
     * @param fileName - the name of the PDF file from which we will extract the text
     */
    public static String pdftoText(String fileName) {
	PDFParser parser;  // this object handles the parsing of the PDF document
	String parsedText = null;  // this is the String that will contain the text from the pdf doc

	// these 3 are all objects within PDF box that allow us to manipulate the pdf document
	PDDocument pdDoc = null;
	COSDocument cosDoc = null;

	File file = new File(fileName);

	if (!file.isFile()) {
	    System.err.println("File " + fileName + " does not exist.");
	    return null;
	}
	// try to create a new PDFParser
	try {
	    parser = new PDFParser(new FileInputStream(file));
	} catch (IOException e) {
	    System.err.println("Unable to open PDF Parser. " + e.getMessage());
	    return null;
	}
	// try to parse the PDF document
	try {
	    PDFTextStripper pdfStripper = null;

	    parser.parse();
	    cosDoc = parser.getDocument();

	    pdfStripper = new PDFTextStripper();
	    pdDoc = new PDDocument(cosDoc);

	    parsedText = pdfStripper.getText(pdDoc);
	} catch (Exception e) {
	    System.err
		.println("An exception occured in parsing the PDF Document."
			 + e.getMessage());
	} 
	// If the document opened, close the two objects that used it
	finally {
	    try {
		if (cosDoc != null)
		    cosDoc.close();
		if (pdDoc != null)
		    pdDoc.close();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
	return parsedText;
    }  


    /** Extract text by page from PDF Document via an InputStream (such as a URL)     
     * @param is  an InputStream from which to read (likely a URL)
     * @param page specifies the page you want to start on (note, this goes by the pageNumber of the pdf itself, not the text you are reading)for example, for the 2012/2013 catalog, the course list appears to start on page 10, but actually starts on 12.
     * 
     */
    public static String pdftoText(BufferedInputStream is, int page) {
	PDFParser parser;
	String parsedText = null;
	PDFTextStripperByArea pdfStripper;
	PDDocument pdDoc = null;
	Rectangle rect,rect2;
	// try to create a new PDFParser
	try {
	    parser = new PDFParser(is);
	    pdfStripper = new PDFTextStripperByArea();
	} catch (IOException e) {
	    System.err.println("Unable to open PDF Parser. " + e.getMessage());
	    return null;
	}
	// try to parse the PDF document
	try {
	    parser.parse();
	    pdDoc = parser.getPDDocument();
	    pdfStripper.setSortByPosition(true);
	    rect = new Rectangle(0,0,300,800);
	    rect2 = new Rectangle(300,0,1000,800);
	    List allPages = pdDoc.getDocumentCatalog().getAllPages(); // List holds a collection of PDPages

	    PDPage firstPage = (PDPage)allPages.get(page-1);

	    pdfStripper.addRegion("class1",rect); // determines region by using a Rectangle object
	    pdfStripper.addRegion("class2",rect2);
	    pdfStripper.extractRegions(firstPage); // extract the text from the region of the page we want
	    parsedText = pdfStripper.getTextForRegion("class1")+pdfStripper.getTextForRegion("class2");
	    
	} catch (Exception e) {
	    System.err
		.println("An exception occured in parsing the PDF Document."
			 + e.getMessage());
	} 
	// If the document opened, close the two objects that used it
	finally {
	    try {
		if (pdDoc != null)
		    pdDoc.close();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
	return parsedText;
    }

}
   

   

