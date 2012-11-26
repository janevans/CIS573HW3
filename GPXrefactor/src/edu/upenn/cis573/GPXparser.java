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
	
            ValueAndIndex objtimeWrapper = getObjTime(in,index);
            String objtime = (String)objtimeWrapper.value;
            index = objtimeWrapper.index;
		
            ValueAndIndex nameWrapper = getName(in,index,tags);
            String name = (String)nameWrapper.value;
            index = nameWrapper.index;
	
            ArrayList trksegs = (ArrayList)getTrkSeqsList(index,tags,in).value;
	
		    // create the GPXobject
		    object = new GPXobject(objtime, name, trksegs);
	
		}
		catch (Exception e) 
        {
		    e.printStackTrace();	
		}
		
	
		return object;
    }    
    
    /**
     * Gets the obj time.
     * @param in
     * @param index
     * @return 
     */
    private static ValueAndIndex getObjTime(Scanner in, int index)
    {
        ValueAndIndex objtimeWrapper = advanceScanner(in,index,3);
        objtimeWrapper.value = objtimeWrapper.getElementValue();
        return objtimeWrapper;
    }
    
    /**
     * Gets the tag.
     * @param in
     * @param index
     * @param tags
     * @return 
     */
    private static ValueAndIndex getName(Scanner in, int index,ArrayList tags)
    {
        ValueAndIndex nameWrapper = advanceScanner(in,index,1);
        index = nameWrapper.index;
        if (tags.get(index).equals("<name")) {
             nameWrapper = advanceScanner(in,nameWrapper.index,2);
             nameWrapper.value = nameWrapper.getElementValue();	
		 }else{
            nameWrapper.value = null;
        }
        return nameWrapper;
    }
      
    
    /**
     * Advance the scanner and return the tag value.
     * @param in
     * @param index
     * @param times
     * @return 
     */
    private static ValueAndIndex advanceScanner(Scanner in, int index, int times)
    {
        String tagValue="";
        while(times>0)
        {
            tagValue = in.next();
            index++;
            times--;
        }
        return new ValueAndIndex(index,tagValue);
        
    }

    /**
     * Gets TrkSeq List.
     * @param index
     * @param tags
     * @param in
     * @return 
     */
    private static ValueAndIndex getTrkSeqsList(int index, ArrayList tags,Scanner in) 
    {
            // to hold the GPXtrk objects
		    ArrayList trksegs = new ArrayList();

		    // now we have some number of <trkseg> tags
		    while (tags.get(index++).equals("<trkseg")) {
				// consume the token
				in.next();
				
				ValueAndIndex valueAndIndex = getTrkPtsList(index,tags,in);
                index = valueAndIndex.index;
                ArrayList trkpts = (ArrayList)valueAndIndex.value;
				// read </trkseg>
				in.next();
		
				// create a GPXtrkseg object
				GPXtrkseg trkseg = new GPXtrkseg(trkpts);
		
				// add it to the list
				trksegs.add(trkseg);
		
			}
            return new ValueAndIndex(index,trksegs);
    }
    
    /**
     * Gets the latitude or longitude from the latlon String.
     * @param latlon
     * @param lat
     * @return 
     */
    private static String getLatLon(String latlon,boolean lat)
    {
       
        // the latitude will be something like lat="xx.xxxx"
        String result = latlon.split(" ")[lat?1:2];
        result = result.substring(5, result.length()-1);
        return result;
    }
    
    /**
     * Form the TrkPts List.
     * @param index
     * @param tags
     * @param in
     * @return 
     */
    private static ValueAndIndex getTrkPtsList(int index, ArrayList tags,Scanner in) 
    {
        // to hold the GPXtrkpt objects
        ArrayList trkpts = new ArrayList();

        // now we have some number of <trkpt> tags
        while (tags.get(index++).equals("<trkpt")) {
            // get the latitude and longitude
            String latlon = in.next().trim();
            //System.out.println("LATLON: " + latlon);
            String latitude = getLatLon(latlon,true);
            // same for longitude
            String longitude = getLatLon(latlon,false);
            
            //get ele
            ValueAndIndex vai = advanceScanner(in,index,2);           
            String ele = vai.getElementValue();
            index = vai.index++;
           
            //get time
            vai = advanceScanner(in,index,2);
            String time = vai.getElementValue();
            index = vai.index++;

            index = advanceScanner(in,index,1).index;

            // create a GPXtrkpt object
            GPXtrkpt trkpt = new GPXtrkpt(Double.parseDouble(latitude), Double.parseDouble(longitude), Double.parseDouble(ele), time);

            // put it into the list
            trkpts.add(trkpt);
        }
        return new ValueAndIndex(index,trkpts);
    }
    
    
    /**
     * The object that wraps the scanner advancement result.
     */
    private static class ValueAndIndex
    {
        int index;
        Object value;
        public ValueAndIndex(int _index,Object _value)
        {
            index = _index;
            value = _value;
        }
        public String getElementValue()
        {
            String ele = (String)value;
            return ele.substring(0, ele.indexOf('<'));
        }
    }
}