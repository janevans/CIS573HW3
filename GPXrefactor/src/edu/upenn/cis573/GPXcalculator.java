package edu.upenn.cis573;

/**
 * This class contains static methods used for performing statistics and
 * other calculations on GPX data.
*/


public class GPXcalculator {
    /**
     * Changes R to EARTH_RADIUS, increase readability.
     */
    public static final int EARTH_RADIUS = 6371; // radius of the earth in km
    
    /**
     * Changes t to time, increase readability.
     */
    long time;
    /**
     * 1 Change o to obj, more readability.
     * 2 
     * @param obj
     * @return 
     */
    public long calculateElapsedTime(Object obj) {
    	if (obj instanceof GPXtrk) 
            return calculateTimeForTrk((GPXtrk)obj);
    	else if (obj instanceof GPXtrkseg) 
        {
    		return GPXtrkseg.time((GPXtrkseg)obj);
    	}
        else
        { 
            return -1;
        }
    }


    /**
     * Calculates the elapsed time for all segments in the track.
     * Note that it does not include the time <b>between</b> segments.
     *
     * @param trk The track for which to calculate the elapsed time.
     * @return the elapsed time in seconds; -1 if the track object is null
     */
    private long calculateTimeForTrk(GPXtrk trk) {
	
		if (trk == null) return -1;
	
		time = 0;
	
		// iterate over all the segments and calculate the time for each
		GPXtrkseg trksegs[] = trk.trksegs();
	
		for (int i = 0; i < trksegs.length; i++) {
		    // keep a running total of the time for each segment
		    time += calculateElapsedTime(trksegs[i]);
		}
		
		return time;
    }


    /**
     * Calculates the distance traveled over the given segment by returning
     * the sum of the distances between successive track points.
     * The distance takes into account latitude, longitude, elevation, and curvature of the earth.
     * To account for the curvature of the earth, the spherical law of cosines
     * is used, based on http://www.movable-type.co.uk/scripts/latlong.html
     *
     * @param trkseg The track segment for which to calculate the distance traveled.
     * @return the total distance in meters
     */
    public double getDistanceInTrkseg(GPXtrkseg trkseg) {

double totalDistance = 0;
		
		// iterate over all the trkpts
		GPXtrkpt pts[] = trkseg.trkpts();
	
		for (int j = 0; j < pts.length-1; j++) {
		    
		    // get this point and the next one
		    GPXtrkpt pt1 = pts[j];
		    GPXtrkpt pt2 = pts[j+1];
		    
//		    // convert lat and lon from degrees to radians
//		    double lat1 = pt1.latitude() * 2 * Math.PI / 360.0;
//		    //System.out.println(lat1+" "+convertDegreesToRadians(pt1.latitude()));
//		    double lon1 = pt1.longitude() * 2 * Math.PI / 360.0;
//		    //System.out.println(lon1+" "+convertDegreesToRadians(pt1.longitude()));
//		    double lat2 = pt2.latitude() * 2 * Math.PI / 360.0;
//		    //System.out.println(lat2+" "+convertDegreesToRadians(pt2.latitude()));
//		    double lon2 = pt2.longitude() * 2 * Math.PI / 360.0;
//		   // System.out.println(lon2+" "+convertDegreesToRadians(pt2.longitude()));
//		    
//		    // use the spherical law of cosines to figure out 2D distance
//		    double d = Math.acos(Math.sin(lat1)*Math.sin(lat2) + Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1)) * EARTH_RADIUS;	
//		    //System.out.println("OD"+d);
//		    // now we need to take the change in elevation into account
//		    double ele1 = pt1.elevation();
//		    double ele2 = pt2.elevation();
//		    System.out.println("OD"+d+" oe1 "+ele1+" oe2 "+ele2);
//		    // calculate the 3D distance
//		    double distance = Math.sqrt(d*d + (ele1-ele2)*(ele1-ele2));
//		    System.out.println(distance+" "+doCalculation(pt1, pt2));
//		    // add it to the running total
//		    totalDistance += distance;
//	    
		    totalDistance +=doCalculation(pt1, pt2);
			
		}
		
		return totalDistance;
    }
    
    /**
     * Converts degree to radians.
     * @param degree
     * @return 
     */
    private double convertDegreesToRadians(double degree)
    {
        return degree* 2 * Math.PI / 360.0;
    }
    private double doCalculation(GPXtrkpt point1, GPXtrkpt point2){
    		double distance = 0.0;
    		// convert lat and lon from degrees to radians
		    double lat1 = convertDegreesToRadians(point1.latitude());
		    double lon1 = convertDegreesToRadians(point1.longitude());
		    double lat2 = convertDegreesToRadians(point2.latitude());
		    double lon2 = convertDegreesToRadians(point2.longitude());
		    
		    // use the spherical law of cosines to figure out 2D distance
		    double d = Math.acos(Math.sin(lat1)*Math.sin(lat2) + Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1)) * EARTH_RADIUS;	
		   
