# lab10, Hunter Buckhorn, Arda Ungun

* Issues: Scrape Engineering Major Courses. Add another section to the GUI to include all of the Engineering courses by major and the ability to search for classes by major, or by individual class/series. Will most likely need to write a new scraper or modify the existing scraper to pull the major courses data from GEAR since this data is formatted differently (need to reference the pdfbox API), will also need to add new sections, listeners and GUI logic to the GUI class. Estimated 300 points. 


* Issues: Implement Serializable to archive GEAR scrape results. Currently, the program scrapes GEAR each time it runs. Have the GEAR-scraper class implement serializable and add in a way to check to see if you can get the gear scraping results from the serialized data from the version of GEAR being scraped, or if GEAR has to be scraped if the serialized data doesnt currently exist. Create a data structure to save the different years of GEAR so that they can be referenced in the future. Estimated 200 points. 
