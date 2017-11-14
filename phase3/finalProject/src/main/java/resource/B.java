package resource;

public class B {
	int num;
	int x=10;
	B(int num){
		this.num = num;
	}
	
	public int getSum() {
		int sum = 0;
		for (int i = 0; i < num; i++) {
			sum += num;
		}
		
		return sum;
	}
//	
//	public static void main(String[] args) {
//		B b = new B(5);
//		int sum = b.getSum();
//		
//		System.out.println(sum);
//	}
}
