package Nodes_Edges;

public class Edge extends AbstractEdgeArc {
	
	//--------------------------------------------------
    // 				Class variables
    //--------------------------------------------------

	
	//--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------
	
	public Edge(UndirectedNode node1, UndirectedNode node2) {
		super(node1, node2);
		
	}
	
	public Edge(UndirectedNode node1, UndirectedNode node2, int weight) {
		super(node1, node2,weight);
	}	

	
	// ------------------------------------------
    // 				Accessors
    // ------------------------------------------
	
	/**
	 * @return the first node incident to the edge
	 */
	public UndirectedNode getFirstNode(){
		return (UndirectedNode) super.node1;
	}
	
	/**
	 * @return the second node incident to the edge
	 */
	public UndirectedNode getSecondNode(){
		return (UndirectedNode) super.node2;
	}
	
	/**
	 * @param n1 the new node of the edge (node1,node2).
	 */
	public void setFirstNode(UndirectedNode n1){
		this.node1 = n1;
	}
	
	/**
	 * @param n2 the new node of the edge (node1,node2).
	 */
	public void setSecondNode(UndirectedNode n2){
		this.node2 = n2;
	}
	
	
	// ------------------------------------------
    // 				Methods
    // ------------------------------------------

	/**
	 * check if two edges are equals => the nodes are the same
	 * @param e an object which is an edge
	 * @return true iff this and e are equal
	 */
	@Override
	public boolean equals(Object e) {
		return e instanceof AbstractEdgeArc &&
                ( ((Edge) e).getFirstNode().equals(this.getFirstNode()) && ((Edge) e).getSecondNode().equals(this.getSecondNode()) ||
                        ((Edge) e).getFirstNode().equals(this.getSecondNode()) && ((Edge) e).getSecondNode().equals(this.getFirstNode()));
	}
	
}
