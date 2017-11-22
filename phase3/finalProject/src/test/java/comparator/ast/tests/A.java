package comparator.ast.tests;

public class A {
	int num;
	int x=10;
	A(int num){
		this.num = num;
	}
	
	public int getSum() {
		int sum = 0;
		int i = 0;
		
		while(i < num) {
			sum += num;
			i++;
		}
		
		return sum;
	}
//	
//	public static void main(String[] args) {
//		A a = new A(5);
//		int sum = a.getSum();
//		
//		System.out.println(sum);
//	}
}
