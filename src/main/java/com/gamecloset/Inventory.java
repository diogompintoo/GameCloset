package com.gamecloset;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.nio.file.*;

public class Inventory {

    private ArrayList<Game> games = new ArrayList<>();
    private ArrayList<Console>  consoles = new ArrayList<>();
    private final String FILE_PATH = "inventory.txt";

    public Inventory() {
        loadFromFile();
    }

    public void addGame(Game game) {
        games.add(game);
        System.out.println("Game added successfully");
        saveToFile();
    }
    public void addConsole(Console console) {
        consoles.add(console);
        System.out.println("Console added successfully");
        saveToFile();
    }
    public void listGames() {
        System.out.println("===== GAMES =====");
        if (games.isEmpty()) System.out.println("No games found");
        games.forEach(System.out::println);

        System.out.println("===== CONSOLES =====");
        if (consoles.isEmpty()) System.out.println("No consoles found");
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

    public void saveToFile() {
        try (PrintWriter write = new PrintWriter(new FileWriter(FILE_PATH))){
            for (Game g : games) {
                write.println("GAME: " + g.getTitle()
                        + ";" + g.getGenre()
                        + ";" + g.getPlatform()
                        + ";" + g.getYear()
                        + ";" + g.getCondition()
                        + ";" + g.getPrice());

            }
            for  (Console c : consoles) {
                write.println("CONSOLE: " + c.toString());
            }
            System.out.println("Database saved successfully");
        }catch (IOException e) {
            System.out.println("Error saving database to file");;
        }
    }

    public void loadFromFile() {
        Path path = Paths.get(FILE_PATH);
        if (!Files.exists(path)) return ;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))){
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                System.out.println("Loading " + line);
            }
        }catch (IOException e) {
            System.out.println("Error reading file");
        }
    }

    public void addForBarcode(Scanner scanner) {
        System.out.print("\n🔎 Add barcode ou digit o EAN/UPC): ");
        String barcode = scanner.nextLine().trim();

        if (barcode.isEmpty()) {
            System.out.println("Empty barcode!");
            return;
        }

        System.out.println("Searching on database games/consoles...");

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
