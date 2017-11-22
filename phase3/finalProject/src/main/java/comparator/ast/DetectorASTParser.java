package comparator.ast;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * The parser for AST
 * 	1) Take the file path of a program as input
 * 	2) Parse the program to String
 * 	3) Create AST with the String using JDT
 * 	4) Visit (traverse) the AST
 * 	5) While extracting and parsing information from the AST
 * 
 * @author Wenjun
 *
 */
public class DetectorASTParser {

	/**
	 * Parse the program in String str
	 * 	- create its AST using JDT
	 * 	- traverse the AST with Visitor
	 * 	- get all Nodes with specified types
	 */
	public static List<Node> parseProgramToTypeAbbrs(String program) {
		// create the AST
		final CompilationUnit cu = createAST(program);
		
		// create an visitor and visit the AST
		ASTVisitor visitor = new DetectorASTVisitor(cu);
		cu.accept(visitor);
		
		// get Nodes from the AST 
		List<Node> nodeList = ((DetectorASTVisitor) visitor).getListOfNodes();
		return nodeList;
	}
	
	/**
	 * Create AST of the given program using JDT
	 * @param program - the program as a String
	 * @return - the CompilationUnit that represents the AST
	 */
	private static CompilationUnit createAST(String program) {
		// use ASTParser to parse the program
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(program.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
 
		// create the AST
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		
		return cu;
	}
}