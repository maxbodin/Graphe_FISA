package AdjacencyMatrix;

import java.util.ArrayList;
import java.util.List;

import AdjacencyList.AdjacencyListDirectedGraph;

/**
 * This class represents the directed graphs structured by an adjacency matrix.
 * We consider only simple graph.
 */
public class AdjacencyMatrixDirectedGraph {

	//--------------------------------------------------
    // 				Class variables
    //--------------------------------------------------

    protected int nbNodes;		// Number of vertices
    protected int nbArcs;		// Number of edges/arcs
    protected int[][] matrix;	// The adjacency matrix
	
	//--------------------------------------------------
	// 				Constructors
	//-------------------------------------------------- 

    public AdjacencyMatrixDirectedGraph() {
        this.matrix = new int[0][0];
        this.nbNodes = 0;
        this.nbArcs = 0;
    }

      
	public AdjacencyMatrixDirectedGraph(int[][] mat) {
		this.nbNodes = mat.length;
		this.nbArcs = 0;
		this.matrix = new int[this.nbNodes][this.nbNodes];
		for(int i = 0; i<this.nbNodes; i++){
			for(int j = 0; j<this.nbNodes; j++){
				this.matrix[i][j] = mat[i][j];
				if (i != j && mat[i][j] > 0) {
					this.nbArcs++;
				}
			}
		}
	}

	public AdjacencyMatrixDirectedGraph(AdjacencyListDirectedGraph g) {
		this.nbNodes = g.getNbNodes();
		this.nbArcs = g.getNbArcs();
		this.matrix = g.toAdjacencyMatrix();
	}

	//--------------------------------------------------
	// 					Accessors
	//--------------------------------------------------


    /**
     * Returns the matrix modeling the graph
     */
    public int[][] getMatrix() {
        return this.matrix;
    }

    /**
     * Returns the number of nodes in the graph (referred to as the order of the graph)
     */
    public int getNbNodes() {
        return this.nbNodes;
    }
	
    /**
	 * @return the number of arcs in the graph
 	 */	
	public int getNbArcs() {
		return this.nbArcs;
	}

	
	/**
	 * @param u the vertex selected
	 * @return a list of vertices which are the successors of u
	 */
	public List<Integer> getSuccessors(int u) {
		List<Integer> succ = new ArrayList<Integer>();
		for(int v =0;v<this.matrix[u].length;v++){
			if(this.matrix[u][v]>0){
				succ.add(v);
			}
		}
		return succ;
	}

	/**
	 * @param v the vertex selected
	 * @return a list of vertices which are the predecessors of v
	 */
	public List<Integer> getPredecessors(int v) {
		List<Integer> pred = new ArrayList<Integer>();
		for(int u =0;u<this.matrix.length;u++){
			if(this.matrix[u][v]>0){
				pred.add(u);
			}
		}
		return pred;
	}
	
	
	// ------------------------------------------------
	// 					Methods 
	// ------------------------------------------------		
	
	/**
	 * @return true if the arc (from,to) exists in the graph.
	 */
	public boolean isArc(int from, int to) {
        validateVertex(from, to);
		return matrix[from][to] > 0;
	}

	/**
	 * Removes the arc (from,to) if there exists one between these nodes in the graph.
	 */
	public void removeArc(int from, int to) {
        validateVertex(from, to);
		if (isArc(from, to)) {
			matrix[from][to] = 0;
			nbArcs--;
		}
	}

	/**
	 * Adds the arc (from,to).
	 */
	public void addArc(int from, int to) {
        validateVertex(from, to);
		if (from != to && !isArc(from, to)) {
			matrix[from][to] = 1;
			nbArcs++;
		}
	}

	/**
	 * Vérifie que les indices de sommets sont valides.
	 */
    private void validateVertex(int... vertices) {
        for (int v : vertices) {
            if (v < 0 || v >= nbNodes) {
                throw new IndexOutOfBoundsException("Vertex " + v + " is out of bounds (0.." + (nbNodes - 1) + ")");
            }
        }
    }

