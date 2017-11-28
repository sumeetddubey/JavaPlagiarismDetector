package comparator.ast.tests;

/**
 * This file is used to test the ASTComparator to mock up user input
 * 
 * @author Wenjun
 *
 */
public class AExtended {
	int num;
	int x=10;
	
	public AExtended() {
		this.num = 20;
	}
	
	AExtended(int num){
		this.num = num;
	}
	
	public int getSum() {
		int sum = 0;
		
		for (int i = 0; i < num; i++) {
			sum += getNegatedNum(num);
		}
		
		return sum;
	}
	
	public int getNegatedNum(int num) {
		return -num;
	}
	
	public static void main(String[] args) {
		AExtended a = new AExtended(5);
		int sum = a.getSum();
		
		System.out.println(sum);
	}
}
