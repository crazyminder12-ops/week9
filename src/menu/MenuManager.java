package menu;

import database.ProductDAO;
import model.*;
import exception.InvalidInputException;

import java.sql.SQLException;
import java.util.Scanner;

public class MenuManager implements Menu {

    private final Scanner scanner = new Scanner(System.in);
    private final ProductDAO dao = new ProductDAO();

    @Override
    public void displayMenu() {
        System.out.println("""
            1. Add Fresh Product
            2. Add Packaged Product
            3. View Products
            4. Update Product
            5. Delete Product
            6. Search by Name
            7. Search by Price Range
            0. Exit
        """);
    }

    @Override
    public void run() {
        while (true) {
            displayMenu();
            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1 -> addFresh();
                    case 2 -> addPackaged();
                    case 3 -> show(dao.getAllProducts());
                    case 4 -> update();
                    case 5 -> delete();
                    case 6 -> show(dao.searchByName(scanner.nextLine()));
                    case 7 -> show(
                            dao.searchByPriceRange(
                                    Double.parseDouble(scanner.nextLine()),
                                    Double.parseDouble(scanner.nextLine())
                            )
                    );
                    case 0 -> System.exit(0);
                    default -> throw new InvalidInputException("Invalid option");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void addFresh() throws SQLException {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Price: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Days to expire: ");
        int days = Integer.parseInt(scanner.nextLine());
        dao.addProduct(new FreshProduct(name, price, days));
    }

    private void addPackaged() throws SQLException {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Price: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Weight: ");
        double weight = Double.parseDouble(scanner.nextLine());
        dao.addProduct(new PackagedProduct(name, price, weight));
    }

    private void update() throws SQLException {
        System.out.print("Product name: ");
        String name = scanner.nextLine();
        System.out.print("New price: ");
        double price = Double.parseDouble(scanner.nextLine());
        dao.updateProduct(name, price);
    }

    private void delete() throws SQLException {
        System.out.print("Product name: ");
        String name = scanner.nextLine();
        System.out.print("Are you sure? (yes/no): ");
        if (scanner.nextLine().equalsIgnoreCase("yes")) {
            dao.deleteProduct(name);
        }
    }

    private void show(Product[] products) {
        for (Product p : products) {
            p.displayInfo();
        }
    }
}
