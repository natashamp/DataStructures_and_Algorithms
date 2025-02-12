package linprabhoo.cs146.project3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * @author natashaprabhoo This class includes an inner graph class which -
 *         generates a maze that automatically generates a randomized maze -
 *         implements DFS and BFS to solve the maze - finds the shortest
 *         solution path to solve the maze
 * 
 *         We created a 2D array which held the randomized direction values to
 *         model our maze and then converted it into a graph. We modeled the
 *         maze as a graph using adjacency list of cells. We used a HashMap of
 *         LinkedLists for our adjacency list data structure. We assigned an
 *         integer number to each cell in the following manner: 4 by 4 maze 0 1
 *         2 3 +---+---+---+---+ 0 | 1 | 2 | 3 | 4 | +---+---+---+---+ 1 | 5 | 6
 *         | 7 | 8 | +---+---+---+---+ 2 | 9 | 10| 11| 12| +---+---+---+---+ 3 |
 *         13| 14| 15| 16| +---+---+---+---+
 * 
 *         In this maze the starting cell is 1 and the finishing cell is 16.
 * 
 */
public class MazeSolution {

	private final int x;
	private final int y;
	private final int[][] maze;
	private Graph graph;

	/**
	 * @author natashaprabhoo create cell for graph with attributes .color, .parent,
	 *         .discoveryTime
	 */
	public class Cell {
		int color; // WHITE = 0, GRAY = -1, BLACK = 1
		int discoveryTime;
		int finishingTime;
		int parent;

		public Cell(int cellNum, int color, int discoveryTime, int finishingTime, int parent) {
			this.color = color;
			this.discoveryTime = discoveryTime;
			this.finishingTime = finishingTime;
			this.parent = parent;
		}
		
		public Cell() {}
	}

	/**
	 * @author natashaprabhoo
	 * 	This class represents the graph for the maze and
	 *  creates an adjacency list from the input maze array.
	 *  There are functions in this class that implement DFS and BFS
	 *  traversal and displays the maze with the shortest solved path 
	 *  and the maze with the order of visited cells.
	 */
	public class Graph {
		final int WHITE = 0;
		final int GRAY = -1;
		final int BLACK = 1;

		Cell[] nodes = null;
		int time = 0;

		public HashMap<Integer, LinkedList<Integer>> mazeGraph = new HashMap<>();

		// adds a new vertex to the graph
		public void addVertex(Integer addCell) {
			mazeGraph.put(addCell, new LinkedList<Integer>());
		}

		// This function adds the edge between source to destination
		public void addEdge(Integer currCell, Integer neighborCell) {
			if (!mazeGraph.containsKey(currCell))
				addVertex(currCell);

			mazeGraph.get(currCell).add(neighborCell);

		}

		// This function gives the count of vertices
		public int getVertexCount() {
			return mazeGraph.keySet().size();
		}

		// This function gives the count of edges
		public int getEdgesCount() {
			int countEdges = 0;
			for (int v : mazeGraph.keySet()) {
				countEdges += mazeGraph.get(v).size();
			}
			return countEdges;
		}

		// returns the adjacency list
		public LinkedList<Integer> getAdjList(int sourceCell) {
			return mazeGraph.get(sourceCell);
		}

		// returns the adjacency list: HashMap< Integer, LinkedList<Integer> >
		public void printAdjList() {
			System.out.println("Adjaceny List: ");
			for (int i = 1; i <= getVertexCount(); i++) {
				System.out.println(i + " : " + getAdjList(i));
			}

		}

		//	wrapper method for DFS traversal method
		public Cell[] DFS(int source, int destination) {
			nodes = new Cell[getVertexCount() + 1]; // putting cells in nodes array
			for (int i = 0; i <= getVertexCount(); i++) {
				nodes[i] = new Cell();
			}

			time = -1;
			for (int i = 1; i <= getVertexCount(); i++) {
				nodes[i].color = WHITE;
				nodes[i].parent = 0; // 0 is equal to NIL
				nodes[i].discoveryTime = Integer.MAX_VALUE;
			}

			visitDFS(source, destination, nodes);

			return nodes;

		}

		//	recursive method for DFS traversal
		public void visitDFS(int source, int destination, Cell[] nodes) {
			if (nodes[destination].color != WHITE) { // base case: destination found
				return;
			}
			nodes[source].color = GRAY;
			nodes[source].discoveryTime = ++time;
			if (source == destination) { // base current room is destination
				return;
			}
			for (int n : getAdjList(source)) { // visit neighbors
				if (nodes[n].color == WHITE) {
					nodes[n].parent = source;
					visitDFS(n, destination, nodes);
				}
			}

			nodes[source].color = BLACK; // processed
			// nodes[source].finishingTime = ++time;

		}

