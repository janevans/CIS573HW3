package edu.upenn.cis573;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GPXparserTest {

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testParseGood() {
		GPXformat format = GPXchecker.checkFormat("files/good.gpx");

		GPXobject obj = GPXparser.parse("files/good.gpx", format);
		
		assertEquals("2009-10-17T22:58:43Z", obj.time());
		
		GPXtrk trk = obj.trk();
		
		assertEquals(obj, trk.parent());
		assertEquals("Walking around a little bit", trk.name());
		
		GPXtrkseg[] trksegs = trk.trksegs();
		GPXtrkpt trkpt = trksegs[0].trkpt(0);

		assertEquals(47.644548, trkpt.latitude(), 0.001);
		assertEquals(-122.326897, trkpt.longitude(), 0.001);
		assertEquals(4.46, trkpt.elevation(), 0.001);
		assertEquals("2009-10-17T18:37:26Z", trkpt.timeString());
	}


	@Test
	public void testParseMissingName() {
		GPXformat format = GPXchecker.checkFormat("files/missingName.gpx");

		GPXobject obj = GPXparser.parse("files/missingName.gpx", format);
		
		assertEquals("2009-10-17T22:58:43Z", obj.time());
		
		GPXtrk trk = obj.trk();
		
		assertEquals(obj, trk.parent());
		
		GPXtrkseg[] trksegs = trk.trksegs();
		GPXtrkpt trkpt = trksegs[0].trkpt(0);

		assertEquals(47.644548, trkpt.latitude(), 0.001);
		assertEquals(-122.326897, trkpt.longitude(), 0.001);
		assertEquals(4.46, trkpt.elevation(), 0.001);
		assertEquals("2009-10-17T18:37:26Z", trkpt.timeString());
	}

	@Test
	public void testParseNoTrkPts() {
		GPXformat format = GPXchecker.checkFormat("files/noTrkPts.gpx");

		GPXobject obj = GPXparser.parse("files/noTrkPts.gpx", format);
		
		assertEquals("2009-10-17T22:58:43Z", obj.time());
		
		GPXtrk trk = obj.trk();
		
		assertEquals(obj, trk.parent());
		assertEquals("Walking around a little bit", trk.name());
		
		GPXtrkseg[] trksegs = trk.trksegs();		
		assertEquals(0, trksegs[0].trkpts().length);
	}

}
