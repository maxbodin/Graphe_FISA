package AdjacencyList;

import java.util.ArrayList;
import java.util.List;

import Nodes_Edges.Arc;
import Nodes_Edges.DirectedNode;

public class AdjacencyListDirectedGraph {

	//--------------------------------------------------
    // 				Class variables
    //--------------------------------------------------
	
	protected List<DirectedNode> nodes; // list of the nodes in the graph
	protected List<Arc> arcs; // list of the arcs in the graph
    protected int nbNodes; // number of nodes
    protected int nbArcs; // number of arcs
	
    

    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------
 

	public AdjacencyListDirectedGraph(){
		this.nodes = new ArrayList<DirectedNode>();
		this.arcs= new ArrayList<Arc>();
		this.nbNodes = 0;
	    this.nbArcs = 0;		
	}
	
	public AdjacencyListDirectedGraph(List<DirectedNode> nodes,List<Arc> arcs) {
		this.nodes = nodes;
		this.arcs= arcs;
        this.nbNodes = nodes.size();
        this.nbArcs = arcs.size();                
    }

    public AdjacencyListDirectedGraph(int[][] matrix) {
        this.nbNodes = matrix.length;
        this.nodes = new ArrayList<DirectedNode>();
        this.arcs= new ArrayList<Arc>();
        
        for (int i = 0; i < this.nbNodes; i++) {
            this.nodes.add(new DirectedNode(i));
        }
        
        for (DirectedNode n1 : this.getNodes()) {
            for (int j = 0; j < matrix[n1.getLabel()].length; j++) {
            	DirectedNode n2 = this.getNodes().get(j);
                if (matrix[n1.getLabel()][j] != 0) {
                	Arc a = new Arc(n1,n2);
                    n1.addArc(a);
                    this.arcs.add(a);                    
                    n2.addArc(a);
                    this.nbArcs++;
                }
            }
        }
    }

    public AdjacencyListDirectedGraph(AdjacencyListDirectedGraph g) {
        super();
        this.nodes = new ArrayList<>();
        this.arcs= new ArrayList<Arc>();
        this.nbNodes = g.getNbNodes();
        this.nbArcs = g.getNbArcs();
        
        for(DirectedNode n : g.getNodes()) {
            this.nodes.add(new DirectedNode(n.getLabel()));
        }
        
        for (Arc a1 : g.getArcs()) {
        	this.arcs.add(a1);
        	DirectedNode new_n   = this.getNodes().get(a1.getFirstNode().getLabel());
        	DirectedNode other_n = this.getNodes().get(a1.getSecondNode().getLabel());
        	Arc a2 = new Arc(a1.getFirstNode(),a1.getSecondNode(),a1.getWeight());
        	new_n.addArc(a2);
        	other_n.addArc(a2);
        }
    }

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------

    /**
     * Returns the list of nodes in the graph
     */
    public List<DirectedNode> getNodes() {
        return nodes;
    }
    
    /**
     * Returns the list of nodes in the graph
     */
    public List<Arc> getArcs() {
        return arcs;
    }

    /**
     * Returns the number of nodes in the graph
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
	 * @return true if arc (from,to) exists in the graph
 	 */
    public boolean isArc(DirectedNode from, DirectedNode to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Nodes cannot be null.");
        }

        if (from.equals(to)) {
            throw new IllegalArgumentException("Cannot check an arc from a node to itself.");
        }

        for (Arc a : this.arcs) {
            if (a.getFirstNode().equals(from) && a.getSecondNode().equals(to)) {
                return true;
            }
        }

