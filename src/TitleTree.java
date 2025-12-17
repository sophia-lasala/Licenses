package Streaming;
import java.util.ArrayList;

import static Streaming.Data.currentHistory;

public class TitleTree {

    // Node class for the BST
    private static class Node {
        String title;
        double cost;
        long views;
        Movie movie;
        Node left;
        Node right;

        Node(Movie movie) {
            this.title = movie.getTitle();
            this.movie = movie;
            this.cost = movie.getCost();
            this.views = movie.getViews();
        }
    }

    private Node root;

    public TitleTree() {
        root = null;
    }


    //Build the tree from an existing ArrayList<Movie>

    public void buildFromList(ArrayList<Movie> movieList) {
        for (Movie m : movieList) {
            insert(m);
        }
    }

    /**
     * Insert a Movie into the BST using its title as the key.
     */
    public void insert(Movie movie) {
        root = insertRec(root, movie);
    }

    private Node insertRec(Node current, Movie movie) {
        if (current == null) {
            return new Node(movie);
        }

        String key = movie.getTitle();
        int cmp = key.compareToIgnoreCase(current.title);

        if (cmp < 0) {
            current.left = insertRec(current.left, movie);
        } else if (cmp > 0) {
            current.right = insertRec(current.right, movie);
        } else {
            current.movie = movie;
        }

        return current;
    }


    //Check if a title exists in the tree.
    public boolean containsTitle(String title) {
        return search(title) != null;
    }


    //Search for a Movie by its title.

    public Movie search(String title) {
        Node node = searchRec(root, title);
        currentHistory("Title Tree", "searched a movie based on title");
        return (node == null) ? null : node.movie;
    }

    private Node searchRec(Node current, String title) {
        if (current == null || title == null) {
            return null;
        }

        int cmp = title.compareToIgnoreCase(current.title);
        if (cmp == 0) {
            return current;
        } else if (cmp < 0) {
            return searchRec(current.left, title);
        } else {
            return searchRec(current.right, title);
        }
    }


    // print all titles in sorted order.

    public void printInOrder() {
        printInOrderRec(root);
    }

    private void printInOrderRec(Node current) {
        if (current == null) return;
        printInOrderRec(current.left);
        System.out.println(current.title + " | " + current.views + " views |  $" + current.cost);
        printInOrderRec(current.right);
        //currentHistory("Title Tree", "organized list in alphabetical order");
        //Causes unspeakable amounts of repeats
    }
}
