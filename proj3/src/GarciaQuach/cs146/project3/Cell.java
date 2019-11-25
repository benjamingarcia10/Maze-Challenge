package GarciaQuach.cs146.project3;

public class Cell {
	// Pointers to Cell's neighbors
	private Cell northNeighbor;
	private Cell eastNeighbor;
	private Cell southNeighbor;
	private Cell westNeighbor;
	
	// Cell Walls
	// True = wall is intact; False = wall is knocked down
	private boolean northWall;
	private boolean eastWall;
	private boolean southWall;
	private boolean westWall;
	
	private int x;					// Row number of cell in 2D array
	private int y;					// Column number of cell in 2D array
	
	private int DFSOrder;			// Order of cell in DFS algorithm
	
	private SearchColor BFSColor;	// Color for use in BFS algorithm
	private SearchColor DFSColor;	// Color for use in DFS algorithm
	
	private int distance;			// Distance between cell and the source cell
	private Cell parent;			// The parent of the cell
	
	/**
	 * Initialize all cell walls to be intact and set x, y values
	 * @param x
	 * 		Row # in 2D Array
	 * @param y
	 * 		Column # in 2D Array
	 */
	public Cell(int x, int y) {
		this.northWall = true;
		this.eastWall = true;
		this.southWall = true;
		this.westWall = true;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Getter for North neighbor
	 * @return Cell's North Neighbor
	 */
	public Cell getNorthNeighbor() {
		return northNeighbor;
	}
	
	/**
	 * Setter for North neighbor
	 * @param northNeighbor
	 * 		Cell's North Neighbor
	 */
	public void setNorthNeighbor(Cell northNeighbor) {
		this.northNeighbor = northNeighbor;
	}

	/**
	 * Getter for East neighbor
	 * @return Cell's East Neighbor
	 */
	public Cell getEastNeighbor() {
		return eastNeighbor;
	}
	
	/**
	 * Setter for East neighbor
	 * @param eastNeighbor
	 * 		Cell's East Neighbor
	 */
	public void setEastNeighbor(Cell eastNeighbor) {
		this.eastNeighbor = eastNeighbor;
	}
	
	/**
	 * Getter for South neighbor
	 * @return Cell's South Neighbor
	 */
	public Cell getSouthNeighbor() {
		return southNeighbor;
	}
	
	/**
	 * Setter for South neighbor
	 * @param southNeighbor
	 * 		Cell's South Neighbor
	 */
	public void setSouthNeighbor(Cell southNeighbor) {
		this.southNeighbor = southNeighbor;
	}
	
	/**
	 * Getter for West neighbor
	 * @return Cell's West Neighbor
	 */
	public Cell getWestNeighbor() {
		return westNeighbor;
	}
	
	/**
	 * Setter for West Neighbor
	 * @param westNeighbor
	 * 		Cell's West Neighbor
	 */
	public void setWestNeighbor(Cell westNeighbor) {
		this.westNeighbor = westNeighbor;
	}
	
	/**
	 * Returns true if North wall is intact and false if broken
	 * @return True/False
	 */
	public boolean getNorthWall() {
		return this.northWall;
	}
	
	/**
	 * Set North wall to be intact or broken
	 * @param bool
	 * 		True = intact; False = broken
	 */
	public void setNorthWall(boolean bool) {
		this.northWall = bool;
	}
	
	/**
	 * Returns true if East wall is intact and false if broken
	 * @return True/False
	 */
	public boolean getEastWall() {
		return this.eastWall;
	}
	
	/**
	 * Set East wall to be intact or broken
	 * @param bool
	 * 		True = intact; False = broken
	 */
	public void setEastWall(boolean bool) {
		this.eastWall = bool;
	}
	
	/**
	 * Returns true if South wall is intact and false if broken
	 * @return True/False
	 */
	public boolean getSouthWall() {
		return this.southWall;
	}
	
	/**
	 * Set South wall to be intact or broken
	 * @param bool
	 * 		True = intact; False = broken
	 */
	public void setSouthWall(boolean bool) {
		this.southWall = bool;
	}
	
	/**
	 * Returns true if West wall is intact and false if broken
	 * @return True/False
	 */
	public boolean getWestWall() {
		return this.westWall;
	}
	
	/**
	 * Set West wall to be intact or broken
	 * @param bool
	 * 		True = intact; False = broken
	 */
	public void setWestWall(boolean bool) {
		this.westWall = bool;
	}
	
	/**
	 * Get the row # of the cell in the 2D array
	 * @return Cell's Row #
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * Get the column # of the cell in the 2D array
	 * @return Cell's Column #
	 */
	public int getY() {
		return this.y;
	}
	
	/**
	 * Getter for the order cell was in for DFS algorithm
	 * @return Order of cell visited by DFS algorithm
	 */
	public int getDFSOrder() {
		return DFSOrder;
	}
	
	/**
	 * Setter for the order cell was in for DFS algorithm
	 * @param value
	 * 		Order of cell visited by DFS algorithm
	 */
	public void setDFSOrder(int value) {
		DFSOrder = value;
	}
	
	/**
	 * Returns color of cell set in BFS algorithm
	 * @return Color of Cell in BFS algorithm(WHITE, GREY, or BLACK)
	 */
	public SearchColor getBFSColor() {
		return BFSColor;
	}
	
	/**
	 * Set color of cell set in BFS algorithm
	 * @param color
	 * 		Color of Cell in BFS algorithm(WHITE, GREY, or BLACK)
	 */
	public void setBFSColor(SearchColor color) {
		BFSColor = color;
	}
	
	/**
	 * Returns color of cell set in DFS algorithm
	 * @return Color of Cell in DFS algorithm (WHITE, GREY, or BLACK)
	 */
	public SearchColor getDFSColor() {
		return DFSColor;
	}
	
	/**
	 * Set color of cell set in DFS algorithm
	 * @param color
	 * 		Color of Cell in DFS algorithm(WHITE, GREY, or BLACK)
	 */
	public void setDFSColor(SearchColor color) {
		DFSColor = color;
	}
	
	/**
	 * Get the distance of the cell from the source cell
	 * @return
	 * 		Distance of the cell from the source cell
	 */
	public int getDistance() {
		return this.distance;
	}
	
	/**
	 * Returns the distance of cell from the source cell
	 * @param distance
	 * 		Distance of the cell from the source cell
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	/**
	 * Returns the cell's parent
	 * @return
	 * 		Parent Cell
	 */
	public Cell getParent() {
		return this.parent;
	}
	
	/**
	 * Set the cell's parent
	 * @param parent
	 * 		Parent Cell
	 */
	public void setParent(Cell parent) {
		this.parent = parent;
	}
	
	/**
	 * Checks if all walls in the cell are intact
	 * @return
	 * 		TRUE = all walls intact; FALSE = 1+ walls broken
	 */
	public boolean allWallsIntact() {
		return this.northWall && this.eastWall && this.southWall && this.westWall;
	}
	
	/**
	 * Override equals method to compare cells by x and y value
	 * @param other
	 * 		Cell Being Compared
	 * @return
	 * 		TRUE = Same Cell; FALSE = Different Cell
	 */
	public boolean equals(Cell other) {
		return (this.x == other.getX() && this.y == other.getY());
	}
}