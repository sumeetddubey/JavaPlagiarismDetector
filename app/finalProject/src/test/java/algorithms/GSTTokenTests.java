package algorithms;

import static org.junit.Assert.*;

import org.junit.Test;

import algorithms.gst.GSTToken;

public class GSTTokenTests {

//	Test for creating a GST token
	@Test
	public void testForCreatingAGSTToken() {
		String value="AD";
		boolean marked = false;
		int location=2;
		GSTToken g = new GSTToken(value, marked, location);
		assertEquals(g.getValue(), "AD");
		assertEquals(g.getLocation(), 2);
		assertFalse(g.isMarked());
//		changing the value of location
		g.setLocation(3);
		assertEquals(g.getLocation(), 3);
//		changing the value of marked
		g.setMarked(true);
		assertTrue(g.isMarked());
//		changing the value of value
		g.setValue("VD");
		assertEquals(g.getValue(), "VD");
	}

}
