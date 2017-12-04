package comparator.ast.tests;

/**
 * This file is used to test the ASTComparator to mock up user input
 * 
 * @author Wenjun
 *
 */
public class TestSample3 {
	int num;
	int x=10;
	
	public TestSample3() {
		this.num = 20;
	}
	
	TestSample3(int num){
		this.num = num;
	}
	
	public int getSum() {
		int sum = 0;
		
		for (int i = 0; i < num; i++) {
			sum += getData(num);
		}
		
		return sum;
	}
	
	public int getData(int num) {
		int temp = num + num * 2;
		temp = -temp;
		return temp;
	}
	
	public static void main(String[] args) {
		TestSample3 a = new TestSample3(5);
		int sum = a.getSum();
		
		System.out.println(sum);
	}
}
