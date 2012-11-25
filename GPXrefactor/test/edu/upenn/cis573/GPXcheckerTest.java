package edu.upenn.cis573;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GPXcheckerTest {

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testCheckFormatGood() {
		GPXformat format = GPXchecker.checkFormat("files/good.gpx");
		assertEquals(true, format.isValid());
	}

	@Test
	public void testCheckFormatMissingName() {
		GPXformat format = GPXchecker.checkFormat("files/missingName.gpx");
		// this is still okay because name is optional
		assertEquals(true, format.isValid());
	}

	@Test
	public void testCheckFormatMissingTime() {
		GPXformat format = GPXchecker.checkFormat("files/missingTime.gpx");
		// this is bad
		assertEquals(false, format.isValid());
		assertEquals("Format error! Expected <time> tag", format.message());
	}
	
	@Test
	public void testCheckFormatNoTrkPts() {
		GPXformat format = GPXchecker.checkFormat("files/noTrkPts.gpx");
		// this is okay because you can have a trkseg with no trkpts
		assertEquals(true, format.isValid());
	}

	@Test
	public void testCheckFormatUnbalancedTags() {
		GPXformat format = GPXchecker.checkFormat("files/unbalancedTags.gpx");
		// this is bad
		assertEquals(false, format.isValid());
		assertEquals("Format error! Expected </trkpt> tag", format.message());
	}


}
