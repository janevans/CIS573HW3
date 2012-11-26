package edu.upenn.cis573;

import java.util.ArrayList;

/**
 * Represents all the data in a GPX file.
 */

public class GPXobject {

	// name of this object
	private String name;
	// time at which it was created
	private String time;
    // holds all the information about the track
    private GPXtrk trk;
    // list of all the track segments
    private ArrayList trksegs;
    // string buffer used for printing
    private StringBuffer out;

    public GPXobject(String time, String name, ArrayList trksegs) {
    	this.time = time;
    	this.trk = new GPXtrk(name, trksegs, this);
    }

    /* Accessors */
    public GPXtrk trk() { return trk; }
    public String name() { return name; }
    public String time() { return time; }


    /**
     * This method writes out the XML for this GPX object.
     * It should primarily be used for debugging purposes.
     *
     * @return a well-formatted (according to the GPX file specification provided for this assignment) string representation of the object
     */
    public String toString() {

		out = new StringBuffer();
	
		// always start with <gpx>
		out.append("<gpx>\n");
		
		out.append("<time>"+time+"</time>\n");		
		
		out.append("\t<trk>\n");
	
		out.append("\t\t<name>" + trk.name() + "</name>\n");
	
		// get all the trkseg obejcts
		GPXtrkseg trksegs[] = trk.trksegs();
	
		if (trksegs != null) {    
			out =   appendTrkSeq(out,trksegs);
		}
	
		out.append("\t</trk>\n");
	
		out.append("</gpx>\n");
	
		return out.toString();

    }
    
    private StringBuffer appendTrkSeq(StringBuffer out,GPXtrkseg trksegs[])
    {
        // iterate over the trksegs
        for (int i = 0; i < trksegs.length; i++) {
            out.append("\t\t<trkseg>\n");
            // get all the trkpt objects
            GPXtrkpt trkpts[] = trksegs[i].trkpts();      
            out = appendTrkpt(out,trkpts);
            out.append("\t\t</trkseg>\n");

         }
        return out;
    }
    
    private StringBuffer appendTrkpt(StringBuffer out,GPXtrkpt trkpts[])
    {
        // iterate over the trkpts
        for (int j = 0; j < trkpts.length; j++) 
        {
            out.append("\t\t\t<trkpt lat=\"" + trkpts[j].latitude() + "\" lon=\"" + trkpts[j].longitude() + "\">\n");
            out.append("\t\t\t\t<ele>" + trkpts[j].elevation() + "</ele>\n");
            out.append("\t\t\t\t<time>" + trkpts[j].timeString() + "</time>\n");
            out.append("\t\t\t</trkpt>\n");

        }
        return out;
    }

    public double calculateBearing(double lat1, double lon1, double lat2, double lon2) {
		double y = Math.sin(lon2-lon1) * Math.cos(lat2);
		double x = Math.cos(lat1)*Math.sin(lat2) - Math.sin(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1);
				
		// return the bearing (after converting to degrees)
		return Math.atan2(y, x) * 360.0 / (2 * Math.PI);

    }
    
}