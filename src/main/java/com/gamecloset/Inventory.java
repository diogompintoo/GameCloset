package com.gamecloset;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.nio.file.*;

public class Inventory {
    private ArrayList<Game> games = new ArrayList<>();
    private ArrayList<Console>  consoles = new ArrayList<>();

    public void addGame(Game game) {
        games.add(game);
        System.out.println("Game added successfully");
    }
    public void addConsole(Console console) {
        consoles.add(console);
        System.out.println("Console added successfully");
    }
    public void listGames() {
        System.out.println("===== GAMES =====");
        games.forEach(System.out::println);

        System.out.println("===== CONSOLES =====");
        consoles.forEach(System.out::println);
    }

    public int totalGames() {
        System.out.println("Total games: " + games.size());
        return games.size();
    }
    public int totalConsoles() {
        System.out.println("Total consoles: " + consoles.size());
        return consoles.size();
    }
    public void addForBarcode(Scanner scanner) {
        System.out.print("\n🔎 Add barcode ou digit o EAN/UPC): ");
        String barcode = scanner.nextLine().trim();

        if (barcode.isEmpty()) {
            System.out.println("Empty barcode!");
            return;
        }

        System.out.println("🔍 Searching on database games/consoles...");

        BarcodeLookup.GameLookup result = BarcodeLookup.barcodeSearch(barcode);

        if (result != null) {
            System.out.println(" Found: " + result.title + " (" + result.platform + ")");

            System.out.print("Genre (or Enter for 'Unknown'): ");
            String genre = scanner.nextLine().trim();
            if (genre.isEmpty()) genre = "Unknown";

            System.out.print("Year (or Enter para 2026): ");
            String yearStr = scanner.nextLine().trim();
            int year = yearStr.isEmpty() ? 2026 : Integer.parseInt(yearStr);

            System.out.print("Condition (New/Good/Used): ");
            String condition = scanner.nextLine();

            System.out.print("Price (€): ");
            double price = scanner.nextDouble();
            scanner.nextLine();

            Game game = new Game(result.title, genre, result.platform, year, condition, price);
            addGame(game);

        } else {
            System.out.println("Code not found. Add it manually......");
            System.out.println("Use option 1 on menu to add item.");
        }

    }
}
