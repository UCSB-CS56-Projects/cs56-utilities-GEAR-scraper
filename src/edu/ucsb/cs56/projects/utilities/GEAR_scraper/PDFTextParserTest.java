package edu.ucsb.cs56.projects.utilities.GEAR_scraper;
// not sure what correct package will be

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * The test class PDFTextParserTest -- it tests the PDFTextParser class
 *
 * @author Alex Mousavi and Kyle Jorgensen
 * @version CS56, Spring 2011
 * @see PDFTextParser
 */

public class PDFTextParserTest
{
    /**
     *  Test a basic "Hello World!" pdf file
     */
    @Test public void testHelloWorldFile()
    {
	PDFTextParser myTester = new PDFTextParser();
	String expected = "Hello World!\n";

	assertEquals(expected,myTester.pdftoText("pdfs/helloWorld.pdf"));
    }
    
    /**
     *  Tests another pdf file
     */
    @Test public void testHoneybadgers()
    {
	PDFTextParser myTester = new PDFTextParser();
	String expected = "Honey Badgers are dangerous animals.\n";

	assertEquals(expected,myTester.pdftoText("pdfs/Honeybadgers.pdf"));
    }

    
    /**
     *  Tests another pdf file with symbols
     */
    @Test public void testSymbols()
    {
	PDFTextParser myTester = new PDFTextParser();
	String expected = "<<TeStInG &&& syMbols and 0DD tYping !! @wesome.\n";

	assertEquals(expected,myTester.pdftoText("pdfs/symbols.pdf"));
    }

    


}
