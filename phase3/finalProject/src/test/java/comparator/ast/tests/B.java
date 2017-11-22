package comparator.ast.tests;

public class B {
	int num;
	int x=10;
	
	B(){
		this.num = 10;
	}
	
	B(int num){
		this.num = num;
	}
	
	public int getSum() {
		int sum = 0;
		for (int i = 0; i < num; i++) {
			//sum += num;
			sum += getNegatedNum(num);
		}
		
		printSum(sum);
		
		return sum;
	}
	
	public int getNegatedNum(int num) {
		return -num;
	}
	
	
	public void printSum(int sum) {
		System.out.println(sum);
	}
	
	public static void main(String[] args) {
		B a = new B(5);
		//A a = new A();
		int sum = a.getSum();
		
		System.out.println(sum);
	}
}
