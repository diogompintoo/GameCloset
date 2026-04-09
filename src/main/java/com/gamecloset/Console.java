package com.gamecloset;

public class Console {
    private String name;
    private String brand;
    private int lunchYear;
    private String state;
    private double price;

    public Console(String name,  String brand, int lunchYear, String state, double price) {
        this.name = name;
        this.brand = brand;
        this.lunchYear = lunchYear;
        this.state = state;
        this.price = price;
    }
    public String toString() {
        return String.format("%s %s %s %s %.2f", name, brand, lunchYear, state, price);
    }
}
