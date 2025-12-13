package Streaming;

public class CostTree {
    class Node {
        int cost;
        Node left, right;

        public Node(int cost) {
            this.cost = cost;
            left = right = null;
        }
    }

    private Node root;

    public CostTree() {
        root = new Node(50);
    }

    public void insert(int cost) {
        root = insertRec(root, cost);
    }

    private Node insertRec(Node root, int cost) {
        if (root == null) {
            root = new Node(cost);
            return root;
        }
        if (cost < root.cost) {
            root.left = insertRec(root.left, cost);
        } else if (cost > root.cost) {
            root.right = insertRec(root.right, cost);
        }
        return root;
    }

    public boolean search(int cost) {
        return searchRec(root, cost);
    }

    private boolean searchRec(Node root, int cost) {
        if (root == null) {
            return false;
        }
        if (root.cost == cost) {
            return true;
        }
        return cost < root.cost ? searchRec(root.left, cost) : searchRec(root.right, cost);
    }
}
