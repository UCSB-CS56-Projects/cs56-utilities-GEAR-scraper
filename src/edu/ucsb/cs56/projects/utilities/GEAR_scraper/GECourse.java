package edu.ucsb.cs56.projects.utilities.GEAR_scraper;

/**
 * CoEGECourse Interface
 * @version CS56, Winter 2014
 * 
 */

public class GECourse implements CoEgeCourse{
    private String deptInGear;
    private String deptCode;
    private String courseNum;
    private boolean isD;
    private boolean isE;
    private boolean isF;
    private boolean isG;
    private boolean isWriting;
    private boolean isAmHistInst;
    private boolean isEuroTrad;
    private boolean isEthnicity;

    public GECourse(String line,String area){
	if (area.equals("D"))
	    isD = true;
	if (area.equals("E"))
	    isE = true;
	if (area.equals("F"))
	    isF = true;
	if (area.equals("G"))
	    isG = true;
	if (line.contains("*"))
	    isWriting = true;
	if (line.contains("@"))
	    isAmHistInst = true;
	if (line.contains("&")  && !(line.contains("Exercise"))  && !(line.contains("Hearing Sciences")) && !(line.contains("Statistics")))
	    isEthnicity = true;
	if (line.contains("^"))
	    isEuroTrad = true;
	int p = 0;
	deptInGear = "";
	for(String s: line.split("\\s+")){
		if(( s.contains("0") || s.contains("1") || s.contains("2") || s.contains("3") || s.contains("4") || s.contains("5") || s.contains("6") || s.contains("7") || s.contains("8") || s.contains("9"))&& (p==0)){
		    courseNum = s;
		    p = 1;
		}
		else if(p==0 & !(s.contains("*")) & !(s.contains("&")) & !(s.contains("@")) & !(s.contains("^")) )
		    deptInGear+=(" "+s);		    

	    }
	setDeptCode(line);


    }

    public String toString(){
	return((deptCode+" "+courseNum).trim());
    }
    public String getDeptInGear(){return deptInGear;} // department offering the course, exactly as formatted in GEAR
                            // e.g. Anthropology
	public String getDeptCode(){return deptCode;}  // department code (){e.g. ANTH} as in GOLD (){you'll have to translate to get that}
    public String getCourseNum(){return courseNum;} // e.g. 118B 
    public boolean isD(){return isD;} // its on the area D list
    public boolean isE(){return isE;} // its on the area E list
    public boolean isF(){return isF;} // its on the area F list
    public boolean isG(){return isG;} // its on the area G list
    public boolean isWriting(){return isWriting;} // This course applies toward the writing requirement. 
    public boolean isAmHistInst(){return isAmHistInst;} // This course applies toward the American History & Institutions requirement.
    public boolean isEthnicity(){return isEthnicity;} //  This course applies toward the ethnicity requirement.
    public boolean isEuroTrad(){return isEuroTrad;} // This course applies toward the European Traditions requirement.

