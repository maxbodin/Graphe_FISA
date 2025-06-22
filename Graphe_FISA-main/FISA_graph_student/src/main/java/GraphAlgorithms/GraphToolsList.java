package GraphAlgorithms;

import java.util.*;

import AdjacencyList.AdjacencyListDirectedGraph;
import Nodes_Edges.Arc;
import Nodes_Edges.DirectedNode;

public class GraphToolsList extends GraphTools {

	private static int _DEBBUG = 0;

	private static int[] visite;
	private static int[] debut;
	private static int[] fin;
	private static List<Integer> order_CC;
	private static int cpt = 0;

	//--------------------------------------------------
	// 				Constructors
	//--------------------------------------------------

	public GraphToolsList() {
		super();
	}

	// ------------------------------------------
	// 				Accessors
	// ------------------------------------------


	// ------------------------------------------
	// 				Methods
	// ------------------------------------------


	public static void parcoursBFSLargeur(AdjacencyListDirectedGraph graph, int s) {
		boolean[] mark = new boolean[graph.getNbNodes()];
		for (int v = 0; v < graph.getNbNodes(); v++) {
			mark[v] = false;
		}
		mark[s] = true;
		Queue<DirectedNode> toVisit = new LinkedList<>();
		toVisit.add(graph.getNodes().get(s));
		while (!toVisit.isEmpty()) {
			DirectedNode v = toVisit.poll();
			System.out.print(v.getLabel() + " ");
			for (Arc outGoingArc : v.getArcSucc()) {
				int w = outGoingArc.getSecondNode().getLabel();
				if (!mark[w]) {
					mark[w] = true;
					toVisit.add(outGoingArc.getSecondNode());
				}
			}
		}
	}

	//static List<DirectedNode> fin = new ArrayList<>();

	//Question 7: complexité en O(n + m) avec n sommets et m arcs, car chaque sommet et arc est visité une seule fois.
//Question 9: Si debut[x] < debut[y], alors :
// 				Soit y est un descendant de x car compris entre son temps de début et de fin, soitd ebut[x] < debut[y] < fin[y] < fin[x]
//				Soit y n’est pas un descendant de x car fini après lui (fin[x] < fin[y])
	public static void explorerSommet(DirectedNode s, Set<DirectedNode> a) {
		a.add(s);
		debut[s.getLabel()] = ++cpt;
		visite[s.getLabel()] = 1;  // 1 = en cours
		System.out.print(s.getLabel() + " ");
		for (Arc t : s.getArcSucc()) {
			DirectedNode voisin = t.getSecondNode();
			if (!a.contains(voisin)) {
				explorerSommet(voisin, a);
			}
		}
		visite[s.getLabel()] = 2; // 2 = terminé
		fin[s.getLabel()] = ++cpt;
	}


	public static void explorerGraphe(AdjacencyListDirectedGraph al) {
		Set<DirectedNode> atteint = new HashSet<>();
		int nbNodes = al.getNbNodes();
		visite = new int[nbNodes];
		cpt = 0;
		debut = new int[nbNodes];
		fin = new int[nbNodes];
		for (int i = 0; i < nbNodes; i++) {
			visite[i] = 0; //0 = non visité
			debut[i] = 0;
			fin[i] = 0;
		}
		for (DirectedNode node : al.getNodes()) {
			if (!atteint.contains(node)) {
				System.out.println("Nouveau départ depuis le sommet " + node.getLabel() + " :");
				explorerSommet(node, atteint);
				System.out.println();
			}
		}

		// Affichage des temps de fin
		System.out.println("\nTemps de fin des sommets :");
		for (int i = 0; i < al.getNbNodes(); i++) {
			System.out.println("Sommet " + i + " : " + fin[i]);
		}

	}

	public static boolean isDescendant(int x, int y) {
		return debut[x] < debut[y] && fin[y] < fin[x];
	}

