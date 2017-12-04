package comparator.ast.tests;

/**
 * This file is used to test the ASTComparator to mock up user input
 *  
 * @author Wenjun
 *
 */
public class TestSample2 {
	int num;
	int x=10;
	TestSample2(int num){
		this.num = num;
	}
	
	public int getSum() {
		int sum = 0;
		
		int i = 0;
		while(i < num) {
			int temp = num + num * 2;
			temp = -temp;
			sum += temp;
			i++;
		}
		
		return sum;
	}
	
	public static void main(String[] args) {
		TestSample1 a = new TestSample1(5);
		int sum = a.getSum();
		
		System.out.println(sum);
	}
}
