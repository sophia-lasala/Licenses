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

    private HashMap<String, Integer> licenseIdToNumber = new HashMap<>();
    private HashMap<Integer, String> numberToLicenseId = new HashMap<>();

    public void buildAllStructures() {
        movieList.clear();
        expirationList.clear();
        pendingQueue.clear();
        licenseMap.clear();
        licenseIdToNumber.clear();
        numberToLicenseId.clear();

        buildMovieList(movieList);
        buildExpirationList(expirationList, movieList);
        buildPendingQueue(expirationList);

        int counter = 1;

        for (Movie m : movieList) {
            String licenseID = m.getLicenseID();
            if (!licenseIdToNumber.containsKey(licenseID)) {
                licenseIdToNumber.put(licenseID, counter);
                numberToLicenseId.put(counter, licenseID);
                m.setMappedID(counter);
                counter++;
            } else {
                m.setMappedID(licenseIdToNumber.get(licenseID));
            }
            licenseMap.put(licenseID, m);
        }

        for (Movie m : expirationList) {
            String licenseID = m.getLicenseID();
            if (licenseIdToNumber.containsKey(licenseID)) {
                m.setMappedID(licenseIdToNumber.get(licenseID));
            } else {
                licenseIdToNumber.put(licenseID, counter);
                numberToLicenseId.put(counter, licenseID);
                m.setMappedID(counter);
                counter++;
            }
        }

        for (Movie m : pendingQueue) {
            String licenseID = m.getLicenseID();
            if (licenseIdToNumber.containsKey(licenseID)) {
                m.setMappedID(licenseIdToNumber.get(licenseID));
            } else {
                licenseIdToNumber.put(licenseID, counter);
                numberToLicenseId.put(counter, licenseID);
                m.setMappedID(counter);
                counter++;
            }
        }
    }

    public static void buildMovieList(ArrayList <Movie> movieList) {
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

    public void printMovieList(ArrayList<Movie> movieList) {
        System.out.println("Here is your list: ");
        System.out.println("Title | License ID | Cost | Expiration Date | Renewal Date | Views\n");
        for (int i = 0; i < movieList.size(); i++) {
            System.out.println((i+1) + " " + movieList.get(i));
        }
        //HistoryManager.currentHistory("Data", "printed movie list");
    }

    public static void buildExpirationList(LinkedList<Movie> expirationList, ArrayList<Movie> movieList) {
        expirationList.clear();

        for (Movie m : movieList) {
            long days = ChronoUnit.DAYS.between(m.getRenewalDate(), m.getExpirationDate());
            m.setDays(days);
            expirationList.add(m);
        }

        orgExpirationList(expirationList);
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

    public void printExpirationList(LinkedList<Movie> expirationList) {
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

    }

    public static void buildPendingQueue(LinkedList<Movie> expirationList) {
        pendingQueue.clear();

        for (int i = 0; i < 10; i++) {
            Movie movie = expirationList.get(i);
            pendingQueue.add(movie);
        }
    }

    // Print the pending queue
    public void printPendingQueue() {
        System.out.println("Licenses expiring most recently:");
        System.out.println("Title | License ID | Expiration Date | Renewal Date | Days");
        for (Movie movie : pendingQueue) {
            System.out.println(movie.toExpirationString());
        }
    }

    public Movie findByLicenseID(String id) {
        return licenseMap.get(id);
    }

    public ArrayList<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(ArrayList<Movie> movieList) {
        this.movieList = movieList;
    }

    public LinkedList<Movie> getExpirationList() {
        return expirationList;
    }

    public void setExpirationList(LinkedList<Movie> expirationList) {
        this.expirationList = expirationList;
    }

    public static Stack<History> getStackHistory() {
        return stackHistory;
    }

    public static void setStackHistory(Stack<History> stackHistory) {
        Data.stackHistory = stackHistory;
    }

    public static Queue<Movie> getPendingQueue() {
        return pendingQueue;
    }

    public static void setPendingQueue(Queue<Movie> pendingQueue) {
        Data.pendingQueue = pendingQueue;
    }

    public HashMap<String, Movie> getLicenseMap() {
        return licenseMap;
    }

    public void setLicenseMap(HashMap<String, Movie> licenseMap) {this.licenseMap = licenseMap;}

    public int getNumberByLicense(String licenseID) {
        return licenseIdToNumber.getOrDefault(licenseID, -1);
    }

    public String getLicenseByNumber(int number) {
        return numberToLicenseId.get(number);
    }

    public Movie getMovieByNumber(int number) {
        String licenseID = numberToLicenseId.get(number);
        return (licenseID != null) ? licenseMap.get(licenseID) : null;
    }

}
