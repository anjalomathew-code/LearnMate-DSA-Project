
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class LearningTree {

    private JFrame frame;
    private JTextArea output;

    public void showLab() {
        frame = new JFrame("LearnMate - DSA Concepts Lab");
        frame.setSize(750, 550);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("DSA Concepts Lab", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        frame.add(title, BorderLayout.NORTH);

        JPanel buttons = new JPanel(new GridLayout(3, 2, 8, 8));

        addButton(buttons, "Linear Search", this::linearSearchDemo);
        addButton(buttons, "Bubble Sort", this::bubbleSortDemo);
        addButton(buttons, "Binary Tree", this::binaryTreeDemo);
        addButton(buttons, "B-Tree", this::bTreeDemo);
        addButton(buttons, "Graph BFS", this::bfsDemo);
        addButton(buttons, "Graph DFS", this::dfsDemo);

        JPanel left = new JPanel(new BorderLayout());
        left.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
        left.add(buttons, BorderLayout.NORTH);
        frame.add(left, BorderLayout.WEST);

        output = new JTextArea();
        output.setEditable(false);
        output.setFont(new Font("Monospaced", Font.PLAIN, 14));
        output.setText("Select a DSA concept to see its demonstration.");
        output.setMargin(new Insets(10, 10, 10, 10));

        frame.add(new JScrollPane(output), BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void addButton(JPanel panel, String name, Runnable action) {
        JButton button = new JButton(name);
        button.addActionListener(e -> action.run());
        panel.add(button);
    }

    private void linearSearchDemo() {
        int[] marks = {75, 42, 88, 60, 95};
        String input = JOptionPane.showInputDialog(
            frame, "Enter the mark to search:");

        if (input == null) return;

        try {
            int target = Integer.parseInt(input);
            StringBuilder result = new StringBuilder();
            result.append("LINEAR SEARCH\n\n");
            result.append("Marks: ").append(Arrays.toString(marks)).append("\n\n");

            int found = -1;
            for (int i = 0; i < marks.length; i++) {
                result.append("Checking index ").append(i)
                      .append(": ").append(marks[i]).append("\n");

                if (marks[i] == target) {
                    found = i;
                    break;
                }
            }

            if (found >= 0) {
                result.append("\nMark found at index ").append(found);
            } else {
                result.append("\nMark not found.");
            }

            result.append("\n\nTime complexity: O(n)");
            output.setText(result.toString());

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Enter a valid integer.");
        }
    }

    private void bubbleSortDemo() {
        int[] marks = {75, 42, 88, 60, 95};
        String before = Arrays.toString(marks);

        for (int i = 0; i < marks.length - 1; i++) {
            for (int j = 0; j < marks.length - 1 - i; j++) {
                if (marks[j] > marks[j + 1]) {
                    int temp = marks[j];
                    marks[j] = marks[j + 1];
                    marks[j + 1] = temp;
                }
            }
        }

        output.setText("BUBBLE SORT\n\n"
            + "Before sorting: " + before + "\n"
            + "After sorting:  " + Arrays.toString(marks) + "\n\n"
            + "Adjacent elements are compared and swapped.\n"
            + "Time complexity: O(n^2) in the average and worst cases.");
    }

    static class Node {
        String value;
        Node left, right;

        Node(String value) {
            this.value = value;
        }
    }

    private void binaryTreeDemo() {
        Node root = new Node("Programming");
        root.left = new Node("Java");
        root.right = new Node("DSA");
        root.left.left = new Node("Variables");
        root.left.right = new Node("Classes");
        root.right.left = new Node("Arrays");
        root.right.right = new Node("Graphs");

        StringBuilder result = new StringBuilder("BINARY TREE\n\n");
        result.append("             Programming\n");
        result.append("             /         \\\n");
        result.append("           Java         DSA\n");
        result.append("          /   \\        /  \\\n");
        result.append("    Variables Classes Arrays Graphs\n\n");

        result.append("Preorder traversal: ");
        preorder(root, result);
        result.append("\n\nPreorder: Root, Left, Right");
        output.setText(result.toString());
    }

    private void preorder(Node node, StringBuilder result) {
        if (node == null) return;
        result.append(node.value).append(" -> ");
        preorder(node.left, result);
        preorder(node.right, result);
    }

    private void bTreeDemo() {
        output.setText(
            "B-TREE DEMONSTRATION\n\n"
            + "A B-tree is a balanced search tree in which each node "
            + "can store multiple sorted keys.\n\n"
            + "Example keys: 10, 20, 30, 40, 50\n\n"
            + "         [20 | 40]\n"
            + "        /    |    \\\n"
            + "     [10]  [30]  [50]\n\n"
            + "The keys are kept in sorted order. When a node becomes "
            + "full, it may split to maintain balance.\n\n"
            + "Note: This button demonstrates the B-tree concept; "
            + "it does not implement B-tree insertion or deletion.");
    }

    private void bfsDemo() {
        Map<Integer, int[]> graph = new LinkedHashMap<>();
        graph.put(0, new int[]{1, 2});
        graph.put(1, new int[]{0, 3});
        graph.put(2, new int[]{0, 3});
        graph.put(3, new int[]{1, 2});

        boolean[] visited = new boolean[4];
        Queue<Integer> queue = new LinkedList<>();
        StringBuilder result = new StringBuilder("GRAPH BFS\n\n");

        queue.add(0);
        visited[0] = true;

        while (!queue.isEmpty()) {
            int vertex = queue.remove();
            result.append(vertex).append(" ");

            for (int next : graph.get(vertex)) {
                if (!visited[next]) {
                    visited[next] = true;
                    queue.add(next);
                }
            }
        }

        result.append("\n\nBFS uses a queue and visits nearby vertices first.");
        output.setText(result.toString());
    }

    private void dfsDemo() {
        int[][] graph = {
            {0, 1, 1, 0},
            {1, 0, 0, 1},
            {1, 0, 0, 1},
            {0, 1, 1, 0}
        };

        boolean[] visited = new boolean[4];
        StringBuilder result = new StringBuilder("GRAPH DFS\n\n");

        dfs(0, graph, visited, result);
        result.append("\n\nDFS explores one path deeply before backtracking.");
        output.setText(result.toString());
    }

    private void dfs(int vertex, int[][] graph,
                     boolean[] visited, StringBuilder result) {
        visited[vertex] = true;
        result.append(vertex).append(" ");

        for (int next = 0; next < graph.length; next++) {
            if (graph[vertex][next] == 1 && !visited[next]) {
                dfs(next, graph, visited, result);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LearningTree().showLab());
    }
}