	/**
	 * @return a new graph which is the inverse graph of this.matrix
	 */
	public AdjacencyMatrixDirectedGraph computeInverse() {
		int[][] inverseMatrix = new int[nbNodes][nbNodes];
		for (int i = 0; i < nbNodes; i++) {
			for (int j = 0; j < nbNodes; j++) {
				inverseMatrix[j][i] = this.matrix[i][j];
			}
		}
		return new AdjacencyMatrixDirectedGraph(inverseMatrix);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("\nAdjacency Matrix Directed Graph:\n    ");

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
            {0, 0, 1, 0, 0},
            {0, 0, 0, 1, 1},
            {1, 0, 0, 0, 0},
            {0, 1, 0, 0, 0}
        };
        System.out.println("Test 1: Creating a directed graph with defined matrix.");
        AdjacencyMatrixDirectedGraph graph = new AdjacencyMatrixDirectedGraph(matrix);
        System.out.println(graph);
        System.out.println("Number of nodes = " + graph.getNbNodes() + " (Should be 5) " + (graph.getNbNodes() == 5 ? "✅" : "❌"));
        System.out.println("Number of arcs = " + graph.getNbArcs() + " (Should be 7) " + (graph.getNbArcs() == 7 ? "✅" : "❌"));

        // Test 2: Check initial arcs.
        System.out.println("\nTest 2: Checking initial arcs.");
        boolean arc01 = graph.isArc(0, 1);
        boolean arc10 = graph.isArc(1, 0);
        boolean arc02 = graph.isArc(0, 2);
        System.out.println("Arc (0,1) exists? " + arc01 + " (Should be TRUE) " + (arc01 ? "✅" : "❌"));
        System.out.println("Arc (1,0) exists? " + arc10 + " (Should be FALSE) " + (!arc10 ? "✅" : "❌"));
        System.out.println("Arc (0,2) exists? " + arc02 + " (Should be FALSE) " + (!arc02 ? "✅" : "❌"));

        // Test 3: Test successors and predecessors.
        System.out.println("\nTest 3: Testing successors and predecessors.");
        List<Integer> successors2 = graph.getSuccessors(2);
        List<Integer> predecessors1 = graph.getPredecessors(1);
        System.out.println("Successors of vertex 2: " + successors2);
        System.out.println("Correct successors of 2? " + (successors2.contains(3) && successors2.contains(4) && successors2.size() == 2) + 
                         " (Should be TRUE) " + (successors2.contains(3) && successors2.contains(4) && successors2.size() == 2 ? "✅" : "❌"));
        System.out.println("Predecessors of vertex 1: " + predecessors1);
        System.out.println("Correct predecessors of 1? " + (predecessors1.contains(0) && predecessors1.contains(4) && predecessors1.size() == 2) + 
                         " (Should be TRUE) " + (predecessors1.contains(0) && predecessors1.contains(4) && predecessors1.size() == 2 ? "✅" : "❌"));

        // Test 4: Add new arc.
        System.out.println("\nTest 4: Adding new arc.");
        graph.addArc(1, 4);
        boolean arc14AfterAdd = graph.isArc(1, 4);
        System.out.println("After adding arc (1,4):");
        System.out.println("Arc (1,4) exists? " + arc14AfterAdd + " (Should be TRUE) " + (arc14AfterAdd ? "✅" : "❌"));
        System.out.println(graph);

        // Test 5: Remove arc.
        System.out.println("\nTest 5: Removing arc.");
        graph.removeArc(1, 4);
        boolean arc14AfterRemove = graph.isArc(1, 4);
        System.out.println("After removing arc (1,4):");
        System.out.println("Arc (1,4) exists? " + arc14AfterRemove + " (Should be FALSE) " + (!arc14AfterRemove ? "✅" : "❌"));
        System.out.println(graph);

        // Test 6: Try to add self-loop.
        System.out.println("\nTest 6: Testing self-loop prevention.");
        graph.addArc(1, 1);
        System.out.println("Self-loop (1,1) was prevented? " + (!graph.isArc(1, 1)) + " (Should be TRUE) " + 
                         (!graph.isArc(1, 1) ? "✅" : "❌"));

        // Test 7: Compute inverse graph.
        System.out.println("\nTest 7: Testing graph inversion.");
        AdjacencyMatrixDirectedGraph inverse = graph.computeInverse();
        System.out.println("Original graph:");
        System.out.println(graph);
        System.out.println("Inverse graph:");
        System.out.println(inverse);
        // Verify one arc inversion.
        boolean originalArc01 = graph.isArc(0, 1);
        boolean inverseArc10 = inverse.isArc(1, 0);
        System.out.println("Arc inversion correct? " + (originalArc01 == inverseArc10) + " (Should be TRUE) " + 
                         (originalArc01 == inverseArc10 ? "✅" : "❌"));

        // Test 8: Error handling.
        System.out.println("\nTest 8: Error handling.");
        try {
            graph.isArc(20, 30);
            System.out.println("❌ Failed to catch invalid vertices");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Successfully caught invalid vertices exception ✅");
        }
    }
}