	public static List<Integer> getOrdreFinDecroissant() {
		List<Integer> ordre = new ArrayList<>();
		for (int i = 0; i < fin.length; i++) {
			ordre.add(i);
		}
		ordre.sort((a, b) -> Integer.compare(fin[b], fin[a]));
		return ordre;
	}

	public static void explorerSommetBis(DirectedNode s, Set<DirectedNode> a) {
		a.add(s);

		for (Arc t : s.getArcSucc()) {
			if (a.stream().noneMatch(directedNode -> directedNode.getLabel()==t.getSecondNode().getLabel())) {
				explorerSommetBis(t.getSecondNode(), a);
			}
		}
		System.out.print(s.getLabel() + " ");
	}

	public static void explorerGrapheBis(AdjacencyListDirectedGraph al, List<Integer> ordreFin) {
		Set<DirectedNode> atteint = new HashSet<>();
		List<DirectedNode> ordreFinDirectedNodes = new ArrayList<>();
		List<DirectedNode> nodes = al.getNodes(); // Tous les noeuds, indexés par label
		for (Integer label : ordreFin) {
			ordreFinDirectedNodes.add(nodes.get(label)); // On suppose que label == index
		}
		System.out.println("Composantes fortements connexes: ");
		for (DirectedNode node : ordreFinDirectedNodes) {
			if (!atteint.contains(node)) {
				System.out.print("{ ");
				explorerSommetBis(node, atteint);
				System.out.print("} ");
			}
		}
	}

	public static void main(String[] args) {
        int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, true, 100001);
        GraphTools.afficherMatrix(Matrix);
        AdjacencyListDirectedGraph al = new AdjacencyListDirectedGraph(Matrix);
        System.out.println(al);
        System.out.println("========");
        System.out.println("Parcours en largeur (BFS) départ 0");
        parcoursBFSLargeur(al, 0);
        System.out.println("\n========");
        System.out.println("Parcours en profondeur (DFS) départ 0 (par défaut)");
		// Question 13 (Kosaraju):
		// 1. Premier en profondeur en notant l’ordre de fin d’exploration des sommets visités
        explorerGraphe(al);
        // 2. Refaire un DFS
        order_CC = getOrdreFinDecroissant(); //en suivant cet ordre de manière inversé
		System.out.println("Ordre inverse des sommets complètement explorés");
		System.out.println(order_CC);
		AdjacencyListDirectedGraph alInverse = al.computeInverse(); // sur le graphe inversé
		System.out.println();
		explorerGrapheBis(alInverse, order_CC);


		//Vérification avec l'exemple du cours https://moodle.imt-atlantique.fr/mod/resource/view.php?id=50133

		System.out.println("\n======Exemple Algo composantes complexes (extrait de cours)====== ");

		int[][] matrix = {
				{0, 0, 0, 0, 0, 1, 0, 0}, // 1 →
				{0, 0, 1, 0, 1, 0, 0, 0}, // 2 →
				{0, 0, 0, 1, 0, 0, 0, 0}, // 3 →
				{0, 0, 0, 0, 0, 0, 0, 0}, // 4 →
				{0, 0, 0, 0, 0, 0, 0, 1}, // 5 →
				{0, 0, 0, 0, 0, 0, 1, 0}, // 6 →
				{1, 0, 1, 1, 0, 0, 0, 0}, // 7 →
				{1, 1, 0, 1, 0, 0, 0, 0}  // 8 →
		};

		AdjacencyListDirectedGraph al2 = new AdjacencyListDirectedGraph(matrix);
		System.out.println(al2.getArcs());

		explorerGraphe(al2);
		// 2. Refaire un DFS
		order_CC = getOrdreFinDecroissant(); //en suivant cet ordre de manière inversé
		System.out.println("Ordre de fin inversé : " + order_CC);
		AdjacencyListDirectedGraph alInverse2 = al2.computeInverse(); // sur le graphe inversé
		System.out.println();
		explorerGrapheBis(alInverse2, order_CC);

	}
}
