package Nodes_Edges;

public class Arc extends AbstractEdgeArc {

	//--------------------------------------------------
    // 				Class variables
    //--------------------------------------------------

	
	//--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------
	
	public Arc(DirectedNode node1, DirectedNode node2) {
		super(node1, node2);
	}
	
	public Arc(DirectedNode node1, DirectedNode node2, int weight) {
		super(node1, node2,weight);
	}
		
	
	// ------------------------------------------
    // 				Accessors
    // ------------------------------------------
	
	/**
	 * @return the origin node of the arc
	 */
	public DirectedNode getFirstNode(){
		return (DirectedNode) super.node1;
	}
	
	/**
	 * @return the destination node  of the arc
	 */
	public DirectedNode getSecondNode(){
		return (DirectedNode) super.node2;
	}
	
	/**
	 * @param n1 the new node of the edge (node1,node2).
	 */
	public void setFirstNode(DirectedNode n1){
		this.node1 = n1;
	}
	
	/**
	 * @param n2 the new node of the edge (node1,node2).
	 */
	public void setSecondNode(DirectedNode n2){
		this.node2 = n2;
	}
	
	
	// ------------------------------------------
    // 				Methods
    // ------------------------------------------
	
	/**
	 * check if two arcs are equals => the nodes are the same
	 * @param e an object which is an arc
	 * @return true iff this and e are equal
	 */
	@Override
	public boolean equals(Object e) {
		return e instanceof AbstractEdgeArc &&
				( ((Arc) e).getFirstNode().equals(this.getFirstNode()) && ((Arc) e).getSecondNode().equals(this.getSecondNode()) );
	}
}
