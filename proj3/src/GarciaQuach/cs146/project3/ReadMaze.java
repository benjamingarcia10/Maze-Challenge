package GarciaQuach.cs146.project3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ReadMaze {
	File file;
	FileReader fr;
	BufferedReader br;
	String temp;
	String[] tempArray;
	String[][] characterArray;
	int numberOfRows;
	Maze maze;
	PrintWriter output;
	
	/**
	 * Create a maze from passed in file
	 * 
	 * @param fileName
	 * 		Filename to read from to create maze
	 */
	public ReadMaze(String fileName) {
		try {
			file = new File(fileName);
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			temp = br.readLine().trim();														// Read first line which contains # of rows and # of columns (separated by single space)
			tempArray = temp.split(" ");
			numberOfRows = Integer.parseInt(tempArray[0]);										// Set numberOfRows to be first int in first line of file
			
			characterArray = new String[(numberOfRows * 2) + 1][(numberOfRows * 2) + 1];		// Initialize characterArray to be (numberOfRows * 2) + 1 (to hold all characters from text file)
			for (int i = 0; i < characterArray.length; i++) {									// Add each character in maze array to characterArray
				temp = br.readLine().trim();
				for (int j = 0; j < characterArray[0].length; j++) {
					characterArray[i][j] = temp.substring(j, j + 1);
				}
			}
			
			output = new PrintWriter("output\\sample" + numberOfRows + " - SOLUTION.txt");		// Initialize PrintWriter
			maze = new Maze(MazeAs2D.create2DArray(numberOfRows));								// Create maze object with default (numberOfRows x numberOfRows) 2D Array
			
			// Cell Walls
			// True = wall is intact; False = wall is knocked down
			boolean northWall;
			boolean eastWall;
			boolean southWall;
			boolean westWall;
			// Run through each cell block (every other column in every other row)
			for (int i = 1; i < characterArray.length; i+= 2) {
				for (int j = 1; j < characterArray[0].length; j+= 2) {
					// If the North wall is there, set North wall flag to true and set cell's North wall to true otherwise do the opposite
					if (characterArray[i-1][j].equals("-")) {
						northWall = true;
						maze.mazeAsArray[i/2][j/2].setNorthWall(true);
					}
					else {
						northWall = false;
						maze.mazeAsArray[i/2][j/2].setNorthWall(false);
					}

					// If the East wall is there, set East wall flag to true and set cell's East wall to true otherwise do the opposite
					if (characterArray[i][j+1].equals("|")) {
						eastWall = true;
						maze.mazeAsArray[i/2][j/2].setEastWall(true);
					}
					else {
						eastWall = false;
						maze.mazeAsArray[i/2][j/2].setEastWall(false);
					}
					
					// If the South wall is there, set South wall flag to true and set cell's South wall to true otherwise do the opposite
					if (characterArray[i+1][j].equals("-")) {
						southWall = true;
						maze.mazeAsArray[i/2][j/2].setSouthWall(true);
					}
					else {
						southWall = false;
						maze.mazeAsArray[i/2][j/2].setSouthWall(false);
					}

					// If the West wall is there, set West wall flag to true and set cell's West wall to true otherwise do the opposite
					if (characterArray[i][j-1].equals("|")) {
						westWall = true;
						maze.mazeAsArray[i/2][j/2].setWestWall(true);
					}
					else {
						westWall = false;
						maze.mazeAsArray[i/2][j/2].setWestWall(false);
					}
					
					// If cell has a North neighbor, set the neighbor's wall to the corresponding North wall flag
					if (northWall && maze.mazeAsArray[i/2][j/2].getNorthNeighbor() != null) {
						maze.mazeAsArray[i/2][j/2].getNorthNeighbor().setSouthWall(true);
					} else if (!northWall && maze.mazeAsArray[i/2][j/2].getNorthNeighbor() != null) {
						maze.mazeAsArray[i/2][j/2].getNorthNeighbor().setSouthWall(false);
					}
					
					// If cell has an East neighbor, set the neighbor's wall to the corresponding East wall flag
					if (eastWall && maze.mazeAsArray[i/2][j/2].getEastNeighbor() != null) {
						maze.mazeAsArray[i/2][j/2].getEastNeighbor().setWestWall(true);
					} else if (!northWall && maze.mazeAsArray[i/2][j/2].getEastNeighbor() != null) {
						maze.mazeAsArray[i/2][j/2].getEastNeighbor().setWestWall(false);
					}
					
					// If cell has a South neighbor, set the neighbor's wall to the corresponding South wall flag
					if (southWall && maze.mazeAsArray[i/2][j/2].getSouthNeighbor() != null) {
						maze.mazeAsArray[i/2][j/2].getSouthNeighbor().setNorthWall(true);
					} else if (!northWall && maze.mazeAsArray[i/2][j/2].getSouthNeighbor() != null) {
						maze.mazeAsArray[i/2][j/2].getSouthNeighbor().setNorthWall(false);
					}
					
					// If cell has a West neighbor, set the neighbor's wall to the corresponding West wall flag
					if (westWall && maze.mazeAsArray[i/2][j/2].getEastNeighbor() != null) {
						maze.mazeAsArray[i/2][j/2].getEastNeighbor().setWestWall(true);
					} else if (!northWall && maze.mazeAsArray[i/2][j/2].getEastNeighbor() != null) {
						maze.mazeAsArray[i/2][j/2].getEastNeighbor().setWestWall(false);
					}
				}
			}
			
			maze.solve(output);							// Solve the sample file and output to the PrintWriter
			
			output.close();								// Close PrintWriter
			br.close();									// Close BufferedReader
			fr.close();									// Close FileReader
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ReadMaze test1 = new ReadMaze("sample-inputs\\maze4.txt");
		ReadMaze test2 = new ReadMaze("sample-inputs\\maze6.txt");
		ReadMaze test3 = new ReadMaze("sample-inputs\\maze8.txt");
		ReadMaze test4 = new ReadMaze("sample-inputs\\maze10.txt");
		ReadMaze test5 = new ReadMaze("sample-inputs\\maze20.txt");
	}
}