		//	BFS traversal method using a queue
		public Cell[] BFS(int source, int destination) {

			nodes = new Cell[getVertexCount() + 1]; // putting cells in nodes array
			for (int i = 0; i <= getVertexCount(); i++) {
				nodes[i] = new Cell();
			}

			for (int i = 1; i <= getVertexCount(); i++) { // initailzing cells
				nodes[i].color = WHITE;
				nodes[i].parent = 0; // 0 is equal to NIL
				nodes[i].discoveryTime = Integer.MAX_VALUE;
			}

			nodes[source].color = GRAY;
			nodes[source].parent = 0;
			nodes[source].discoveryTime = 0;

			ArrayDeque<Integer> q = new ArrayDeque<>(); // queue for BFS

			q.offer(source);

			time = 0;
			while (!q.isEmpty()) {
				int u = q.poll();
				for (int v : getAdjList(u)) {

					if (nodes[v].color == WHITE) {
						nodes[v].color = GRAY;
						nodes[v].discoveryTime = ++time;
						nodes[v].parent = u;

						if (v == destination) { // terminate BFS when reached finishing room
							return nodes;
						}
						q.offer(v); // updating to undiscovered adjacent cell
					}
				}
				nodes[u].color = BLACK;
			}
			return nodes;
		}

		
		// prints the DFS maze with the shortest solution
		public void displayPathMaze(Cell[] nodes, int destination, HashSet<Integer> path) {
			System.out.println(getPathMaze(nodes, destination, path));
		}

		//	prints maze with order of visited cells
		public void displayDiscoveryPathMaze(Cell[] nodes, int destination, HashSet<Integer> path) {
			System.out.println(getDiscoveryMaze(nodes, destination, path));
		}

		//	returns maze with order of visited cells in text representation for
		//	saving to file
		public String getDiscoveryMaze(Cell[] nodes, int destination, HashSet<Integer> path) {
			String returnString = "";

			for (int i = 0; i < y; i++) {
				// draw the north edge
				for (int j = 0; j < x; j++) {

					if (i == 0 && j == 0) {

						returnString = returnString + "+   ";
					} else {						
						returnString = returnString + ((maze[i][j] & 1) == 0 ? "+---" : "+   ");
					}
				}
				returnString = returnString + "+\n";

				for (int j = 0; j < x; j++) {

					int cellNumber = getRoomNum(i, j);

					int discoveryTime = nodes[cellNumber].discoveryTime;

					char first = ' ';
					if ((maze[i][j] & 8) == 0) { // no opening to west
						first = '|';
					}
					if (discoveryTime != Integer.MAX_VALUE) {
						if (discoveryTime < 10) {
							returnString = returnString + first + " " + discoveryTime + " ";
						} else if (discoveryTime < 100) {
							returnString = returnString + first + " " + discoveryTime;
						} else {				
							returnString = returnString + first + discoveryTime;
						}
					} else {
						returnString = returnString + first + "   ";
					}
				}
				// draw the west edge
				returnString = returnString + "|\n";
			}
			// draw the bottom line
			for (int j = 0; j < x - 1; j++) {
				returnString = returnString + "+---";
			}
			returnString = returnString + "+   +";
			return returnString;
		}

			//	returns maze with shortest solution in text representation for
			//	saving to file
		public String getPathMaze(Cell[] nodes, int destination, HashSet<Integer> path) {
			String returnString = "";
			for (int i = 0; i < y; i++) {
				// draw the north edge
				for (int j = 0; j < x; j++) {
					if (i == 0 && j == 0) {
						returnString = returnString + "+   ";
					} else {
						returnString = returnString + ((maze[i][j] & 1) == 0 ? "+---" : "+   ");
					}
				}
				returnString = returnString + "+\n";
				// draw the west edge
				for (int j = 0; j < x; j++) {

					if ((maze[i][j] & 8) == 0) {
						returnString = returnString + (path.contains(getRoomNum(i, j)) ? "| # " : "|   ");
					} else {
						returnString = returnString + (path.contains(getRoomNum(i, j)) ? "  # " : "    ");
					}

				}
				returnString = returnString + "|\n";
			}
			// draw the bottom line
			for (int j = 0; j < x - 1; j++) {
				returnString = returnString + "+---";
			}
			returnString = returnString + "+   +";      // finishing point
			return returnString;
		}

		
		//	recursively builds a HashSet that stores the cells in the shortest path
		public void getPathSet(Cell[] nodes, int source, int destination, HashSet<Integer> path) {
			if (destination != 0) {
				getPathSet(nodes, source, nodes[destination].parent, path); // follows the parents
				path.add(destination);
			}
		}

		
		//	Generates a path to save to file
		public void saveToFilePath(Cell[] nodes, int source, int destination, HashSet<Integer> path) {

			if (destination != 0) {
				saveToFilePath(nodes, source, nodes[destination].parent, path); // follows the parents
				System.out.println(destination + " -> " + getRow(destination) + ", " + getColumn(destination));
				path.add(destination);
			}
		}
		
