package edu.upenn.cis573;

/**
 * Represents a track point in a GPX file.
 */

public class GPXtrkpt {

    // latitude
    private double lat;
    // longitude
    private double lon;
    // elevation
    private double ele;
    // time is assumed to be in the following format: YYYY-MM-DDThh:mm:ssZ
    private String time;

    public GPXtrkpt(double lat, double lon, double ele, String time) {
		this.lat = lat;
		this.lon = lon;
		this.ele = ele;
		this.time = time;
    }

    /* Accessors */
    public double lat() { return lat; }
    public double lon() { return lon; }
    public double ele() { return ele; }
    public String timeString() { return time; }
    
    public long time() {
		try {

		    int y = Integer.parseInt(time.substring(0, 4));
		    int m = Integer.parseInt(time.substring(5, 7));
		    int d = Integer.parseInt(time.substring(8, 10));
		    int h = Integer.parseInt(time.substring(11, 13));
		    int min = Integer.parseInt(time.substring(14, 16));
		    int s = Integer.parseInt(time.substring(17, 19));
		    
		    /*
		    System.out.println("y is " + y);
		    System.out.println("m is " + m);
		    System.out.println("d is " + d);
		    System.out.println("h is " + h);
		    System.out.println("min is " + min);
		    System.out.println("s is " + s);
		    */
		    
		    // make sure the values are valid
		    if (y < 1970 || m < 1 || m > 12 || d < 1 || d > 31 || h < 0 || h > 23 || min < 0 || min > 59 || s < 0 || s > 59) return -1;
	
		    // if we made it here, we're okay

			long time = 0;
			
			// first, take care of the years
			time = ((y - 1970) * (60 * 60 * 24 * 365));
			
			// now, those pesky leap years... for each one, we have to add an extra day
			for (int i = 1970; i < y; i++) {
			    // keep in mind that 2000 was a leap year but 2100, 2200, etc. are not!
			    if ((i % 4 == 0 && i % 100 != 0) || (i % 400 == 0)) {
			    	time += (60 * 60 * 24);
			    }
			}
		
			// then, months
			int daysPerMonth[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 } ;
			for (int i = 0; i < m-1; i++) {
			    time += (daysPerMonth[i] * (60 * 60 * 24));
			}
		
			// then, days
			time += ((d) * 60 * 60 * 24);
		
			// then, hours
			time += (h * 60 * 60);
			
			// MAGIC FOUR-HOUR FUDGE FACTOR TO ACCOUNT FOR TIME ZONE DIFFERENCE
			time += 4 * 60 * 60;
		
			// then, minutes
			time += (min * 60);
		
			// last, seconds
			time += s;
		
			// done!
			return time * (long)1000;
		    
		}
		catch (Exception e) {
		    // presumably a NumberFormatException
		    // e.printStackTrace();
		    return -1;
		}

    }
    

}
