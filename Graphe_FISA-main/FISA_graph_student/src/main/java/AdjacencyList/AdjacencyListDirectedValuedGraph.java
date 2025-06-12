package AdjacencyList;

import Nodes_Edges.Arc;
import Nodes_Edges.DirectedNode;

public class AdjacencyListDirectedValuedGraph extends AdjacencyListDirectedGraph {

	//--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------

	public AdjacencyListDirectedValuedGraph(int[][] matrixVal) {
    	super();
    	this.nbNodes = matrixVal.length;
        for (int i = 0; i < this.nbNodes; i++) {
            this.nodes.add(new DirectedNode(i));
        }
        for (DirectedNode n1 : this.getNodes()) {
            for (int j = 0; j < matrixVal[n1.getLabel()].length; j++) {
            	DirectedNode n2 = this.getNodes().get(j);
                if (matrixVal[n1.getLabel()][j] != 0) {
                	Arc a1 = new Arc(n1,n2,matrixVal[n1.getLabel()][j]);
                    n1.addArc(a1);
                    this.arcs.add(a1);
                	n2.addArc(a1);
                    this.nbArcs ++;
                }
            }
        }            	
    }

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------
    

    /**
     * Adds the arc (from,to) with cost if it is not already present in the graph. 
     * And adds this arc to the incident list of both extremities (nodes) and into the global list "arcs" of the graph.
     */
    public void addArc(DirectedNode from, DirectedNode to, int cost) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Nodes cannot be null.");
        }

        if (from.equals(to)) {
            throw new IllegalArgumentException("Cannot add an arc from a node to itself.");
        }

        // Find existing arc by direct search
        for (Arc arc : this.arcs) {
            if (arc.getFirstNode().equals(from) && arc.getSecondNode().equals(to)) {
                arc.setWeight(cost);
                return;
            }
        }

        // If no existing arc found, create new one.
        Arc newArc = new Arc(from, to, cost);
        from.addArc(newArc);
        to.addArc(newArc);
        this.arcs.add(newArc);
        this.nbArcs++;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Directed Valued Graph\n");
        s.append("Number of nodes: ").append(nbNodes).append("\n");
        s.append("Number of arcs: ").append(nbArcs).append("\n\n");
        s.append("List of nodes and their successors/predecessors :\n");
        for (DirectedNode n : this.nodes) {
            s.append("\nNode ").append(n).append(" : ");
            s.append("\nList of out-going arcs: ");
            for (Arc a : n.getArcSucc()) {
                s.append(a).append("  ");
            }
            s.append("\nList of in-coming arcs: ");
            for (Arc a : n.getArcPred()) {
                s.append(a).append("  ");
            }
            s.append("\n");
        }
        s.append("\nList of arcs :\n");
        for (Arc a : this.arcs) {
            s.append(a).append("  ");
        }
        s.append("\n");
        return s.toString();
    }

    public static void main(String[] args) {
        int[][] matrix = {
            {0, 3, 0, 4, 0},
            {0, 0, 2, 0, 0},
            {0, 0, 0, 5, 1},
            {6, 0, 0, 0, 0},
            {0, 7, 0, 0, 0}
        };

        System.out.println("Test: Creating a directed valued graph with a defined matrix.");
        AdjacencyListDirectedValuedGraph graph = new AdjacencyListDirectedValuedGraph(matrix);
        System.out.println(graph);

        // Test addArc.
        System.out.println("Adding arc from node 0 to node 2 with weight 8...");
        graph.addArc(graph.getNodes().get(0), graph.getNodes().get(2), 8);
        boolean hasNewArc = graph.getNodes().get(0).getArcSucc().stream()
            .anyMatch(a -> a.getSecondNode().equals(graph.getNodes().get(2)) && a.getWeight() == 8);
        System.out.println("Arc (0,2) with weight 8 exists? " + hasNewArc + " (Should be TRUE) " + (hasNewArc ? "✅" : "❌"));
        System.out.println(graph);

        // Test updateArc.
        System.out.println("Updating arc from node 0 to node 1 with new weight 9...");
        graph.addArc(graph.getNodes().get(0), graph.getNodes().get(1), 9);
        boolean costUpdated = graph.getNodes().get(0).getArcSucc().stream()
            .anyMatch(a -> a.getSecondNode().equals(graph.getNodes().get(1)) && a.getWeight() == 9);
        System.out.println("Arc (0,1) updated to weight 9? " + costUpdated + " (Should be TRUE) " + (costUpdated ? "✅" : "❌"));
        System.out.println(graph);
    }
}
