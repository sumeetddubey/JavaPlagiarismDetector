package algorithms;

import static org.junit.Assert.*;
import java.util.HashSet;
import org.junit.Test;

public class GreedyStringTillingTests {
	
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
		HashSet<Match> actual = GreedyStringTilling.GST(str1, str2);
		HashSet<Match> expected = new HashSet<Match>();
		assertTrue(equals(actual, expected));
	}
	
//	Test for two empty strings
	@Test
	public void TestTwoEmptyStrings() {
		String str1="";
		String str2="";
		HashSet<Match> actual = GreedyStringTilling.GST(str1, str2);
		HashSet<Match> expected = new HashSet<Match>();
		assertTrue(equals(actual, expected));
	}

//	Test for two simple strings
	@Test
	public void TestTwoSimpleStrings() {
		String str1="abc";
		String str2="bcd";
		HashSet<Match> actual = GreedyStringTilling.GST(str1, str2);
		HashSet<Match> expected = new HashSet<Match>();
		expected.add(new Match(1, 0, 2));
		assertTrue(equals(actual, expected));
	}
	
//	Test for two complex strings
	@Test
	public void TestTwoComplexStrings() {
		String str1="abcdcdefferrfgher";
		String str2="abcdfferfher";
		HashSet<Match> actual = GreedyStringTilling.GST(str1, str2);
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
		int LARGEINT=1000;
		for(int i=0; i<LARGEINT; i++) {
			str1+="a";
		}
		String str2="abcdfferfher";
		for(int i=0; i<LARGEINT; i++) {
			str2+="a";
		}
		HashSet<Match> actual = GreedyStringTilling.GST(str1, str2);
		HashSet<Match> expected = new HashSet<Match>();
		expected.add(new Match(0, 0, 4));
		expected.add(new Match(7, 4, 4));
		expected.add(new Match(14, 9, 1003));
		assertTrue(equals(actual, expected));
	}
}
	
