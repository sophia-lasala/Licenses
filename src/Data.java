import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;

public class Data
{
    public static void main (String [] args) {
        String fileName = "Movie Database.csv";
        ArrayList<Movie> movieList = new ArrayList<>();
        PrintWriter outputStream = null;
        Scanner inputStream = null;
        Scanner keyboard = new Scanner(System.in);
        String title, licenseID, line;
        long views;
        double cost;
        LocalDate expirationDate, renewalDate;

        System.out.println("Here is your list: ");
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
            printMovieList(movieList);
        } catch (FileNotFoundException e) {
            System.out.println("Error opening the file " +
                    fileName);
            System.exit(0);
        }
    }

    public static void printMovieList(ArrayList<Movie> movieList) {
        System.out.println("Title | License ID | Cost | Expiration Date | Renewal Date | Views\n");
        for (int i = 0; i < movieList.size(); i++) {
            System.out.println((i+1) + " " + movieList.get(i));
        }
    }
}
