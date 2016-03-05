package edu.ucsb.cs56.projects.utilities.GEAR_scraper;

import java.net.URL;
import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
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
 */


public class GEAR_scraper {

    BufferedInputStream is;
    String defaultURL = "http://engineering.ucsb.edu/current_undergraduates/pdf/GEAR-15-16.pdf";
    ArrayList<String> textToParse = new ArrayList<String>();



    // default constructor - uses '15-'16 GEAR, pages 14 through 22

    public GEAR_scraper() {
        //parse one page at a time
        try {
            int startPage = 14;
            for (int i = 0; i < 9; i++) {
                is = new BufferedInputStream((new URL(defaultURL)).openStream());
                PDFTextParser myTester = new PDFTextParser();
                String x = myTester.pdftoText(is, startPage + i);
                textToParse.add(x);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Constructor - uses PDFParser to create Gear_scraper object.
     *
     * @param url       - a URL object for the gear pdf you'd like to scrape
     * @param startPage - the page the GE catalog starts at, note: GEAR has its own page numbers physically printed on the pages. these are the wrong ones, you want the page # of the pdf document, not the catalog.
     * @see PDFParser
     */
    public GEAR_scraper(URL url, int startPage, int endPage) {
        try {

            for (int i = startPage; i < endPage + 1; i++) {
                BufferedInputStream is = new BufferedInputStream(url.openStream());
                PDFTextParser myTester = new PDFTextParser();
                String x = myTester.pdftoText(is, i);
                textToParse.add(x);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Get the current GEAR default url, returns the default url as a string
     * @return String of the current default url for the GEAR pdf
     *
     */
    public String getCurrentDefualtURL() {
        return defaultURL;
    }

    public String getCurrentYears() {
        int len = defaultURL.length();
        String GEARyear = defaultURL.substring(len - 14, len - 4);
        return GEARyear;
    }

    /**
     * just a helper function that checks if the line of text is a course
     *
     * @param the line of text you want to check
     * @return true if it is a course, false if it isn't
     */
    private boolean isCourse(String s) {
        if ((s.contains("0") || s.contains("1") || s.contains("2") || s.contains("3") || s.contains("4") || s.contains("5") || s.contains("6") || s.contains("7") || s.contains("8") || s.contains("9")) && (s.contains("Anthropology")) || s.contains("Art") || s.contains("Science") || s.contains("Exercise") || s.contains("Astronomy") || s.contains("Biology") || s.contains("Biomolecular") || s.contains("Black Studies") || s.contains("Chemical") || s.contains("Engineering") || s.contains("French") || s.contains("Studies") || s.contains("Chinese") || s.contains("Classic") || s.contains("Communication") || s.contains("Literature") || s.contains("Counseling") || s.contains("Dance") || s.contains("Dynamical") || s.contains("Ecology") || s.contains("Economics") || s.contains("Education") || s.contains("German") || s.contains("English") || s.contains("Geography") || s.contains("Global") || s.contains("Greek") || s.contains("Hebrew") || s.contains("History") || s.contains("Interdisciplinary") || s.contains("Italian") || s.contains("Japanese") || s.contains("Latin") || s.contains("Linguistics") || s.contains("Literature") || s.contains("Materials") || s.contains("Math") || s.contains("Molecular") || s.contains("Music") || s.contains("Philosophy") || s.contains("Physics") || s.contains("Portuguese") || s.contains("Psychology") || s.contains("Slavic") || s.contains("Sociology") || s.contains("Spanish") || s.contains("Statistics") || s.contains("Technology") || s.contains("Theater") || s.contains("Writing"))
            return true;
        return false;

    }

    /**
     * Creates an arraylist of the course objects and returns it.
     *
     * @return an ArrayList of GECourse objects from the text parsed in the constructor.
     * @see GECourse
     */
    public ArrayList<GECourse> createArrayList() {
        String area = "junk";
        ArrayList<GECourse> x = new ArrayList<GECourse>();
        for (String page : textToParse) {
            //line by line
            for (String s : page.split("\n")) {
                if (s.contains("Area D"))
                    area = "D";
                else if (s.contains("perspective on world cultures") || s.contains("Area E") || s.contains("cultures through the study of human history"))
                    area = "E";
                else if (s.contains("Area F") || s.contains("Area f") || s.contains("F: Arts"))
                    area = "F";
                else if (s.contains("develop an appreciation of literature") || s.contains("Area G") || s.contains("learn to analyza"))
                    area = "G";
                else if (s.contains("Special Subject Area"))
                    area = "S";
                else if (s.contains("Area H"))
                    area = "H";
                else if (isCourse(s) && !(area.equals("junk")) && !s.contains("riting 2"))
                    x.add(new GECourse(s, area));
            }
        }


        return x;
    }

}
