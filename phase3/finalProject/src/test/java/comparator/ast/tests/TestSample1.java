package comparator.ast.tests;

/**
 * This file is used to test the ASTComparator to mock up user input
 *  
 * @author Wenjun
 *
 */
public class TestSample1 {
	int num;
	int x=10;
	TestSample1(int num){
		this.num = num;
	}
	
	public int getSum() {
		int sum = 0;
		
		for (int i = 0; i < num; i++) {
			int temp = num + num * 2;
			temp = -temp;
			sum += temp;
		}
		
		return sum;
	}
	
	public static void main(String[] args) {
		TestSample1 a = new TestSample1(5);
		int sum = a.getSum();
		
		System.out.println(sum);
	}
}
