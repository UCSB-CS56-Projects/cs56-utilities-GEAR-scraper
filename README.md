cs-utilities-GEAR-scraper
=========================

Scrape the UCSB Gear Catalog to form lists of structured information (e.g. course numbers that meet certain GE requirements.)

project history
===============
```
 W14 | andrewberls 5pm | alanbuzdar | Scrape the UCSB Gear Catalog to form lists of structured information
```

```
W15  | hunterbuckhorn
```

The GEAR catalog is here, as a PDF:

http://engineering.ucsb.edu/current_undergraduates/pdf/GEAR-15-16.pdf

The library http://pdfbox.apache.org/ provides tools in Java to get information out of PDF files.



OVERVIEW
========




CLASS DOCS
==========

CoEgeCourse.java -------------------------------------------------------------------------------------------------------
    Used to contain information about each GE in GEAR

public interface CoEgeCourse {
    public String getDeptInGear();     // department offering the course, exactly as formatted in GEAR
                                       // e.g. Anthropology
    public String getDeptCode();       // department code (e.g. ANTH) as in GOLD (you'll have to translate to get that)
    public String getCourseNum();      // e.g. 118B
    public boolean isD();              // its on the area D list
    public boolean isE();              // its on the area E list
    public boolean isF();              // its on the area F list
    public boolean isG();              // its on the area G list
    public boolean isWriting();        // This course applies toward the writing requirement.
    public boolean isAmHistInst();     // This course applies toward the American History & Institutions requirement.
    public boolean isEthnicity();      //  This course applies toward the ethnicity requirement.
    public boolean isEuroTrad();       // This course applies toward the European Traditions requirement.
}


GECourse.java ----------------------------------------------------------------------------------------------------------
    Implements the CoEgeCourse.java interface
    Contains all of the definitions for the CoEgeCourse.java interface


PDFTextParser.java -----------------------------------------------------------------------------------------------------
    Processes the PDFs into text with the Apache PDF box API

    API DOCS: http://pdfbox.apache.org/docs/1.8.10/javadocs/

    class adopted from http://thottingal.in/blog/2009/06/24/pdfbox-extract-text-from-pdf/

public class PDFTextParser {

    public static String pdftoText(String fileName) {
        uses a PDFParser object from the API to handle parsing the pdf document
        uses a pdDoc, cosDoc and File object to to manipulate the parsed data.
        checks to see if the File is valid
        creates a new PDFParser by passing the file as a FileInputStream
        parses the PDF document
            creates a PDFTextStripper object and creates a cosDoc and then generates a pdDoc from the cosDoc
            the cosDoc is then passed to the PDFTextStripper object and creates a String, parsedText
        closes cosDoc and pdDoc and returns the String parsedText
    }


    public static String pdftoText(BufferedInputStream is, int page) {
        creates a PDFParser from the BufferedInputStream is
        creates a PDFTextStripperByArea object
        parses the PDF document
        creates a pdDoc with the PDFParser object
        calls method setSortByPosition(true) to sort the pdf based on a specific area
        creates two Rectangle objects to use for parsing by area
        creates a List of PDPages and creates a PDFPage object, firstPage
        adds two Rectangles to PDFTextStripper object using addRegion method
        the PDFTextStripper object calls getTextForRegion with each Rectangle region
        the result is a String, parsedText
        closes pdDoc and returns the String parsedText
    }
}



GEAR_scraper.java ------------------------------------------------------------------------------------------------------
    Uses the PDFTextParser class to scrape data from GEAR and create an ArrayList of GECourse objects
    the default url for the GEAR pdf to be scraped is stored as an instance variable in this class, along with the pages
    of the GE courses in the pdf to be parsed (we only want to parse the pages of the pdf with GE class listings)



public GEAR_scraper():
        for each page in the default range of the pdf on the default GEAR pdf url
        creates a PDFTextParser object and converts the page of the pdf into a String
        adds the String of parsed text to the ArrayList<String> textToParse


public GEAR_scraper(URL url, int startPage, int endPage):
        this constructor is essential the same as the one above, except with the ability to pass in the
        startPage value and the endPage values used in the for loop


private boolean isCourse(String s):
        helper function for checking if a line of text is a course name
        contains a very very long boolean logic statement that checks for substrings that determine if the line is a
        course name or not


public ArrayList<GECourse> createArrayList():
        creates the ArrayList<GECourses> by iterating through the textToParse ArrayList<String> and building a GECourse
        for every line of each page of the pdf text and adding it.



GEAR_scraper_GUI.java --------------------------------------------------------------------------------------------------
    A graphical interface to interact with the GEAR scraper and search for courses based on name and GE course area




APACHE PDFBOX API DOCS
======================
Can be found at http://pdfbox.apache.org/docs/1.8.10/javadocs/

API packages used:
------------------
org.apache.pdfbox.cos.COSDocument;
org.apache.pdfbox.pdfparser.PDFParser;
org.apache.pdfbox.pdmodel.PDDocument;
org.apache.pdfbox.util.PDFTextStripper;
org.apache.pdfbox.util.PDFTextStripperByArea;
org.apache.pdfbox.pdmodel.PDPage;
org.apache.pdfbox.pdmodel.common.PDStream;


The objects and methods used in this project along with a brief description are as follows:

PDFParser
    .parse()
    .getPDDocument()

PDDocument
    .close()
    .getDocumentCatalog().getAllPages()



COSDocument
    .close()

PDFTextStripper
    .getText()

PDFTextStripperByArea
    .setSortByPosition()
    .addRegion()
    .extractRegions()
    .getTextForRegion





PDF LAYOUT
==========




