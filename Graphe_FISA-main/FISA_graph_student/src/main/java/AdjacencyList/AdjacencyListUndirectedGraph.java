package AdjacencyList;

import java.util.ArrayList;
import java.util.List;

import Nodes_Edges.Edge;
import Nodes_Edges.UndirectedNode;


public class AdjacencyListUndirectedGraph {

	//--------------------------------------------------
    // 				Class variables
    //--------------------------------------------------

	protected List<UndirectedNode> nodes; // list of the nodes in the graph
	protected List<Edge> edges; // list of the edges in the graph
    protected int nbNodes; // number of nodes
    protected int nbEdges; // number of edges

    
    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------
    
	public AdjacencyListUndirectedGraph() {
		 this.nodes = new ArrayList<UndirectedNode>();
		 this.edges = new ArrayList<Edge>();
		 this.nbNodes = 0;
	     this.nbEdges = 0;
	}
	
		
	public AdjacencyListUndirectedGraph(List<UndirectedNode> nodes,List<Edge> edges) {
		this.nodes = nodes;
		this.edges = edges;
        this.nbNodes = nodes.size();
        this.nbEdges = edges.size();
        
    }

    public AdjacencyListUndirectedGraph(int[][] matrix) {
        this.nbNodes = matrix.length;
        this.nodes = new ArrayList<UndirectedNode>();
        this.edges = new ArrayList<Edge>();
        
        for (int i = 0; i < this.nbNodes; i++) {
            this.nodes.add(new UndirectedNode(i));
        }
        for (UndirectedNode n1 : this.getNodes()) {
            for (int j = n1.getLabel(); j < matrix[n1.getLabel()].length; j++) {
            	UndirectedNode n2 = this.getNodes().get(j);
                if (matrix[n1.getLabel()][j] != 0) {
                    Edge e1 = new Edge(n1,n2);
                    n1.addEdge(e1);
                    this.edges.add(e1);
                	n2.addEdge(new Edge(n2,n1));
                    this.nbEdges++;
                }
            }
        }
    }

    public AdjacencyListUndirectedGraph(AdjacencyListUndirectedGraph g) {
        super();
        this.nbNodes = g.getNbNodes();
        this.nbEdges = g.getNbEdges();
        this.nodes = new ArrayList<UndirectedNode>();
        this.edges = new ArrayList<Edge>();
        
        
        for (UndirectedNode n : g.getNodes()) {
            this.nodes.add(new UndirectedNode(n.getLabel()));
        }
        
        for (Edge e : g.getEdges()) {
        	this.edges.add(e);
        	UndirectedNode new_n   = this.getNodes().get(e.getFirstNode().getLabel());
        	UndirectedNode other_n = this.getNodes().get(e.getSecondNode().getLabel());
        	new_n.addEdge(new Edge(e.getFirstNode(),e.getSecondNode(),e.getWeight()));
        	other_n.addEdge(new Edge(e.getSecondNode(),e.getFirstNode(),e.getWeight()));
        }        
    }
    

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------
    
    /**
     * Returns the list of nodes in the graph
     */
    public List<UndirectedNode> getNodes() {
        return this.nodes;
    }
    
    /**
     * Returns the list of edges in the graph
     */
    public List<Edge> getEdges() {
        return this.edges;
    }

    /**
     * Returns the number of nodes in the graph
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
     * @return true if there is an edge between x and y
     */
    public boolean isEdge(UndirectedNode x, UndirectedNode y) {
        for (Edge e : this.edges) {
            if ((e.getFirstNode().equals(x) && e.getSecondNode().equals(y)) ||
                (e.getFirstNode().equals(y) && e.getSecondNode().equals(x))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes edge (x,y) if there exists one. And remove this edge and the inverse in the list of edges from the two extremities (nodes)
     */
    public void removeEdge(UndirectedNode x, UndirectedNode y) {
        this.edges.removeIf(e ->
            (e.getFirstNode().equals(x) && e.getSecondNode().equals(y)) ||
            (e.getFirstNode().equals(y) && e.getSecondNode().equals(x))
        );
        x.getIncidentEdges().removeIf(e -> e.getSecondNode().equals(y));
        y.getIncidentEdges().removeIf(e -> e.getSecondNode().equals(x));
        this.nbEdges--;
    }

    /**
     * Adds edge (x,y) if it is not already present in the graph, requires that nodes x and y already exist. 
     * And adds this edge to the incident list of both extremities (nodes) and into the global list "edges" of the graph.
     * In non-valued graph, every edge has a cost equal to 0.
     */
    public void addEdge(UndirectedNode x, UndirectedNode y) {
        if (!isEdge(x, y)) {
            Edge edge = new Edge(x, y);
            this.edges.add(edge);
            x.addEdge(edge);
            y.addEdge(new Edge(y, x));
            this.nbEdges++;
        }
    }

    //--------------------------------------------------
    // 					Methods
    //--------------------------------------------------

    /**
     * @return the corresponding nodes in the list this.nodes
     */
    public UndirectedNode getNodeOfList(UndirectedNode v) {
        return this.getNodes().get(v.getLabel());
    }
    
    /**
     * @return a matrix representation of the graph 
     */
    public int[][] toAdjacencyMatrix() {
        int[][] matrix = new int[this.nbNodes][this.nbNodes];
        for (Edge e : this.edges) {
            int i = e.getFirstNode().getLabel();
            int j = e.getSecondNode().getLabel();
            matrix[i][j] = 1;
            matrix[j][i] = 1; // Symmetric for undirected graph.
        }
        return matrix;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Undirected Graph\n");
        s.append("Number of nodes: ").append(nbNodes).append("\n");
        s.append("Number of edges: ").append(nbEdges).append("\n");
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
            {0, 1, 0, 0, 0},
            {1, 0, 1, 0, 0},
            {0, 1, 0, 1, 0},
            {0, 0, 1, 0, 1},
            {0, 0, 0, 1, 0}
        };

        System.out.println("Test: Creating an undirected graph with a defined matrix.");
        AdjacencyListUndirectedGraph graph = new AdjacencyListUndirectedGraph(matrix);
        System.out.println(graph);

        // Test isEdge.
        System.out.println("Testing edge existence between node 0 and node 1:");
        boolean edge01 = graph.isEdge(graph.getNodes().get(0), graph.getNodes().get(1));
        System.out.println("Edge (0,1) exists? " + edge01 + " (Should be TRUE) " + (edge01 ? "✅" : "❌"));

        // Test addEdge.
        System.out.println("Adding edge between node 0 and node 4...");
        graph.addEdge(graph.getNodes().get(0), graph.getNodes().get(4));
        boolean edge04 = graph.isEdge(graph.getNodes().get(0), graph.getNodes().get(4));
        System.out.println("Edge (0,4) exists? " + edge04 + " (Should be TRUE) " + (edge04 ? "✅" : "❌"));
        System.out.println(graph);

        // Test removeEdge.
        System.out.println("Removing edge between node 0 and node 4...");
        graph.removeEdge(graph.getNodes().get(0), graph.getNodes().get(4));
        boolean edge04Supp = graph.isEdge(graph.getNodes().get(0), graph.getNodes().get(4));
        System.out.println("Edge (0,4) exists after removal? " + edge04Supp + " (Should be FALSE) " + (!edge04Supp ? "✅" : "❌"));
        System.out.println(graph);

        // Test toAdjacencyMatrix.
        System.out.println("Adjacency matrix:");
        int[][] adjacencyMatrix = graph.toAdjacencyMatrix();
        for (int[] row : adjacencyMatrix) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }
}
