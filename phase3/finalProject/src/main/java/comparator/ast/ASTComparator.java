package comparator.ast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import algorithms.gst.GreedyStringTilling;
import algorithms.gst.Match;
import interfaces.IComparator;
import utility.ReadFileToString;
import utility.Report;

public class ASTComparator implements IComparator {
	/**
	 * Generate similarity report for the two given programs, programA and programB
	 * by comparing AST of the two programs
	 */
	@Override
	public Report generateReport(File programA, File programB) throws IOException {
		String programAStr = ReadFileToString.readFileToString(programA);
		String programBStr = ReadFileToString.readFileToString(programA);
		
		// get node lists of two programs respectively
		List<Node> programANodeList = DetectorASTParser.parseProgramToTypeAbbrs(programAStr);
		List<Node> programBNodeList = DetectorASTParser.parseProgramToTypeAbbrs(programBStr);
		
		// get the matched node index pair([startIndex, endIndex]) list for the two programs
		List<SimilarNodeListPairs> matchedNodeIndexPairList = getMatchedNodeListPairs(programANodeList, programBNodeList);
		
		// get non-duplicated matched indexes for the two programs separately
		Set<StartEndNodeIndexPair> matchedNodeIndexPairsForA = getMatchedNodeIndexPairs(0, matchedNodeIndexPairList);
		Set<StartEndNodeIndexPair> matchedNodeIndexPairsForB = getMatchedNodeIndexPairs(1, matchedNodeIndexPairList);
		
		// calculate the similarity score for AST comparison
		float score = getSimilarityScore(matchedNodeIndexPairsForA, matchedNodeIndexPairsForB, 
						   programANodeList.size(), programBNodeList.size());
		
		// get the suspicious line numbers of the two programs separately
		List<Integer> suspiciousLineNumsOfA = getSuspiciousLineNumsOfProgram(programANodeList, matchedNodeIndexPairsForA);
		List<Integer> suspiciousLineNumsOfB = getSuspiciousLineNumsOfProgram(programBNodeList, matchedNodeIndexPairsForB);
		
		// message to create the Report
		String message = suspiciousLineNumsOfA.toString() + "\n" + suspiciousLineNumsOfB.toString();
		
		return new Report("2", score, message);
	}

	/**
	 * Find all suspicious line numbers from the given nodeList
	 * 
	 * @param nodeList - a list of Node representing a program
	 * @param nodeIndexPairs - a set of StartEndNodeIndexPair that is found to be matched with another program
	 * @return - a list of Integer that represents the suspicious line numbers of the program
	 */
	private List<Integer> getSuspiciousLineNumsOfProgram(List<Node> nodeList, Set<StartEndNodeIndexPair> nodeIndexPairs) {
		// a list of line numbers that found to be similar to another program 
		List<Integer> lineNumList = new ArrayList<Integer>();
		
		// iterate through nodeIndexPairs
		for(StartEndNodeIndexPair pair: nodeIndexPairs) {
			// get start index of the node pair
			int startNodeIndex = pair.getStartIndex();
			// get end index of the node pair
			int endNodeIndex = pair.getEndIndex();
			
			// get the start line number of the block that is found to be similar to another program 
			int start = nodeList.get(startNodeIndex).getStartLineNum();
			// get the end line number of the block that is found to be similar to another program 
			int end = nodeList.get(endNodeIndex).getEndLineNumber();
			
			// add all numbers in range [start, end] to lineNumList 
			for(int i = start; i <= end; i++) {
				lineNumList.add(i);
			}
		}
		
		// sort the line number in order
		Collections.sort(lineNumList);
		return lineNumList;
	}
	
	/**
	 * Calculate the similarity score: totalNumOfMatchedNodes / totalNumOfNodes 
	 * 
	 * @param matchedNodeIndexPairsForA - a set of StartEndNodeIndexPair for program A that is found to be matched with program B
	 * @param matchedNodeIndexPairsForB - a set of StartEndNodeIndexPair for program B that is found to be matched with program A
	 * @param numOfNodesInA - number of total nodes in program A
	 * @param numOfNodesInB - number of total nodes in program B
	 * @return - the simialrity score
	 */
	private float getSimilarityScore(Set<StartEndNodeIndexPair> matchedNodeIndexPairsForA, Set<StartEndNodeIndexPair> matchedNodeIndexPairsForB, 
								   int numOfNodesInA, int numOfNodesInB) {
		
		// get the number of matched nodes in two programs separately 
		int matchedNumOfNodesInA =  getNumOfMatchNodes(matchedNodeIndexPairsForA);
		int matchedNumOfNodesInB =  getNumOfMatchNodes(matchedNodeIndexPairsForB);
		
		// calculate the similarity score
		float score = (float)(matchedNumOfNodesInA + matchedNumOfNodesInB) / (numOfNodesInA + numOfNodesInB) * 100;
		
		return score;
	}
	
