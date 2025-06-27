package AdjacencyMatrix;

import java.util.List;


public class AdjacencyMatrixDirectedValuedGraph extends AdjacencyMatrixDirectedGraph {

	//--------------------------------------------------
	// 				Class variables
	//-------------------------------------------------- 

	// No class variable, we use the matrix variable but with costs values 

	//--------------------------------------------------
	// 				Constructors
	//-------------------------------------------------- 

	public AdjacencyMatrixDirectedValuedGraph(int[][] matrixVal) {
		super(matrixVal);
	}

	
	// ------------------------------------------------
	// 					Methods
	// ------------------------------------------------	
	
	
	/**
     * adds the arc (from,to,cost). If there is already one initial cost, we replace it.
     */	
	public void addArc(int from, int to, int cost ) {
		if (from != to) {
			matrix[from][to] = cost;
			if (!isArc(from, to)) {
				nbArcs++;
			}
		}
	}

	/**
	 * Question 1: Implémentation de l'algorithme de Dijkstra
	 * 
	 * @param s sommet source (point de départ)
	 */
	public void dijkstra(int s) {
		int n = this.nbNodes;
		
		// Ligne 1: Initialisation des structures de données
		boolean[] mark = new boolean[n];
		int[] val = new int[n];
		Integer[] pred = new Integer[n];
		
		// Lignes 2-4: Initialisation des valeurs
		for (int v = 0; v < n; v++) {
			mark[v] = false;
			val[v] = Integer.MAX_VALUE / 2; // MaxInt/2 pour éviter les débordements
			pred[v] = null;
		}
		
		// Ligne 5: Initialisation du sommet source
		val[s] = 0;
		pred[s] = s;
		
		System.out.println("=== Algorithme de Dijkstra depuis le sommet " + s + " ===");
		System.out.println("Initialisation:");
		afficherEtat(mark, val, pred);
		
		// Ligne 6: Boucle principale tant qu'il reste des sommets non marqués
		while (true) {
			int x = -1;
			
			// Lignes 8-14: Recherche du sommet x non marqué de val minimum
			int min = Integer.MAX_VALUE / 2;
			for (int y = 0; y < n; y++) {
				if (!mark[y] && val[y] < min) {
					x = y;
					min = val[y];
				}
			}
			
			// Ligne 16: Si aucun sommet atteignable trouvé, arrêter
			if (min >= Integer.MAX_VALUE / 2 || x == -1) {
				break;
			}
			
			// Ligne 17: Marquer le sommet choisi
			mark[x] = true;
			System.out.println("\nTraitement du sommet " + x + " (distance: " + val[x] + ")");
			
			// Lignes 18-22: Mise à jour des successeurs non fixés de x
			for (int y = 0; y < n; y++) {
				if (!mark[y] && matrix[x][y] > 0) { // il y a un arc de x vers y
					int nouveauCost = val[x] + matrix[x][y];
					if (nouveauCost < val[y]) {
						val[y] = nouveauCost;
						pred[y] = x;
						System.out.println("  Mise à jour: sommet " + y + 
								" nouvelle distance = " + val[y] + 
								", prédécesseur = " + pred[y]);
					}
				}
			}
			
			afficherEtat(mark, val, pred);
		}
		
		// Affichage final des résultats
		System.out.println("\n=== Résultats finaux ===");
		System.out.println("Distances depuis le sommet " + s + ":");
		for (int v = 0; v < n; v++) {
			if (val[v] == Integer.MAX_VALUE / 2) {
				System.out.println("Sommet " + v + ": INACCESSIBLE");
			} else {
				System.out.println("Sommet " + v + ": distance = " + val[v] + 
						", prédécesseur = " + pred[v]);
			}
		}
		
		// Affichage des chemins
		System.out.println("\nChemins optimaux:");
		for (int v = 0; v < n; v++) {
			if (v != s && val[v] != Integer.MAX_VALUE / 2) {
				System.out.print("Chemin vers " + v + ": ");
				afficherChemin(s, v, pred);
				System.out.println(" (coût total: " + val[v] + ")");
			}
		}
	}
	
