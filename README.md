cs-utilities-GEAR-scraper
=========================

Scrape the UCSB Gear Catalog to form lists of structured information (e.g. course numbers that meet certain GE requirements.)


The GEAR catalog is here, as a PDF:

http://engineering.ucsb.edu/current_undergraduates/pdf/GEAR-12-13.pdf

The library http://pdfbox.apache.org/ provides tools in Java to get information out of PDF files.

The challenge: 

* See if you can use PDFBox to go into the GEAR catalog and pull out a complete list of all the courses listed on pages 10-17.
* For each of them, create an object that implements the CoEGECourse interface shown below.
* Make a class with a method that, given the URL of a GEAR catalog, pulls out all the course, and returns an ArrayList<CoEGECourse>


```
public interface CoEGECourse {
public String getDeptInGear(); // department offering the course, exactly as formatted in GEAR
                            // e.g. Anthropology
public String getDeptCode();  // department code (e.g. ANTH) as in GOLD (you'll have to translate to get that)
public String getCourseNum(); // e.g. 118B 
public boolean isD(); // its on the area D list
public boolean isE(); // its on the area E list
public boolean isF(); // its on the area F list
public boolean isG(); // its on the area G list
public boolean isWriting(); // This course applies toward the writing requirement. 
public boolean isAmHistInst(); // This course applies toward the American History & Institutions requirement.
public boolean isEthnicity(); //  This course applies toward the ethnicity requirement.
public boolean isEuroTrad(); // This course applies toward the European Traditions requirement.
}
```
