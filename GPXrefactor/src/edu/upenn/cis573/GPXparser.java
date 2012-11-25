package edu.upenn.cis573;


/**
 * Contains a static method to parse a well-formed GPX file and create
 * a GPXobject.
 */

import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

public class GPXparser {

    /**
     * This method takes a file in GPX format and converts it into a GPXobject,
     * using the GPXformat to determine what needs to be read.
     * It assumes that the file has already been checked and that the format
     * is valid.
     *
     * @param filename The file to be read
     * @param format A GPXformat object that would be created as the result of calling GPXchecker.checkFormat
     * @returns a GPXobject that holds all the data in the file
     */
    public static GPXobject parse(String filename, GPXformat format) {
		// make sure the format is valid before proceeding
		if (format.isValid() == false) return null;
	
		// return value
		GPXobject object = null;
	
		try {
		    // create a scanner to read the file and set its delimeter
		    Scanner in = new Scanner(new File(filename));
		    in.useDelimiter(">");
	
		    // get the list of tags
		    ArrayList tags = format.tags();
		    // index for reading the list
		    int index = 0;
	
		    // read the <gpx> tag
		    in.next();
		    index++;
	
		    // next is <time>
		    in.next();
		    index++;
		    
		    // now the content and </time>
		    String objtime = in.next();
		    index++;
		    //System.out.println("TIME: " + objtime);
		    objtime = objtime.substring(0, objtime.indexOf('<'));
		    //System.out.println("time: " + objtime);
		
		    // now we're on <trk>
		    in.next();
		    index++;
		    
		    String name = null;
		    // next is <name> but it's optional
		    if (tags.get(index).equals("<name")) {
		    	in.next();
		    	index++;
		    
		    	// then the content and </name>
		    	name = in.next();
		    	index++;
		    	//System.out.println("NAME: " + name);
		    	name = name.substring(0, name.indexOf('<'));
		    	//System.out.println("name: " + name);
		    }
	
		    // to hold the GPXtrk objects
		    ArrayList trksegs = new ArrayList();


		    // now we have some number of <trkseg> tags
		    while (tags.get(index++).equals("<trkseg")) {
				// consume the token
				in.next();
				
				// to hold the GPXtrkpt objects
				ArrayList trkpts = new ArrayList();
		
				// now we have some number of <trkpt> tags
				while (tags.get(index++).equals("<trkpt")) {
				    // get the latitude and longitude
				    String latlon = in.next().trim();
				    //System.out.println("LATLON: " + latlon);
		
				    // the latitude will be something like lat="xx.xxxx"
				    String lat = latlon.split(" ")[1];
				    lat = lat.substring(5, lat.length()-1);
				    //System.out.println("lat: " + lat);
		
				    // same for longitude
				    String lon = latlon.split(" ")[2];
				    lon = lon.substring(5, lon.length()-1);
				    //System.out.println("lon: " + lon);
				    
		
				    // read <ele>
				    in.next();
				    index++;
		
				    // read elevation and </ele>
				    String ele = in.next();
				    //System.out.println("ELE: " + ele);
				    // the elevation will be a number then the </ele tag
				    ele = ele.substring(0, ele.indexOf('<'));
				    //System.out.println("ele: " + ele);
				    index++;
		
				    // read <time>
				    in.next();
				    index++;
		
				    // read time and </time>
				    String time = in.next();
				    //System.out.println("TIME: " + time);
				    // the time will be the time string followed by </time
				    time = time.substring(0, time.indexOf('<'));
				    //System.out.println("time: " + time);
				    index++;
		
				    // read </trkpt>
				    in.next();
				    index++;
		
				    // create a GPXtrkpt object
				    GPXtrkpt trkpt = new GPXtrkpt(Double.parseDouble(lat), Double.parseDouble(lon), Double.parseDouble(ele), time);
				    
				    // put it into the list
				    trkpts.add(trkpt);
				}
		
				// read </trkseg>
				in.next();
		
				// create a GPXtrkseg object
				GPXtrkseg trkseg = new GPXtrkseg(trkpts);
		
				// add it to the list
				trksegs.add(trkseg);
		
			}
		    
		    // don't care about </trk> and </gpx>
		    in.next();
		    in.next();
	
		    // create the GPXobject
		    object = new GPXobject(objtime, name, trksegs);
	
		}
		catch (Exception e) {
		    e.printStackTrace();
	
		}
		
	
		return object;
    }

}