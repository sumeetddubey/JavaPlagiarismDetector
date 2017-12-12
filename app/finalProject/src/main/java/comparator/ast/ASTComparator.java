package comparator.ast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import algorithms.gst.GreedyStringTilling;
import algorithms.gst.Match;
import interfaces.IComparator;
import utility.Report;
import utility.Report.ComparisonLayer;

/**
 * ASTComparator that compares two programs and generates report
 * 	1) Create list of Node to represent each program  
 * 	2) Convert list of Node to String representation to represent the program
 * 	   and compare the String representation by Greedy String Tilling algorithm
 * 	3) Get suspicious pair of block(node list represented by node indexes) in two programs
 * 	4) Get suspicious nodes of each program separately
 * 	5) Generate Report - calculate similarity score and generate message(suspicious line numbers in both programs)
 * 
 * @author Wenjun
 *
 */
public class ASTComparator implements IComparator {
	
	private int MIN_MATCHED_LEN_FOR_GST = 4;
	
	List<Node> programANodeList; // list of Node that represents programA
	List<Node> programBNodeList; // list of Node that represents programB
	
	/**
	 * Generate similarity report for the two given programs, programA and programB
	 * by comparing AST of the two programs
	 */
	@Override
	public Report generateReport(String programA, String programB) {
		if(programA == null || programB == null) {
			return new Report(ComparisonLayer.AST, (float)0.0, "[]" + "\n" + "[]");
		}
		
		float score;
		String message;
		boolean needToBeContinued = extractListOfNodesFromPrograms(programA, programB);
		if (!needToBeContinued) {
			message="[]" + "\n" + "[]";
			return new Report(ComparisonLayer.AST, (float)0.0, message);
		}

		// get list of suspicious nodes(represented by node index intervals) pair 
		List<PairOfNodeIndexIntervals> listOfSuspiciousPairs = getListOfSuspiciousNodeIndexIntervalPairs();

		// get suspicious nodes from program A
		Set<Node> suspiciousNodesInA = getAllSuspiciousNodesForProgramA(listOfSuspiciousPairs);
		// get suspicious nodes from program B
		Set<Node> suspiciousNodesInB = getAllSuspiciousNodesForProgramB(listOfSuspiciousPairs);

		// get the suspicious line numbers of both programs in sorted order
		Integer[] suspiciousLineNumsOfA = getSuspiciousLineNums(suspiciousNodesInA);
		Integer[] suspiciousLineNumsOfB = getSuspiciousLineNums(suspiciousNodesInB);

		// calculate similarity score of the two programs
		score = calculateSimilarityScore(suspiciousNodesInA.size(), suspiciousNodesInB.size());
		
		// generate message (list of suspicious nodes for programA and programB) for the Report
		message = Arrays.toString(suspiciousLineNumsOfA) + "\n" + Arrays.toString(suspiciousLineNumsOfB);
		
		return new Report(ComparisonLayer.AST, score, message);
	}
	
	
	/**
	 * Covert list of nodes into String representation: a list of abbreviation of types
	 * 
	 * @param nodeList - the node list
	 * @return - the String representation of the node list
	 */
	public static String getProgramRepresentation(List<Node> nodeList) {
		StringBuilder sb = new StringBuilder();

		for (Node node : nodeList) {
			sb.append(node.getNodeTypeAbbr());
		}

		return sb.toString();
	}
	
	/**
	 * Extract list of Node from programs and decide whether needs to continue 
	 * comparing the two programs
	 * @param programA
	 * @param programB
	 * @return - whether is it worth to continue compare the two programs
	 */
	private boolean extractListOfNodesFromPrograms(String programA, String programB) {
		if (programA.length() == 0 || programB.length() == 0) {
			// if one of the programs is empty, no need to continue comparing
			return false;
		} 
		
		// get node lists of two programs respectively
		DetectorASTParser parserA = new DetectorASTParser();		
		DetectorASTParser parserB = new DetectorASTParser();
		programANodeList = parserA.parseProgramToListOfNodes(programA);
		programBNodeList = parserB.parseProgramToListOfNodes(programB);
		
		if (programANodeList.size() == 0 || programBNodeList.size() == 0) {
			// if one of the programs does not have any nodes inside of it, no need to continue comparing
			return false;
		}
		
		// otherwise, continue comparing two programs
		return true;
	}
	
	
	/**
	 * Calculate similarity score for program A and B
	 * 
	 * @param numOfSuspiciousNodesInA - number of suspicious nodes in program A
	 * @param numOfSuspiciousNodesInB - number of suspicious nodes in program B
	 * @param numOfNodesInA - number of total nodes in program A
	 * @param numOfNodesInB - number of total nodes in program B
	 * @return - similarity score of the two programs
	 */
	private float calculateSimilarityScore(int numOfSuspiciousNodesInA, int numOfSuspiciousNodesInB) {
		// calculate the similarity score
		return  (float) (numOfSuspiciousNodesInA + numOfSuspiciousNodesInB) 
				/ (programANodeList.size() + programBNodeList.size()) * 100;
	}
	

