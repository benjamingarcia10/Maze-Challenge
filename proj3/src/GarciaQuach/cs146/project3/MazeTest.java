package GarciaQuach.cs146.project3;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

class MazeTest {
	
	@Test
	void create2DArray() {
		Cell[][] test = MazeAs2D.create2DArray(5);
		assertEquals(test.length, 5);
	}
	
	@Test
	void test4x4() {
		ReadMaze test = new ReadMaze("sample-inputs\\maze4.txt");
		test.maze.solve(test.output);
	}
	
	@Test
	void test6x6() {
		ReadMaze test = new ReadMaze("sample-inputs\\maze6.txt");
		test.maze.solve(test.output);
	}
	
	@Test
	void test8x8() {
		ReadMaze test = new ReadMaze("sample-inputs\\maze6.txt");
		test.maze.solve(test.output);
	}
	
	@Test
	void test10x10() {
		ReadMaze test = new ReadMaze("sample-inputs\\maze10.txt");
		test.maze.solve(test.output);
	}
	
	@Test
	void test20x20() {
		ReadMaze test = new ReadMaze("sample-inputs\\maze20.txt");
		test.maze.solve(test.output);
	}
}
