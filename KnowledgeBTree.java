
public class KnowledgeBTree {

    private static final int T = 2;

    class Node {
        int n;
        int[] keys = new int[2 * T - 1];
        String[] topics = new String[2 * T - 1];
        Node[] children = new Node[2 * T];
        boolean leaf = true;
    }

    private Node root = new Node();

    // Insert a topic into the B-tree
    public void insert(int key, String topic) {
        // Update an existing topic ID instead of inserting a duplicate
        if (updateExisting(root, key, topic)) {
            return;
        }

        if (root.n == 2 * T - 1) {
            Node newRoot = new Node();
            newRoot.leaf = false;
            newRoot.children[0] = root;

            splitChild(newRoot, 0);
            root = newRoot;
        }

        insertNonFull(root, key, topic);
    }

    // Update topic if the key already exists
    private boolean updateExisting(Node node, int key, String topic) {
        int i = 0;

        while (i < node.n && key > node.keys[i]) {
            i++;
        }

        if (i < node.n && key == node.keys[i]) {
            node.topics[i] = topic;
            return true;
        }

        if (node.leaf) {
            return false;
        }

        return updateExisting(node.children[i], key, topic);
    }

    // Split a full child
    private void splitChild(Node parent, int index) {
        Node full = parent.children[index];
        Node right = new Node();

        right.leaf = full.leaf;
        right.n = T - 1;

        // Copy the largest keys into the new right child
        for (int j = 0; j < T - 1; j++) {
            right.keys[j] = full.keys[j + T];
            right.topics[j] = full.topics[j + T];
        }

        // Copy child pointers if the node is not a leaf
        if (!full.leaf) {
            for (int j = 0; j < T; j++) {
                right.children[j] = full.children[j + T];
            }
        }

        int middleKey = full.keys[T - 1];
        String middleTopic = full.topics[T - 1];

        // Reduce the original child's size
        full.n = T - 1;

        // Shift parent child pointers to make space
        for (int j = parent.n; j >= index + 1; j--) {
            parent.children[j + 1] = parent.children[j];
        }

        parent.children[index + 1] = right;

        // Shift parent keys and topics
        for (int j = parent.n - 1; j >= index; j--) {
            parent.keys[j + 1] = parent.keys[j];
            parent.topics[j + 1] = parent.topics[j];
        }

        // Promote the middle key into the parent
        parent.keys[index] = middleKey;
        parent.topics[index] = middleTopic;
        parent.n++;
    }

    // Insert into a node that is not full
    private void insertNonFull(Node node, int key, String topic) {
        int i = node.n - 1;

        if (node.leaf) {
            while (i >= 0 && key < node.keys[i]) {
                node.keys[i + 1] = node.keys[i];
                node.topics[i + 1] = node.topics[i];
                i--;
            }

            node.keys[i + 1] = key;
            node.topics[i + 1] = topic;
            node.n++;
        } else {
            while (i >= 0 && key < node.keys[i]) {
                i--;
            }

            i++;

            // Split a full child before descending
            if (node.children[i].n == 2 * T - 1) {
                splitChild(node, i);

                if (key > node.keys[i]) {
                    i++;
                }
            }

            insertNonFull(node.children[i], key, topic);
        }
    }

    // Search for a topic by its ID
    public String search(int key) {
        return searchNode(root, key);
    }

    private String searchNode(Node node, int key) {
        int i = 0;

        while (i < node.n && key > node.keys[i]) {
            i++;
        }

        if (i < node.n && key == node.keys[i]) {
            return node.topics[i];
        }

        if (node.leaf) {
            return null;
        }

        return searchNode(node.children[i], key);
    }

    // Display all topics in ascending order
    public void display() {
        System.out.println("\n=== KNOWLEDGE B-TREE ===");
        displayNode(root);
    }

    private void displayNode(Node node) {
        int i;

        for (i = 0; i < node.n; i++) {
            if (!node.leaf) {
                displayNode(node.children[i]);
            }

            System.out.println(
                node.keys[i] + " : " + node.topics[i]
            );
        }

        if (!node.leaf) {
            displayNode(node.children[i]);
        }
    }

    // Main method
    public static void main(String[] args) {
        KnowledgeBTree tree = new KnowledgeBTree();

        tree.insert(10, "Java Basics");
        tree.insert(20, "Arrays");
        tree.insert(5, "Programming Fundamentals");
        tree.insert(6, "Linked Lists");
        tree.insert(12, "Binary Trees");
        tree.insert(30, "Graph Algorithms");
        tree.insert(7, "Object-Oriented Programming");
        tree.insert(17, "Database Basics");

        tree.display();

        int searchKey = 12;
        String result = tree.search(searchKey);

        System.out.println(
            "\nSearching for topic ID " + searchKey
        );

        if (result != null) {
            System.out.println("Topic found: " + result);
        } else {
            System.out.println("Topic not found.");
        }
    }
}