		    // now we need to take the change in elevation into account
		    double ele1 = point1.elevation();
		    double ele2 = point2.elevation();
		    System.out.println("ND"+d+" ne1 "+ele1+" ne2 "+ele2);
		    // calculate the 3D distance
		    distance = Math.sqrt(d*d + (ele1-ele2)*(ele1-ele2));
    		return distance;
    }

    /**
     * Calculates the distance traveled over all segments in the specified
     * track by returning the sum of the distances for each track segment.
     *
     * @param trk The track for which to calculate the distance traveled.
     * @return the total distance in meters
     */
    public double calculateDistanceTraveled(GPXtrk trk) {
	
		double totalDistance = 0;
	
		// iterate over all the trksegs
		GPXtrkseg segs[] = trk.trksegs();
//		for(int i=0;i<segs.length;i++){
//			totalDistance += getDistanceInTrkseg(segs[i]);
//		}
		for (int i = 0; i < segs.length; i++) {
		    // calculate the distance for each segment

			// iterate over all the trkpts
			//GPXtrkpt pts[] = segs[i].trkpts();
		
//			for (int j = 0; j < pts.length-1; j++) {
//			    
//			    // get this point and the next one
//			    GPXtrkpt pt1 = pts[j];
//			    GPXtrkpt pt2 = pts[j+1];
//			    
//			    // convert lat and lon from degrees to radians
//			    double lat1 = pt1.latitude() * 2 * Math.PI / 360.0;
//			    double lon1 = pt1.longitude() * 2 * Math.PI / 360.0;
//			    double lat2 = pt2.latitude() * 2 * Math.PI / 360.0;
//			    double lon2 = pt2.longitude() * 2 * Math.PI / 360.0;
//			    
//			    // use the spherical law of cosines to figure out 2D distance
//			    double d = Math.acos(Math.sin(lat1)*Math.sin(lat2) + Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1)) *EARTH_RADIUS;	
//			    // now we need to take the change in elevation into account
//			    double ele1 = pt1.elevation();
//			    double ele2 = pt2.elevation();
//			    
//			    // calculate the 3D distance
//			    double distance = Math.sqrt(d*d + (ele1-ele2)*(ele1-ele2));
//			    
//			    // add it to the running total
//			    totalDistance += distance;
//			}
//		}
			totalDistance +=getDistanceInTrkseg(segs[i]);
		}
		return totalDistance;
	

    }

    /**
     * Calculate the average speed over the entire track by determining
     * the distance traveled and the total time for each segment.
     *
     * @param trk The track for which to calculate the average speed
     * @return the average speed in meters per second.
     */
    public double calculateAverageSpeed(GPXtrk trk) {

		long time = 0;

		// iterate over all the segments and calculate the time for each
		GPXtrkseg trksegs[] = trk.trksegs();
	
		for (int i = 0; i < trksegs.length; i++) {
		    // keep a running total of the time for each segment
		    time += calculateElapsedTime(trksegs[i]);
		}		
		
		// figure out the distance in kilometers
		double distance = calculateDistanceTraveled(trk);
	
		// return the average in meters/second
		return distance/time;

    }


    /**
     * Calculate the bearing (direction) from the first point in the
     * track to the last point in the track, using the bearing calculation
     * from http://www.movable-type.co.uk/scripts/latlong.html
     *
     * @param trk The track for which to calculate the overall bearing.
     * @return the bearing in degrees
     */
    public double bearing(GPXtrk trk) {

//		// get the first trkpt in the first trkseg
//		GPXtrkpt start = trk.trkseg(0).trkpt(0);
//		// get the last trkpt in the last trkseg
//		GPXtrkseg lastSeg = trk.trkseg(trk.numSegments()-1);
//		GPXtrkpt end = lastSeg.trkpt(lastSeg.numPoints()-1);
//	
//		// get the points and convert to radians
//		double lat1 = start.lat() * 2 * Math.PI / 360.0;
//		double lon1 = start.lon() * 2 * Math.PI / 360.0;
//		double lat2 = end.lat() * 2 * Math.PI / 360.0;
//		double lon2 = end.lon() * 2 * Math.PI / 360.0;
		
		return trk.bearingDele();
	

    }


    /**
     * Determines which track segment has the fastest average speed.
     *
     * @param trk The track for which to calculate the fastest segment.
     * @return the 0-based index of the fastest segment.
     */
    public int calculateFastestSegment(GPXtrk trk) {

		GPXtrkseg trksegs[] = trk.trksegs();
	
		int fastestSegment = 0;
		double fastestTime = 0;
	
		for (int i = 0; i < trksegs.length; i++) {
			double speed = getDistanceInTrkseg(trksegs[i])/calculateElapsedTime(trksegs[i]);
		    if ( speed>= fastestTime){
		    		fastestTime=speed;
		    		fastestSegment = i;
		    }
		}
	
		return fastestSegment;

    }

}