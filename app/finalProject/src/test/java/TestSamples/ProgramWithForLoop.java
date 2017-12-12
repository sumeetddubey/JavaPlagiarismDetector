package TestSamples;

/**
 * This file is used to test the ASTComparator to mock up user input
 *  
 * @author Wenjun
 *
 */
public class ProgramWithForLoop {
	int num;
	int x=10;
	ProgramWithForLoop(int num){
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
		ProgramWithForLoop a = new ProgramWithForLoop(5);
		int sum = a.getSum();
		
		System.out.println(sum);
	}
}
