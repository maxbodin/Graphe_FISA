package AdjacencyMatrix;

import GraphAlgorithms.GraphTools;

import java.util.ArrayList;
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
		StringBuilder s = new StringBuilder("\nAdjacency Matrix Undirected Graph:\n    ");

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
        int[][] matrix = {
            {0, 1, 0, 1, 0},
            {1, 0, 1, 0, 0},
            {0, 1, 0, 1, 1},
            {1, 0, 1, 0, 1},
            {0, 0, 1, 1, 0}
        };
        System.out.println("Test 1: Creating a graph with defined matrix.");
        AdjacencyMatrixUndirectedGraph graph = new AdjacencyMatrixUndirectedGraph(matrix);
        System.out.println(graph);
        System.out.println("Number of nodes = " + graph.getNbNodes() + " (Should be 5) " + (graph.getNbNodes() == 5 ? "✅" : "❌"));
        System.out.println("Number of edges = " + graph.getNbEdges() + " (Should be 6) " + (graph.getNbEdges() == 6 ? "✅" : "❌"));

        // Test 2: Check initial edges.
        System.out.println("\nTest 2: Checking initial edges.");
        boolean edge01 = graph.isEdge(0, 1);
        boolean edge02 = graph.isEdge(0, 2);
        boolean edge12 = graph.isEdge(1, 2);
        System.out.println("Edge (0,1) exists? " + edge01 + " (Should be TRUE) " + (edge01 ? "✅" : "❌"));
        System.out.println("Edge (0,2) exists? " + edge02 + " (Should be FALSE) " + (!edge02 ? "✅" : "❌"));
        System.out.println("Edge (1,2) exists? " + edge12 + " (Should be TRUE) " + (edge12 ? "✅" : "❌"));

        // Test 3: Test neighbors.
        System.out.println("\nTest 3: Testing getNeighbours.");
        List<Integer> neighbors2 = graph.getNeighbours(2);
        System.out.println("Neighbours of vertex 2: " + neighbors2);
        boolean correctNeighbors = neighbors2.contains(1) && neighbors2.contains(3) && 
                                 neighbors2.contains(4) && neighbors2.size() == 3;
        System.out.println("Vertex 2 has correct neighbors? " + correctNeighbors + " (Should be TRUE) " + 
                         (correctNeighbors ? "✅" : "❌"));

        // Test 4: Add new edge.
        System.out.println("\nTest 4: Adding new edge.");
        graph.addEdge(0, 2);
        boolean edge02AfterAdd = graph.isEdge(0, 2);
        boolean edge20AfterAdd = graph.isEdge(2, 0); // Test symmetry.
        System.out.println("After adding edge (0,2):");
        System.out.println("Edge (0,2) exists? " + edge02AfterAdd + " (Should be TRUE) " + (edge02AfterAdd ? "✅" : "❌"));
        System.out.println("Edge (2,0) exists? " + edge20AfterAdd + " (Should be TRUE) " + (edge20AfterAdd ? "✅" : "❌"));
        System.out.println(graph);

        // Test 5: Remove edge.
        System.out.println("\nTest 5: Removing edge.");
        graph.removeEdge(0, 2);
        boolean edge02AfterRemove = graph.isEdge(0, 2);
        boolean edge20AfterRemove = graph.isEdge(2, 0); // Test symmetry.
        System.out.println("After removing edge (0,2):");
        System.out.println("Edge (0,2) exists? " + edge02AfterRemove + " (Should be FALSE) " + (!edge02AfterRemove ? "✅" : "❌"));
        System.out.println("Edge (2,0) exists? " + edge20AfterRemove + " (Should be FALSE) " + (!edge20AfterRemove ? "✅" : "❌"));
        System.out.println(graph);

        // Test 6: Try to add self-loop.
        System.out.println("\nTest 6: Testing self-loop prevention.");
        graph.addEdge(1, 1);
        System.out.println("Self-loop (1,1) was prevented? " + (!graph.isEdge(1, 1)) + " (Should be TRUE) " + 
                         (!graph.isEdge(1, 1) ? "✅" : "❌"));

        // Test 7: Error handling.
        System.out.println("\nTest 7: Error handling.");
        // Test out of bounds
        try {
            graph.isEdge(20, 30);
            System.out.println("❌ Failed to catch invalid vertices.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Successfully caught invalid vertices exception ✅");
        }

        // Test 8: Matrix symmetry.
        System.out.println("\nTest 8: Testing matrix symmetry.");
        boolean isSymmetric = true;
        for (int i = 0; i < graph.getNbNodes(); i++) {
            for (int j = 0; j < graph.getNbNodes(); j++) {
                if (graph.matrix[i][j] != graph.matrix[j][i]) {
                    isSymmetric = false;
                    break;
                }
            }
        }
        System.out.println("Matrix is symmetric? " + isSymmetric + " (Should be TRUE) " + (isSymmetric ? "✅" : "❌"));

        // Test 9: Random graph.
        System.out.println("\nTest 9: Testing with random generated graph.");
        int[][] randomMatrix = GraphTools.generateGraphData(5, 8, false, true, false, 100001);
        AdjacencyMatrixUndirectedGraph randomGraph = new AdjacencyMatrixUndirectedGraph(randomMatrix);
        System.out.println(randomGraph);

		// Verify symmetry in random graph.
        boolean randomIsSymmetric = true;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (randomGraph.matrix[i][j] != randomGraph.matrix[j][i]) {
                    randomIsSymmetric = false;
                    break;
                }
            }
        }
        System.out.println("Random graph is symmetric? " + randomIsSymmetric + " (Should be TRUE) " + (isSymmetric ? "✅" : "❌"));
    }
}