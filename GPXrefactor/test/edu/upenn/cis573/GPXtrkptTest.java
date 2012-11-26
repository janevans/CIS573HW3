package edu.upenn.cis573;


import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

public class GPXtrkptTest {

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testTime() {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH) + 1;
		int day = now.get(Calendar.DAY_OF_MONTH);
		int hour = now.get(Calendar.HOUR_OF_DAY);
		int minute = now.get(Calendar.MINUTE);
		int second = now.get(Calendar.SECOND);
		
		String monthString = month + "";
		if (monthString.length() == 1) monthString = "0" + month;
		String dayString = day + "";
		if (dayString.length() == 1) dayString = "0" + day;
		String hourString = hour + "";
		if (hourString.length() == 1) hourString = "0" + hour;
		String minuteString = minute + "";
		if (minuteString.length() == 1) minuteString = "0" + minute;
		String secondString = second + "";
		if (secondString.length() == 1) secondString = "0" + second;
		
		// format is YYYY-MM-DDThh:mm:ssZ
		String timeString = year + "-" + monthString + "-" + dayString + "T" + hourString + ":" + minuteString + ":" + secondString + "Z";

		GPXtrkpt trkpt = new GPXtrkpt(0, 0, 0, timeString);
		
		long trackTime = trkpt.getTimeInMilliSecond();
				
		assert(now.getTimeInMillis() - trackTime < 1000);
		
	}

}