		//	counts the number of cells in the shortest solution path
		public int countPath(Cell[] nodes, int source, int destination, int count) {
			if (destination != 0) {
				return countPath(nodes, source, nodes[destination].parent, ++count); // follows the parents

			} else {
				return count;
			}
		}

		//	returns the path to be saved in a file in text represention
		public String returnStringPath(Cell[] nodes, int source, int destination, String str) {
			if (destination != 0) {
				return returnStringPath(nodes, source, nodes[destination].parent,
						(" (" + getRow(destination) + ", " + getColumn(destination) + ")" + str));
			} else {
				return str;
			}
		}
	}

	//	constructor for MazeSolution class
	public MazeSolution(int x, int y) {
		this.x = x;
		this.y = y;
		maze = new int[this.x][this.y];
		createMaze(0, 0);
	}

	//	getter for graph
	public Graph getGraph() {
		return graph;
	}

	//	constructor for MazeSolution class to be used by provided sample inputs
	public MazeSolution(int x, int y, int[][] m) {
		this.x = x;
		this.y = y;
		this.maze = m;
	}

	//	provided code to generate randomized maze
	private void createMaze(int cx, int cy) {
		DIR[] dirs = DIR.values();
		Collections.shuffle(Arrays.asList(dirs));
		for (DIR dir : dirs) {
			// find neighbor cell
			int nx = cx + dir.dx;
			int ny = cy + dir.dy;
			// if neighbor exists and not visited
			if (between(nx, x) && between(ny, y) && (maze[nx][ny] == 0)) {
				// remove walls
				// update current cell using or (|) bit operations
				// example if a cell has north (1) and south (2) neighbor openings, maze holds 3
				// example if a cell has east (4) and west (8) neighbor openings, maze holds 12

				maze[cx][cy] |= dir.bit;
				// update neighbor cell
				maze[nx][ny] |= dir.opposite.bit;
				// recursive call to neighbor cell
				createMaze(nx, ny);
			}
		}
	}

