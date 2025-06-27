package AdjacencyList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import GraphAlgorithms.BinaryHeapEdge;
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

    /**
     * Questions 9-10: Implémentation de l'algorithme de Prim
     * 
     * Réponse à la question 9:
     * Le tas binaire est utilisé pour maintenir efficacement les arêtes candidates
     * triées par poids croissant. À chaque étape, on extrait l'arête de poids minimum
     * qui ne crée pas de cycle, ce qui garantit l'optimalité de l'algorithme.
     * 
     * @param startVertex le sommet de départ pour l'algorithme
     * @return la liste des arêtes de l'arbre couvrant minimum
     */
    public List<Edge> prim(int startVertex) {
        if (startVertex < 0 || startVertex >= this.nbNodes) {
            throw new IllegalArgumentException("Sommet de départ invalide: " + startVertex);
        }
        
        System.out.println("=== Algorithme de Prim depuis le sommet " + startVertex + " ===");
        
        List<Edge> mst = new ArrayList<>(); // Arbre couvrant minimum (solution)
        Set<UndirectedNode> visited = new HashSet<>(); // Nœuds déjà visités
        BinaryHeapEdge candidateEdges = new BinaryHeapEdge(); // Tas binaire pour les arêtes candidates
        
        // Étape 1: Ajouter le sommet initial dans la liste des nœuds visités
        UndirectedNode startNode = this.nodes.get(startVertex);
        visited.add(startNode);
        System.out.println("Sommet initial: " + startVertex);
        
        // Étape 2: Insérer dans le tas binaire chaque arête adjacente au sommet initial
        System.out.println("Ajout des arêtes adjacentes au sommet " + startVertex + ":");
        for (Edge edge : startNode.getIncidentEdges()) {
            UndirectedNode neighbor = edge.getSecondNode();
            if (!visited.contains(neighbor)) {
                candidateEdges.insert(edge.getFirstNode(), edge.getSecondNode(), edge.getWeight());
                System.out.println("  Arête ajoutée: " + edge);
            }
        }
        
        // Étape 3: Boucle principale
        while (!candidateEdges.isEmpty() && visited.size() < this.nbNodes) {
            // Retirer l'arête de poids minimum du tas binaire
            Edge minEdge = candidateEdges.remove();
            
            if (minEdge == null) break;
            
            // Vérifier si cette arête crée un cycle
            UndirectedNode from = (UndirectedNode) minEdge.getFirstNode();
            UndirectedNode to = (UndirectedNode) minEdge.getSecondNode();
            
            // Si les deux sommets sont déjà visités, l'arête créerait un cycle
            if (visited.contains(from) && visited.contains(to)) {
                System.out.println("Arête " + minEdge + " ignorée (créerait un cycle)");
                continue;
            }
            
            // Ajouter l'arête à la solution
            mst.add(minEdge);
            System.out.println("Arête ajoutée à l'arbre: " + minEdge + " (poids: " + minEdge.getWeight() + ")");
            
            // Déterminer le nouveau sommet à ajouter
            UndirectedNode newVertex = visited.contains(from) ? to : from;
            visited.add(newVertex);
            System.out.println("Nouveau sommet visité: " + newVertex.getLabel());
            
            // Insérer dans le tas binaire les nouvelles arêtes adjacentes
            // (ne créant pas de cycle)
            System.out.println("Ajout des nouvelles arêtes adjacentes:");
            for (Edge edge : newVertex.getIncidentEdges()) {
                UndirectedNode neighbor = edge.getSecondNode();
                if (!visited.contains(neighbor)) {
                    candidateEdges.insert(edge.getFirstNode(), edge.getSecondNode(), edge.getWeight());
                    System.out.println("  Arête ajoutée: " + edge);
                }
            }
            
            System.out.println("Arêtes dans l'arbre: " + mst.size() + "/" + (this.nbNodes - 1));
            System.out.println();
        }
        
        // Vérification du résultat
        if (mst.size() != this.nbNodes - 1) {
            System.out.println("ATTENTION: Le graphe n'est pas connexe!");
            System.out.println("Arêtes trouvées: " + mst.size() + ", attendues: " + (this.nbNodes - 1));
        } else {
            System.out.println("Arbre couvrant minimum trouvé avec succès!");
        }
        
        return mst;
    }
    
    /**
     * Méthode utilitaire pour calculer le poids total de l'arbre couvrant minimum
     */
    public int calculerPoidsTotal(List<Edge> mst) {
        int poidsTotal = 0;
        for (Edge edge : mst) {
            poidsTotal += edge.getWeight();
        }
        return poidsTotal;
    }
    
    /**
     * Méthode utilitaire pour afficher l'arbre couvrant minimum
     */
    public void afficherMST(List<Edge> mst) {
        System.out.println("=== Arbre Couvrant Minimum ===");
        System.out.println("Nombre d'arêtes: " + mst.size());
        System.out.println("Arêtes:");
        for (Edge edge : mst) {
            System.out.println("  " + edge.getFirstNode().getLabel() + 
                             " -- " + edge.getSecondNode().getLabel() + 
                             " (poids: " + edge.getWeight() + ")");
        }
        System.out.println("Poids total: " + calculerPoidsTotal(mst));
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

    /**
     * Question 11: Tests de l'algorithme de Prim
     */
    public static void main(String[] args) {
        System.out.println("=== Tests de l'algorithme de Prim ===\n");
        
        // Test 1: Graphe simple connexe
        System.out.println("--- Test 1: Graphe simple ---");
        int[][] matrix1 = {
            {0, 2, 0, 6, 0},
            {2, 0, 3, 8, 5},
            {0, 3, 0, 0, 7},
            {6, 8, 0, 0, 9},
            {0, 5, 7, 9, 0}
        };
        
        AdjacencyListUndirectedValuedGraph graph1 = new AdjacencyListUndirectedValuedGraph(matrix1);
        System.out.println("Graphe:");
        System.out.println(graph1);
        
        List<Edge> mst1 = graph1.prim(0);
        graph1.afficherMST(mst1);
        
        System.out.println("\n--- Test 2: Graphe plus complexe ---");
        int[][] matrix2 = {
            {0, 4, 0, 0, 0, 0, 0, 8, 0},
            {4, 0, 8, 0, 0, 0, 0, 11, 0},
            {0, 8, 0, 7, 0, 4, 0, 0, 2},
            {0, 0, 7, 0, 9, 14, 0, 0, 0},
            {0, 0, 0, 9, 0, 10, 0, 0, 0},
            {0, 0, 4, 14, 10, 0, 2, 0, 0},
            {0, 0, 0, 0, 0, 2, 0, 1, 6},
            {8, 11, 0, 0, 0, 0, 1, 0, 7},
            {0, 0, 2, 0, 0, 0, 6, 7, 0}
        };
        
        AdjacencyListUndirectedValuedGraph graph2 = new AdjacencyListUndirectedValuedGraph(matrix2);
        System.out.println("Graphe:");
        System.out.println(graph2);
        
        List<Edge> mst2 = graph2.prim(0);
        graph2.afficherMST(mst2);
        
        // Test avec différents points de départ
        System.out.println("\n--- Test 3: Différents points de départ ---");
        System.out.println("MST depuis le sommet 2:");
        List<Edge> mst3 = graph1.prim(2);
        System.out.println("Poids total: " + graph1.calculerPoidsTotal(mst3));
        
        System.out.println("\n=== Complexité et propriétés ===");
        System.out.println("Complexité de Prim avec tas binaire: O((V + E) log V)");
        System.out.println("- V: nombre de sommets, E: nombre d'arêtes");
        System.out.println("- Chaque arête est insérée et extraite au plus une fois du tas");
        System.out.println("- Le tas binaire assure une extraction du minimum en O(log E)");
        System.out.println("\nPropriétés de l'arbre couvrant minimum:");
        System.out.println("- Contient exactement V-1 arêtes");
        System.out.println("- Connecte tous les sommets");
        System.out.println("- Poids total minimal parmi tous les arbres couvrants possibles");
    }
}