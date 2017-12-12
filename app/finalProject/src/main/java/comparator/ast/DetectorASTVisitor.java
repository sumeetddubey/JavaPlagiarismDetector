package comparator.ast;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.WhileStatement;


/**
 * DetectorASTVisitor visits the AST that is created by JDT
 * using Visitor Pattern
 * 
 * @author Wenjun
 *
 */
public class DetectorASTVisitor extends ASTVisitor {

	// to store the list of Nodes in the AST
	List<Node> listOfNodes;
	
	// CompilationUnit 
	// in order to get the line number out of the AST
	CompilationUnit cu;

	/**
	 * Constructor of MyASTVistor
	 */
	DetectorASTVisitor(CompilationUnit cu) {
		this.cu = cu;
		listOfNodes = new ArrayList<Node>();
	}

	/**
	 * return the class field listOfNodes 
	 * which stores all discovered Nodes with specified 
	 * Types in the AST 
	 */
	public List<Node> getListOfNodes() {
		return listOfNodes;
	}

	/**
	 * Visit the Assignment type of node in an AST
	 * and keep visiting any sub-node of it 
	 */
	public boolean visit(Assignment node) {
		listOfNodes.add(new Node(cu.getLineNumber(node.getStartPosition()), 
								 cu.getLineNumber(node.getStartPosition() + node.getLength()),
								 "AS"));
		return true;
	}
	

	
	/**
	 * Visit the InfixExpression type of node in an AST
	 * and keep visiting any sub-node of it 
	 */
	public boolean visit(InfixExpression node) {
		listOfNodes.add(new Node(cu.getLineNumber(node.getStartPosition()), 
								 cu.getLineNumber(node.getStartPosition() + node.getLength()),
								 "EX"));
		return true;
	}
	
	/**
	 * Visit the PrefixExpression type of node in an AST
	 * and keep visiting any sub-node of it 
	 */
	public boolean visit(PrefixExpression node) {
		listOfNodes.add(new Node(cu.getLineNumber(node.getStartPosition()), 
								 cu.getLineNumber(node.getStartPosition() + node.getLength()),
								 "EX"));
		return true;
	}
	
	/**
	 * Visit the PostfixExpression type of node in an AST
	 * and keep visiting any sub-node of it 
	 */
	public boolean visit(PostfixExpression node) {
		listOfNodes.add(new Node(cu.getLineNumber(node.getStartPosition()), 
								 cu.getLineNumber(node.getStartPosition() + node.getLength()),
								 "EX"));
		return true;
	}


	/**
	 * Visit the ReturnStatement type of node in an AST
	 * and keep visiting any sub-node of it 
	 */
	public boolean visit(ReturnStatement node) {
		listOfNodes.add(new Node(cu.getLineNumber(node.getStartPosition()), 
								 cu.getLineNumber(node.getStartPosition() + node.getLength()),
								 "RS"));
		return true;
	}
	
	/**
	 * Visit the BreakStatement type of node in an AST
	 * and keep visiting any sub-node of it 
	 */
	public boolean visit(BreakStatement node) {
		listOfNodes.add(new Node(cu.getLineNumber(node.getStartPosition()), 
								 cu.getLineNumber(node.getStartPosition() + node.getLength()),
								 "BS"));
		return true;
	}
	
	/**
	 * Visit the ContinueStatement type of node in an AST
	 * and keep visiting any sub-node of it 
	 */
	public boolean visit(ContinueStatement node) {
		listOfNodes.add(new Node(cu.getLineNumber(node.getStartPosition()), 
								 cu.getLineNumber(node.getStartPosition() + node.getLength()),
								 "CS"));
		return true;
	}

	/**
	 * Visit the SwitchCase type of node in an AST
	 * and keep visiting any sub-node of it 
	 */
	public boolean visit(SwitchCase node) {
		listOfNodes.add(new Node(cu.getLineNumber(node.getStartPosition()), 
								 cu.getLineNumber(node.getStartPosition() + node.getLength()),
								 "SC"));
		return true;
	}

	/**
	 * Visit the SwitchStatement type of node in an AST
	 * and keep visiting any sub-node of it 
	 */
	public boolean visit(SwitchStatement node) {
		listOfNodes.add(new Node(cu.getLineNumber(node.getStartPosition()), 
								 cu.getLineNumber(node.getStartPosition() + node.getLength()),
								 "SS"));
		return true;
	}

	/**
	 * Visit the ForStatement type of node in an AST
	 * and keep visiting any sub-node of it 
	 */
	public boolean visit(ForStatement node) {
		listOfNodes.add(new Node(cu.getLineNumber(node.getStartPosition()), 
								 cu.getLineNumber(node.getStartPosition() + node.getLength()),
								 "LP"));
		
		return true;
	}

	/**
	 * Visit the WhileStatement type of node in an AST
	 * and keep visiting any sub-node of it 
	 */
	public boolean visit(WhileStatement node) {
		listOfNodes.add(new Node(cu.getLineNumber(node.getStartPosition()), 
								 cu.getLineNumber(node.getStartPosition() + node.getLength()),
				 				 "LP"));
		return true;
	}

	/**
	 * Visit the IfStatement type of node in an AST
	 * and keep visiting any sub-node of it 
	 */
	public boolean visit(IfStatement node) {
		listOfNodes.add(new Node(cu.getLineNumber(node.getStartPosition()), 
								 cu.getLineNumber(node.getStartPosition() + node.getLength()),
								 "IS"));
		return true;
	}

	/**
	 * Visit the MethodDeclaration type of node in an AST
	 * and keep visiting any sub-node of it 
	 */
	public boolean visit(MethodDeclaration node) {
		listOfNodes.add(new Node(cu.getLineNumber(node.getStartPosition()), 
								 // set the end line number also as the start number 
								 cu.getLineNumber(node.getStartPosition()),  
								 "MD"));
		return true; 
	}

	/**
	 * Visit the MethodInvocation type of node in an AST
	 * and keep visiting any sub-node of it 
	 */
	public boolean visit(MethodInvocation node) {
		listOfNodes.add(new Node(cu.getLineNumber(node.getStartPosition()), 
								 cu.getLineNumber(node.getStartPosition() + node.getLength()),
								 "MI"));
		return true;
	}
	
	/**
	 * Visit the FieldDeclaration type of node in an AST
	 * and keep visiting any sub-node of it 
	 */
	public boolean visit(FieldDeclaration node) {
		listOfNodes.add(new Node(cu.getLineNumber(node.getStartPosition()), 
								 cu.getLineNumber(node.getStartPosition() + node.getLength()),
								 "FD"));
		return true;
	}
	
	/**
	 * Visit the VariableDeclarationFragment type of node in an AST
	 * and keep visiting any sub-node of it 
	 */
	public boolean visit(VariableDeclarationFragment node) {
		listOfNodes.add(new Node(cu.getLineNumber(node.getStartPosition()), 
								 cu.getLineNumber(node.getStartPosition() + node.getLength()),
								 "VF"));
		return true;
	}
	
	/**
	 * Visit the SingleVariableDeclaration type of node in an AST
	 * and keep visiting any sub-node of it 
	 */
	public boolean visit(SingleVariableDeclaration node) {
		listOfNodes.add(new Node(cu.getLineNumber(node.getStartPosition()), 
								 cu.getLineNumber(node.getStartPosition() + node.getLength()),
								 "VS"));
		return true;
	}
}
