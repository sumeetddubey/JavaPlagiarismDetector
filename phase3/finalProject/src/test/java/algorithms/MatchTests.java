package algorithms;

import static org.junit.Assert.*;
import java.util.LinkedList;

import org.junit.Test;

import algorithms.gst.Match;

public class MatchTests {
	
	@Test
	public void testMatchTextualRepresentation() {
		Match m = new Match(0, 2, 2);
		
		String actual=m.toString();
		String expected="Index in string 1: 0 \t"+ 
				"Index in string 2: 2 \tMatch length: 2";
		assertEquals(actual, expected);
	}
	
	@Test
	public void compareTwoMatchesWhenOneIsNull() {
		Match m1 = new Match(0, 2, 2);
		Match m2=null;
		boolean isEqual = m1.equals(m2);
		assertFalse(isEqual);
	}
	
	@Test
	public void compareTwoMatchesWhenOneIsNonAssinableClass() {
		Match m1 = new Match(0, 2, 2);
		LinkedList<String> l = new LinkedList<String>();
		boolean isEqual = m1.equals(l);
		assertFalse(isEqual);
	}
	
	@Test
	public void compareTwoMatchesThatAreEqual() {
		Match m1 = new Match(0, 2, 2);
		Match m2 = new Match(0, 2, 2);
		boolean isEqual = m1.equals(m2);
		assertTrue(isEqual);
	}
	
	@Test
	public void compareTwoMatchesThatHaveDifferentFirstStringIndex() {
		Match m1 = new Match(1, 2, 2);
		Match m2 = new Match(0, 2, 2);
		boolean isEqual = m1.equals(m2);
		assertFalse(isEqual);
	}
}
