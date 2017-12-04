package comparator.ast.tests;

import java.util.Arrays;

public class TestSample8 {
	public int test(int[] args) {
		int len = args.length;
		if(true) {
			switch (len) {
			case 1:
				Arrays.sort(args);
				break;
			}
		}
			return 1;
	}
}