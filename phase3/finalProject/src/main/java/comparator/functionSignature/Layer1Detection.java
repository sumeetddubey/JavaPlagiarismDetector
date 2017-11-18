package comparator.functionSignature;
import java.lang.reflect.*;
import java.util.ArrayList;


// class for detecting function signature similarity
public class Layer1Detection {
//	function for getting all methods of a class
	private static ArrayList<FunctionSignature> getAllMethods(String className) {
		ArrayList<FunctionSignature> fns=new ArrayList<FunctionSignature>();
		String name, returnType;		
		Class<?>[] paramClass;
		try {
			Class<?> c=Class.forName(className);
			Method[] methods=c.getDeclaredMethods();
//			iterating over all methods to get their arguments and storing
//			in an arraylist
			for(Method m: methods) {
				ArrayList<String> params = new ArrayList<String>();
				name=m.getName();
				returnType=m.getReturnType().getSimpleName();
				paramClass=m.getParameterTypes();
				
				if(paramClass.length==0) {
					params.add("none");
				}
				else {
					for(Class<?> param: paramClass) {
						params.add(param.getSimpleName());
					}
				}
				FunctionSignature fs=new FunctionSignature(name, params, returnType);
				fs.textualRepresentation();
				fns.add(fs);
			}
			System.out.println("\n");
		}
//		will catch when a class definition is not found 
		catch(Exception e) {
			System.out.println("error is " +e);
		}
		return fns;
	}
	
//	function for computing similarity score of two classes
	public static float comparePrograms(String c1, String c2) {
		int cnt;
//		score calculated as number of similar methods divided by total
//		number of methods * 100
		float score;
		ArrayList<FunctionSignature> fns1=getAllMethods(c1);
		ArrayList<FunctionSignature> fns2=getAllMethods(c2);
//		if block to calculate score based on the program with lesser
//		number of functions
		if(fns1.size()<fns2.size()) {
			cnt=compareProgramsHelper(fns1, fns2);
			score=cnt/fns1.size() * 100;
		}
		else {
			cnt=compareProgramsHelper(fns2, fns1);
			score=(float)cnt/(float)fns2.size() * 100;
		}
		return score;
	}
	
//	function to compare each function in both classes
	private static int compareProgramsHelper(ArrayList<FunctionSignature> fns1, ArrayList<FunctionSignature> fns2) {
		int cnt=0;
		for (FunctionSignature fs1: fns1) {
			for(FunctionSignature fs2: fns2) {
				if(fs1.SignatureComparison(fs2)) {
					cnt++;
					break;
				}
			}
		}
		return cnt;
	}
}
