package edu.upenn.cis573;

/**
 * Represents a GPX track, which includes a name and some number
 * of GPX track segments.
 */

import java.util.ArrayList;

public class GPXtrk {
    
    // the name for this track
    private String name;
    // a list of track segments
    private ArrayList trksegs;
    // reference to parent GPXobject
    private GPXobject parent;

    public GPXtrk(String name, ArrayList trksegs, GPXobject parent) {
		this.name = name;
		this.trksegs = trksegs;
		this.parent = parent;
    }

    public String name() { return name; }
    public GPXobject parent() { return parent; }
    public double bearingDele(){
    	// get the first trkpt in the first trkseg
    			GPXtrkpt start = trkseg(0).trkpt(0);
    			// get the last trkpt in the last trkseg
    			GPXtrkseg lastSeg = trkseg(numSegments()-1);
    			GPXtrkpt end = lastSeg.trkpt(lastSeg.numPoints()-1);
    		
    			// get the points and convert to radians
    			double lat1 = start.latitude() * 2 * Math.PI / 360.0;
    			double lon1 = start.longitude() * 2 * Math.PI / 360.0;
    			double lat2 = end.latitude() * 2 * Math.PI / 360.0;
    			double lon2 = end.longitude() * 2 * Math.PI / 360.0;
    			return parent.bearing(lat1, lon1, lat2, lon2);
    }

    /**
     * Get the track segment for the given index.
     *
     * @param index The index of the segment to be retrieved.
     * @return The track segment at the provided index. Return null if the index is too large (i.e., is larger than the number of segments)
     */
    public GPXtrkseg trkseg(int index) {
		if (index >= trksegs.size()) return null;
		else return (GPXtrkseg)(trksegs.get(index));
    }

    /**
     * @return the number of segments
     */
    public int numSegments() {
    	return trksegs.size();
    }
    
    /**
     * @return an array containing all of the track segments.
     */
    public GPXtrkseg[] trksegs() {
		GPXtrkseg segs[] = new GPXtrkseg[trksegs.size()];
		for (int i = 0; i < segs.length; i++) segs[i] = (GPXtrkseg)trksegs.get(i);
		return segs;
    }

}