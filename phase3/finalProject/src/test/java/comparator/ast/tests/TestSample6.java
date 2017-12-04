package comparator.ast.tests;

public class TestSample6 {
	
	public int test(String[] args) {
		int len = args.length;
		if(len == 0)
			return 0;
		
		switch (len) {
		case 1:
			System.out.println("1");
			break;
		}
		
		return 1;
		
	}
}
