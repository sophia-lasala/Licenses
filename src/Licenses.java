package Streaming;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.time.LocalDate;

public class Licenses {

    private LinkedList<Movie> expirationList = new LinkedList<>();
    private Queue<Movie> pendingQueue = new LinkedList<>();

    // HashMap that maps license ID -> full Movie object
    private HashMap<String, Movie> licenseMap = new HashMap<>();

    // Build ArrayList (from CSV), HashMap, LinkedList and Queue
    public void buildAllStructures() {
        Data data = new Data();
        ArrayList<Movie> movieList = new ArrayList<>();

        // Fill ArrayList with movies from CSV
        data.movieList(movieList);

        // Clear structures in case method is called more than once
        expirationList.clear();
        pendingQueue.clear();
        licenseMap.clear();

        for (Movie m : movieList) {
            // 1) HashMap: license ID -> full Movie
            licenseMap.put(m.getLicenseID(), m);

            // 2) Build expiration list with days until expiration
            long days = ChronoUnit.DAYS.between(m.getRenewalDate(), m.getExpirationDate());

            Movie temp = new Movie(
                    m.getTitle(),
                    m.getLicenseID(),
                    m.getExpirationDate(),
                    m.getRenewalDate(),
                    days
            );

            expirationList.add(temp);
        }

        // Sort linked list by days until expiration
        orgExpirationList(expirationList);

        // Build queue of licenses expiring within 30 days
        buildPendingQueue(expirationList);
    }

    // Bubble-sort the expiration list by days
    public void orgExpirationList(LinkedList<Movie> expirationList) {
        int n = expirationList.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                Movie current = expirationList.get(j);
                Movie next = expirationList.get(j + 1);
                long x = current.getDays();
                long y = next.getDays();

                if (x > y) {
                    expirationList.set(j, next);
                    expirationList.set(j + 1, current);
                }
            }
        }
    }

    // Print the expiration linked list
    public static void printExpirationList(LinkedList<Movie> expirationList) {
        System.out.println("Title | License ID | Expiration Date | Renewal Date | Days");
        for (Movie movie : expirationList) {
            System.out.println(movie.toExpirationString());
        }
    }

    // Build queue of licenses that expire within 30 days
    public void buildPendingQueue(LinkedList<Movie> expirationList) {
        for (Movie movie : expirationList) {
            if (movie.getDays() <= 30) {
                pendingQueue.add(movie);
            }
        }
    }

    // Print the pending queue
    public void printPendingQueue() {
        System.out.println("Licenses expiring within 30 days:");
        System.out.println("Title | License ID | Expiration Date | Renewal Date | Days");
        for (Movie movie : pendingQueue) {
            System.out.println(movie.toExpirationString());
        }
    }

    // Look up a movie by its license ID using the HashMap
    public Movie findByLicenseID(String id) {
        return licenseMap.get(id);
    }

    // Getters
    public HashMap<String, Movie> getLicenseMap() {
        return licenseMap;
    }

    public LinkedList<Movie> getExpirationList() {
        return expirationList;
    }

    public Queue<Movie> getPendingQueue() {
        return pendingQueue;
    }
}
