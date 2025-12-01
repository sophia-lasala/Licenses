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
    }
}
