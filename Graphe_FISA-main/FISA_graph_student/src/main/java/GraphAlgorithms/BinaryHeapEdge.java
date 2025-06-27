package GraphAlgorithms;

import java.util.ArrayList;
import java.util.List;

import Nodes_Edges.DirectedNode;
import Nodes_Edges.Edge;
import Nodes_Edges.UndirectedNode;

public class BinaryHeapEdge {

	/**
	 * A list structure for a faster management of the heap by indexing
	 * 
	 */
	private  List<Edge> binh;

    public BinaryHeapEdge() {
        this.binh = new ArrayList<Edge>();
    }

    public boolean isEmpty() {
        return binh.isEmpty();
    }

    /**
	 * Insert a new edge in the binary heap
	 * 
	 * @param from one node of the edge
	 * @param to one node of the edge
	 * @param val the edge weight
	 */
    public boolean insert(UndirectedNode from, UndirectedNode to, int val) {
        // Créer une nouvelle arête
        Edge newEdge = new Edge(from, to, val);
        
        // Ajouter l'arête à la fin de la liste (première feuille disponible)
        binh.add(newEdge);
        
        // Remonter l'arête jusqu'à rencontrer un parent de poids plus petit ou égal
        int currentIndex = binh.size() - 1;
        while (currentIndex > 0) {
            int parentIndex = (currentIndex - 1) / 2;
            if (binh.get(parentIndex).getWeight() <= binh.get(currentIndex).getWeight()) {
                break; // Le parent a un poids plus petit ou égal, on s'arrête
            }
            // Sinon, on échange avec le parent
            swap(parentIndex, currentIndex);
            currentIndex = parentIndex;
        }
        
        return true;
    }

    
    /**
	 * Removes the root edge in the binary heap, and swap the edges to keep a valid binary heap
	 * 
	 * @return the edge with the minimal value (root of the binary heap)
	 * 
	 */
    public Edge remove() {
        if (isEmpty()) {
            return null;
        }
        
        // Sauvegarder l'arête à retourner (racine = minimum)
        Edge removedEdge = binh.get(0);
        
        // Permuter la racine avec la dernière feuille utilisée
        Edge lastEdge = binh.get(binh.size() - 1);
        binh.set(0, lastEdge);
        binh.remove(binh.size() - 1); // Retirer la dernière arête
        
        // Si le tas n'est pas vide, descendre l'élément
        if (!isEmpty()) {
            int currentIndex = 0;
            while (!isLeaf(currentIndex)) {
                int bestChildIndex = getBestChildPos(currentIndex);
                if (bestChildIndex == Integer.MAX_VALUE || 
                    binh.get(currentIndex).getWeight() <= binh.get(bestChildIndex).getWeight()) {
                    break; // L'arête est à la bonne position
                }
                // Sinon, échanger avec le meilleur fils
                swap(currentIndex, bestChildIndex);
                currentIndex = bestChildIndex;
            }
        }
        
        return removedEdge;
    }
    

    /**
	 * From an edge indexed by src, find the child having the least weight and return it
	 * 
	 * @param src an index of the list edges
	 * @return the index of the child edge with the least weight
	 */
    private int getBestChildPos(int src) {
    	int lastIndex = binh.size()-1; 
        if (isLeaf(src)) { // the leaf is a stopping case, then we return a default value
            return Integer.MAX_VALUE;
        } else {
            int leftChildIndex = 2 * src + 1;
            int rightChildIndex = 2 * src + 2;
            
            // Si le nœud n'a qu'un seul fils (gauche)
            if (rightChildIndex > lastIndex) {
                return leftChildIndex;
            }
            
            // Sinon, retourner l'index du fils ayant le plus petit poids
            if (binh.get(leftChildIndex).getWeight() <= binh.get(rightChildIndex).getWeight()) {
                return leftChildIndex;
            } else {
                return rightChildIndex;
            }
        }
    }