	// prints the value of maze array
	public void displayCells() {
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++)
				System.out.print(" " + maze[i][j]);
			System.out.println();
		}
	}

	// checks if 0<=v<upper
	private static boolean between(int v, int upper) {
		return (v >= 0) && (v < upper);
	}

	// constant for creating direction for cells
	public enum DIR {
		NORTH(1, -1, 0), SOUTH(2, 1, 0), EAST(4, 0, 1), WEST(8, 0, -1);

		public final int bit;
		public final int dx;
		public final int dy;
		public DIR opposite;

		// use the static initializer to resolve forward references
		static {
			NORTH.opposite = SOUTH;
			SOUTH.opposite = NORTH;
			EAST.opposite = WEST;
			WEST.opposite = EAST;
		}

		private DIR(int bit, int dx, int dy) {
			this.bit = bit;
			this.dx = dx;
			this.dy = dy;
		}
	};

	// prints the maze with the cells and walls removed
	public void displayMaze() {
		for (int i = 0; i < y; i++) {
			// draw the north edge
			for (int j = 0; j < x; j++) {
				if (i == 0 && j == 0) {
					System.out.print("+   "); // starting point
				} else {
					System.out.print((maze[i][j] & 1) == 0 ? "+---" : "+   ");
				}
			}

			System.out.println("+");
			// draw the west edge
			for (int j = 0; j < x; j++) {
				System.out.print((maze[i][j] & 8) == 0 ? "|   " : "    ");
			}
			System.out.println("|");
		}
		// draw the bottom line
		for (int j = 0; j < x - 1; j++) {
			System.out.print("+---");
		}
		System.out.println("+   +");

	}

	
	//	prints maze to console
	public void setdisplayTestMaze(int[][] maze, int x1, int y1) {
		for (int i = 0; i < y1; i++) {
			// draw the north edge
			for (int j = 0; j < x1; j++) {
				System.out.print((maze[i][j] & 1) == 0 ? "+---" : "+   ");
			}
			System.out.println("+");
			// draw the west edge
			for (int j = 0; j < x1; j++) {
				System.out.print((maze[i][j] & 8) == 0 ? "|   " : "    ");
			}
			System.out.println("|");
		}
		// draw the bottom line
		for (int j = 0; j < x1 - 1; j++) {
			System.out.print("+---");
		}
		System.out.println("+   +");

	}

	// this method is accepting the coordinates of the room
	// and returning the index of the room
	public int getRoomNum(int i, int j) {
		int roomNum = (i * this.y) + (j + 1);
		return roomNum;
	}

	// getting coordinates for cells
	public int getColumn(int roomNum) {
		if (roomNum % y == 0) {
			return y - 1; // get last column
		}
		return roomNum - ((roomNum / y) * y) - 1;
	}

	public int getRow(int roomNum) {
		if (roomNum % x == 0) {
			return (roomNum / x) - 1; // get last row
		}
		return (roomNum / x);
	}

	// finding walls for each cell
	public Graph createGraph() {
		graph = new Graph();

		// iterating over the row
		for (int i = 0; i < x; i++) {

			// iterating over the columns
			for (int j = 0; j < y; j++) {
				// System.out.println("Room : " + getRoomNum(i, j) + ", value : " + maze[i][j]);

				if ((maze[i][j] & DIR.SOUTH.bit) != 0) {
					// add edges to graph from current cell to below cell
					graph.addEdge(getRoomNum(i, j), getRoomNum(i + DIR.SOUTH.dx, j + DIR.SOUTH.dy));
					// System.out.println("Room to south: " + getRoomNum(i, j) + " to "
					// + getRoomNum(i + DIR.SOUTH.dx, j + DIR.SOUTH.dy));

				}
				if ((maze[i][j] & DIR.EAST.bit) != 0) {
					// add edges to graph from current cell to right cell
					graph.addEdge(getRoomNum(i, j), getRoomNum(i + DIR.EAST.dx, j + DIR.EAST.dy));
					// System.out.println("Room to east : " + getRoomNum(i, j) + " to "
					// + getRoomNum(i + DIR.EAST.dx, j + DIR.EAST.dy));

				}
				// check if the door above is open
				if ((maze[i][j] & DIR.NORTH.bit) != 0) {
					// add edges to graph from current cell to above cell
					graph.addEdge(getRoomNum(i, j), getRoomNum(i + DIR.NORTH.dx, j + DIR.NORTH.dy));
					// System.out.println("Room to north: " + getRoomNum(i, j) + " to "
					// + getRoomNum(i + DIR.NORTH.dx, j + DIR.NORTH.dy));

				}
				if ((maze[i][j] & DIR.WEST.bit) != 0) {
					// add edges to graph from current cell to left cell
					graph.addEdge(getRoomNum(i, j), getRoomNum(i + DIR.WEST.dx, j + DIR.WEST.dy));
					// System.out.println("Room to west : " + getRoomNum(i, j) + " to "
					// + getRoomNum(i + DIR.WEST.dx, j + DIR.WEST.dy));

				}
			}
		}
		return graph;
	}

	
	//	wrapper function to call DFS
	public void runDFS() {
		Cell[] nodes = graph.DFS(1, x * y);
		return;

	}
