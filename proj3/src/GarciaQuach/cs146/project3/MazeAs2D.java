package GarciaQuach.cs146.project3;

public class MazeAs2D {
	/**
	 * Return a 2D array based on number of rows set
	 * @param numberOfRows
	 * 		Number of Rows in Array (# of rows = # of columns)
	 * @return
	 * 		2D Array of Size (numberOfRows x numberOfRows)
	 */
	public static Cell[][] create2DArray(int numberOfRows) {
		Cell[][] mazeAsArray =  new Cell[numberOfRows][numberOfRows];				// 2D array to hold maze in graph data structure
		System.out.println("Graph Size: " + numberOfRows);
		for (int column = 0; column < numberOfRows; column++) {
			for (int row = 0; row < numberOfRows; row++) {
				mazeAsArray[row][column] = new Cell(row, column);					// Initialize each cell in the grid
			}
		}
		
		// Sets the neighbors to the cell; Sets null if the neighbor is out of bounds
		for (int column = 0; column < numberOfRows; column++) {
			for (int row = 0; row < numberOfRows; row++) {
				if (row - 1 >= 0) {
					mazeAsArray[row][column].setNorthNeighbor(mazeAsArray[row - 1][column]);
				} else {
					mazeAsArray[row][column].setNorthNeighbor(null);
				}
				if (column + 1 < numberOfRows) {
					mazeAsArray[row][column].setEastNeighbor(mazeAsArray[row][column + 1]);
				} else {
					mazeAsArray[row][column].setEastNeighbor(null);
				}
				if (row + 1 < numberOfRows) {
					mazeAsArray[row][column].setSouthNeighbor(mazeAsArray[row + 1][column]);
				} else {
					mazeAsArray[row][column].setSouthNeighbor(null);
				}
				if (column - 1 >= 0) {
					mazeAsArray[row][column].setWestNeighbor(mazeAsArray[row][column - 1]);
				} else {
					mazeAsArray[row][column].setWestNeighbor(null);
				}
			}
		}
		return mazeAsArray;
	}
}
