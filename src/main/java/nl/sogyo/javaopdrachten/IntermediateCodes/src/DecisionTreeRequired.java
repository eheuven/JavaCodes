import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class DecisionTreeRequired {
	static HashMap<String,Node> nodeMap;
	
	private static void extractNodes(String fileName) throws IOException{
		BufferedReader dataFile = new BufferedReader(new FileReader(fileName));
		nodeMap = new HashMap<>();
		String line = "";
		
		while((line = dataFile.readLine())!= null) {
			String[] fields = line.split(", ");
			if(fields.length == 2) {
				nodeMap.put(fields[0],new Node(fields));
			}
		}
		dataFile.close();
	}
	
	public static void connectNodesWithEdges(String fileName) throws IOException {
		BufferedReader dataFile = new BufferedReader(new FileReader(fileName));
		String line = "";
		
		while((line = dataFile.readLine())!= null) {
			String[] fields = line.split(", ");
			if(fields.length == 3) {
				nodeMap.get(fields[0]).setDestination(fields[2], nodeMap.get(fields[1]));
			}
		}
		dataFile.close();
	}
	
	
	public static Node findFirstNode() {
		for (Node node : nodeMap.values()) {
			if(node.getPreviousNode() == null) {
				return node;
			}
		}	
		return null;
	}

	public static void readFile(String fileName) {
		try {
			extractNodes(fileName);
			connectNodesWithEdges(fileName);
		} catch (IOException e) {
			 System.out.println("File Read Error");
		}
	}
	
	public static void main(String[] args) {
		readFile("..\\..\\..\\..\\..\\resources\\intermediate\\decision-tree-data.txt"); // current map is IntermediateCodes
		
		Node firstNode = findFirstNode();
		firstNode.executeDecisionTree();
	}

}


class Node{
	String name;
	String question;
	Node previousNode;
	HashMap<String,Node> destinations;
	
	public Node(String[] fileFields) {
		name = fileFields[0];
		question = fileFields[1];
		previousNode = null;
		destinations = new HashMap<>();
	}
	
	public void setDestination(String answer, Node nextNode) {
		destinations.put(answer, nextNode);
		nextNode.setPrevious(this);
	}
	
	private void setPrevious(Node prevNode) {
		previousNode = prevNode;
	}
	
	public Node getPreviousNode() {
		return previousNode;
	}
	
	public void executeDecisionTree() {
		Scanner input = new Scanner(System.in);
		if (destinations.size() != 0) {
			System.out.println(question + " (Ja/Nee)");
			destinations.get(input.next()).executeDecisionTree();
		} else {
			System.out.println("Het blad is van een " + question);
			input.close();
		}	
	}

}


