package Streaming;
import java.util.ArrayList;

public class CostTree {

    // Node class for the BST
    private static class Node {
        double cost;
        String title;
        long views;
        Movie movie;
        Node left;
        Node right;

        Node(Movie movie) {
            this.cost = movie.getCost();
            this.title = movie.getTitle();
            this.views = movie.getViews();
            this.movie = movie;
        }
    }

    private Node root;

    public CostTree() {
        root = null;
    }


    //Build the tree from an existing ArrayList<Movie>

    public void buildFromList(ArrayList<Movie> movieList) {
        for (Movie m : movieList) {
            insert(m);
        }
    }

    /**
     * Insert a Movie into the BST using its cost as the key.
     */
    public void insert(Movie movie) {
        root = insertRec(root, movie);
    }

    private Node insertRec(Node current, Movie movie) {
        if (current == null) {
            return new Node(movie);
        }

        String key = Double.toString(movie.getCost());
        int cmp = key.compareToIgnoreCase(String.valueOf(current.cost));

        if (cmp < 0) {
            current.left = insertRec(current.left, movie);
        } else if (cmp > 0) {
            current.right = insertRec(current.right, movie);
        } else {
            current.movie = movie;
        }

        return current;
    }


    //Check if a cost exists in the tree.
    public boolean containsCost(double cost) {
        return search(cost) != null;
    }


    //Search for a Movie by its cost.

    public Movie search(double cost) {
        Node node = searchRec(root, cost);
        return (node == null) ? null : node.movie;
    }

    private Node searchRec(Node current, double cost) {
        if (current == null || cost == 0) {
            return null;
        }

        int cmp = (int) cost;

        if (cmp == 0) {
            return current;
        } else if (cmp < 0) {
            return searchRec(current.left, cost);
        } else {
            return searchRec(current.right, cost);
        }
    }


    // print all costs in sorted order.

    public void printInOrder() {
        printInOrderRec(root);
    }

    private void printInOrderRec(Node current) {
        if (current == null) return;
        printInOrderRec(current.left);
        System.out.println(current.title + " | " + current.views + " views |  $" + current.cost);
        printInOrderRec(current.right);
    }
}
