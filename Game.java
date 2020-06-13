import java.util.Hashtable;
import java.util.Map;

public class Game {
	
	int[][] puzzle;
	int[][] easy = {{1,3,4},{8,6,2},{7,0,5}};
	int[][] medium = {{2,8,1},{0,4,3},{7,6,5}};
	int[][] hard = {{5,6,7},{4,0,8},{3,2,1}};
	int x,y;
	int count = 0;
	int cost = 0;

	//Constructor
	public Game(int dimX, int dimY) {
		puzzle = new int[dimX][dimY];

	}
	
	//Sets the difficulty of the puzzle
	public void setType(String difficulty) {
		if(difficulty == "easy") {
			puzzle = easy;
		}
		else if(difficulty == "medium") {
			puzzle = medium;
		}
		else if(difficulty == "hard") {
			puzzle = hard;
		} else {
			System.out.println("Invalid difficulty setting!");
		}
	}
	public void findZero() {
		for(int i = 0; i < puzzle.length; i++) {
			for(int j = 0; j < puzzle.length; j++) {
				if(puzzle[i][j] == 0) {
					x = i;
					y = j;
				}
			}
		}
	}

	//Idea: Work off of the empty space, and check if that can be swapped with any of the surrounding spaces
	//instead of checking each individual piece and if they can be moved into the empty space.

	//Checks the 0 tile if a certain move is valid.
	public boolean validMove(int x, int y) {
		//change 3 to be the dimensions of the puzzle
		if(x >= 0 && y >= 0 && x < 3 && y < 3) {
			return true;
		}
		return false;
	}

	//Moves the 0 tile up
	public void up() {
		findZero();
		if(validMove(x-1, y) == false) {
			return;
		}
		swap(x, y, x-1, y);
	}
	//Moves the 0 tile down
	public void down() {
		findZero();
		if(validMove(x+1, y) == false) {
			return;
		}
		swap(x, y, x + 1, y);
	}

	//Moves the 0 tile left
	public void left() {
		findZero();
		if(validMove(x, y-1) == false) {
			return;
		}
		swap(x, y, x, y - 1);
	}

	//Moves the 0 tile right
	public void right() {
		findZero();
		if(validMove(x, y+1) == false) {
			return;
		}
		swap(x, y, x, y + 1);
	}

	//Swaps the tiles
	public void swap(int x, int y, int newX, int newY) {
		int temp = puzzle[x][y];
		cost = puzzle[newX][newY];
		puzzle[x][y] = puzzle[newX][newY];
		puzzle[newX][newY] = temp;
		this.x = newX;
		this.y = newY;
	}

	public static void main(String args[]) {
		
		Game test = new Game(3,3);
		
		System.out.println("\nSTART PUZZLE");
		test.setType("easy");
		Map<Integer, String> testing = new Hashtable<Integer,String>();
		testing.put(5, "poop");
		testing.put(5, "yeet");
		
		
		System.out.println(test.x + "  " + test.y);
		test.left();
		test.left();
		System.out.println(test.x + "  " + test.y);
		System.out.println("\nCURRENT STATE");
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				System.out.print(test.puzzle[i][j]);
			}
			System.out.println();
		}
	}

}
