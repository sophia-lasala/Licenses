package Streaming;

import java.util.ArrayList;
import java.util.LinkedList;

public class Main {
    public static void main (String [] args){
        Data data = new Data();
        ArrayList<Movie> movieList = new ArrayList<>();
        data.movieList(movieList);
        System.out.println(" ");
        Expiration exp = new Expiration();
        LinkedList<Movie> expirationList = new LinkedList<>();
        exp.expirationList(expirationList);

    }
}