	/**
	 * Get the total number of matched nodes from the matchedNodeIndexPairs
	 * 
	 * @param matchedNodeIndexPairs - a set of StartEndNodeIndexPair the stores 
	 * 		  the [startNodeIndex, endNodeIndex] of matched blocks
	 * @return - the number of matched nodes
	 */
	private int getNumOfMatchNodes(Set<StartEndNodeIndexPair> matchedNodeIndexPairs) {
		// count the total number of matched nodes
		int count = 0;
		
		for(StartEndNodeIndexPair pair: matchedNodeIndexPairs) {
			// get the number of matched nodes for current StartEndNodeIndexPair
			int matchedNodesInBlock = pair.getEndIndex() - pair.getStartIndex() + 1;
			// add to the count
			count += matchedNodesInBlock;
		}
		
		return count;
	}
	
	
	/**
	 * Get matched node index pairs for specified program number
	 * 
	 * @param programNum - the program number (0 for first program, 1 for second program)
	 * @param matchedNodeIndexesList
	 * @return
	 */
	private Set<StartEndNodeIndexPair> getMatchedNodeIndexPairs(int programNum, List<SimilarNodeListPairs> matchedNodeIndexesList) {
		if (programNum != 0 || programNum != 1) {
			// TODO
			//throw new Exception();
		}
		
		// an array of StartEndNodeIndexPair that stores the matched node index pairs <startNodeIndex, endNodeIndex>
		StartEndNodeIndexPair[] matchedNodeIndexPairs = new StartEndNodeIndexPair[matchedNodeIndexesList.size()];
		
		
		if (programNum == 0) {
			// get matched node index pairs for first program
			for(int i = 0; i < matchedNodeIndexesList.size(); i++) {
				matchedNodeIndexPairs[i] = matchedNodeIndexesList.get(i).getFirstStartEndPair();
			}
		} else {
			// get matched node index pairs for second program
			for(int i = 0; i < matchedNodeIndexesList.size(); i++) {
				matchedNodeIndexPairs[i] = matchedNodeIndexesList.get(i).getSecondStartEndPair();
			}
		}
		
		// return the set of StartEndNodeIndexPair without duplicates
		return removeDuplicatedPairs(matchedNodeIndexPairs);
	}
	
	
	/**
	 * Remove duplications from the array of StartEndNodeIndexPair
	 * 
	 * @param matchedNodeIndexPairs
	 * @return - a set of matchedNodeIndexPairs
	 */
	private Set<StartEndNodeIndexPair> removeDuplicatedPairs(StartEndNodeIndexPair[] matchedNodeIndexPairs) {
		Set<StartEndNodeIndexPair> set = new HashSet<StartEndNodeIndexPair>();
		
		for(int i = 0; i < matchedNodeIndexPairs.length; i++) {
			set.add(matchedNodeIndexPairs[i]);
		}
		
		return set;
	}
	
	/**
	 * Get matched node list pairs
	 */
	private List<SimilarNodeListPairs> getMatchedNodeListPairs(List<Node> programANodeList, List<Node> programBNodeList) {
		HashSet<Match> matchedSubstrings = compareTwoNodeLists(programANodeList, programBNodeList);

		List<SimilarNodeListPairs> similarNodeListPairs = new ArrayList<SimilarNodeListPairs>();

		Iterator<Match> it = matchedSubstrings.iterator();
		while (it.hasNext()) {
			Match curSimilarBlock = it.next();
			int matchedLen = curSimilarBlock.getMatchLength();
			int startIndexofAStr = curSimilarBlock.getFirstStringIndex();
			int startIndexofBStr = curSimilarBlock.getSecondStringIndex();

			SimilarNodeListPairs pair = getSimilarNodeListPairs(startIndexofAStr, startIndexofBStr, matchedLen);
			if(pair != null) {
				similarNodeListPairs.add(pair);
			}
			
		}

		return similarNodeListPairs;
	}
	
	/**
	 * Get similar node list pairs
	 */
	private SimilarNodeListPairs getSimilarNodeListPairs(int startIndexofAStr, int startIndexofBStr, int matchedLen) {
		if(matchedLen % 2 == 0) {
			return getSimilarNodeListPairsForEvenMatchedLen(startIndexofAStr, startIndexofBStr, matchedLen);
		}
		
		return getSimilarNodeListPairsForOddMatchedLen(startIndexofAStr, startIndexofBStr, matchedLen);
	}
	
