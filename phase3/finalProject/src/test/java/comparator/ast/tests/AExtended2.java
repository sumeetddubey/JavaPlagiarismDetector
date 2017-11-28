package comparator.ast.tests;

/**
 * This file is used to test the ASTComparator to mock up user input
 * 
 * @author Wenjun
 *
 */
public class AExtended2 {
	int num;
	int x=10;
	
	public AExtended2() {
		this.num = 20;
	}
	
	AExtended2(int num){
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
		return negatedNum(num);
	}
	
	public int negatedNum(int num) {
		return -num;
	}
	
	public static void main(String[] args) {
		AExtended2 a = new AExtended2(5);
		int sum = a.getSum();
		
		System.out.println(sum);
	}
}
