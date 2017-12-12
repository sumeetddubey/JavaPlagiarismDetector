package algorithms;

import static org.junit.Assert.*;
import java.util.HashSet;
import org.junit.Test;

import algorithms.gst.GreedyStringTilling;
import algorithms.gst.Match;

public class GreedyStringTillingTests {
//	minimum substring length for a legal match
	private final int MIN_MATCH_LENGTH=4;
	
//	Test helper method for comparing two match sets
	private boolean equals(HashSet<Match> matches1, HashSet<Match> matches2) {
		boolean isEqual;
		for(Match match1: matches1) {
			isEqual=false;
			for(Match match2: matches2) {
				if(match1.equals(match2)) {
					isEqual=true;
				}
			}
			if(!isEqual) {
				return false;
			}
		}
		return true;
	}
	
//	Test for one empty string
	@Test
	public void TestOneEmptyStrings() {
		String str1="";
		String str2="bcd";
		GreedyStringTilling gst=new GreedyStringTilling(MIN_MATCH_LENGTH);
		HashSet<Match> actual = gst.GST(str1, str2);
		HashSet<Match> expected = new HashSet<Match>();
		assertTrue(equals(actual, expected));
	}
	
//	Test for two empty strings
	@Test
	public void TestTwoEmptyStrings() {
		String str1="";
		String str2="";
		GreedyStringTilling gst=new GreedyStringTilling(MIN_MATCH_LENGTH);
		HashSet<Match> actual = gst.GST(str1, str2);
		HashSet<Match> expected = new HashSet<Match>();
		assertTrue(equals(actual, expected));
	}

//	Test for two simple strings
	@Test
	public void TestTwoSimpleStrings() {
		String str1="abc";
		String str2="bcd";
		GreedyStringTilling gst=new GreedyStringTilling(MIN_MATCH_LENGTH);
		HashSet<Match> actual = gst.GST(str1, str2);
		HashSet<Match> expected = new HashSet<Match>();
		expected.add(new Match(1, 0, 2));
		assertTrue(equals(actual, expected));
	}
	
//	Test for two complex strings
	@Test
	public void TestTwoComplexStrings() {
		String str1="abcdcdefferrfgher";
		String str2="abcdfferfher";
		GreedyStringTilling gst=new GreedyStringTilling(MIN_MATCH_LENGTH);
		HashSet<Match> actual = gst.GST(str1, str2);
		HashSet<Match> expected = new HashSet<Match>();
		expected.add(new Match(0, 0, 4));
		expected.add(new Match(14, 9, 3));
		expected.add(new Match(7, 4, 4));
		assertTrue(equals(actual, expected));
	}

//	Test two long strings
	@Test
	public void TestTwoLongStrings() {
		String str1="abcdcdefferrfgher";
		String str2="abcdfferfher";
		int LARGEINT=1000;
		for(int i=0; i<LARGEINT; i++) {
			str1+="a";
			str2+="a";
		}
		GreedyStringTilling gst=new GreedyStringTilling(MIN_MATCH_LENGTH);
		HashSet<Match> actual = gst.GST(str1, str2);
		HashSet<Match> expected = new HashSet<Match>();
		expected.add(new Match(0, 0, 4));
		expected.add(new Match(7, 4, 4));
		expected.add(new Match(14, 9, 1003));
		assertTrue(equals(actual, expected));
	}
	
//	Test for a null string
	@Test
	public void TestNullString() {
		String str1="sampleString";
		String str2=null;
		GreedyStringTilling gst=new GreedyStringTilling(MIN_MATCH_LENGTH);
		HashSet<Match> actual = gst.GST(str1, str2);
		HashSet<Match> expected = new HashSet<Match>();
		assertEquals(actual, expected);
	}
}
	