	/**
	 * Get all line numbers for a set of suspicious nodes in the program
	 * 
	 * @param suspiciousNodes - set of suspicious nodes of a program
	 * @return - all line numbers that contains the given suspiciousNodes
	 */
	private Integer[] getSuspiciousLineNums(Set<Node> suspiciousNodes) {
		Set<Integer> lineNums = new HashSet<Integer>();

		for (Node node : suspiciousNodes) {
			lineNums.addAll(allIntegerInRange(node.getStartLineNum(), node.getEndLineNumber()));
		}

		Integer[] lineNumArr = new Integer[lineNums.size()];
		Arrays.sort(lineNums.toArray(lineNumArr));
		return lineNumArr;
	}
	
	/**
	 * Get all suspicious nodes from program A that appear in the list of nodes pairs 
	 * 
	 * @param programANodeList - all nodes of program A
	 * @param listOfSuspiciousPairs - all suspicious pair of blocks of program A and B
	 * @return - all suspicious nodes from program A
	 */
	private Set<Node> getAllSuspiciousNodesForProgramA(List<PairOfNodeIndexIntervals> listOfSuspiciousPairs) {
		Set<Integer> nodeIndexes = new HashSet<Integer>();
		
		for (PairOfNodeIndexIntervals pair : listOfSuspiciousPairs) {
			// program A is the first element in the pair of suspicious blocks
			nodeIndexes.addAll(allIntegerInRange(pair.getFirstStartNodeIndex(), pair.getFirstEndNodeIndex()));
		}

		return findNodesByIndexes(programANodeList, nodeIndexes);
	}
	
	/**
	 * Get all suspicious nodes from program B that appear in the list of nodes pairs 
	 * 
	 * @param programANodeList - all nodes of program B
	 * @param listOfSuspiciousPairs - all suspicious pair of blocks of program A and B
	 * @return - all suspicious nodes from program B
	 */
	private Set<Node> getAllSuspiciousNodesForProgramB(List<PairOfNodeIndexIntervals> listOfSuspiciousPairs) {
		Set<Integer> nodeIndexes = new HashSet<Integer>();
		// program B is the second element in the pair of suspicious blocks
		for (PairOfNodeIndexIntervals pair : listOfSuspiciousPairs) {
			nodeIndexes.addAll(allIntegerInRange(pair.getSecondStartNodeIndex(), pair.getSecondEndNodeIndex()));
		}

		return findNodesByIndexes(programBNodeList, nodeIndexes);
	}

	/**
	 * Find all nodes by node indexes in a list of Node
	 * 
	 * @param nodeList - the list of Node
	 * @param indexes - a set of node indexes
	 * @return - all nodes in the list of Node that are at indexes
	 */
	private Set<Node> findNodesByIndexes(List<Node> nodeList, Set<Integer> indexes) {
		Set<Node> nodes = new HashSet<Node>();

		for (Integer index : indexes) {
			nodes.add(nodeList.get(index));
		}

		return nodes;
	}

	/**
	 * Get all integers within a range between [start, end] (inclusive)
	 * 
	 * @param start - start number of the range
	 * @param end - end number of the range
	 * @return - all integers between start and end
	 */
	private List<Integer> allIntegerInRange(int start, int end) {
		List<Integer> range = new ArrayList<Integer>();

		for (int i = start; i <= end; i++) {
			range.add(i);
		}

		return range;
	}

	/**
	 * Compare two node list of two ASTs 
	 * 	1) convert the list of Node into String representation
	 * 	2) use Greedy String Tilling(GST) Algorithm to find similar substrings 
	 * 		represented by start indexes of the matched character in the String
	 * 		and the length of the matched substring
	 * 
	 * @param programANodeList
	 * @param programBNodeList
	 * @return - a set of Match that are discovered by GST
	 */
	private Set<Match> compareTwoNodeLists(List<Node> programANodeList, List<Node> programBNodeList) {
		// convert node list to string representations in order
		String programATypeAbbr = getProgramRepresentation(programANodeList);
		String programBTypeAbbr = getProgramRepresentation(programBNodeList);

		// call Greedy String Tilling algorithms to compare the Strings
		GreedyStringTilling gst = new GreedyStringTilling(MIN_MATCHED_LEN_FOR_GST);

		return gst.GST(programATypeAbbr, programBTypeAbbr);
	}

