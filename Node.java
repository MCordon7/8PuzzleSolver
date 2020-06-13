import java.util.Hashtable;
import java.util.Map;


public class Node {
	Map<Node, Integer> children = new Hashtable<Node, Integer>();
	Game puzzle;
	Node parent;
	int[][] goal = {{1,2,3},{8,0,4},{7,6,5}};
	int depth,cost,diff,manhattan,manX,manY = 0;
	String movement = "none";

	//Constructor
	public Node() {
		puzzle = new Game(3,3);
	}

	//Constructor
	public Node(Game puzzle) {
		this.puzzle = puzzle;
	}
	
	//First Heuristic: Calculates the number of tiles out of place from the goal.
	public void diffTiles(Game puzzle) {
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(puzzle.puzzle[i][j] != goal[i][j]) {
					diff++;
				}
			}
		}
	}
	
	//Gets the number of misplaced tiles
	public int getDiff() {
		return diff;
	}
	
	//Second Heuristic: Calculates the Manhattan distance.
	public void calcManhattan(Game puzzle) {
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(puzzle.puzzle[i][j] == goal[i][j]) {
					findGoal(puzzle.puzzle[i][j]);
					manhattan += Math.abs(i - manX);
					manhattan += Math.abs(j - manY);
				}
			}
		}
	}
	
	//Gets the Manhattan distance.
	public int getManhattan() {
		return manhattan;
	}
	
	public int getASTAR() {
		return manhattan + getCost();
	}
	
	//Finds the tile from the main state in the goal state.
	public void findGoal(int tile) {
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(goal[i][j] == tile) {
					manX = i;
					manY = j;
				}
			}
		}
	}
	
	//Sets the cost of a given move.
	public void setCost(int puzzle) {
		this.cost = puzzle;
	}

	//Gets the cost of a given move.
	public int getCost() {
		return cost;
	}

	//Sets which way the space moved.
	public void setMove(String move) {
		movement = move;
	}

	//Gets the movement from the current piece.
	public String getMove() {
		return movement;
	}

	//Sets the puzzle.
	public void setPuzzle(int[][] newPuzzle) {
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				this.puzzle.puzzle[i][j] = newPuzzle[i][j];
			}
		}
	}

	//Returns this nodes puzzle state.
	public Game getPuzzle() {
		return this.puzzle;
	}

	//Adds new children nodes
	public void addChild(Node newState) {
		this.children.put(newState, this.depth+1);
	}

	//Gets children nodes
	public Map<Node, Integer> getChildren() {
		return this.children;
	}


	//Sets parent node
	public void setParent(Node parent) {
		this.parent = parent;
	}


	//Gets parent nodes
	public Node getParent() {
		return parent;
	}


}
