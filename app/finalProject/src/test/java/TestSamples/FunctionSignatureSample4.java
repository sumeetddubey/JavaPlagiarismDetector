package TestSamples;

// sample class for checking function similarity
public class FunctionSignatureSample4 {
	int a;
	String b;
	String c;
	
	public FunctionSignatureSample4(int a, String b, String c) {
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