    private boolean isLeaf(int src) {
        // Un nœud est une feuille si son premier fils gauche dépasse la taille du tas
        return (2 * src + 1) >= binh.size();
    }

    
    /**
	 * Swap two edges in the binary heap
	 * 
	 * @param father an index of the list edges
	 * @param child an index of the list edges
	 */
    private void swap(int father, int child) {         
        // Méthode corrigée : échanger les positions dans la liste plutôt que les contenus
        Edge temp = binh.get(father);
        binh.set(father, binh.get(child));
        binh.set(child, temp);
    }

    
    /**
	 * Create the string of the visualisation of a binary heap
	 * 
	 * @return the string of the binary heap
	 */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Edge no: binh) {
            s.append(no).append(", ");
        }
        return s.toString();
    }
    
    
    private String space(int x) {
		StringBuilder res = new StringBuilder();
		for (int i=0; i<x; i++) {
			res.append(" ");
		}
		return res.toString();
	}
	
	/**
	 * Print a nice visualisation of the binary heap as a hierarchy tree
	 * 
	 */	
	public void lovelyPrinting(){
		int nodeWidth = this.binh.get(0).toString().length();
		int depth = 1+(int)(Math.log(this.binh.size())/Math.log(2));
		int index=0;
		
		for(int h = 1; h<=depth; h++){
			int left = ((int) (Math.pow(2, depth-h-1)))*nodeWidth - nodeWidth/2;
			int between = ((int) (Math.pow(2, depth-h))-1)*nodeWidth;
			int i =0;
			System.out.print(space(left));
			while(i<Math.pow(2, h-1) && index<binh.size()){
				System.out.print(binh.get(index) + space(between));
				index++;
				i++;
			}
			System.out.println("");
		}
		System.out.println("");
	}
	
	// ------------------------------------
    // 					TEST
	// ------------------------------------

	/**
	 * Recursive test to check the validity of the binary heap
	 * 
	 * @return a boolean equal to True if the binary tree is compact from left to right
	 * 
	 */
    private boolean test() {
        return this.isEmpty() || testRec(0);
    }

    private boolean testRec(int root) {
    	System.out.println("root= "+root);
    	int lastIndex = binh.size()-1; 
        if (isLeaf(root)) {
            return true;
        } else {
            int left = 2 * root + 1;
            int right = 2 * root + 2;
            System.out.println("left = "+left);
            System.out.println("right = "+right);
            if (right > lastIndex) {
                return binh.get(left).getWeight() >= binh.get(root).getWeight() && testRec(left);
            } else {
                return binh.get(left).getWeight() >= binh.get(root).getWeight() && testRec(left)
                    && binh.get(right).getWeight() >= binh.get(root).getWeight() && testRec(right);
            }
        }
    }

    public static void main(String[] args) {
        BinaryHeapEdge jarjarBin = new BinaryHeapEdge();
        System.out.println("Tas binaire d'arêtes vide? " + jarjarBin.isEmpty() + "\n");
        
        // Tests d'insertion avec des arêtes
        System.out.println("=== Tests d'insertion d'arêtes ===");
        int[] weights = {4, 10, 8, 6, 3, 15, 7};
        
        for (int i = 0; i < weights.length; i++) {
            UndirectedNode from = new UndirectedNode(i);
            UndirectedNode to = new UndirectedNode(i + 10);
            System.out.println("Insertion d'arête avec poids " + weights[i]);
            jarjarBin.insert(from, to, weights[i]);
            System.out.println("Tas après insertion: " + jarjarBin);
            System.out.println("Tas valide? " + jarjarBin.test());
            System.out.println();
        }
        
        System.out.println("Tas final: " + jarjarBin);
        
        // Tests de suppression
        System.out.println("\n=== Tests de suppression ===");
        for (int i = 0; i < 3; i++) {
            System.out.println("Suppression " + (i + 1) + ":");
            Edge removed = jarjarBin.remove();
            if (removed != null) {
                System.out.println("Arête supprimée: " + removed + " (poids: " + removed.getWeight() + ")");
                System.out.println("Tas après suppression: " + jarjarBin);
                System.out.println("Tas valide? " + jarjarBin.test());
            } else {
                System.out.println("Tas vide!");
            }
            System.out.println();
        }
        
        System.out.println("=== Complexité ===");
        System.out.println("Complexité insertion: O(log n) - remontée dans l'arbre");
        System.out.println("Complexité suppression: O(log n) - descente dans l'arbre");
    }

}

