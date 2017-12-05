package TestSamples;

import java.util.Arrays;
import java.util.Comparator;

public class ASTStringSample4 {
	public int test(Integer[] args) {
		int len = args.length;
		
		class MyComparator implements Comparator<Integer>{
			@Override
			public int compare(Integer a, Integer b) {
				return b - a;
			}
		}
		len = 2;
		switch (len) {
		case 1:
			class MyClass {
				public void name() {
					System.out.println("hello");
				}
			}
			break;
		}
		return 1;
	}
}