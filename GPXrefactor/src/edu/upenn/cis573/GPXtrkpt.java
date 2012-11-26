package edu.upenn.cis573;

/**
 * Represents a track point in a GPX file.
 */

public class GPXtrkpt {

    // latitude
    /*change name, increase readability.*/
    private double latitude;
    // longitude
    /*change name, increase readability.*/
    private double longitude;
    // elevation
    /*change name, increase readability.*/
    private double elevation;
    // time is assumed to be in the following format: YYYY-MM-DDThh:mm:ssZ
    private String time;

    public GPXtrkpt(double lat, double lon, double ele, String time) {
		this.latitude = lat;
		this.longitude = lon;
		this.elevation = ele;
		this.time = time;
    }

    /* Accessors */
    /*change name, increase readability.*/
    public double latitude() { return latitude; }
    public double longitude() { return longitude; }
    public double elevation() { return elevation; }
    public String timeString() { return time; }
    
    public long getTimeInMilliSecond() {
		try {
            /*
             * primitive obssession
             * Wrap the information into a time object instead.
             */		    
		    Time result = new Time(time);
            if(result.inWrongFormat())
                return -1;
			
			return result.getTimeInMilliSecond();
		    
		}
		catch (Exception e) {
		    // presumably a NumberFormatException
		    // e.printStackTrace();
		    return -1;
		}

    }
    
    private class Time
    {
        private int year;
        private int month;
        private int day ;
        private int hour ;
        private int minute;
        private int second;
        
        /**
         * Constructor
         * @param time 
         */
        public Time(String time)
        {
            year = Integer.parseInt(time.substring(0, 4));
		    month = Integer.parseInt(time.substring(5, 7));
		    day = Integer.parseInt(time.substring(8, 10));
		    hour = Integer.parseInt(time.substring(11, 13));
		    minute = Integer.parseInt(time.substring(14, 16));
		    second = Integer.parseInt(time.substring(17, 19));
        }
        
        /**
         * Validate the correctness of the time range.
         */
        public boolean inWrongFormat()
        {
            // make sure the values are valid
		   return   (year < 1970)              ||
                    (month < 1 || month > 12)   ||
                    ( day < 1 || day > 31)      ||
                    ( hour < 0 || hour > 23)    ||
                    (minute < 0 || minute > 59 )||
                    (second < 0 || second > 59) ;
                    
        }
        public long getTimeInMilliSecond()
        {
            long time;
			// first, take care of the years
			time = ((year - 1970) * (60 * 60 * 24 * 365));
			
			// now, those pesky leap years... for each one, we have to add an extra day
			for (int i = 1970; i < year; i++) {
			    // keep in mind that 2000 was a leap year but 2100, 2200, etc. are not!
			    if ((i % 4 == 0 && i % 100 != 0) || (i % 400 == 0)) {
			    	time += (60 * 60 * 24);
			    }
			}
		
			// then, months
			int daysPerMonth[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 } ;
			for (int i = 0; i < month-1; i++) {
			    time += (daysPerMonth[i] * (60 * 60 * 24));
			}
		
			// then, days
			time += ((day) * 60 * 60 * 24);
		
			// then, hours
			time += (hour * 60 * 60);
			
			// MAGIC FOUR-HOUR FUDGE FACTOR TO ACCOUNT FOR TIME ZONE DIFFERENCE
			time += 4 * 60 * 60;
		
			// then, minutes
			time += (minute * 60);
		
			// last, seconds
			time += second;
		
            time = time*(long)1000;
            return time;
        }
    }

}
