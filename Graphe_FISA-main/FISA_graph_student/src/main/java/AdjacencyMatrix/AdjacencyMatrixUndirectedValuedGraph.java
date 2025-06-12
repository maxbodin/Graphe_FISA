package AdjacencyMatrix;

import GraphAlgorithms.GraphTools;


public class AdjacencyMatrixUndirectedValuedGraph extends AdjacencyMatrixUndirectedGraph {

	//--------------------------------------------------
	// 				Class variables
	//-------------------------------------------------- 

	// No class variable, we use the matrix variable but with costs values

	//--------------------------------------------------
	// 				Constructors
	//-------------------------------------------------- 

	public AdjacencyMatrixUndirectedValuedGraph(int[][] matrixVal) {
		super(matrixVal);
	}


	
	// ------------------------------------------------
	// 					Methods 
	// ------------------------------------------------	
	
	
	/**
     * Adds the edge (x,y,cost). If there is already one initial cost, we replace it.
     */
	public void addEdge(int x, int y, int cost) {
		if (x != y) {
			matrix[x][y] = cost;
			matrix[y][x] = cost;
			if (!isEdge(x, y)) {
				nbEdges++;
			}
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder("\n Matrix of Costs: \n");
		for (int[] lineCost : this.matrix) {
			for (int i : lineCost) {
				s.append(i).append("\t");
			}
			s.append("\n");
		}
		s.append("\n");
		return s.toString();
	}
	
	
	public static void main(String[] args) {
        int[][] matrixValued = {
            {0, 2, 5, 0},
            {2, 0, 3, 0},
            {5, 3, 0, 4},
            {0, 0, 4, 0}
        };
        System.out.println("Test 1: Creating a small valued graph.");
        AdjacencyMatrixUndirectedValuedGraph graph = new AdjacencyMatrixUndirectedValuedGraph(matrixValued);
        System.out.println(graph);
        
        // Test initial conditions.
        boolean edge01Initial = graph.isEdge(0, 1);
        boolean edge03Initial = graph.isEdge(0, 3);
        System.out.println("Initial edge (0,1) exists? " + edge01Initial + " (Should be TRUE) " + (edge01Initial ? "✅" : "❌"));
        System.out.println("Initial edge (0,3) exists? " + edge03Initial + " (Should be FALSE) " + (!edge03Initial ? "✅" : "❌"));

        // Test 2: Add a new edge.
        System.out.println("\nTest 2: Adding new edge between vertices 0 and 3 with cost 6.");
        graph.addEdge(0, 3, 6);
        boolean edge03AfterAdd = graph.isEdge(0, 3);
        System.out.println(graph);
        System.out.println("After adding edge (0,3), isEdge(0,3)? " + edge03AfterAdd + " (Should be TRUE) " + (edge03AfterAdd ? "✅" : "❌"));

        // Test 3: Update an existing edge.
        System.out.println("\nTest 3: Updating existing edge between vertices 0 and 1 from cost 2 to 8.");
        int oldCost = graph.matrix[0][1];
        graph.addEdge(0, 1, 8);
        int newCost = graph.matrix[0][1];
        System.out.println(graph);
        System.out.println("Cost changed from " + oldCost + " to " + newCost + " (Should be 8) " + (newCost == 8 ? "✅" : "❌"));
        System.out.println("Symmetric cost (1,0) is also 8? " + (graph.matrix[1][0] == 8) + " (Should be TRUE) " + (graph.matrix[1][0] == 8 ? "✅" : "❌"));

        // Test 4: Try to add edge to same vertex.
        System.out.println("\nTest 4: Attempting to add edge to same vertex (1,1) - should be ignored.");
        graph.addEdge(1, 1, 10);
        System.out.println(graph);
        System.out.println("Self-loop (1,1) was prevented? " + (graph.matrix[1][1] == 0) + " (Should be TRUE) " + (graph.matrix[1][1] == 0 ? "✅" : "❌"));

        // Test 5: Test with random generated graph.
        System.out.println("\nTest 5: Testing with random generated graph (5 vertices).");
        int[][] randomMatrix = GraphTools.generateValuedGraphData(10, false, true, true, false, 100001);
        AdjacencyMatrixUndirectedValuedGraph randomGraph = new AdjacencyMatrixUndirectedValuedGraph(randomMatrix);
        System.out.println(randomGraph);
        
        // Verify symmetry in random graph.
        boolean isSymmetric = true;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (randomGraph.matrix[i][j] != randomGraph.matrix[j][i]) {
                    isSymmetric = false;
                    break;
                }
            }
        }
        System.out.println("Random graph is symmetric? " + isSymmetric + " (Should be TRUE) " + (isSymmetric ? "✅" : "❌"));
    }
}
