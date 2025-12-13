package Streaming;
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
    public void insert(long views) {
        root = insertRec(root, views);
    }

    private Node insertRec(Node root, long views) {
        if (root == null) {
            root = new Node(views);
            return root;
        }
        if (views < root.views) {
            root.left = insertRec(root.left, views);
        } else if (views > root.views) {
            root.right = insertRec(root.right, views);
        }
        return root;
    }

    // Method to search for a value in the tree
    public boolean search(long views) {
        return searchRec(root, views);
    }

    private boolean searchRec(Node root, long views) {
        if (root == null) {
            return false;
        }
        if (root.views == views) {
            return true;
        }
        return views < root.views ? searchRec(root.left, views) : searchRec(root.right, views);
    }
}
