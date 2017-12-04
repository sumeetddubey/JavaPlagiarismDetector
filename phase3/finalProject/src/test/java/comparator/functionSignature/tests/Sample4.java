package comparator.functionSignature.tests;

// sample class for checking function similarity
public class Sample4 {
	int a;
	String b;
	String c;
	
	public Sample4(int a, String b, String c) {
		this.a=a;
		this.b=b;
		this.c=c;
	}
	
	public int getA() {
		return a;
	}
	
	public void setA(int a, String b, String c) {
		if(b.equals(c)) {
			this.a=a;
		}
		else {
			this.a=0;
		}
	}
}
