import java.util.*;

public class Search {
	/* 
	 * Keep all visited states in a HashTable.
	 * Create heuristic function that keeps track of # of misplaced tiles.
	 * Arrays.deepEquals(currentState, goal); <- This compares two 2D arrays, so this should save some time
	 * when comparing arrays.
	 */
	Node root;
	Map<int[][], Integer> visited = new Hashtable<int[][], Integer>();
	int[][] goal = {{1,2,3},{8,0,4},{7,6,5}};
	int depth = 0;
	boolean goalFound = false;

	//Constructor
	public Search(Node root) {
		this.root = root;
	}
	
	//Prints out the successor nodes from the given node.
	public LinkedList<Node> successor(Node node) {
		//Creates a temp node and copies the data from the given node by value.
		LinkedList<Node> successList = new LinkedList<Node>();
		Node temp = new Node();
		temp.setPuzzle(node.puzzle.puzzle);
		temp.puzzle.left();
		//If the node has not been visited, then we can add it to the visitors table.
		if(!isVisited(temp)) {
			Node child = new Node();
			child.setPuzzle(temp.puzzle.puzzle);
			child.setMove("left");
			child.setCost(temp.puzzle.cost);
			successList.add(child);
		}

		//Resets the data from temp node.
		temp.setPuzzle(node.puzzle.puzzle);
		temp.puzzle.right();
		if(!isVisited(temp)) {
			Node child = new Node();
			child.setPuzzle(temp.puzzle.puzzle);
			child.setMove("right");
			child.setCost(temp.puzzle.cost);
			successList.add(child);
		}

		temp.setPuzzle(node.puzzle.puzzle);
		temp.puzzle.up();
		if(!isVisited(temp)) {
			Node child = new Node();
			child.setPuzzle(temp.puzzle.puzzle);
			child.setMove("up");
			child.setCost(temp.puzzle.cost);
			successList.add(child);
		}

		temp.setPuzzle(node.puzzle.puzzle);
		temp.puzzle.down();
		if(!isVisited(temp)) {
			Node child = new Node();
			child.setPuzzle(temp.puzzle.puzzle);
			child.setMove("down");
			child.setCost(temp.puzzle.cost);
			successList.add(child);
		}

		return successList;
	}
	
	//Checks if the given node has already been visited.
	public boolean isVisited(Node node) {
		Set<int[][]> states = visited.keySet();
		for(int[][] s : states) {
			if(Arrays.deepEquals(s, node.puzzle.puzzle)) {
				return true;
			}
		}
		return false;
	}


	//Checks a given node if it contains the goal.
	public boolean isGoal(Node node) {
		if(Arrays.deepEquals(node.puzzle.puzzle, goal)) {
			return true;
		}
		return false;
	}


