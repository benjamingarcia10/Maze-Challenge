package GarciaQuach.cs146.project3;

import java.io.PrintWriter;

public class PrintMaze {
	/**
	 * Output the created blank maze using passed in PrintWriter
	 * @param maze
	 * 		Maze Object to be Printed
	 * @param out
	 * 		PrintWriter for Output
	 */
	public static void printMaze(Maze maze, PrintWriter out) {
		out.println("Graph Size: " + maze.numberOfRows);
		out.println("MAZE:");
		for (Cell[] row: maze.mazeAsArray) {
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
			
			// Print the left and right walls for all cells
			out.print("|");
			for (Cell c: row) {
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
		for (Cell c: maze.mazeAsArray[maze.numberOfRows - 1]) {
			if (c.getSouthWall())
				out.print("-");
			else
				out.print(" ");
			out.print("+");
		}
		out.println();
	}
}
