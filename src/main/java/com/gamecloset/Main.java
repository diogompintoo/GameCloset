package com.gamecloset;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Inventory inventory = new Inventory();

        System.out.println("GAME CLOSET - Material Inventory");


        while (true) {
            System.out.println("\n1. ADD GAME (manually)");
            System.out.println("2. ADD CONSOLE (manually)");
            System.out.println("3. CONSULT INVENTORY");
            System.out.println("4. SCAN BARCODE (AUTO!)");
            System.out.println("5. QUIT");
            System.out.print("SELECT: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            if (option == 1) {
                System.out.print("Title: "); String title = scanner.nextLine();
                System.out.print("Genre: "); String genre = scanner.nextLine();
                System.out.print("Platform: "); String platform = scanner.nextLine();
                System.out.print("Year: "); int year = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Condition: "); String condition = scanner.nextLine();
                System.out.print("Price: "); double price = scanner.nextDouble();

                Game game = new Game(title, genre, platform, year, condition, price);
                inventory.addGame(game);

            } else if (option == 2) {
                System.out.print("Name of Console: "); String name = scanner.nextLine();
                System.out.print("Brand: "); String brand = scanner.nextLine();
                System.out.print("Lunch year: "); int year = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Condition: "); String condition = scanner.nextLine();
                System.out.print("Price: "); double price = scanner.nextDouble();

                Console console = new Console(name, brand, year, condition, price);
                inventory.addConsole(console);

            } else if (option == 3) {
                inventory.totalGames();
                System.out.println("\nTotal items: " + inventory.totalGames());

            } else if (option == 4) {
                inventory.addForBarcode(scanner);

            } else if (option == 5) {
                System.out.println("GAME CLOSET turned off, see you next time!");
                break;
            }
        }
        scanner.close();
    }
}
