package Streaming;
import java.util.ArrayList;

import static Streaming.Data.currentHistory;

public class ViewsTree {

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

    public ViewsTree() {
        root = null;
    }


    //Build the tree from an existing ArrayList<Movie>

    public void buildFromList(ArrayList<Movie> movieList) {
        for (Movie m : movieList) {
            insert(m);
        }
    }

    /**
     * Insert a Movie into the BST using its views as the key.
     */
    public void insert(Movie movie) {
        root = insertRec(root, movie);
    }

    private Node insertRec(Node current, Movie movie) {
        if (current == null) {
            return new Node(movie);
        }

        String key = Long.toString(movie.getViews());
        int cmp = key.compareToIgnoreCase(String.valueOf(current.views));

        if (cmp < 0) {
            current.left = insertRec(current.left, movie);
        } else if (cmp > 0) {
            current.right = insertRec(current.right, movie);
        } else {
            current.movie = movie;
        }

        return current;
    }


    //Check if a views exists in the tree.
    public boolean containsViews(long views) {
        return search(views) != null;
    }


    //Search for a Movie by its views.

    public Movie search(long views) {
        Node node = searchRec(root, views);
        return (node == null) ? null : node.movie;
    }

    private Node searchRec(Node current, long views) {
        if (current == null || views == 0) {
            return null;
        }

        int cmp = (int) views;

        if (cmp == 0) {
            return current;
        } else if (cmp < 0) {
            return searchRec(current.left, views);
        } else {
            return searchRec(current.right, views);
        }
    }


    // print all views in sorted order.

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
