package comparator.ast;

/**
 * The type to store all desired attributes of a Node in an AST
 * including 
 * 	- line number of the node in the program
 * 	- type of the node 
 * 	- the String representation of the node (only for debugging phase, will be removed later)
 * 
 * @author Wenjun
 *
 */
public class Node {

	
	private int startLineNum;	// line number of the node
	private int endLineNum;
	private String nodeType;	// type of the node
	private String nodeStrRep;	// String representation of the node

	/**
	 * Constructor to create a Node
	 * @param lineNum
	 * @param nodeType
	 * @param nodeStrRep
	 */
	public Node(int startLineNum, int endLineNum, String nodeType, String nodeStrRep) {
		this.startLineNum = startLineNum;
		this.endLineNum = endLineNum;
		this.nodeType = nodeType;
		this.nodeStrRep = nodeStrRep;
	}

	/**
	 * @return the string representation of the node
	 */
	public String getNodeStrRep() {
		return nodeStrRep;
	}

	/**
	 * @return type of the node
	 */
	public String getNodeTypeAbbr() {
		return nodeType;
	}

	/**
	 * @return line number of the node
	 */
	public int getStartLineNum() {
		return startLineNum;
	}

	public int getEndLineNumber() {
		return endLineNum;
	}
	
	/**
	 * define the toString method to show the Node
	 */
	@Override
	public String toString() {
		return "{'type': '" + nodeType + "'" 
				+ ", 'line': '" + startLineNum + "'"
				+ "'}";
		
		// return "{'type': '" + nodeType + "'"
		// + ", 'line': '" + lineNum + "'"
		// + ", 'representation': '" + nodeStrRep + "'}";
	}
	
	
//	/**
//	 * Check if the obj is equals to this Node
//	 */
//	@Override
//	public boolean equals(Object obj) {
//		if (obj == null) {
//	        return false;
//	    }
//	    if (!Node.class.isAssignableFrom(obj.getClass())) {
//	        return false;
//	    }
//	    
//	    final Node node = (Node) obj;
//
//		if(this.getStartLineNum() == node.getStartLineNum() && 
//				this.getEndLineNumber() == node.getEndLineNumber() &&
//				this.getNodeTypeAbbr() == node.getNodeTypeAbbr())
//			return true;
//		
//		return false;
//	}
}