    //helper function to set deptCode
    private void setDeptCode(String line){

	if(line.contains("Anthropology"))
	    deptCode = "ANTH";
	else if(line.contains("Art") && !(line.contains("History")))
	    deptCode = "ART";
	else if(line.contains("Art History"))
	    deptCode = "ARTHI";
	else if(line.contains("Art Studio"))
	    deptCode = "ARTST";
	else if(line.contains("Asian American"))
	    deptCode =  "AS AM";
	else if(line.contains("Astronomy"))
	    deptCode =  "ASTRO";
	else if(line.contains("Biology"))
	    deptCode = "BIOL";
	else if(line.contains("Biomolecular"))
	    deptCode =  "BMSE";
	else if(line.contains("Black Studies"))
	    deptCode =  "BL ST";
	else if(line.contains("Chemical Engineering"))
	    deptCode =  "CH E";
	else if(line.contains("Chicano"))
	    deptCode =  "CH ST";
	else if(line.contains("Chinese"))
	    deptCode =  "CHIN";
	else if(line.contains("Classics"))
	    deptCode =  "CLASS";
	else if(line.contains("Communication "))
	    deptCode =  "COMM";
	else if(line.contains("Comparative Literature"))
	    deptCode =  "C LIT";
	else if(line.contains("Computer Science"))
	    deptCode =  "CMPSCI";
	else if(line.contains("Counseling "))
	    deptCode =  "CNCSP";
	else if(line.contains("Dance "))
	    deptCode =  "DANCE";
	else if(line.contains("Dynamical"))
	    deptCode =  "DYNMNS";
	else if(line.contains("Earth Science"))
	    deptCode =  "EARTH";
	else if(line.contains("East Asian Cultural Studies"))
	    deptCode =  "EACS";
	else if(line.contains("Ecology "))
	    deptCode =  "EEMB";
	else if(line.contains("Economics "))
	    deptCode =  "ECON";
	else if(line.contains("Education "))
	    deptCode =  "ED";
	else if(line.contains("Electrical Computer Engineering"))
	    deptCode =  "ECE";
	else if(line.contains("Engineering Sciences"))
	    deptCode =  "ENGR";
	else if(line.contains("English "))
	    deptCode =  "ENGL";
	else if(line.contains("Environmental Science"))
	    deptCode =  "ESM";
	else if(line.contains("Environmental Studies"))
	    deptCode =  "ENV S";
	else if(line.contains("Exercise & Sport "))
	    deptCode =  "ESS";
	else if(line.contains("Exercise Sport "))
	    deptCode =  "ES";
	else if(line.contains("Feminist "))
	    deptCode =  "FEMST";
	else if(line.contains("Film Studies"))
	    deptCode =  "FLMST";
	else if(line.contains("French "))
	    deptCode =  "FR";
	else if(line.contains("General Studies"))
	    deptCode =  "GEN S";
	else if(line.contains("Geography "))
	    deptCode =  "GEO";
	else if(line.contains("German "))
	    deptCode =  "GER";
	else if(line.contains("Global Peace and Security"))
	    deptCode =  "GPS";
	else if(line.contains("Global Studies"))
	    deptCode =  "GLOBL";
	else if(line.contains("Greek"))
	    deptCode =  "GREEK";
	else if(line.contains("Hebrew"))
	    deptCode =  "HEB";
	else if(line.contains("History") && !(line.contains("Music")))
	    deptCode =  "HIST";
	else if(line.contains("Interdisciplanary"))
	    deptCode =  "INT";
	else if(line.contains("Italian"))
	    deptCode =  "ITAL";
	else if(line.contains("Japanese"))
	    deptCode =  "JAPAN";
	else if(line.contains("Korean"))
	    deptCode =  "KOR";
	else if(line.contains("Latin")&&!(line.contains("Latin American")))
	    deptCode =  "LATIN";
	else if(line.contains("Latin American and Iberian"))
	    deptCode =  "LAIS";
	else if(line.contains("Linguistics"))
	    deptCode =  "LING";
	else if(line.contains("Literature (Creative Studies)"))
	    deptCode =  "LIT";
	else if(line.contains("Marine Science"))
	    deptCode =  "MARSC";
	else if(line.contains("Materials"))
	    deptCode =  "MATRL";
	else if(line.contains("Mathematics"))
	    deptCode =  "MATH";
	else if(line.contains("Mechanical Engineering"))
	    deptCode =  "ME";
	else if(line.contains("Media Arts and Technology"))
	    deptCode =  "MAT";
	else if(line.contains("Medieval Studies"))
	    deptCode =  "ME ST";
	else if(line.contains("Middle East Studies"))
	    deptCode =  "MES";
	else if(line.contains("Military Science"))
	    deptCode =  "MS";
	else if(line.contains("Molecular, Cellular"))
	    deptCode =  "MCDB";
	else if(line.contains("Music") && !(line.contains("Performance Laboratories")))
	    deptCode =  "MUS";
	else if(line.contains("Music Performance"))
	    deptCode =  "MUS A";
	else if(line.contains("Philosophy"))
	    deptCode =  "PHIL";
	else if(line.contains("Physics"))
	    deptCode =  "PHYS";
	else if(line.contains("Political Science"))
	    deptCode =  "POL S";
	else if(line.contains("Portuguese"))
	    deptCode =  "PORT";
	else if(line.contains("Psychology"))
	    deptCode =  "PSY";
	else if(line.contains("Religious Studies"))
	    deptCode =  "RG ST";
	else if(line.contains("Renaissance Studies"))
	    deptCode =  "RENST";
	else if(line.contains("Slavic"))
	    deptCode =  "SLAV";
	else if(line.contains("Sociology"))
	    deptCode =  "SOC";
	else if(line.contains("Spanish"))
	    deptCode =  "SPAN";
	else if(line.contains("Hearing Sciences"))
	    deptCode =  "SHS";
	else if(line.contains("Statistics"))
	    deptCode =  "PSTAT";
	else if(line.contains("Technology Management"))
	    deptCode =  "TMP";
	else if(line.contains("Theater"))
	    deptCode =  "THTR";
	else if(line.contains("Writing"))
	    deptCode =  "WRIT";
	else
	    deptCode = deptInGear ;

	
    }



}