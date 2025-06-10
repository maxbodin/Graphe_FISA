package AdjacencyMatrix;

import GraphAlgorithms.GraphTools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import AdjacencyList.AdjacencyListUndirectedGraph;

/**
 * This class represents the undirected graphs structured by an adjacency matrix.
 * We consider only simple graph
 */
public class AdjacencyMatrixUndirectedGraph {
	
	//--------------------------------------------------
    // 				Class variables
    //--------------------------------------------------

    protected int nbNodes;		// Number of vertices
    protected int nbEdges;		// Number of edges/arcs
    protected int[][] matrix;	// The adjacency matrix

	//--------------------------------------------------
	// 				Constructors
	//--------------------------------------------------

	/**
	 * Creates an empty graph with no nodes and no edges.
	 */
	public AdjacencyMatrixUndirectedGraph() {
		this.matrix = new int[0][0];
		this.nbNodes = 0;
		this.nbEdges = 0;
	}

	/**
	 * Creates a graph from an adjacency matrix.
	 * @param mat The adjacency matrix to initialize the graph
	 */
	public AdjacencyMatrixUndirectedGraph(int[][] mat) {
		if (mat == null) {
			throw new IllegalArgumentException("Matrix cannot be null");
		}

		this.nbNodes = mat.length;
		this.nbEdges = 0;
		this.matrix = new int[this.nbNodes][this.nbNodes];

		for (int i = 0; i < this.nbNodes; i++) {
			if (mat[i].length != this.nbNodes) {
				throw new IllegalArgumentException("Matrix must be square");
			}

			for (int j = i; j < this.nbNodes; j++) {
				if (mat[i][j] > 0) {
					this.matrix[i][j] = mat[i][j];
					this.matrix[j][i] = mat[i][j];
					if (i != j) { // On compte pas les loop
						this.nbEdges += mat[i][j];
					}
				} else {
					this.matrix[i][j] = 0;
					this.matrix[j][i] = 0;
				}
			}
		}
	}

	/**
	 * Creates a graph from an adjacency list representation.
	 * @param g The adjacency list graph to convert
	 */
	public AdjacencyMatrixUndirectedGraph(AdjacencyListUndirectedGraph g) {
		if (g == null) {
			throw new IllegalArgumentException("Graph cannot be null");
		}

		this.nbNodes = g.getNbNodes();
		this.nbEdges = g.getNbEdges();
		this.matrix = g.toAdjacencyMatrix();
	}

	//--------------------------------------------------
	// 					Accessors
	//--------------------------------------------------

	/**
     * @return the matrix modeling the graph
     */
    public int[][] getMatrix() {
        return this.matrix;
    }

    /**
     * @return the number of nodes in the graph (referred to as the order of the graph)
     */
    public int getNbNodes() {
        return this.nbNodes;
    }
	
    /**
	 * @return the number of edges in the graph
 	 */
	public int getNbEdges() {
		return this.nbEdges;
	}

	/**
	 * Gets all neighbors of a vertex.
	 * @param v The vertex selected to get neighbors for
	 * @return a list of vertices which are the neighbors of v
	 * @throws IndexOutOfBoundsException if v is not a valid vertex
	 */
	public List<Integer> getNeighbours(int v) {
		validateVertex(v);
		List<Integer> neighbors = new ArrayList<>();
		for (int i = 0; i < matrix[v].length; i++) {
			if (matrix[v][i] > 0) {
				neighbors.add(i);
			}
		}
		return neighbors;
	}

	// ------------------------------------------------
	// 					Methods
	// ------------------------------------------------

	/**
	 * Checks if the given indices represent valid vertices.
	 * @param vertices The vertices to validate.
	 * @throws IndexOutOfBoundsException if any vertex is invalid
	 */
	private void validateVertex(int... vertices) {
		for (int v : vertices) {
			if (v < 0 || v >= nbNodes) {
				throw new IndexOutOfBoundsException("Vertex " + v + " is out of bounds (0.." + (nbNodes - 1) + ")");
			}
		}
	}

	/**
	 * Checks if there's an edge between two vertices.
	 * @param x First vertex
	 * @param y Second vertex
	 * @return true if the edge is in the graph.
	 * @throws IndexOutOfBoundsException if x or y is not a valid vertex
	 */
	public boolean isEdge(int x, int y) {
		validateVertex(x, y);
		return matrix[x][y] > 0;
	}

	/**
	 * Removes the edge (x,y) if iit exists one between these nodes in the graph.
	 * @param x First vertex
	 * @param y Second vertex
	 * @throws IndexOutOfBoundsException if x or y is not a valid vertex
	 */
	public void removeEdge(int x, int y) {
		validateVertex(x, y);
		if (isEdge(x, y)) {
			matrix[x][y] = 0;
			matrix[y][x] = 0;
			nbEdges--;
		}
	}

	/**
	 * Adds the edge (x,y) if there is not already one
	 * @param x First vertex
	 * @param y Second vertex
	 * @throws IndexOutOfBoundsException if x or y is not a valid vertex
	 */
	public void addEdge(int x, int y) {
		validateVertex(x, y);
		if (x != y && !isEdge(x, y)) {
			matrix[x][y] = 1;
			matrix[y][x] = 1;
			nbEdges++;
		}
	}

	/**
	 * @return the adjacency matrix representation int[][] of the graph
	 */
	public int[][] toAdjacencyMatrix() {
		// Create a deep copy to avoid external modification
		int[][] copy = new int[nbNodes][nbNodes];
		for (int i = 0; i < nbNodes; i++) {
			System.arraycopy(matrix[i], 0, copy[i], 0, nbNodes);
		}
		return copy;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("\nAdjacency Matrix:\n    ");

		// Column headers.
		for (int i = 0; i < nbNodes; i++) {
			s.append(String.format("%3d", i));
		}
		s.append("\n   ");

		// Separator line.
		for (int i = 0; i < 3 * nbNodes; i++) {
			s.append("-");
		}
		s.append("\n");

		// Matrix rows with row indices.
		for (int i = 0; i < nbNodes; i++) {
			s.append(String.format("%2d |", i));
			for (int j = 0; j < nbNodes; j++) {
				s.append(String.format("%3d", matrix[i][j]));
			}
			s.append("\n");
		}
		return s.toString();
	}

	public static void main(String[] args) {
		int[][] mat2 = GraphTools.generateGraphData(10, 35, false, true, false, 100001);
		AdjacencyMatrixUndirectedGraph am = new AdjacencyMatrixUndirectedGraph(mat2);
		System.out.println(am);
		System.out.println("n = " + am.getNbNodes() + "\nm = " + am.getNbEdges() + "\n");

		// Neighbors of vertex 2 :
		System.out.println("Neighbours of vertex 2 : ");
		List<Integer> t2 = am.getNeighbours(2);
		for (Integer integer : t2) {
			System.out.print(integer + ", ");
		}

		// We add three edges {3,5} :
		System.out.println("\n\nisEdge(3, 5) ? " + am.isEdge(3, 5));
		am.addEdge(3, 5);
		System.out.println("After adding edge {3,5}, isEdge(3, 5) ? " + am.isEdge(3, 5));

		System.out.println("\n" + am);

		System.out.println("\nAfter removing edge {3,5} :");
		am.removeEdge(3, 5);
		System.out.println(am);

		// Test error handling
		try {
			am.isEdge(20, 30);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Caught expected exception: " + e.getMessage());
		}
	}
}