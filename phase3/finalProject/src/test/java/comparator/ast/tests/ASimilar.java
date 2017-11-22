package comparator.ast.tests;

public class ASimilar {
	int num;
	int x=10;
	ASimilar(int num){
		this.num = num;
	}
	
	public int getSum() {
		int sum = 0;
		
		int i = 0;
		while(i < num) {
			sum += i;
			i++;
		}

		return sum;
	}
	
	public static void main(String[] args) {
		ASimilar a = new ASimilar(5);
		int sum = a.getSum();
		
		System.out.println(sum);
	}
}
