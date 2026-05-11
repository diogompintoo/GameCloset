package com.gamecloset;

public class Game {
    private String title;
    private String gameGenrer;
    private String platform;
    private int year;
    private String condition;
    private double price;

    public Game (String title, String gameGender, String platform, int year, String condition, double price) {
        this.title = title;
        this.gameGenrer = gameGender;
        this.platform = platform;
        this.year = year;
        this.condition = condition;
        this.price = price;
    }
    public String getTitle () { return title; }
    public String getGenre () { return gameGenrer; }
    public String getPlatform() { return platform; }
    public int getYear() { return year; }
    public String getCondition() { return condition; }
    public double getPrice() { return price; }

    public String toString() {
        return String.format(" %s (%s - %s) | %d | %s | €%.2f", title, gameGenrer, platform, year, condition, price);
    }
}