	/**
	 * Get similar node list pairs for odd matched length
	 */
	private SimilarNodeListPairs getSimilarNodeListPairsForEvenMatchedLen(int startIndexofAStr, int startIndexofBStr, int matchedLen) {
		SimilarNodeListPairs nodeListPairs = null;
		
		int startNodeIndexForA = -1;
		int endNodeIndexForA = -1;
		int startNodeIndexForB = -1;
		int endNodeIndexForB = -1;
		
		if (startIndexofAStr % 2 == 0 && startIndexofBStr % 2 == 0) {
			// 4 5 6 7 && 2 3 4 5
			// get start line number
			startNodeIndexForA = getStartNodeIndex(startIndexofAStr);
			endNodeIndexForA = getEndNodeIndex(startIndexofAStr, matchedLen);
			startNodeIndexForB = getStartNodeIndex(startIndexofBStr);
			endNodeIndexForB = getEndNodeIndex(startIndexofBStr, matchedLen);
		} else if (startIndexofAStr % 2 == 1 && startIndexofBStr % 2 == 1) {
			// 3 4 5 6 && 5 6 7 8
			startNodeIndexForA = getStartNodeIndex(startIndexofAStr + 1);
			endNodeIndexForA = getEndNodeIndex(startIndexofAStr + 1, matchedLen - 2);
			startNodeIndexForB = getStartNodeIndex(startIndexofBStr + 1);
			endNodeIndexForB = getEndNodeIndex(startIndexofBStr + 1, matchedLen - 2);
		}
		
		if(startNodeIndexForA != -1 && endNodeIndexForA != -1 && startNodeIndexForB != -1 && endNodeIndexForB != -1) {
			nodeListPairs = new SimilarNodeListPairs(startNodeIndexForA, endNodeIndexForA, 
															  startNodeIndexForB, endNodeIndexForB);
		}
		
		return nodeListPairs;
	}
	
	/**
	 * Get similar node list pairs for odd matched length
	 */
	private SimilarNodeListPairs getSimilarNodeListPairsForOddMatchedLen(int startIndexofAStr, int startIndexofBStr, int matchedLen) {
		SimilarNodeListPairs nodeListPairs = null;
		
		int startNodeIndexForA = -1;
		int endNodeIndexForA = -1;
		int startNodeIndexForB = -1;
		int endNodeIndexForB = -1;
		
		if (startIndexofAStr % 2 == 0 && startIndexofBStr % 2 == 0) {
			// 4 5 6 && 8 9 10

			startNodeIndexForA = getStartNodeIndex(startIndexofAStr);
			endNodeIndexForA = getEndNodeIndex(startIndexofAStr, matchedLen - 1);
			startNodeIndexForB = getStartNodeIndex(startIndexofBStr);
			endNodeIndexForB = getEndNodeIndex(startIndexofBStr, matchedLen - 1);
		} else if (startIndexofAStr % 2 == 1 && startIndexofBStr % 2 == 1) {
			// 3 4 5 && 5 6 7

			startNodeIndexForA = getStartNodeIndex(startIndexofAStr + 1);
			endNodeIndexForA = getEndNodeIndex(startIndexofAStr + 1, matchedLen - 1);
			startNodeIndexForB = getStartNodeIndex(startIndexofBStr + 1);
			endNodeIndexForB = getEndNodeIndex(startIndexofBStr + 1, matchedLen - 1);
		}
		
		if(startNodeIndexForA != -1 && endNodeIndexForA != -1 && startNodeIndexForB != -1 && endNodeIndexForB != -1) {
			nodeListPairs = new SimilarNodeListPairs(startNodeIndexForA, endNodeIndexForA, 
															  startNodeIndexForB, endNodeIndexForB);
		}
		
		return nodeListPairs;
	}
	
	/**
	 * Calculate the start node index
	 */
	private int getStartNodeIndex(int validEndIndexOfStr) {
		return validEndIndexOfStr / 2;
	}
	
	/**
	 * Calculate the end node index
	 */
	private int getEndNodeIndex(int validStartIndexOfStr, int len) {
		return (validStartIndexOfStr + len - 2) / 2;
	}
	
	
	/**
	 * Compare two node list of two ASTs using Greedy String Tilling Algorithm
	 */
	private HashSet<Match> compareTwoNodeLists(List<Node> programANodeList, 
											  List<Node> programBNodeList){
		// convert node list to string representations
		String programATypeAbbr = getProgramRepresentation(programANodeList);
		String programBTypeAbbr = getProgramRepresentation(programBNodeList);
		
		// call Greedy String Tilling algorithms to compare the Strings
		GreedyStringTilling gst=new GreedyStringTilling();
		HashSet<Match> matchedSubstrings = gst.GST(programATypeAbbr, programBTypeAbbr);
		
		Iterator<Match> it=matchedSubstrings.iterator();
		while(it.hasNext()) {
			it.next().textualRepresentation();
		}
		
		return matchedSubstrings;
	}
	
	/**
	 * Covert list of nodes into abbreviation of types
	 */
	private static String getProgramRepresentation(List<Node> nodeList) {
		StringBuilder sb = new StringBuilder();
		
		for(Node node: nodeList) {
			sb.append(node.getNodeTypeAbbr());
		}
		
		return sb.toString();
	}
}
