//package edu.ucsb.cs56.projects.utilities.GEAR-scraper;
// Not sure what correct package will be

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
 * @version CS56, Spring 2011
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
	PDFTextStripper pdfStripper = null;
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


    /** Extract text BY AREA from PDF Document via an InputStream (such as a URL), and a Rectangle object to 
     *      determine the area from which to extract the text.
     * @param is  an InputStream from which to read (likely a URL)
     * @param rect  specifies the bounded area that you want to parse from
     * @param n  the page number that you want to extract from
     */
    public static String pdftoText(InputStream is, Rectangle rect, int n) {
	PDFParser parser;
	String parsedText = null;
	PDFTextStripperByArea pdfStripper = null;
	PDDocument pdDoc = null;
	COSDocument cosDoc = null;

	// try to create a new PDFParser
	try {
	    parser = new PDFParser(is);
	} catch (IOException e) {
	    System.err.println("Unable to open PDF Parser. " + e.getMessage());
	    return null;
	}
	// try to parse the PDF document
	try {
	    parser.parse();
	    cosDoc = parser.getDocument();
	    pdfStripper = new PDFTextStripperByArea();
	    pdDoc = new PDDocument(cosDoc);

	    List allPages = pdDoc.getDocumentCatalog().getAllPages(); // List holds a collection of PDPages
	    	    
	    PDPage firstPage = (PDPage)allPages.get(n); // get the page number we want..?
	    
	    pdfStripper.addRegion("class1",rect);  // determines region by using a Rectangle object
	    pdfStripper.extractRegions(firstPage);  // extract the text from the region of the page we want
	    parsedText = pdfStripper.getTextForRegion("class1"); 
	    //System.out.println("stripper by area is working");
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

}
   

   