	/**
	 * Méthode utilitaire pour afficher l'état actuel des tableaux
	 */
	private void afficherEtat(boolean[] mark, int[] val, Integer[] pred) {
		System.out.print("Marqués: [");
		for (int i = 0; i < mark.length; i++) {
			System.out.print(mark[i] ? "T" : "F");
			if (i < mark.length - 1) System.out.print(", ");
		}
		System.out.println("]");
		
		System.out.print("Distances: [");
		for (int i = 0; i < val.length; i++) {
			if (val[i] == Integer.MAX_VALUE / 2) {
				System.out.print("∞");
			} else {
				System.out.print(val[i]);
			}
			if (i < val.length - 1) System.out.print(", ");
		}
		System.out.println("]");
		
		System.out.print("Prédécesseurs: [");
		for (int i = 0; i < pred.length; i++) {
			System.out.print(pred[i] == null ? "null" : pred[i]);
			if (i < pred.length - 1) System.out.print(", ");
		}
		System.out.println("]");
	}
	
	/**
	 * Méthode utilitaire pour afficher le chemin de s à v
	 */
	private void afficherChemin(int s, int v, Integer[] pred) {
		if (v == s) {
			System.out.print(s);
		} else if (pred[v] == null) {
			System.out.print("Pas de chemin");
		} else {
			afficherChemin(s, pred[v], pred);
			System.out.print(" → " + v);
		}
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("\nAdjacency Matrix Directed Valued Graph:\n    ");

		// Column headers.
		for (int i = 0; i < nbNodes; i++) {
			s.append(String.format("%3d", i));
		}
		s.append("\n   ");

		// Separator line.
		for (int i = 0; i < 3 * nbNodes; i++) {
			s.append("-");
		}
		s.append("\n");

		// Matrix rows with row indices.
		for (int i = 0; i < nbNodes; i++) {
			s.append(String.format("%2d |", i));
			for (int j = 0; j < nbNodes; j++) {
				s.append(String.format("%3d", matrix[i][j]));
			}
			s.append("\n");
		}
		return s.toString();
	}

	/**
	 * Tests de l'algorithme de Dijkstra
	 */
	public static void main(String[] args) {
		System.out.println("=== Tests de l'algorithme de Dijkstra ===\n");
		
		// Test 1: Graphe orienté simple
		System.out.println("--- Test 1: Graphe orienté simple ---");
		int[][] matrix1 = {
			{0, 4, 2, 0, 0},
			{0, 0, 1, 5, 0},
			{0, 0, 0, 8, 10},
			{0, 0, 0, 0, 2},
			{3, 0, 0, 0, 0}
		};
		
		AdjacencyMatrixDirectedValuedGraph graph1 = new AdjacencyMatrixDirectedValuedGraph(matrix1);
		System.out.println("Graphe:");
		System.out.println(graph1);
		
		graph1.dijkstra(0);
		
		System.out.println("\n--- Test 2: Graphe avec cycles ---");
		int[][] matrix2 = {
			{0, 1, 4, 0, 0},
			{0, 0, 2, 6, 0},
			{0, 0, 0, 3, 2},
			{0, 0, 0, 0, 1},
			{0, 0, 0, 0, 0}
		};
		
		AdjacencyMatrixDirectedValuedGraph graph2 = new AdjacencyMatrixDirectedValuedGraph(matrix2);
		System.out.println("Graphe:");
		System.out.println(graph2);
		
		graph2.dijkstra(0);
		
		System.out.println("\n=== Complexité ===");
		System.out.println("Complexité de Dijkstra (version naïve): O(n²)");
		System.out.println("- n itérations de la boucle principale");
		System.out.println("- À chaque itération: O(n) pour trouver le minimum + O(n) pour la mise à jour");
		System.out.println("Optimisation possible avec un tas binaire: O((n + m) log n)");
	}
}
