package TestSamples;

// sample class for testing function similarity
public class FunctionSignatureSample1 {
	int a, b;
	
	public FunctionSignatureSample1(int a, int b) {
		this.a=a;
		this.b=b;
	}
	
	public int getSum() {
		return a+b;
	}
	
	public int getA() {
		return a;
	}
	
	public int getB() {
		return b;
	}
	
	public void setA(int a) {
		this.a=a;
	}
	
	public void setB(int b) {
		this.b=b;
	}
}
