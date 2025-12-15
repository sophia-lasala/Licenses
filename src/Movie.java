package Streaming;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Movie {
    private String title, licenseID;
    private long views;
    private double cost;
    private LocalDate expirationDate, renewalDate;
    private long days;
    private int mappedID;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public Movie() {
        title = "No title provided";
        licenseID = "No license provided";
        views = 0;
        cost = 0;
        expirationDate = LocalDate.of(2000, 1, 1);
        renewalDate = LocalDate.of(2000, 1, 1);
    }

    public Movie(String title, String licenseID, long views, double cost, LocalDate expirationDate, LocalDate renewalDate) {
        this.title = title;
        this.licenseID = licenseID;
        this.views = views;
        this.cost = cost;
        this.expirationDate = expirationDate;
        this.renewalDate = renewalDate;
    }

    public Movie (String title, String licenseID, LocalDate expirationDate, LocalDate renewalDate, long days){
        this.title = title;
        this.licenseID = licenseID;
        this.expirationDate = expirationDate;
        this.renewalDate = renewalDate;
        this.days = days;
    }

    public String toString() {
        return title + " | " + mappedID + " | $" + cost + " | " + expirationDate.format(FORMATTER) + " | " +
                renewalDate.format(FORMATTER) + " | " + views + " views";
    }

    public String toExpirationString() {
        long days = java.time.temporal.ChronoUnit.DAYS.between(renewalDate, expirationDate);
        return title + " | " + mappedID + " | " +
                expirationDate.format(FORMATTER) + " | " +
                renewalDate.format(FORMATTER) + " | " +
                days + " days";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLicenseID() {
        return licenseID;
    }

    public void setLicenseID(String licenseID) {
        this.licenseID = licenseID;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LocalDate getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(LocalDate renewalDate) {
        this.renewalDate = renewalDate;
    }

    public long getDays() { return days;}

    public void setDays(long days) {this.days = days;}

    public int getMappedID() { return mappedID;}

    public void setMappedID(int mappedID) {this.mappedID = mappedID;}
}
