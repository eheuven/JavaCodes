import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class DecisionTreeRequired {
	static HashMap<String,Node> nodeMap;
	static ArrayList<Edge> edgeList;
	
	private static void extractNodes(String fileName) throws IOException{
		BufferedReader dataFile = new BufferedReader(new FileReader(fileName));
		HashMap<String,Node> nodes = new HashMap<>();
		String line = "";
		
		while((line = dataFile.readLine())!= null) {
			String[] fields = line.split(", ");
			if(fields.length == 2) {
				nodes.put(fields[0],new Node(fields));
			}
		}
		dataFile.close();
		nodeMap = nodes;
	}
	
	private static void assignEdges(String fileName) throws IOException {
		BufferedReader dataFile = new BufferedReader(new FileReader(fileName));
		ArrayList<Edge> edges = new ArrayList<>();
		String line = "";
		
		while((line = dataFile.readLine())!= null) {
			String[] fields = line.split(", ");
			if(fields.length == 3) {
				edges.add(new Edge(nodeMap.get(fields[0]),nodeMap.get(fields[1]),fields[2]));
			}
		}
		dataFile.close();
		edgeList = edges;
	}
	
	private static Node findFirstNode() {
		for (Node node : nodeMap.values()) {
			boolean startNode = true;
			for(Edge edge : edgeList) {
				if (node == edge.getDestinationNode()) {
					startNode = false;
					break;
				}
			}
			if (startNode) {
				return node;
			}
		}
		return null ;
	}

	private static Node nextNode(Node origin, boolean answer) {
		for(Edge edge : edgeList) {
			if(origin == edge.getOriginNode() && (answer == edge.getAnswer())) {
				return edge.getDestinationNode();
			}
		}
		return null;
	}
	
	private static boolean isAnswerNode(Node node) {
		for(Edge edge : edgeList) {
			if(node == edge.getOriginNode()) {
				return false;
			}
		}
		return true;
	}
	
	public static void readFile(String fileName) {
		try {
			extractNodes(fileName);
			assignEdges(fileName);
		} catch (IOException e) {
			 System.out.println("File Read Error");
		}
	}
	
	public static void executeDecisionTree() {
		Scanner sc = new Scanner(System.in);
		Node currentNode = findFirstNode();
		
		while (currentNode != null) {
			System.out.println(currentNode.getQuestion() + " (Ja/Nee)");
			String answer = sc.next();
			currentNode = nextNode(currentNode,answer.equals("Ja"));
			
			if (isAnswerNode(currentNode)) {
				System.out.println("Het blad is van een " + currentNode.getQuestion());
				break;
			}	
		}
		sc.close();
	}

	public static void main(String[] args) {
		readFile("..\\..\\..\\..\\..\\resources\\intermediate\\decision-tree-data.txt"); // current map is IntermediateCodes

		executeDecisionTree();
	}

}


class Node{
	String name;
	String question;
	
	public Node(String[] fileFields) {
		name = fileFields[0];
		question = fileFields[1];
	}
	
	public String getQuestion() {
		return question;
	}
}

class Edge{
	Node originNode;
	Node destinationNode;
	Boolean answerBool;
	
	public Edge(Node origin, Node destination, String answer) {
		originNode = origin;
		destinationNode = destination;
		answerBool = answer.equals("Ja");
	}
	
	public Node getDestinationNode() {
		return destinationNode;
	}
	
	public Node getOriginNode() {
		return originNode;
	}
	
	public Boolean getAnswer() {
		return answerBool;
	}
	
}

