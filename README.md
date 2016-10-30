cs-utilities-GEAR-scraper
=========================

Scrape the UCSB Gear Catalog to form lists of structured information (e.g. course numbers that meet certain GE requirements.)

https://engineering.ucsb.edu/sites/engineering.ucsb.edu/files/docs/GEAR-16-17.pdf

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


// Handles the parsing of the PDF Document

PDFParser
    .parse(): parses the stream and populates the COSDocument
    .getPDDocument(): returns the PDDocument that was parsed



// This is the in-memory representation of the PDF document.
// You need to call close() on this object when you are done using it!!
// This class implements the Pageable interface, but since PDFBox version 1.3.0 you should be using
// the PDPageable adapter instead (see PDFBOX-788).

PDDocument
    .close(): Closes the document
    .getDocumentCatalog(): returns a PDDocumentCatalog, which represents tha acroform of a PDF document
        .getAllPages(): returns a list of PDPageNode nad PDPages, PDFs contain a hierarchical structure of PDPages and
                        PDPageNodes to store this information



// This is the in-memory representation of the PDF document.
// You need to call close() on this object when you are done using it!!

COSDocument
    .close(): Closes the document

// This class will take a pdf document and strip out all of the text and ignore the formatting and such.
// Please note; it is up to clients of this class to verify that a specific user has the correct permissions to
// extract text from the PDF document. The basic flow of this process is that we get a document and use a series
// of processXXX() functions that work on smaller and smaller chunks of the page. Eventually, we fully process
// each page and then print it.

PDFTextStripper
    .getText(PDDocument): returns the string of the parsed text file



// This will extract text from a specified region in the PDF.

PDFTextStripperByArea
    .setSortByPosition(boolean newSortByPosition): The order of the text tokens in a PDF file may not be in the same as they appear
                          visually on the screen. For example, a PDF writer may write out all text by font,
                          so all bold or larger text, then make a second pass and write out the normal text.
    .addRegion(String regionName, Rectangle2D rect): Adds a new region to group the text by
    .extractRegions(PDPage page): Process the page to extract the region text.
    .getTextForRegion(String regionName): Get the text for the region, this should be called after extractRegions()





PDF LAYOUT
==========
Helpful links:
Basic intro which is what most of the information below is based on:
    http://resources.infosecinstitute.com/pdf-file-format-basic-structure/

More in depth intro that goes slightly deeper into the structure of pdfs, objects used in pdfs, structures and layout
    https://web.archive.org/web/20141010035745/http://gnupdf.org/Introduction_to_PDF

The actual adobe PDF reference documentation:
    http://www.adobe.com/devnet/pdf/pdf_reference.html



Basic PDF layout

 -------------
|   HEADER    |
 -------------
|             |
|    BODY     |
|             |
 -------------
|'xref' Table |
 -------------
|   Trailer   |
 -------------

General information:
A simple pdf contains 4 parts:
* the header with the PDF version (and an option line to specify if the PDF contains binary data)
* the body, containing a series of objects that are used to hold all of the document's data
* xref Table: a cross-reference table, that specifies the position of the objects
* a trailer, with information about where the document starts
------------------------------------------------------------------------------------------------------------------------
In the body (the object list), there are several types of definitions:

Indirect Object (1 0 obj ... endojb): define a numbered top-level object.
    The first number (1) is the object number,
    the second number (0) is the revision number, which we don't use in this example.

There are 9 types of objects:

* Number: e.g. 3
* Indirect reference (n r R): references an object, e.g. 5 0 R. If the objects doesn't exist this is equivalent
    to the Null object (see below).
* Name (/Name): names are identifiers. If you know Lisp or Scheme, this is similar to the quote special form (e.g. 'ok).
    The initial / introduces the name but isn't part of the name; this is similar to $ in Bash, Perl or PHP.
* Dictionary (<< ... >>): this is a unordered list of (Name,Object) pairs. They are essentially hash tables.
    The Object part can be another Name (e.g. /Type /Font).
* Array ([ x y z ... ]): an ordered list of objects, e.g. [ 0 0 200 200 ].
* String Object ((text)): text. The complete syntax is complex, but for now suffice to say it's text between
    parenthesis, e.g. (Hello, world!).
* Stream (<< /Length ... >> stream ... endstream): embedded data, can be compressed. It starts with a dictionary that
    describes the stream such as its length or the encoding (/Filter) is uses.
* Boolean: true or false.
* Null Object: null.

Representing and manipulating these objects forms the Object layer of the GNUpdf library.


PDFs contain a Cross-reference table
This is a sequential list of objects (#1, #2, etc) offsets, which is where the object is stored in memory relative to
the beginning of the file. The cross reference table allows any given object to be referenced quickly and efficiently.
Each line contains the offset of the object definition, a revision number, and an on/off marker f (free) or n (in use).
If you modify this test document, remember to update all of the offsets, as well as the startxref line, which describes
the offset of the xref section.
ex)
xref
0 6
0000000000 65535 f
0000000010 00000 n
0000000079 00000 n


How to read the PDF file:
PDF files aren't read top to bottom, they are read by accessing data that is stored across the entire file
1) Reads the first line to get the PDF version
2) Goes to the end of the document to get check the %%EOF marker to get the offset of the Cross-Reference table.
3) Jumps to the Cross-Reference table and builds a list of object offsets
4) After the Cross-Reference table, it can read the trailer dictionary which contains the Catalog, which is the start
    of the document. It's specified by an indirect reference to object 1: 1 0 R

5) Now the reader checks the Catalog object. The Catalog object will contain a Pages object which is a tree like data
    structure. It can reference either leaves (pages) of other nodes (which can do the same).
    The Pages object also contains the size of the objects that it points to

6) The Pages object reference its parent object which contains a set of resources that are needed
    to render the page and its content

7)


