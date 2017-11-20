package algorithms;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Iterator;

import org.junit.Test;

public class GreedyStringTillingTests {

	@Test
	public void TestTwoSimpleStrings() {
		String str1="abc";
		String str2="bcd";
		GreedyStringTilling gst= new GreedyStringTilling(2, str1, str2);
		HashSet<Match> actual = gst.GST();
		HashSet<Match> expected = new HashSet<Match>();
		expected.add(new Match(1, 0, 2));
		Iterator<Match> it=actual.iterator();
		while(it.hasNext()) {
			it.next().textualRepresentation();
		}
	}
	
	@Test
	public void TestTwoComplexStrings() {
		String str1="abcdcdefferrfgherhertjerjejwcwwq";
		String str2="dcdeabcdqegewgwehgerhfdjgtjfykfghsgfsehdtkyjdrgqegegaefferc";
		GreedyStringTilling gst= new GreedyStringTilling(2, str1, str2);
		HashSet<Match> set = gst.GST();
		Iterator<Match> it=set.iterator();
		while(it.hasNext()) {
			it.next().textualRepresentation();
		}
	}
}
