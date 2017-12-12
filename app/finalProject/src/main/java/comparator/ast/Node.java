package comparator.ast;

/**
 * The type to store all desired attributes of a Node in an AST
 * including 
 * 	- start line number of the node in the program
 * 	- end line number of the node in the program
 * 	- type of the node 
 * 
 * @author Wenjun
 *
 */
public class Node {
	
	private int startLineNum;	// start line number of the node
	private int endLineNum;		// end line number of the node
	private String nodeType;	// abbreviation of type of the node

	/**
	 * Constructor to create a Node
	 * 
	 * @param startLineNum
	 * @param endLineNum
	 * @param nodeType
	 */
	public Node(int startLineNum, int endLineNum, String nodeType) {
		this.startLineNum = startLineNum;
		this.endLineNum = endLineNum;
		this.nodeType = nodeType;
	}

	/**
	 * @return type of the node
	 */
	public String getNodeTypeAbbr() {
		return nodeType;
	}

	/**
	 * @return start line number of the node
	 */
	public int getStartLineNum() {
		return startLineNum;
	}

	/**
	 * @return end line number of the node
	 */
	public int getEndLineNumber() {
		return endLineNum;
	}
}
