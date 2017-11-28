package comparator.ast.tests;


/**
 * This file is used to test the ASTComparator to mock up user input
 * 
 * @author Wenjun
 *
 */
public class ASimilar {
	int num;
	int x=10;
	
	ASimilar(int num){
		this.num = num;
	}
	
	public int getSum() {
		int sum = 0;
		
		int i = 0;
		while (i < num) {
			sum += i;
			i++;
		}
		
		return sum;
	}
	
	public static void main(String[] args) {
		A a = new A(5);
		int sum = a.getSum();
		
		System.out.println(sum);
	}
}
