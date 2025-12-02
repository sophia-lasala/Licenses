package Streaming;

import java.util.ArrayList;
import java.util.LinkedList;

public class Main {
    public static void main (String [] args){
        ArrayList<Movie> movieList = new ArrayList<>();
        Data.movieList(movieList);
        System.out.println(" ");
        LinkedList<Movie> expirationList = new LinkedList<>();
        Expiration.expirationList(expirationList);
        HistoryManager.printHistory();
        Licenses lic = new Licenses();
lic.buildAllStructures();

Movie m = lic.findByLicenseID("LIC-93810");
if (m != null) {
    System.out.println("Found: " + m);
}

Licenses.printExpirationList(lic.getExpirationList());
lic.printPendingQueue();
        Tree titleTree = new Tree();
titleTree.buildFromList(movieList);
String searchTitle = "Inception";
if (titleTree.containsTitle(searchTitle)) {
    Movie found = titleTree.search(searchTitle);
    System.out.println("Found movie: " + found);
} else {
    System.out.println(searchTitle + " is not in the system.");
}
    }
}