    	return false;
    }

    /**
	 * Removes the arc (from,to), if it exists. And remove this arc and the inverse in the list of arcs from the two extremities (nodes)
 	 */
    public void removeArc(DirectedNode from, DirectedNode to) {
        Arc arcToRemove = null;
        for (Arc a : this.arcs) {
            if (a.getFirstNode().equals(from) && a.getSecondNode().equals(to)) {
                arcToRemove = a;
                break;
            }
        }

        if (arcToRemove != null) {
            this.arcs.remove(arcToRemove);
            from.getArcSucc().removeIf(e -> e.getSecondNode().equals(to));
            to.getArcPred().removeIf(e -> e.getFirstNode().equals(from));
            this.nbArcs--;
        }
    }

    /**
	* Adds the arc (from,to) if it is not already present in the graph, requires the existing of nodes from and to. 
	* And add this arc to the incident list of both extremities (nodes) and into the global list "arcs" of the graph.   	 
  	* On non-valued graph, every arc has a weight equal to 0.
 	*/
    public void addArc(DirectedNode from, DirectedNode to) {
         if (from == null || to == null) {
            throw new IllegalArgumentException("Nodes cannot be null.");
        }

        if (from.equals(to)) {
            throw new IllegalArgumentException("Cannot add an arc from a node to itself.");
        }

        Arc a = new Arc(from, to);

        if (!this.arcs.contains(a)) {
            from.addArc(a);
            to.addArc(a);
            this.arcs.add(a);
            this.nbArcs++;
        }
    }

    //--------------------------------------------------
    // 				Methods
    //--------------------------------------------------

     /**
     * @return the corresponding nodes in the list this.nodes
     */
    public DirectedNode getNodeOfList(DirectedNode src) {
        return this.getNodes().get(src.getLabel());
    }

    /**
     * @return the adjacency matrix representation int[][] of the graph
     */
    public int[][] toAdjacencyMatrix() {
        int[][] matrix = new int[this.nbNodes][this.nbNodes];
        for (Arc a : this.arcs) {
            int i = a.getFirstNode().getLabel();
            int j = a.getSecondNode().getLabel();
            matrix[i][j] = 1;
        }
        return matrix;
    }

    /**
	 * @return a new graph implementing IDirectedGraph interface which is the inverse graph of this
 	 */
    public AdjacencyListDirectedGraph computeInverse() {
        AdjacencyListDirectedGraph inverse = new AdjacencyListDirectedGraph();
        // Add nodes.
        for (int i = 0; i < this.nbNodes; i++) {
            inverse.nodes.add(new DirectedNode(i));
        }
        inverse.nbNodes = this.nbNodes;
        
        // For each arc in the original graph, add its inverse to the new graph.
        for (Arc arc : this.arcs) {
            DirectedNode newFrom = inverse.nodes.get(arc.getSecondNode().getLabel());
            DirectedNode newTo = inverse.nodes.get(arc.getFirstNode().getLabel());
            
            // Create new arc with switched direction and same weight.
            Arc newArc = new Arc(newFrom, newTo, arc.getWeight());
            newFrom.addArc(newArc);
            newTo.addArc(newArc);
            inverse.arcs.add(newArc);
            inverse.nbArcs++;
        }
        
        return inverse;
    }
    
    @Override
    public String toString(){
    	StringBuilder s = new StringBuilder();
        s.append("Directed Graph\n");
        s.append("Number of nodes: ").append(nbNodes).append("\n");
        s.append("Number of arcs: ").append(nbArcs).append("\n");
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
            {0, 1, 0, 0, 0},
            {0, 0, 1, 0, 0},
            {0, 0, 0, 1, 0},
            {0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0}
        };

        System.out.println("Test: Creating a directed graph with a defined matrix.");
        AdjacencyListDirectedGraph graph = new AdjacencyListDirectedGraph(matrix);
        System.out.println(graph);

        // Test inverse graph.
        System.out.println("Computing inverse graph...");
        AdjacencyListDirectedGraph inverse = graph.computeInverse();
        System.out.println("Inverse graph:");
        System.out.println(inverse);

        // Verify some arcs in the inverse graph.
        boolean hasInverseArc01 = inverse.isArc(graph.getNodes().get(1), graph.getNodes().get(0));
        System.out.println("Inverse arc (1,0) exists? " + hasInverseArc01 + " (Should be TRUE) " + 
                         (hasInverseArc01 ? "✅" : "❌"));

        boolean hasInverseArc40 = inverse.isArc(graph.getNodes().get(0), graph.getNodes().get(4));
        System.out.println("Inverse arc (0,4) exists? " + hasInverseArc40 + " (Should be TRUE) " + 
                         (hasInverseArc40 ? "✅" : "❌"));

        // Test addArc.
        System.out.println("Adding arc from node 0 to node 2...");
        graph.addArc(graph.getNodes().get(0), graph.getNodes().get(2));
        boolean hasNewArc = graph.getNodes().get(0).getArcSucc().stream()
            .anyMatch(a -> a.getSecondNode().equals(graph.getNodes().get(2)));
        System.out.println("Arc (0,2) exists? " + hasNewArc + " (Should be TRUE) " + (hasNewArc ? "✅" : "❌"));
        System.out.println(graph);

        // Test removeArc.
        System.out.println("Removing arc from node 0 to node 2...");
        graph.removeArc(graph.getNodes().get(0), graph.getNodes().get(2));
        boolean arcRemoved = graph.getNodes().get(0).getArcSucc().stream()
            .noneMatch(a -> a.getSecondNode().equals(graph.getNodes().get(2)));
        System.out.println("Arc (0,2) removed? " + arcRemoved + " (Should be TRUE) " + (arcRemoved ? "✅" : "❌"));
        System.out.println(graph);
    }
}
