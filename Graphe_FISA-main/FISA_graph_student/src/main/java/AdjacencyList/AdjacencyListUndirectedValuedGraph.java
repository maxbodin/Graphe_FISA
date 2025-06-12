package AdjacencyList;

import Nodes_Edges.Arc;
import Nodes_Edges.Edge;
import Nodes_Edges.UndirectedNode;

public class AdjacencyListUndirectedValuedGraph extends AdjacencyListUndirectedGraph {

	//--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------

    public AdjacencyListUndirectedValuedGraph(int[][] matrixVal) {
    	super();
    	this.nbNodes = matrixVal.length;
        
        for (int i = 0; i < this.nbNodes; i++) {
            this.nodes.add(new UndirectedNode(i));            
        }
        for (UndirectedNode n1 : this.getNodes()) {
            for (int j = n1.getLabel(); j < matrixVal[n1.getLabel()].length; j++) {
            	UndirectedNode n2 = this.getNodes().get(j);
                if (matrixVal[n1.getLabel()][j] != 0) {
                	Edge e1 = new Edge(n1,n2,matrixVal[n1.getLabel()][j]);
                    n1.addEdge(e1);
                    this.edges.add(e1);
                	n2.addEdge(new Edge(n2,n1,matrixVal[n1.getLabel()][j]));
                    this.nbEdges++;
                }
            }
        }
    }

    //--------------------------------------------------
    // 				Methods
    //--------------------------------------------------
    

    /**
     * Adds the edge (from,to) with cost if it is not already present in the graph.
     * And adds this edge to the incident list of both extremities (nodes) and into the global list "edges" of the graph.
     */
    public void addEdge(UndirectedNode x, UndirectedNode y, int cost) {
        if (x == null || y == null) {
            throw new IllegalArgumentException("Nodes cannot be null");
        }

        if (x.equals(y)) {
            throw new IllegalArgumentException("Cannot add an edge from a node to itself");
        }

        // Find existing arc by direct search
        for (Edge edge : this.edges) {
            if (edge.getFirstNode().equals(x) && edge.getSecondNode().equals(y)) {
                edge.setWeight(cost);
                return;
            }
        }

        // If no existing edge found, create new ones.
        Edge e1 = new Edge(x, y, cost);
        Edge e2 = new Edge(y, x, cost);
        x.addEdge(e1);
        y.addEdge(e2);
        this.edges.add(e1);
        this.nbEdges++;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Undirected Valued Graph\n");
        s.append("Number of nodes: ").append(nbNodes).append("\n");
        s.append("Number of edges: ").append(nbEdges).append("\n\n");
        s.append("List of nodes and their neighbours :\n");
        for (UndirectedNode n : this.nodes) {
            s.append("Node ").append(n.getLabel()).append(" -> ");
            for (Edge e : n.getIncidentEdges()) {
                s.append("(").append(e.getSecondNode().getLabel()).append(", weight=").append(e.getWeight()).append(") ");
            }
            s.append("\n");
        }
        s.append("\nList of edges :\n");
        for (Edge e : this.edges) {
            s.append(e).append("  ");
        }
        s.append("\n");
        return s.toString();
    }

    public static void main(String[] args) {
        int[][] matrix = {
            {0, 3, 0, 4, 0},
            {3, 0, 2, 0, 0},
            {0, 2, 0, 5, 1},
            {4, 0, 5, 0, 6},
            {0, 0, 1, 6, 0}
        };

        System.out.println("Test: Creating an undirected valued graph with a defined matrix.");
        AdjacencyListUndirectedValuedGraph graph = new AdjacencyListUndirectedValuedGraph(matrix);
        System.out.println(graph);

        // Test addEdge.
        System.out.println("Adding edge between node 0 and node 2 with weight 8...");
        graph.addEdge(graph.getNodes().get(0), graph.getNodes().get(2), 8);
        boolean hasNewEdge = graph.getNodes().get(0).getIncidentEdges().stream()
            .anyMatch(e -> e.getSecondNode().equals(graph.getNodes().get(2)) && e.getWeight() == 8);
        System.out.println("Edge (0,2) with weight 8 exists? " + hasNewEdge + " (Should be TRUE) " + (hasNewEdge ? "✅" : "❌"));
        System.out.println(graph);

        // Test updateEdge.
        System.out.println("Updating edge between node 0 and node 1 with new weight 9...");
        graph.addEdge(graph.getNodes().get(0), graph.getNodes().get(1), 9);
        boolean costUpdated = graph.getNodes().get(0).getIncidentEdges().stream()
            .anyMatch(e -> e.getSecondNode().equals(graph.getNodes().get(1)) && e.getWeight() == 9);
        System.out.println("Edge (0,1) updated to weight 9? " + costUpdated + " (Should be TRUE) " + (costUpdated ? "✅" : "❌"));
        System.out.println(graph);
    }
}