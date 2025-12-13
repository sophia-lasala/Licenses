import java.util.ArrayList;

public class ViewsTree {

    class Node {
        long views;
        Node left, right;

        public Node(long views) {
            this.views = views;
            left = right = null;
        }
    }

    private Node root;

    public ViewsTree() {
        root = new Node(50);
    }

    // Method to insert a new value into the tree
    public void insert(int value) {
        root = insertRec(root, value);
    }

    private Node insertRec(Node root, int value) {
        if (root == null) {
            root = new Node(value);
            return root;
        }
        if (value < root.views) {
            root.left = insertRec(root.left, value);
        } else if (value > root.views) {
            root.right = insertRec(root.right, value);
        }
        return root;
    }

    // Method to search for a value in the tree
    public boolean search(int value) {
        return searchRec(root, value);
    }

    private boolean searchRec(Node root, int value) {
        if (root == null) {
            return false;
        }
        if (root.views == value) {
            return true;
        }
        return value < root.views ? searchRec(root.left, value) : searchRec(root.right, value);
    }
}
