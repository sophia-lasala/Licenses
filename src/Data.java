package Streaming;

import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Data
{
    private ArrayList<Movie> movieList = new ArrayList<>();
    private LinkedList<Movie> expirationList = new LinkedList<>();
    private static Stack<History> stackHistory = new Stack<>();
    private static Queue<Movie> pendingQueue = new LinkedList<>();
    private HashMap<String, Movie> licenseMap = new HashMap<>();

    public void buildAllStructures() {
        movieList.clear();
        expirationList.clear();
        pendingQueue.clear();
        licenseMap.clear();

        movieList(movieList);
        expirationList(expirationList);
        buildPendingQueue(expirationList);

        for (Movie m : movieList) {
            licenseMap.put(m.getLicenseID(), m);
        }

        for (Movie m : expirationList){
            licenseMap.put(m.getLicenseID(), m);
        }

        for (Movie m : pendingQueue){
            licenseMap.put(m.getLicenseID(), m);
        }
    }

    public static void movieList(ArrayList <Movie> movieList) {
        String fileName = "Movie Database.csv";
        PrintWriter outputStream = null;
        Scanner inputStream = null;
        Scanner keyboard = new Scanner(System.in);
        String title, licenseID, line;
        long views;
        double cost;
        LocalDate expirationDate, renewalDate;

        try {
            inputStream = new Scanner(new File(fileName));
            line = inputStream.nextLine();
            while (inputStream.hasNextLine()) {
                line = inputStream.nextLine();
                String[] ary = line.split(",");
                title = ary[0];
                licenseID = ary[1];
                cost = Double.parseDouble(ary[2]);
                expirationDate = LocalDate.parse(ary[3]);
                renewalDate = LocalDate.parse(ary[4]);
                views = Long.parseLong(ary[5]);

                movieList.add(new Movie(title, licenseID, views, cost, expirationDate, renewalDate));
            }
            inputStream.close();
            //HistoryManager.currentHistory("Data", "formed movie list");
            //printMovieList(movieList); //for testing
        } catch (FileNotFoundException e) {
            System.out.println("Error opening the file " +
                    fileName);
            System.exit(0);
        }
    }

    public static void printMovieList(ArrayList<Movie> movieList) {
        System.out.println("Here is your list: ");
        System.out.println("Title | License ID | Cost | Expiration Date | Renewal Date | Views\n");
        for (int i = 0; i < movieList.size(); i++) {
            System.out.println((i+1) + " " + movieList.get(i));
        }
        //HistoryManager.currentHistory("Data", "printed movie list");
    }

    public static void expirationList(LinkedList<Movie> expirationList){
        ArrayList<Movie> movieList = new ArrayList<>();
        Data.movieList(movieList);

        String title, licenseID;
        LocalDate expirationDate, renewalDate;
        long days;

        for (int i = 0 ; i < movieList.size(); i++){
            Movie temp = movieList.get(i);
            title = temp.getTitle();
            licenseID = temp.getLicenseID();
            expirationDate = temp.getExpirationDate();
            renewalDate = temp.getRenewalDate();

            days = ChronoUnit.DAYS.between(renewalDate, expirationDate);

            temp = new Movie(title, licenseID, expirationDate, renewalDate, days);
            expirationList.add(temp);

        }
        //HistoryManager.currentHistory("Expiration", "formed expiration list");
        orgExpirationList(expirationList);
        //printExpirationList(expirationList);
    }

    public static void orgExpirationList(LinkedList<Movie> expirationList) {
        int n = expirationList.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                Movie current = expirationList.get(j);
                Movie next = expirationList.get(j + 1);

                long x = current.getDays(), y = next.getDays();
                if (x > y) {
                    expirationList.set(j, next);
                    expirationList.set(j + 1, current);
                }
            }
        }
        //HistoryManager.currentHistory("Expiration", "organized expiration list");
    }

    public static void printExpirationList(LinkedList<Movie> expirationList) {
        System.out.println("Title | License ID | Expiration Date | Renewal Date | Days");

        for (int i = 0; i < expirationList.size(); i++) {
            Movie movie = expirationList.get(i);
            System.out.println(movie.toExpirationString());
        }

       // HistoryManager.currentHistory("Expiration", "printed expiration list");
    }

    public static void currentHistory (String location, String task){
        stackHistory.push(new History(location, task));
    }

    public static void printHistory () {
        if (stackHistory.isEmpty()){
            System.out.println("No changes have been currently made.");
        }
        else {
            for (int i = 0; i < stackHistory.size(); i++) {
                System.out.println((i + 1) + " " + stackHistory.get(i));
            }
        }
    }

    public void renewQueue(String licenseID, LocalDate newRenewalDate, LocalDate newExpirationDate) {
        Movie movie = licenseMap.get(licenseID);
        if (movie != null) {
            movie.setRenewalDate(newRenewalDate);
            movie.setExpirationDate(newExpirationDate);

            long days = ChronoUnit.DAYS.between(newRenewalDate, newExpirationDate);
            movie.setDays(days);
        }

        for (Movie m : expirationList) {
            if (m.getLicenseID().equals(licenseID)) {
                m.setRenewalDate(newRenewalDate);
                m.setExpirationDate(newExpirationDate);
                m.setDays(ChronoUnit.DAYS.between(newRenewalDate, newExpirationDate));
            }
        }

        for (Movie m : pendingQueue) {
            if (m.getLicenseID().equals(licenseID)) {
                m.setRenewalDate(newRenewalDate);
                m.setExpirationDate(newExpirationDate);
                m.setDays(ChronoUnit.DAYS.between(newRenewalDate, newExpirationDate));
            }
        }

        orgExpirationList(expirationList);

        buildPendingQueue(expirationList);

        currentHistory("Data", "renewed queue with new dates ");
    }

    public static void buildPendingQueue(LinkedList<Movie> expirationList) {
        pendingQueue.clear();

        for (int i = 0; i < 10; i++) {
            Movie movie = expirationList.get(i);
            pendingQueue.add(movie);
        }
    }

    // Print the pending queue
    public static void printPendingQueue() {
        System.out.println("Licenses expiring most recently:");
        System.out.println("Title | License ID | Expiration Date | Renewal Date | Days");
        for (Movie movie : pendingQueue) {
            System.out.println(movie.toExpirationString());
        }
    }
}
