package edu.upenn.cis573;

/**
 * Contains a static method for checking whether a file matches the 
 * GPX format specification.
 *
 * Note that, for purposes of simplicity, this does not perform the check
 * using the REAL specification, but rather uses the one provided for this
 * particular assignment!
 */

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GPXchecker {
            
    /*
     * Checks the format of the GPX file specified by the filename.
     * See the documentation for the expected file format.
     *
     * @param filename The GPX file to be checked.
     * @return a GPXformat object that indicates whether this file is valid; if so, it contains an ArrayList of all the tags that have been encountered (to make parsing easier); if not, it contains an error message.
     */
    public static GPXformat checkFormat(String filename) {
		// the return value
		GPXformat format = new GPXformat();
	
		try {            
		    // now go through and see what we've got in the list
		    ArrayList<String> tags = createTagsListFromFile(filename);
		    // index into the list
		    int index = checkTagsBeforeTrkSeg(tags);
		    index = checkTagsOfTrkSeq(tags,index);
            index = checkTagsAfterTrkSeq(tags,index);
            
		    // if we made it here, everything is okay
		    format.setValid(true);
		    format.setMessage("Format is valid.");
		    format.setTags(tags);
	
		}
		catch (Exception e) {
		    //e.printStackTrace();
		    // indicate that this is not a valid file
		    format.setValid(false);
		    format.setMessage(e.getMessage());
		}
	
		return format;
    }
    
    /**
     * Creates a tags array list for further processing.
     * @param filename the name of the file
     * @return the tags array list.
     * @throws FileNotFoundException 
     */
    private static ArrayList<String>  createTagsListFromFile(String filename) throws FileNotFoundException
    {
        Scanner in = new Scanner(new File(filename));
        in.useDelimiter(">");

        // hold all the tags we find, in sequence
        ArrayList<String> tags = new ArrayList<String>();

        // loop through each line and find all the tags, then add them to the list
        while (in.hasNext()) {
        // this is a line ending with ">" (which is ignored)
            String tagLine = in.next().trim();
            // this is the tag we're looking for
            String tag = null;

            if (tagLine.startsWith("<")) 
            {
                // if it starts with a "<", and has a blank space in the line, then there's more stuff but we'll just ignore it
                if (tagLine.contains(" ")) 
                {
                    tag = tagLine.substring(0, tagLine.indexOf(' '));
                }
                // otherwise we want the whole line
                else 
                {
                    tag = tagLine;
                }
            }else{
                // if it doesn't start with a "<", we can ignore whatever comes before it
                tag = tagLine.substring(tagLine.indexOf("<"));
            }
            //System.out.println(tag);
            tags.add(tag);
        }
        return tags;
    }
    
    /**
     * Check Tags before the first TrkSeq
     * @param tags
     * @return
     * @throws Exception 
     */
    private static int checkTagsBeforeTrkSeg(ArrayList<String> tags) throws Exception
    {
        int index=0;
        // the first tag must be <gpx>
        checkTag(tags.get(index++),"<gpx");
        checkTag(tags.get(index++),"<time");
        checkTag(tags.get(index++),"</time");
        checkTag(tags.get(index++),"<trk");           
        // the <name> tag is optional
        if (tags.get(index).equals("<name")) {
            index++;
            // now we should have </name>
            checkTag(tags.get(index++),"</name");
        }
        return index;
    }
    
    
    /**
     * Check tags of TrkSeq.
     * @param tags
     * @param index
     * @return
     * @throws Exception 
     */
    private static int checkTagsOfTrkSeq(ArrayList<String> tags, int index) throws Exception
    {
        // now we have some number of <trkseg>
        String tag = (String)(tags.get(index++));
        while (tag.equals("<trkseg")) 
        {
            // now we have some number of <trkpt>
            tag = (String)(tags.get(index++));
            while (tag.equals("<trkpt")) 
            {
                checkTag(tags.get(index++),"<ele") ;
                checkTag(tags.get(index++),"</ele") ;
                checkTag(tags.get(index++),"<time") ;
                checkTag(tags.get(index++),"</time") ;
                checkTag(tags.get(index++),"</trkpt") ;
                // consume the next tag
                tag = (String)(tags.get(index++));
            }
            checkTag(tag,"</trkseg") ;
            // consume the next tag
            tag = (String)(tags.get(index++));
        }
        index--;
        return index;
    }
    
    /**
     * Check tags 
     * @param tags
     * @param index
     * @return
     * @throws Exception 
     */
    private static int checkTagsAfterTrkSeq(ArrayList<String>tags, int index) throws Exception
    {        
        // if we get here, then we should be on </trk>
        checkTag(tags.get(index++),"</trk");      
        // last but not least: </gpx>
        checkTag(tags.get(index++),"</gpx");
        return index;
    }
    
    /**
     * Check if the current tag is valid.
     * @param currentTag
     * @param matchingTag
     * @throws Exception 
     */
    private static void checkTag(final String currentTag, final String matchingTag) throws Exception
    {
        if (currentTag.equals(matchingTag) == false) 
            throw new Exception("Format error! Expected "+matchingTag+"> tag");
    }
}