/*
 * GPXstatsMain.java
 *
 */

package edu.upenn.cis573;

public class GPXstatsMain {

    public static void main(String[] args) {
	
		// make sure a file is specified
		if (args.length == 0) {
		    System.out.println("Usage: java edu.upenn.cis573.GPXstats.GPXstatsMain [GPX file]");
		    System.exit(1);
		}
	
		// get the name of the file
		String filename = args[0];
		System.out.println("Filename: " + filename);
	
		// check the format
		GPXformat format = GPXchecker.checkFormat(filename);
		if (format.isValid() == false) {
		    System.out.println("ERROR! " + filename + " is not a valid file.");
		    System.out.println(format.message());
		    System.exit(1);
		}
	
		// create an object to represent the input file, using the format that was read in the previous step
		GPXobject object = GPXparser.parse(filename, format);
	
		// print the stats
		long elapsedTime = new GPXcalculator().calculateElapsedTime(object.trk());
	
		// DONE!
		System.out.println("Elapsed time = " + elapsedTime);
    }



}