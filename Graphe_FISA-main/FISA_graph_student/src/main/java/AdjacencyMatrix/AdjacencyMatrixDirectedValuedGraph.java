package AdjacencyMatrix;

import java.util.List;


public class AdjacencyMatrixDirectedValuedGraph extends AdjacencyMatrixDirectedGraph {

	//--------------------------------------------------
	// 				Class variables
	//-------------------------------------------------- 

	// No class variable, we use the matrix variable but with costs values 

	//--------------------------------------------------
	// 				Constructors
	//-------------------------------------------------- 

	public AdjacencyMatrixDirectedValuedGraph(int[][] matrixVal) {
		super(matrixVal);
	}

	
	// ------------------------------------------------
	// 					Methods
	// ------------------------------------------------	
	
	
	/**
     * adds the arc (from,to,cost). If there is already one initial cost, we replace it.
     */	
	public void addArc(int from, int to, int cost ) {
		if (from != to) {
			matrix[from][to] = cost;
			if (!isArc(from, to)) {
				nbArcs++;
			}
		}
	}


	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("\nAdjacency Matrix Directed Valued Graph:\n    ");

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
        int[][] definedMatrix = {
            {0, 3, 0, 4, 0},
            {0, 0, 2, 0, 0},
            {0, 0, 0, 5, 1},
            {6, 0, 0, 0, 0},
            {0, 7, 0, 0, 0}
        };
        System.out.println("Test 1: Creating a directed valued graph with defined matrix.");
        AdjacencyMatrixDirectedValuedGraph graph = new AdjacencyMatrixDirectedValuedGraph(definedMatrix);
        System.out.println(graph);
        System.out.println("Number of nodes = " + graph.getNbNodes() + " (Should be 5) " + (graph.getNbNodes() == 5 ? "✅" : "❌"));
        System.out.println("Number of arcs = " + graph.getNbArcs() + " (Should be 7) " + (graph.getNbArcs() == 7 ? "✅" : "❌"));

        // Test 2: Check initial arcs and costs.
        System.out.println("\nTest 2: Checking initial arcs and costs.");
        boolean arc01 = graph.isArc(0, 1);
        boolean arc10 = graph.isArc(1, 0);
        System.out.println("Arc (0,1) exists? " + arc01 + " (Should be TRUE) " + (arc01 ? "✅" : "❌"));
        System.out.println("Cost of arc (0,1) is 3? " + (graph.matrix[0][1] == 3) + " (Should be TRUE) " + (graph.matrix[0][1] == 3 ? "✅" : "❌"));
        System.out.println("Arc (1,0) exists? " + arc10 + " (Should be FALSE) " + (!arc10 ? "✅" : "❌"));

        // Test 3: Test successors and predecessors with costs.
        System.out.println("\nTest 3: Testing successors and predecessors.");
        List<Integer> successors2 = graph.getSuccessors(2);
        List<Integer> predecessors1 = graph.getPredecessors(1);
        System.out.println("Successors of vertex 2: " + successors2);
        System.out.println("Cost to successor 3 is 5? " + (graph.matrix[2][3] == 5) + " (Should be TRUE) " + 
                         (graph.matrix[2][3] == 5 ? "✅" : "❌"));
        System.out.println("Predecessors of vertex 1: " + predecessors1);
        System.out.println("Cost from predecessor 0 is 3? " + (graph.matrix[0][1] == 3) + " (Should be TRUE) " + 
                         (graph.matrix[0][1] == 3 ? "✅" : "❌"));

        // Test 4: Add new arc with cost.
        System.out.println("\nTest 4: Adding new arc with cost.");
        graph.addArc(1, 4, 8);
        boolean arc14AfterAdd = graph.isArc(1, 4);
        System.out.println("After adding arc (1,4) with cost 8:");
        System.out.println("Arc (1,4) exists? " + arc14AfterAdd + " (Should be TRUE) " + (arc14AfterAdd ? "✅" : "❌"));
        System.out.println("Cost of new arc is 8? " + (graph.matrix[1][4] == 8) + " (Should be TRUE) " + 
                         (graph.matrix[1][4] == 8 ? "✅" : "❌"));
        System.out.println(graph);

        // Test 5: Update existing arc cost.
        System.out.println("\nTest 5: Updating existing arc cost.");
        int oldCost = graph.matrix[0][1];
        graph.addArc(0, 1, 9);
        int newCost = graph.matrix[0][1];
        System.out.println("Original cost of arc (0,1): " + oldCost);
        System.out.println("After updating arc (0,1) to cost 9:");
        System.out.println("New cost is 9? " + (newCost == 9) + " (Should be TRUE) " + (newCost == 9 ? "✅" : "❌"));
        System.out.println(graph);

        // Test 6: Try to add self-loop.
        System.out.println("\nTest 6: Testing self-loop prevention.");
        graph.addArc(1, 1, 10);
        System.out.println("Self-loop (1,1) was prevented? " + (!graph.isArc(1, 1)) + " (Should be TRUE) " + 
                         (!graph.isArc(1, 1) ? "✅" : "❌"));

        // Test 7: Error handling.
        System.out.println("\nTest 7: Error handling.");
        try {
            graph.addArc(20, 30, 5);
            System.out.println("❌ Failed to catch invalid vertices");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Successfully caught invalid vertices exception ✅");
        }
    }
}