	/**
	 * Get list of suspicious node in two program list represented by a pair of block (NodeIndexInterval)
	 * 
	 * @param programANodeList
	 * @param programBNodeList
	 * @return
	 */
	private List<PairOfNodeIndexIntervals> getListOfSuspiciousNodeIndexIntervalPairs() {

		List<PairOfNodeIndexIntervals> listOfPairs = new ArrayList<PairOfNodeIndexIntervals>();

		// compare the two node list by Greedy String Tilling algorithm
		Set<Match> matchedSubstrings = compareTwoNodeLists(programANodeList, programBNodeList);
		Iterator<Match> it = matchedSubstrings.iterator();
		while (it.hasNext()) {
			Match match = it.next();
			
			// Given the node is represented by abbreviation of its type with fixed length: 2
			// not all results that are returned from GST are valid, we need to find out 
			// valid block pairs
			PairOfNodeIndexIntervals validIntervalPair = getValidNodeIndexIntervalPair(match);

			// if the current match does not produce a valid block pair, the validIntervalPair will be null
			// otherwise, add it to the list of pairs
			if (validIntervalPair != null) {
				listOfPairs.add(validIntervalPair);
			}
		}

		return listOfPairs;
	}

	
	/**
	 *  Given the node is represented by abbreviation of its type with fixed length: 2,
	 *  not all results that are returned from GST are valid, we need to find out valid pairs
	 *  considering if the matched length and the start indexes are even or odd, because for
	 *  matched nodes, the start indexes and the matched length should always be even
	 * 
	 * @param match - the match returned by GST algorithm
	 * @return - a pair of valid node index intervals, if there is one, otherwise, null.
	 */
	private PairOfNodeIndexIntervals getValidNodeIndexIntervalPair(Match match) {
		// init pair to be null, which represents the current match does not produce a valid pair
		PairOfNodeIndexIntervals pair = null;

		// the indexes of the matched substrings
		int firstStrMatchedStartIndex = match.getFirstStringIndex();
		int secondStrMatchedStartIndex = match.getSecondStringIndex();
		// length of the matched substrings
		int matchedLen = match.getMatchLength();

		if (matchedLen % 2 == 0) {
			//if length of the matched substrings is even
			if (firstStrMatchedStartIndex % 2 == 0 && secondStrMatchedStartIndex % 2 == 0) {
				// if both substrings starts from even index, it is already a valid pair
				pair = createNodeIndexIntervalPair(firstStrMatchedStartIndex, secondStrMatchedStartIndex, matchedLen);
			} else if (firstStrMatchedStartIndex % 2 == 1 && secondStrMatchedStartIndex % 2 == 1) {
				// if both substrings starts from odd index, ignore the first and last characters
				pair = createNodeIndexIntervalPair(firstStrMatchedStartIndex + 1, secondStrMatchedStartIndex + 1,
						matchedLen - 2);
			}
		} else {
			//if length of the matched substrings is odd
			if (firstStrMatchedStartIndex % 2 == 0 && secondStrMatchedStartIndex % 2 == 0) {
				// if both substrings starts from even index, ignore the last character
				pair = createNodeIndexIntervalPair(firstStrMatchedStartIndex, secondStrMatchedStartIndex, matchedLen - 1);
			} else if (firstStrMatchedStartIndex % 2 == 1 && secondStrMatchedStartIndex % 2 == 1) {
				// if both substrings starts from odd index, ignore the first character
				pair = createNodeIndexIntervalPair(firstStrMatchedStartIndex + 1, secondStrMatchedStartIndex + 1,
						matchedLen - 1);
			}
		}

		return pair;
	}

	/**
	 * Create a new PairOfNodeIndexIntervals that is represented by the Strings of abbreviations of types
	 * 
	 * @param firstStart - the start index of first matched substring 
	 * @param secondStart - the start index of second matched substring 
	 * @param matchedLen - the match length of the two substrings
	 * @return - the PairOfNodeIndexIntervals that is represented by the two substrings
	 */
	private PairOfNodeIndexIntervals createNodeIndexIntervalPair(int firstStart, int secondStart, int matchedLen) {
		// new first and second 
		NodeIndexInterval first = new NodeIndexInterval(getStartNodeIndex(firstStart),
				getEndNodeIndex(firstStart, matchedLen));
		NodeIndexInterval second = new NodeIndexInterval(getStartNodeIndex(secondStart),
				getEndNodeIndex(secondStart, matchedLen));

		PairOfNodeIndexIntervals pair = new PairOfNodeIndexIntervals(first, second);

		return pair;
	}

	/**
	 * Calculate the start node index, given the fixed length of abbreviation of types is 2
	 * 
	 * @param validStartIndexOfStr - the valid start index of the matched String of types' abbreviation 
	 * @return - the start index of a node that is represented by a matched String of types' abbreviation 
	 */
	private int getStartNodeIndex(int validStartIndexOfStr) {
		return validStartIndexOfStr / 2;
	}
	
	/**
	 * Calculate the start node index, given the fixed length of abbreviation of types is 2
	 * 
	 * @param validStartIndexOfStr - the valid start index of the matched String of types' abbreviation
	 * @param len - length of the matched string
	 * @return - the start index of a node that is represented by a matched String of types' abbreviation 
	 */
	private int getEndNodeIndex(int validStartIndexOfStr, int len) {
		return (validStartIndexOfStr + len - 2) / 2;
	}
}