	//Prints the state of a given node.
	public void print(Node node) {
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				System.out.print(node.puzzle.puzzle[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}


	//Prints the parents from the goal node.
	public void printPath(Node node) {
		System.out.println(node.getMove());
		print(node);
		while(node.getParent() != null) {
			node = node.getParent();
			System.out.println(node.getMove());
			print(node);
		}
	}

	//Gets the length of the solution path.
	public int getLength(Node node) {
		int length = 0;
		while(node.getParent() != null) {
			length++;
			node = node.getParent();
		}
		return length;
	}

	//Gets the total cost of the solution path
	public int getCost(Node node) {
		int cost = 0;
		while(node.getParent() != null) {
			cost += node.getCost();
			node = node.getParent();
		}
		return cost;
	}

	/*
	 *STATS:
	 * 
	 * EASY:
	 * 
	 * -------------
	 * Length: 5
	 * Cost: 17
	 * Time: 54
	 * Space: 89
	 * -------------
	 * 
	 * MEDIUM:
	 * -------------
	 * Length: 9
	 * Cost: 31
	 * Time: 392
	 * Space: 640
	 * -------------
	 * 
	 * HARD:
	 * 	Exceeds 5 minute mark.
	 * 
	 */
	public void BFS(Node node) {
		System.out.println("BFS: ");
		LinkedList<Node> q = new LinkedList<Node>();
		int time = 0;
		int max = 0;
		//If the node is not the goal, we keep on looping.
		while(!isGoal(node)) {
			
			//Adds this node to the visited table.
			visited.put(node.puzzle.puzzle, 0);
			
			//Creates a successor Linked List.
			LinkedList<Node> successList = successor(node); 
			
			//Loops through the successor list.
			//Sets the parent, movement, and cost.
			//Once completed the node will add the successor node as a child and to the queue.
			for(Node s : successList) {
				visited.put(s.puzzle.puzzle, 0);
				Node child = new Node(s.puzzle);
				child.setParent(node);
				child.setMove(s.getMove());
				child.setCost(s.getCost());
				node.addChild(child);
				//First in
				q.add(child);
			}
			max = visited.size();
			//First out
			node = q.poll();
			time++;
		}
		System.out.println("-------------");
		System.out.println("Length: " + getLength(node));
		System.out.println("Cost: " + getCost(node));
		System.out.println("Time: " + time);
		System.out.println("Space: " + max + "\n");
		print(node);
		System.out.println("-------------");
		return;
	}

	/*
	 * STATS:
	 * EASY:
	 * 		Exceeds 5 minute mark.
	 * 
	 * MEDIUM:
	 * 		Exceeds 5 minute mark.
	 * 
	 * HARD:
	 *		-------------
	 *		Length: 4578
	 *		Cost: 20654
	 *		Time: 4701
	 * 		Space: 8352
	 *		-------------
	 */
	public void DFS(Node node) {
		System.out.println("DFS: ");
		LinkedList<Node> q = new LinkedList<Node>();
		int time = 0;
		int max = 0;
		while(!isGoal(node)) {
			visited.put(node.puzzle.puzzle, 0);
			LinkedList<Node> successList = successor(node); 
			for(Node s : successList) {
				visited.put(s.puzzle.puzzle, 0);
				Node child = new Node(s.puzzle);
				child.setParent(node);
				child.setMove(s.getMove());
				child.setCost(s.getCost());
				node.addChild(child);
				//Last in
				q.add(child);
			}
			max = visited.size();
			//First out
			node = q.pollLast();
			time++;
		}
		System.out.println("-------------");
		System.out.println("Length: " + getLength(node));
		System.out.println("Cost: " + getCost(node));
		System.out.println("Time: " + time);
		System.out.println("Space: " + max + "\n");
		print(node);
		System.out.println("-------------");
		return;
	}

	/*
	 * STATS:
	 * EASY:
	 * 		-------------
	 *		Length: 83
	 *		Cost: 331
	 *		Time: 144
	 * 		Space: 234
	 *		-------------
	 * 
	 * MEDIUM:
	 * 		Exceeds 5 minute mark.
	 * 
	 * HARD:
	 * 		Exceeds 5 minute mark.
	 * 
	 */
	public void uniformCost(Node node) {
		System.out.println("Uniform-Cost: ");
		Stack<Node> q = new Stack<Node>();
		Comparable comp = new Comparable();
		comp.setType("UniformCost");
		int time = 0;
		int max = 0;
		while(!isGoal(node)) {
			visited.put(node.puzzle.puzzle, 0);
			LinkedList<Node> successList = successor(node); 
			for(Node s : successList) {
				visited.put(s.puzzle.puzzle, 0);
				Node child = new Node(s.puzzle);
				child.setParent(node);
				child.setMove(s.getMove());
				child.setCost(s.getCost());
				node.addChild(child);
				q.push(child);

			}
			//This will sort the queue so that the lowest cost will always be first.
			q.sort(comp);
			node = q.pop();
			max = visited.size();
			time++;
		}
		System.out.println("-------------");
		System.out.println("Length: " + getLength(node));
		System.out.println("Cost: " + getCost(node));
		System.out.println("Time: " + time);
		System.out.println("Space: " + max + "\n");
		printPath(node);
		System.out.println("-------------");
		return;
	}

	/*
	 * STATS:
	 * 
	 * EASY:
	 * 		-------------
	 *		Length: 9
	 *		Cost: 31
	 *		Time: 9
	 * 		Space: 20
	 *		-------------
	 * 
	 * MEDIUM:
	 * 		-------------
	 *		Length: 21
	 *		Cost: 85
	 *		Time: 66
	 * 		Space: 116
	 *		-------------
	 * 
	 * HARD:
	 *		-------------
	 *		Length: 114
	 *		Cost: 494
	 *		Time: 620
	 * 		Space: 1036
	 *		-------------
	 * 
	 */
	public void bestFirst(Node node) {
		System.out.println("Best-First: ");
		Stack<Node> q = new Stack<Node>();
		Comparable comp = new Comparable();
		comp.setType("BestFirst");
		int time = 0;
		int max = 0;
		while(!isGoal(node)) {
			visited.put(node.puzzle.puzzle, 0);
			LinkedList<Node> successList = successor(node); 
			for(Node s : successList) {
				visited.put(s.puzzle.puzzle, 0);
				Node child = new Node(s.puzzle);
				child.setParent(node);
				child.setMove(s.getMove());
				child.setCost(s.getCost());
				child.diffTiles(s.puzzle);
				node.addChild(child);
				q.push(child);
			}
			
			//Sorts so that the state with the least amount of misplaced tiles will be expanded first.
			q.sort(comp);
			node = q.pop();
			max = visited.size();
			time++;
		}
		System.out.println("-------------");
		System.out.println("Length: " + getLength(node));
		System.out.println("Cost: " + getCost(node));
		System.out.println("Time: " + time);
		System.out.println("Space: " + max + "\n");
		printPath(node);
		System.out.println("-------------");
		return;
	}
	
	
	public void aStar(Node node) {
		System.out.println("A*: ");
		Stack<Node> q = new Stack<Node>();
		Comparable comp = new Comparable();
		comp.setType("A*");
		int time = 0;
		int max = 0;
		while(!isGoal(node)) {
			visited.put(node.puzzle.puzzle, 0);
			LinkedList<Node> successList = successor(node); 
			for(Node s : successList) {
				visited.put(s.puzzle.puzzle, 0);
				Node child = new Node(s.puzzle);
				child.setParent(node);
				child.setMove(s.getMove());
				child.setCost(s.getCost());
				child.calcManhattan(s.puzzle);
				node.addChild(child);
				q.push(child);
			}
			q.sort(comp);
			node = q.pop();
			max = visited.size();
			time++;
		}
		System.out.println("-------------");
		System.out.println("Length: " + getLength(node));
		System.out.println("Cost: " + getCost(node));
		System.out.println("Time: " + time);
		System.out.println("Space: " + max + "\n");
		printPath(node);
		System.out.println("-------------");
		return;
	}
	public static void main(String[] args) {
		Node tn = new Node(new Game(3,3));
		tn.puzzle.setType("hard");
		Search testing = new Search(tn);
		System.out.println("Starting Puzzle:" );
		testing.print(tn);
		testing.DFS(tn);
	}
}
