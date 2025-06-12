package Nodes_Edges;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by gsimonin on 05/03/2025.
 */
public class UndirectedNode extends AbstractNode {

    //--------------------------------------------------
    // 				Class variables
    //--------------------------------------------------

	private List<Edge> incidentEdges; // List of edges incident with the node this.

    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------

    public UndirectedNode(int i) {
        super(i);
        this.incidentEdges = new LinkedList<Edge>();        
    }

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------

    /**
     * @return the list of incident edges of the current node this
     */
    public List<Edge> getIncidentEdges() {
        return this.incidentEdges;
    }


    /**
     * @return the number of neighbors of node this
     */
	public int getNbNeigh() {
        return incidentEdges.size();
    }
	

	/**
	 * Add a new edge with its value cost. If the neighbour exists, the weight is changed.
	 */
	public void addEdge(Edge e1) {
		if(!this.getIncidentEdges().contains(e1)) {			
			if(e1.getFirstNode().equals(this)) {
				this.incidentEdges.add(e1);
			}
			else if(e1.getSecondNode().equals(this) ) {
				this.incidentEdges.add(new Edge(e1.getSecondNode() ,e1.getFirstNode(),e1.getWeight()));
			} 			
		}
		else {
			// Update of the existing weight.
			int indexEdge;
			if(this.getIncidentEdges().contains(e1)){
				indexEdge = this.getIncidentEdges().indexOf(e1);
			}
			else {
				indexEdge = this.getIncidentEdges().indexOf(new Edge(e1.getSecondNode(),e1.getFirstNode()) );
			}
			this.getIncidentEdges().get(indexEdge).setWeight(e1.getWeight());
		}
	}
}