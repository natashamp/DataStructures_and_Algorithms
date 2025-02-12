package linprabhoo.cs146.project3;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import linprabhoo.cs146.project3.MazeSolution.DIR;
import linprabhoo.cs146.project3.MazeSolution.Graph;

class TestMazeSolution {

	class MazeDetails {
		public int x;
		public int y;
		public int[][] maze;

		MazeDetails(int x, int y, int[][] maze) {
			this.x = x;
			this.y = y;
			this.maze = maze;
		}
	}

	/**
	 * @return
	 */
	//	return object mazeDetails which had total number of rows and 
	// 	columns and the 2d maze array with the bit representation of 
	//	direction of the rooms. This function reads the sample input 
	// 	and builds this 2d array for DFS and BFS traversal
	public MazeDetails getMazeInput(String filePath) {
		
		Scanner scanner = null;
		try {
			scanner = new Scanner(new FileReader(filePath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String size = scanner.nextLine();
//		System.out.println(size);
		String[] dimensions = size.split("\\s");
		String width = dimensions[0];
		String length = dimensions[1];
		int x = Integer.parseInt(width);
		int y = Integer.parseInt(length);
		char[][] array = new char[(x * 2) + 1][y * 2];

		for (int i = 0; i < array.length; i++) {
			String line = scanner.nextLine();
			array[i] = line.toCharArray();
		}


		int[][] mazeInput = new int[x][y];
		int x1 = 0;
		for (int i = 1; i < array.length; i = i + 2) {
			int y1 = 0;
			for (int j = 1; j < array[0].length; j = j + 2) {
				int value = 0;
				for (DIR d : DIR.values()) {
					if (array[i + d.dx][j + d.dy] == ' ') {
						value |= d.bit;
					}
				}
				// System.out.print(value + " ");
				mazeInput[x1][y1++] = value;
			}
			x1++;
			// System.out.println();
		}
		mazeInput[0][0] &= (~DIR.NORTH.bit);
		mazeInput[x - 1][y - 1] &= (~DIR.SOUTH.bit);

		MazeDetails mazeDetails = new MazeDetails(x, y, mazeInput);
		return mazeDetails;
	}

	//	DFS traversal for sample input maze20.txt.  The output is in PrintMazeDFS20X20.txt
	@Test
	void testUsing20x20FileDFS() {
		MazeDetails mazeDetails = getMazeInput(
				"/Users/natashaprabhoo/eclipse-workspace/cs146F22.Prabhoo.project3/src/linprabhoo/cs146/project3/maze20.txt");

		MazeSolution maze = new MazeSolution(mazeDetails.x, mazeDetails.y, mazeDetails.maze);
		Graph graph = maze.createGraph();

		System.out.println();
		// maze.getGraph().printAdjList();
		// maze.displayMaze();

//		maze.runDFS();
//		maze.getDFSPath();
		maze.getdisplayDFSMaze();
		maze.getdisplaydiscoveryPathDFSMaze();
		maze.getSaveToFileDFSMaze(graph);

		// Check if length of path is 61
		assertEquals(61,
				(graph.countPath(graph.DFS(1, mazeDetails.x * mazeDetails.y), 1, mazeDetails.x * mazeDetails.y, 0)));
		assertEquals(83, (graph.time + 1)); // Number of Visited cells should be 83
	}

//	BFS traversal for sample input maze20.txt.  The output is in PrintMazeBFS20X20.txt
	@Test
	void testUsing20x20FileBFS() {
		MazeDetails mazeDetails = getMazeInput(
				"/Users/natashaprabhoo/eclipse-workspace/cs146F22.Prabhoo.project3/src/linprabhoo/cs146/project3/maze20.txt");

		MazeSolution maze = new MazeSolution(mazeDetails.x, mazeDetails.y, mazeDetails.maze);
		Graph graph = maze.createGraph();

		System.out.println();
		// maze.getGraph().printAdjList();
		// maze.displayMaze();

//		maze.runDFS();
//		maze.getDFSPath();
		maze.getdisplayBFSMaze();
		maze.getdisplaydiscoveryPathBFSMaze();
		maze.getSaveToFileBFSMaze(graph);

		// Check if length of path is 61
		assertEquals(61,
				(graph.countPath(graph.BFS(1, mazeDetails.x * mazeDetails.y), 1, mazeDetails.x * mazeDetails.y, 0)));
		assertEquals(377, (graph.time + 1)); // Number of Visited cells should be 377
	}

//	DFS traversal for sample input maze10.txt.  The output is in PrintMazeDFS10X10.txt
	@Test
	void testUsing10x10FileDFS() {
		MazeDetails mazeDetails = getMazeInput(
				"/Users/natashaprabhoo/eclipse-workspace/cs146F22.Prabhoo.project3/src/linprabhoo/cs146/project3/maze10.txt");

		MazeSolution maze = new MazeSolution(mazeDetails.x, mazeDetails.y, mazeDetails.maze);
		Graph graph = maze.createGraph();

		System.out.println();
		// maze.getGraph().printAdjList();
		// maze.displayMaze();

//		maze.runDFS();
//		maze.getDFSPath();
		maze.getdisplayDFSMaze();
		maze.getdisplaydiscoveryPathDFSMaze();
		maze.getSaveToFileDFSMaze(graph);

		// Check if length of path is 21
		assertEquals(21,
				(graph.countPath(graph.DFS(1, mazeDetails.x * mazeDetails.y), 1, mazeDetails.x * mazeDetails.y, 0)));
		assertEquals(21, (graph.time + 1)); // Number of Visited cells should be 21
	}

//	BFS traversal for sample input maze10.txt.  The output is in PrintMazeBFS10X10.txt
	@Test
	void testUsing10x10FileBFS() {
		MazeDetails mazeDetails = getMazeInput(
				"/Users/natashaprabhoo/eclipse-workspace/cs146F22.Prabhoo.project3/src/linprabhoo/cs146/project3/maze10.txt");

		MazeSolution maze = new MazeSolution(mazeDetails.x, mazeDetails.y, mazeDetails.maze);
		Graph graph = maze.createGraph();

		System.out.println();
		// maze.getGraph().printAdjList();
		// maze.displayMaze();

//		maze.runDFS();
//		maze.getDFSPath();
		maze.getdisplayBFSMaze();
		maze.getdisplaydiscoveryPathBFSMaze();
		maze.getSaveToFileBFSMaze(graph);

		// Check if length of path is 21
		assertEquals(21,
				(graph.countPath(graph.BFS(1, mazeDetails.x * mazeDetails.y), 1, mazeDetails.x * mazeDetails.y, 0)));
		assertEquals(96, (graph.time + 1)); // Number of Visited cells should be 96
	}

//	DFS traversal for sample input maze8.txt.  The output is in PrintMazeDFS8X8.txt
	@Test
	void testUsing8x8FileDFS() {
		MazeDetails mazeDetails = getMazeInput(
				"/Users/natashaprabhoo/eclipse-workspace/cs146F22.Prabhoo.project3/src/linprabhoo/cs146/project3/maze8.txt");

		MazeSolution maze = new MazeSolution(mazeDetails.x, mazeDetails.y, mazeDetails.maze);
		Graph graph = maze.createGraph();

		System.out.println();
		// maze.getGraph().printAdjList();
		// maze.displayMaze();

//		maze.runDFS();
//		maze.getDFSPath();
		maze.getdisplayDFSMaze();
		maze.getdisplaydiscoveryPathDFSMaze();
		maze.getSaveToFileDFSMaze(graph);

		// Check if length of path is 45
		assertEquals(45,
				(graph.countPath(graph.DFS(1, mazeDetails.x * mazeDetails.y), 1, mazeDetails.x * mazeDetails.y, 0)));
		assertEquals(45, (graph.time + 1)); // Number of Visited cells should be 45
	}

//	BFS traversal for sample input maze8.txt.  The output is in PrintMazeBFS8X8.txt
	@Test
	void testUsing8x8FileBFS() {
		MazeDetails mazeDetails = getMazeInput(
				"/Users/natashaprabhoo/eclipse-workspace/cs146F22.Prabhoo.project3/src/linprabhoo/cs146/project3/maze8.txt");

		MazeSolution maze = new MazeSolution(mazeDetails.x, mazeDetails.y, mazeDetails.maze);
		Graph graph = maze.createGraph();

		System.out.println();
		// maze.getGraph().printAdjList();
		// maze.displayMaze();

//		maze.runDFS();
//		maze.getDFSPath();
		maze.getdisplayBFSMaze();
		maze.getdisplaydiscoveryPathBFSMaze();
		maze.getSaveToFileBFSMaze(graph);

		// Check if length of path is 45
		assertEquals(45,
				(graph.countPath(graph.BFS(1, mazeDetails.x * mazeDetails.y), 1, mazeDetails.x * mazeDetails.y, 0)));
		assertEquals(60, (graph.time + 1)); // Number of Visited cells should be 60
	}

//	DFS traversal for sample input maze6.txt.  The output is in PrintMazeDFS6X6.txt
	@Test
	void testUsing6x6FileDFS() {
		MazeDetails mazeDetails = getMazeInput(
				"/Users/natashaprabhoo/eclipse-workspace/cs146F22.Prabhoo.project3/src/linprabhoo/cs146/project3/maze6.txt");

		MazeSolution maze = new MazeSolution(mazeDetails.x, mazeDetails.y, mazeDetails.maze);
		Graph graph = maze.createGraph();

		System.out.println();
		// maze.getGraph().printAdjList();
		// maze.displayMaze();

//		maze.runDFS();
//		maze.getDFSPath();
		maze.getdisplayDFSMaze();
		maze.getdisplaydiscoveryPathDFSMaze();
		maze.getSaveToFileDFSMaze(graph);

		// Check if length of path is 15
		assertEquals(15,
				(graph.countPath(graph.DFS(1, mazeDetails.x * mazeDetails.y), 1, mazeDetails.x * mazeDetails.y, 0)));
		assertEquals(15, (graph.time + 1)); // Number of Visited cells should be 15
	}

	
//	BFS traversal for sample input maze6.txt.  The output is in PrintMazeBFS6X6.txt
	@Test
	void testUsing6x6FileBFS() {
		MazeDetails mazeDetails = getMazeInput(
				"/Users/natashaprabhoo/eclipse-workspace/cs146F22.Prabhoo.project3/src/linprabhoo/cs146/project3/maze6.txt");

		MazeSolution maze = new MazeSolution(mazeDetails.x, mazeDetails.y, mazeDetails.maze);
		Graph graph = maze.createGraph();

		System.out.println();
		// maze.getGraph().printAdjList();
		// maze.displayMaze();

//		maze.runDFS();
//		maze.getDFSPath();
		maze.getdisplayBFSMaze();
		maze.getdisplaydiscoveryPathBFSMaze();
		maze.getSaveToFileBFSMaze(graph);

		// Check if length of path is 15
		assertEquals(15,
				(graph.countPath(graph.BFS(1, mazeDetails.x * mazeDetails.y), 1, mazeDetails.x * mazeDetails.y, 0)));
		assertEquals(21, (graph.time + 1)); // Number of Visited cells should be 21
	}

//	DFS traversal for sample input maze4.txt.  The output is in PrintMazeDFS4X4.txt
	@Test
	void testUsing4x4FileDFS() {
		MazeDetails mazeDetails = getMazeInput(
				"/Users/natashaprabhoo/eclipse-workspace/cs146F22.Prabhoo.project3/src/linprabhoo/cs146/project3/maze4.txt");

		MazeSolution maze = new MazeSolution(mazeDetails.x, mazeDetails.y, mazeDetails.maze);
		Graph graph = maze.createGraph();

		System.out.println();
		// maze.getGraph().printAdjList();
		// maze.displayMaze();

//		maze.runDFS();
//		maze.getDFSPath();
		maze.getdisplayDFSMaze();
		maze.getdisplaydiscoveryPathDFSMaze();
		maze.getSaveToFileDFSMaze(graph);

		// Check if length of path is 13
		assertEquals(13,
				(graph.countPath(graph.DFS(1, mazeDetails.x * mazeDetails.y), 1, mazeDetails.x * mazeDetails.y, 0)));
		assertEquals(13, (graph.time + 1)); // Number of Visited cells should be 13
	}

//	BFS traversal for sample input maze4.txt. The output is in PrintMazeBFS4X4.txt
	@Test
	void testUsing4x4FileBFS() {
		MazeDetails mazeDetails = getMazeInput(
				"/Users/natashaprabhoo/eclipse-workspace/cs146F22.Prabhoo.project3/src/linprabhoo/cs146/project3/maze4.txt");

		MazeSolution maze = new MazeSolution(mazeDetails.x, mazeDetails.y, mazeDetails.maze);
		Graph graph = maze.createGraph();

		System.out.println();
		// maze.getGraph().printAdjList();
		// maze.displayMaze();

//		maze.runDFS();
//		maze.getDFSPath();
		maze.getdisplayBFSMaze();
		maze.getdisplaydiscoveryPathBFSMaze();
		maze.getSaveToFileBFSMaze(graph);

		// Check if length of path is 13
		assertEquals(13,
				(graph.countPath(graph.BFS(1, mazeDetails.x * mazeDetails.y), 1, mazeDetails.x * mazeDetails.y, 0)));
		assertEquals(16, (graph.time + 1)); // Number of Visited cells should be 16
	}

}