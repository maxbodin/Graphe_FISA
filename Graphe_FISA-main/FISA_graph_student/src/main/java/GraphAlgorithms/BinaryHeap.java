package GraphAlgorithms;


public class BinaryHeap {

    private int[] nodes;
    private int pos;

    public BinaryHeap() {
        this.nodes = new int[32];
        for (int i = 0; i < nodes.length; i++) {
            this.nodes[i] = Integer.MAX_VALUE;
        }
        this.pos = 0;
    }

    public void resize() {
        int[] tab = new int[this.nodes.length + 32];
        for (int i = 0; i < nodes.length; i++) {
            tab[i] = Integer.MAX_VALUE;
        }
        System.arraycopy(this.nodes, 0, tab, 0, this.nodes.length);
        this.nodes = tab;
    }

    public boolean isEmpty() {
        return pos == 0;
    }

    public boolean insert(int element) {
        if (pos >= nodes.length) {
            resize();
        }
        
        nodes[pos] = element;
        
        int currentIndex = pos;
        while (currentIndex > 0) {
            int parentIndex = (currentIndex - 1) / 2;
            if (nodes[parentIndex] <= nodes[currentIndex]) {
                break;
            }
            swap(parentIndex, currentIndex);
            currentIndex = parentIndex;
        }
        
        pos++;
        return true;
    }

    public int remove() {
        if (isEmpty()) {
            return Integer.MAX_VALUE;
        }
        
        int removedElement = nodes[0];
        
        pos--;
        nodes[0] = nodes[pos];
        nodes[pos] = Integer.MAX_VALUE;
        
        int currentIndex = 0;
        while (!isLeaf(currentIndex)) {
            int bestChildIndex = getBestChildPos(currentIndex);
            if (bestChildIndex == Integer.MAX_VALUE || nodes[currentIndex] <= nodes[bestChildIndex]) {
                break;
            }
            swap(currentIndex, bestChildIndex);
            currentIndex = bestChildIndex;
        }
        
        return removedElement;
    }

    private int getBestChildPos(int src) {
        if (isLeaf(src)) {
            return Integer.MAX_VALUE;
        } else {
            int leftChildIndex = 2 * src + 1;
            int rightChildIndex = 2 * src + 2;
            
            if (rightChildIndex >= pos) {
                return leftChildIndex;
            }
            
            if (nodes[leftChildIndex] <= nodes[rightChildIndex]) {
                return leftChildIndex;
            } else {
                return rightChildIndex;
            }
        }
    }

    private boolean isLeaf(int src) {
        return (2 * src + 1) >= pos;
    }

    private void swap(int father, int child) {
        int temp = nodes[father];
        nodes[father] = nodes[child];
        nodes[child] = temp;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < pos; i++) {
            s.append(nodes[i]).append(", ");
        }
        return s.toString();
    }

    public boolean test() {
        return this.isEmpty() || testRec(0);
    }

    private boolean testRec(int root) {
        if (isLeaf(root)) {
            return true;
        } else {
            int left = 2 * root + 1;
            int right = 2 * root + 2;
            if (right >= pos) {
                return nodes[left] >= nodes[root] && testRec(left);
            } else {
                return nodes[left] >= nodes[root] && testRec(left) && nodes[right] >= nodes[root] && testRec(right);
            }
        }
    }

    public static void main(String[] args) {
        BinaryHeap jarjarBin = new BinaryHeap();
        System.out.println("Tas binaire vide? " + jarjarBin.isEmpty() + "\n");
        
        System.out.println("=== Tests d'insertion (Question 4) ===");
        int[] valuesToInsert = {4, 10, 8, 6, 3};
        
        for (int val : valuesToInsert) {
            System.out.println("Insertion de " + val);
            boolean success = jarjarBin.insert(val);
            System.out.println("Succès: " + success);
            System.out.println("Tas après insertion: " + jarjarBin);
            System.out.println("Tas valide? " + jarjarBin.test());
            System.out.println();
        }
        
        System.out.println("Tas final: " + jarjarBin);
        System.out.println("Tas valide? " + jarjarBin.test());
        
        System.out.println("\n=== Tests de suppression (Question 7) ===");
        System.out.println("Première suppression:");
        int removed1 = jarjarBin.remove();
        System.out.println("Élément supprimé: " + removed1);
        System.out.println("Tas après suppression: " + jarjarBin);
        System.out.println("Tas valide? " + jarjarBin.test());
        
        System.out.println("\nDeuxième suppression:");
        int removed2 = jarjarBin.remove();
        System.out.println("Élément supprimé: " + removed2);
        System.out.println("Tas après suppression: " + jarjarBin);
        System.out.println("Tas valide? " + jarjarBin.test());
        
        System.out.println("\n=== Complexité ===");
        System.out.println("Complexité insertion: O(log n) - remontée dans l'arbre");
        System.out.println("Complexité suppression: O(log n) - descente dans l'arbre");
    }

}
