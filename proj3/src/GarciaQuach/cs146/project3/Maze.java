package GarciaQuach.cs146.project3;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Maze {
	Cell[][] mazeAsArray;				// 2D array to hold maze in graph data structure
	Cell start;							// Starting cell
	Cell end;							// Ending cell (goal to reach)
	int numberOfRows;					// # of rows and columns for array (# of rows = # of columns)
	PrintWriter outputFile;				// PrintWriter for output file
	
	int DFSOrder;						// Visit order during DFS algorithm
	int DFSPath;						// Path length for DFS algorithm
	int DFSVisited;						// Amount of cells visited during DFS algorithm

	/**
	 * Constructor for the maze class with no arguments
	 * <br>
	 * Take user input for # of rows and create maze based on that input
	 */
	public Maze() {
		Scanner in = new Scanner(System.in);				// Scanner to accept user input for maze size
		
		System.out.print("Enter Size of Maze: ");
		numberOfRows = in.nextInt();															// Set numberOfRows to user input
		in.close();
		
		mazeAsArray = MazeAs2D.create2DArray(numberOfRows);										// Call function to initialize and create 2D array based on number of rows
		generateMaze();																			// Call function to generate perfect maze
		try {
			outputFile = new PrintWriter("output\\maze" + numberOfRows + " - SOLUTION.txt");	// Initialize PrintWriter to output to file named "maze(NUMBEROFROWS) - SOLUTION.txt" in output folder
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		solve(outputFile);																		// Call function to solve maze and output solution to text file
		outputFile.close();
	}
	
	/**
	 * Constructor for the maze class
	 * @param r
	 *		Number of Rows in Maze
	 */
	public Maze(int rows) {
		numberOfRows = rows;																	// Set numberOfRows to value passed in parameter r
		mazeAsArray = MazeAs2D.create2DArray(numberOfRows);										// Call function to initialize and create 2D array based on number of rows
		generateMaze();																			// Call function to generate perfect maze
		try {
			outputFile = new PrintWriter("output\\maze" + numberOfRows + " - SOLUTION.txt");	// Initialize PrintWriter to output to file named "maze(NUMBEROFROWS) - SOLUTION.txt" in output folder
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		solve(outputFile);																		// Call function to solve maze and output solution to text file
		outputFile.close();
	}
	
	/**
	 * Constructor for the maze class that initializes maze with 2D Array
	 * @param array
	 * 		Maze being initialized
	 * @param output
	 * 		PrintWriter to output to
	 */
	public Maze(Cell[][] array) {
		numberOfRows = array.length;															// Set numberOfRows to length of passed in 2D Array
		mazeAsArray = array;																	// Set maze representation to premade 2D Array
		setStartFinish();																		// Set start and finish points
	}
	
	/**
	 * Main function call which will call all other functions:
	 * <br>
	 * Solve maze using DFS and BFS, and output solution to text file
	 */
	public void solve(PrintWriter output) {
		// Print Created Maze
		PrintMaze.printMaze(this, output);
		output.println();
		output.println();
		
		// Solve and Print Maze Solution Using DFS
		solveDFS(output);
		output.println();
		output.println();
		
		// Solve and Print Maze Solution Using BFS
		solveBFS(output);
		output.println();
		
		output.println("======================");
		output.println("  Program Completed!");
		output.println("======================");
		output.close();
	}
	
	/**
	 * Set start and finish points
	 */
	private void setStartFinish() {
		start = mazeAsArray[0][0];										// Set starting position to be top left
		end = mazeAsArray[numberOfRows-1][numberOfRows-1];				// Set ending position (goal) to be bottom right
		start.setNorthWall(false);										// Open top left wall for starting position
		end.setSouthWall(false);										// Open bottom right wall for ending position
	}
	
	/**
	 * Generates a maze using DFS
	 */
	private void generateMaze() {
		Stack<Cell> cellStack = new Stack<>();							// Stack for cells to be visited
		int totalCells = numberOfRows * numberOfRows;					// # of cells in maze
		Cell currentCell = mazeAsArray[0][0];							// Set initial cell to start
		int visitedCells = 1;											// Mark visitedCells = 1 (visited start cell)
		while (visitedCells < totalCells) {
			ArrayList<Cell> neighborsWithWalls = new ArrayList<>();		// ArrayList to hold all neighbors of currentCell that have walls in tact
			// If currentCell's North neighbor has all walls intact, add it to the list
			if (currentCell.getNorthNeighbor() != null && currentCell.getNorthNeighbor().allWallsIntact()) {
				neighborsWithWalls.add(currentCell.getNorthNeighbor());
			}
			// If currentCell's East neighbor has all walls intact, add it to the list
			if (currentCell.getEastNeighbor() != null && currentCell.getEastNeighbor().allWallsIntact()) {
				neighborsWithWalls.add(currentCell.getEastNeighbor());
			}
			// If currentCell's South neighbor has all walls intact, add it to the list
			if (currentCell.getSouthNeighbor() != null && currentCell.getSouthNeighbor().allWallsIntact()) {
				neighborsWithWalls.add(currentCell.getSouthNeighbor());
			}
			// If currentCell's West neighbor has all walls intact, add it to the list
			if (currentCell.getWestNeighbor() != null && currentCell.getWestNeighbor().allWallsIntact()) {
				neighborsWithWalls.add(currentCell.getWestNeighbor());
			}
			
			// If list is not empty, choose a random cell to knock the wall down between it and currentCell
			if (neighborsWithWalls.size() >= 1) {
				Random gen = new Random();
				Cell selected = neighborsWithWalls.get(gen.nextInt(neighborsWithWalls.size()));			// Set selected to be a random cell in the ArrayList
				// If the currentCell is the selected cell's North neighbor, knock down the North wall of selected cell and the South wall of currentCell
				if (selected.getNorthNeighbor() != null && selected.getNorthNeighbor().equals(currentCell)) {
					selected.setNorthWall(false);
					currentCell.setSouthWall(false);
				}
				// If the currentCell is the selected cell's East neighbor, knock down the East wall of selected cell and the West wall of currentCell
				else if (selected.getEastNeighbor() != null && selected.getEastNeighbor().equals(currentCell)) {
					selected.setEastWall(false);
					currentCell.setWestWall(false);
				}
				// If the currentCell is the selected cell's South neighbor, knock down the South wall of selected cell and the North wall of currentCell
				else if (selected.getSouthNeighbor() != null && selected.getSouthNeighbor().equals(currentCell)) {
					selected.setSouthWall(false);
					currentCell.setNorthWall(false);
				}
				// If the currentCell is the selected cell's West neighbor, knock down the West wall of selected cell and the East wall of currentCell
				else if (selected.getWestNeighbor() != null && selected.getWestNeighbor().equals(currentCell)) {
					selected.setWestWall(false);
					currentCell.setEastWall(false);
				}
				cellStack.push(currentCell);			// Add currentCell to stack
				currentCell = selected;					// Set currentCell to the newly selected cell
				visitedCells++;							// Increase visitedCells
			} else {
				currentCell = cellStack.pop();			// Go to the next cell in the stack
			}
		}
		setStartFinish();								// Set start and finish points
	}
	
	/**
	 * Solve the maze using DFS algorithm and print to file using passed in PrintWriter
	 * @param out
	 */
	public void solveDFS(PrintWriter out) {
		for (Cell[] row: mazeAsArray) {
			for (Cell c: row) {
				c.setDFSColor(SearchColor.WHITE);		// Initialize each cell to have WHITE color
				c.setParent(null);						// Initialize each cell's parent to be null
			}
		}
		DFSOrder = 0;									// Set DFS visit order to be 0 (at start)
		DFSVisited = 0;									// Set amount of cells visited to be 0
		DFSVisit(start);								// Call recursive function to visit all cells using DFS algorithm until end is reached
		
		out.println("DFS:");
		for (Cell[] row: mazeAsArray) {
			// Print the top walls for all cells with '+' for corners
			out.print("+");
			for (Cell c: row) {
				if (c.getNorthWall())
					out.print("-");
				else
					out.print(" ");
				out.print("+");
			}
			out.println();
			
			// Print the left and right walls for all cells and the DFS order in the cell
			out.print("|");
			for (Cell c: row) {
				if (c.getDFSOrder() != 0 || c.equals(start))
					out.print(c.getDFSOrder() % 10);
				else
					out.print(" ");
				if (c.getEastWall())
					out.print("|");
				else
					out.print(" ");
			}
			out.println();
		}
		
		// Print the bottom walls for the last row of cells with '+' for corners
		out.print("+");
		for (Cell c: mazeAsArray[numberOfRows - 1]) {
			if (c.getSouthWall())
				out.print("-");
			else
				out.print(" ");
			out.print("+");
		}
		out.println();
		out.println();
		
		printDFSPath(out);
		out.printf("\nLength of Path: %d\n", DFSPath);
		out.printf("\nVisited Cells: %d\n", DFSVisited);
	}
	
	/**
	 * Recursive method to visit cells using DFS
	 * @param c
	 */
	private void DFSVisit(Cell c) {
		DFSVisited++;											// Increase amount of cells visited
		c.setDFSColor(SearchColor.GREY);						// Switch color of cell to GREY (discovered, not fully explored)
		c.setDFSOrder(DFSOrder);								// Set DFS order of cell
		DFSOrder++;												// Increase DFS order by 1
		// If cell is the end cell, complete function call
		if (c.equals(end))
			return;
		// If wall between cell and North neighbor is open and the neighbor's DFS SearchColor is white, run DFSVisit on North neighbor
		if (c.getNorthNeighbor() != null && !c.getNorthWall() && !c.getNorthNeighbor().getSouthWall() && c.getNorthNeighbor().getDFSColor() == SearchColor.WHITE) {
			c.getNorthNeighbor().setParent(c);
			DFSVisit(c.getNorthNeighbor());
		}
		// If wall between cell and East neighbor is open and the neighbor's DFS SearchColor is white, run DFSVisit on East neighbor
		if (c.getEastNeighbor() != null && !c.getEastWall() && !c.getEastNeighbor().getWestWall() && c.getEastNeighbor().getDFSColor() == SearchColor.WHITE) {
			c.getEastNeighbor().setParent(c);
			DFSVisit(c.getEastNeighbor());
		}
		// If wall between cell and South neighbor is open and the neighbor's DFS SearchColor is white, run DFSVisit on South neighbor
		if (c.getSouthNeighbor() != null && !c.getSouthWall() && !c.getSouthNeighbor().getNorthWall() && c.getSouthNeighbor().getDFSColor() == SearchColor.WHITE) {
			c.getSouthNeighbor().setParent(c);
			DFSVisit(c.getSouthNeighbor());
		}
		// If wall between cell and West neighbor is open and the neighbor's DFS SearchColor is white, run DFSVisit on West neighbor
		if (c.getWestNeighbor() != null && !c.getWestWall() && !c.getWestNeighbor().getEastWall() && c.getWestNeighbor().getDFSColor() == SearchColor.WHITE) {
			c.getWestNeighbor().setParent(c);
			DFSVisit(c.getWestNeighbor());
		}
		c.setDFSColor(SearchColor.BLACK);						// Switch cell color to BLACK (fully explored)
	}
	
	/**
	 * Print the maze path created by DFS algorithm using PrintWriter passed in parameter
	 * @param out
	 */
	private void printDFSPath(PrintWriter out) {
		ArrayList<Cell> path = new ArrayList<>();				// ArrayList holding cells for solution path
		Cell currentCell = end;									// Start from the end
		while (currentCell != null) {
			path.add(currentCell);								// While currentCell is not null, add the currentCell to the path ArrayList and set the currentCell to it's parent:
			currentCell = currentCell.getParent();				// which gives path from end all the way back to start
		}
		
		for (Cell[] row: mazeAsArray) {
			// Print the top walls for all cells with '+' for corners
			out.print("+");
			for (Cell c: row) {
				if (c.getNorthWall())
					out.print("-");
				else
					out.print(" ");
				out.print("+");
			}
			out.println();
			
			// Print the left and right walls for all cells and '#' for cells in the solution path
			out.print("|");
			for (Cell c: row) {
				if (path.contains(c))
					out.print("#");
				else
					out.print(" ");
				if (c.getEastWall())
					out.print("|");
				else
					out.print(" ");
			}
			out.println();
		}
		
		// Print the bottom walls for the last row of cells with '+' for corners
		out.print("+");
		for (Cell c: mazeAsArray[numberOfRows - 1]) {
			if (c.getSouthWall())
				out.print("-");
			else
				out.print(" ");
			out.print("+");
		}
		out.println();
		out.println();
		
		out.print("Path:");
		for (int i = path.size() - 1; i >= 0; i--) {
			out.printf(" (%d, %d)", path.get(i).getX(), path.get(i).getY());		// Print entire path by printing (x, y) values from last cell in ArrayList to beginning
		}
		DFSPath = path.size();														// Set the path length to size of ArrayList
		out.println();
	}
	
	/**
	 * Solve the maze using BFS algorithm and print using passed in PrintWriter
	 * @param out The PrintWriter to output
	 */
	public void solveBFS(PrintWriter out) { 
		Queue<Cell> queue = new LinkedList<>();				// Queue to hold all cells to be visited
		ArrayList<Cell> solution = new ArrayList<>();		// ArrayList holding cells for solution path
		int numberOfVisitedCells = 0;						// Initialize numberOfVisitedCells to 0
		for (Cell[] row : this.mazeAsArray) {
			for (Cell c : row) {
				c.setBFSColor(SearchColor.WHITE);			// Initialize each cell to have WHITE color
				c.setParent(null);							// Initialize each cell's parent to be null
			}
		}
		start.setBFSColor(SearchColor.GREY);				// Change starting cell color to GREY (visited but not fully explored)
		start.setDistance(0);								// Set starting cell distance to 0
		//start.setParent(null);
		queue.add(start);									// Add starting cell to queue
		solution.add(start);								// Add starting cell to solution path
		while (queue.size() != 0) {
			Cell vertex = queue.remove();
			if (vertex.equals(end)) {
				vertex.setDistance(vertex.getParent().getDistance() + 1);
				vertex.setBFSColor(SearchColor.BLACK);
				numberOfVisitedCells++;
				Cell parent = end.getParent();
				// Find the correct solution by checking until the parent is the start
				do {
					// If cell is dead end, move on to next parent
					if (parent.getNorthWall() && parent.getEastWall() && parent.getWestWall())
						parent = parent.getParent();
					else if (parent.getNorthWall() && parent.getEastWall() && parent.getSouthWall())
						parent = parent.getParent();
					else if (parent.getEastWall() && parent.getSouthWall() && parent.getWestWall())
						parent = parent.getParent();
					else if (parent.getNorthWall() && parent.getWestWall() && parent.getSouthWall())
						parent = parent.getParent();
					else {
						solution.add(parent);
						parent = parent.getParent();
					}

				} while (parent != start);
				
				solution.add(end);
				Cell temp = solution.get(solution.size() - 1);
				solution.set(solution.size() - 1, solution.get(0));
				solution.set(0, temp);
				Collections.reverse(solution);							// Order is from end to start, reverse to get start to end
				break;
			}
			// If the North neighbor exists and there is no wall, add to queue
			if (vertex.getNorthNeighbor() != null && vertex.getNorthNeighbor().getSouthWall() == false
					&& vertex.getNorthNeighbor().getBFSColor() == SearchColor.WHITE) {
				vertex.getNorthNeighbor().setBFSColor(SearchColor.GREY);
				vertex.getNorthNeighbor().setDistance(vertex.getDistance() + 1);
				vertex.getNorthNeighbor().setParent(vertex);
				numberOfVisitedCells++;
				queue.add(vertex.getNorthNeighbor());
			}
			// If the East neighbor exists and there is no wall, add to queue
			if (vertex.getEastNeighbor() != null && vertex.getEastNeighbor().getWestWall() == false
					&& vertex.getEastNeighbor().getBFSColor() == SearchColor.WHITE) {
				vertex.getEastNeighbor().setBFSColor(SearchColor.GREY);
				vertex.getEastNeighbor().setDistance(vertex.getDistance() + 1);
				vertex.getEastNeighbor().setParent(vertex);
				numberOfVisitedCells++;
				queue.add(vertex.getEastNeighbor());
			}
			// If the South neighbor exists and there is no wall, add to queue
			if (vertex.getSouthNeighbor() != null && vertex.getSouthNeighbor().getNorthWall() == false
					&& vertex.getSouthNeighbor().getBFSColor() == SearchColor.WHITE) {
				vertex.getSouthNeighbor().setBFSColor(SearchColor.GREY);
				vertex.getSouthNeighbor().setDistance(vertex.getDistance() + 1);
				vertex.getSouthNeighbor().setParent(vertex);
				numberOfVisitedCells++;
				queue.add(vertex.getSouthNeighbor());
			}
			// If the West neighbor exists and there is no wall, add to queue
			if (vertex.getWestNeighbor() != null && vertex.getWestNeighbor().getEastWall() == false
					&& vertex.getWestNeighbor().getBFSColor() == SearchColor.WHITE) {
				vertex.getWestNeighbor().setBFSColor(SearchColor.GREY);
				vertex.getWestNeighbor().setDistance(vertex.getDistance() + 1);
				vertex.getWestNeighbor().setParent(vertex);
				numberOfVisitedCells++;
				queue.add(vertex.getWestNeighbor());
			}
			vertex.setBFSColor(SearchColor.BLACK);						// Set the BFSColor to black after all neighbors are visited
		}
		out.println("BFS:");
		for (Cell[] row : mazeAsArray) {
			// Print the top walls for all cells with '+' for corners
			out.print("+");
			for (Cell c : row) {
				if (c.getNorthWall())
					out.print("-");
				else
					out.print(" ");
				out.print("+");
			}
			out.println();

			// Print the left and right walls for all cells and the order visited by BFS algorithm
			out.print("|");
			for (Cell c : row) {
				if (c.getEastWall() && c.getBFSColor() == SearchColor.BLACK)
					out.print(c.getDistance() % 10 + "|");
				else if (!c.getEastWall() && c.getBFSColor() == SearchColor.BLACK)
					out.print(c.getDistance() % 10 + " ");
				else if (c.getEastWall())
					out.print(" |");
				else
					out.print("  ");
			}
			out.println();
		}

		// Print the bottom walls for the last row of cells with '+' for corners
		out.print("+");
		for (Cell c : mazeAsArray[numberOfRows - 1]) {
			if (c.getSouthWall())
				out.print("-");
			else
				out.print(" ");
			out.print("+");
		}
		out.println();
		out.println();

		for (Cell[] row : mazeAsArray) {
			// Print the top walls for all cells with '+' for corners
			out.print("+");
			for (Cell c : row) {
				if (c.getNorthWall())
					out.print("-");
				else
					out.print(" ");
				out.print("+");
			}
			out.println();

			// Print the left and right walls for all cells with '#' for cell in solution path
			out.print("|");
			for (Cell c : row) {
				if (c.equals(start) && c.getEastWall())
					out.print("#|");
				else if (c.equals(start) && !c.getEastWall())
					out.print("# ");
				else if (solution.contains(c) && c.getEastWall() && c.getBFSColor() == SearchColor.BLACK)
					out.print("#|");
				else if (solution.contains(c) && !c.getEastWall() && c.getBFSColor() == SearchColor.BLACK)
					out.print("# ");
				else if (c.getEastWall())
					out.print(" |");
				else
					out.print("  ");
			}
			out.println();
		}
		// Print the bottom walls for the last row of cells with '+' for corners
		out.print("+");
		for (Cell c : mazeAsArray[numberOfRows - 1]) {
			if (c.getSouthWall())
				out.print("-");
			else
				out.print(" ");
			out.print("+");
		}
		// Print path, length of path, and visited cells
		out.println("\n");
		out.print("Path: ");
		for (Cell c : solution) {
			out.print("(" + c.getX() + "," + c.getY() + ") ");
		}
		out.println("\n");
		out.println("Length of path: " + solution.size() + "\n");
		out.println("Visited cells: " + numberOfVisitedCells);
	}

	public static void main(String[] args) {
		Maze maze = new Maze();
	}
}
