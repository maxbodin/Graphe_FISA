package Nodes_Edges;

import java.util.LinkedList;
import java.util.List;

public class DirectedNode extends AbstractNode {

    //--------------------------------------------------
    // 				Class variables
    //--------------------------------------------------

	private List<Arc> arcSucc; // List of out-going arc from the node this.
	private List<Arc> arcPred; // List of in-going arc from the node this.

    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------

    public DirectedNode(int i) {
        super(i);
        this.arcSucc = new LinkedList<Arc>();
        this.arcPred = new LinkedList<Arc>();
    }
    
    
    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------

    /**
     * @return the number of successors of node this
     */
    public int getNbSuccs() {
        return arcSucc.size();
    }
    
    /**
     * @return the number of predecessors of node this
     */
    public int getNbPreds() {
        return arcPred.size();
    }

   
	/** 
	 * @return the list of out-going arcs that are successors of the current node represented by "this"
	 */
	public List<Arc> getArcSucc() {
		return this.arcSucc;
	}
	
	/** 
	 * @return the list of in-going arcs that are predecessors of the current node represented by "this"
	 */
	public List<Arc> getArcPred() {
		return this.arcPred;
	}
    		
	
    // ------------------------------------------
    // 				Methods
    // ------------------------------------------
    
    	
	/**
	 * add a new edge with its value cost. If the successor exists, the weight is changed.
	 */
	public void addArc(Arc a1) {
		if (a1.getFirstNode().equals(this)) { // case where the arc is out-going from "this".
			if (!this.getArcSucc().contains(a1)) {
				this.arcSucc.add(a1);
			} else {
				// Update of the existing weight
				int indexEdge = this.getArcSucc().indexOf(a1);
				this.getArcSucc().get(indexEdge).setWeight(a1.getWeight());
			}
		} else if (a1.getSecondNode().equals(this)) { // case where the arc is in-going to "this".
			if (!this.getArcPred().contains(a1)) {
				this.arcPred.add(a1);
			} else {
				// Update of the existing weight
				int indexEdge = this.getArcPred().indexOf(a1);
				this.getArcPred().get(indexEdge).setWeight(a1.getWeight());
			}
		}
	}
}