//	wrapper function to call bFS
	public void runBFS() {
		Cell[] nodes = graph.BFS(1, x * y);
		return;
	}

	
	//	get DFS Path
	public HashSet<Integer> getDFSPath() {
		HashSet<Integer> path = new HashSet<Integer>();
		graph.getPathSet(graph.DFS(1, x * y), 1, x * y, path);
		return path;
	}

	
	//	get BFS path
	public HashSet<Integer> getBFSPath() {
		HashSet<Integer> path = new HashSet<Integer>();
		graph.getPathSet(graph.BFS(1, x * y), 1, x * y, path);
		return path;
	}

	
	//	prints BFS length of path and visited cells to console
	public String runsaveToFileBFSPath() {
		String str = "";
		HashSet<Integer> path = new HashSet<Integer>();
		graph.saveToFilePath(graph.BFS(1, x * y), 1, x * y, path);
		System.out.println("Length of Path : " + graph.countPath(graph.BFS(1, x * y), 1, x * y, 0));
		System.out.println("Visited Cells : " + (graph.nodes.length - 1));
		return str;
	}

	//	prints DFS length of path and visited cells to console
	public String runsaveToFileDFSPath() {
		String str = "";
		HashSet<Integer> path = new HashSet<Integer>();
		graph.saveToFilePath(graph.DFS(1, x * y), 1, x * y, path);
		System.out.println("Length of Path : " + graph.countPath(graph.DFS(1, x * y), 1, x * y, 0));
		System.out.println("Visited Cells : " + (graph.nodes.length - 1));
		return str;
	}

	//	getter for displaying DFS maze
	public void getdisplayDFSMaze() {
		graph.displayPathMaze(graph.DFS(1, x * y), x * y, getDFSPath());

	}

	//	getter for displaying BFS maze
	public void getdisplayBFSMaze() {
		graph.displayPathMaze(graph.BFS(1, x * y), x * y, getBFSPath());
	}

	//	getter for displaying DFS discovery path maze
	public void getdisplaydiscoveryPathDFSMaze() {
		graph.displayDiscoveryPathMaze(graph.DFS(1, x * y), x * y, getDFSPath());
	}

	//	getter for displaying BFS discovery path maze
	public void getdisplaydiscoveryPathBFSMaze() {
		graph.displayDiscoveryPathMaze(graph.BFS(1, x * y), x * y, getBFSPath());
	}
	
	

	// Used for JUnit Testing
	public String TestPathDFSMaze() {
		return graph.getPathMaze(graph.DFS(1, x * y), x * y, getDFSPath());
	}

	public String TestPathBFSMaze() {
		return graph.getPathMaze(graph.BFS(1, x * y), x * y, getBFSPath());
	}

	public String TestDiscoveryPathDFSMaze() {
		return graph.getDiscoveryMaze(graph.DFS(1, x * y), x * y, getDFSPath());
	}

	public String TestDiscoveryPathBFSMaze() {
		return graph.getDiscoveryMaze(graph.BFS(1, x * y), x * y, getBFSPath());
	}
	
	

	// Saves DFS maze to file
	public void getSaveToFileDFSMaze(Graph graph) {
		Cell[] DFSNodes = graph.DFS(1, x * y);
		HashSet<Integer> DFSPath = getDFSPath();
		Path path = Paths.get(
				"/Users/natashaprabhoo/eclipse-workspace/cs146F22.Prabhoo.project3/src/linprabhoo/cs146/project3/PrintDFSMaze"
						+ x + "X" + y + ".txt");

		String str = "DFS Maze :\n" + graph.getDiscoveryMaze(DFSNodes, x * y, DFSPath) + "\n\n"
				+ graph.getPathMaze(DFSNodes, x * y, DFSPath) + "\n" + "Path:"
				+ graph.returnStringPath(DFSNodes, 1, x * y, "") + "\nDFS Length of Path : "
				+ graph.countPath(DFSNodes, 1, x * y, 0) + "\nDFS Visited Cells : " + (graph.time + 1) + "\n";

		byte[] arr = str.getBytes();
		try {
			Files.write(path, arr);
		} catch (IOException ex) {
			System.out.print("Invalid Path");
		}
	}

	// Saves BFS maze to file
	public void getSaveToFileBFSMaze(Graph graph) {
		Cell[] BFSNodes = graph.BFS(1, x * y);
		HashSet<Integer> BFSPath = getBFSPath();
		Path path = Paths.get(
				"/Users/natashaprabhoo/eclipse-workspace/cs146F22.Prabhoo.project3/src/linprabhoo/cs146/project3/PrintBFSMaze"
						+ x + "X" + y + ".txt");

		String str = "BFS Maze :\n" + graph.getDiscoveryMaze(BFSNodes, x * y, BFSPath) + "\n\n"
				+ graph.getPathMaze(BFSNodes, x * y, BFSPath) + "\n" + "Path:"
				+ graph.returnStringPath(BFSNodes, 1, x * y, "") + "\nBFS Length of Path : "
				+ graph.countPath(BFSNodes, 1, x * y, 0) + "\nBFS Visited Cells : " + (graph.time + 1);

		byte[] arr = str.getBytes();
		try {
			Files.write(path, arr);
		} catch (IOException ex) {
			System.out.print("Invalid Path");
		}
	}

	//main function executes randomized mazes
	public static void main(String[] args) {

		MazeSolution maze = new MazeSolution(12, 12);
		Graph graph = maze.createGraph();
		maze.getGraph().printAdjList();

		System.out.println();

		System.out.println("DFS: ");
		maze.runDFS();
		maze.getDFSPath();
		maze.getdisplayDFSMaze();

		maze.runsaveToFileDFSPath();

		System.out.println();
		maze.getdisplaydiscoveryPathDFSMaze();
		maze.getSaveToFileDFSMaze(graph);

		System.out.println();
		System.out.println("BFS: ");

		maze.runBFS();
		maze.getBFSPath();
		maze.getdisplayBFSMaze();

		maze.runsaveToFileBFSPath();

		System.out.println();
		maze.getdisplaydiscoveryPathBFSMaze();
		maze.getSaveToFileBFSMaze(graph);

	}

}
