package edu.ucsb.cs56.projects.utilities.GEAR_scraper;

import java.net.URL;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;

public class GEAR_scraper_Interface{

    public static void main(String... args){
	GEAR_scraper x = new GEAR_scraper();
	ArrayList<GECourse> p = x.createArrayList();
	for(GECourse s: p)
	    System.out.println(s.getDeptInGear()+" "+s.getCourseNum());
    }




}