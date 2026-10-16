import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * Java Concepts Used (for poster)
 * 1. Data types and operators: String, int, boolean; +, -, *, <, >, ==
 * 2. Scanner class: reads product, quantity and payment input.
 * 3. Control structures: while, for, do-while and if-else statements.
 * 4. Methods: displayMenu, selectProduct, calculateTotal and input methods.
 * 5. Arrays: Product[] stores the vending-machine products.
 * 6. Inheritance: Product extends Item (single inheritance).
 * 7. Strings: product names, messages and toUpperCase().
 * 8. Exception handling: InputMismatchException handles invalid input.
 * 9. Method overriding: Product overrides the describe() method from Item.
 */
public class VendingMachineController {

    private static final String CURRENCY = "Rs.";

    // Parent class: demonstrates encapsulation and a reusable item template.
    static class Item {
        private String name;
        private int price;

        Item(String name, int price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }

        public String describe() {
            return name + " - " + CURRENCY + price;
        }
    }

    // Child class: demonstrates single inheritance and method overriding.
    static class Product extends Item {
        Product(String name, int price) {
            super(name, price);
        }

        @Override
        public String describe() {
            return super.describe() + " - Available";
        }
    }

    public static void main(String[] args) {
        Product[] products = createProducts(); // Array of Product objects.

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("=== VENDING MACHINE CONTROLLER SYSTEM ===");

            while (true) {
                Product selectedProduct = selectProduct(scanner, products);

                if (selectedProduct == null) {
                    System.out.println("Thank you for using the Vending Machine!");
                    break;
                }

                System.out.println("Selected product: "
                        + selectedProduct.getName().toUpperCase());
                System.out.println("Price per item: " + CURRENCY + selectedProduct.getPrice());

                int quantity = readPositiveInteger(scanner, "Enter quantity: ");
                int totalPrice = calculateTotal(selectedProduct, quantity);

                System.out.println("Total price: " + CURRENCY + totalPrice);

                int amount = readPositiveInteger(scanner, "Enter amount: " + CURRENCY);

                if (amount < totalPrice) {
                    System.out.println("Insufficient amount. Please add " + CURRENCY
                            + (totalPrice - amount) + " more.");
                } else {
                    int change = amount - totalPrice;
                    System.out.println("\nPurchase successful! Please collect " + quantity
                            + " x " + selectedProduct.getName() + ".");

                    if (change > 0) {
                        System.out.println("Your change: " + CURRENCY + change);
                    } else {
                        System.out.println("No change remaining.");
                    }
                }
            }
        }
    }

    private static Product[] createProducts() {
        return new Product[] {
            new Product("Lay's", 20),
            new Product("Dairy Milk", 30),
            new Product("Coca Cola", 40),
            new Product("Kinley", 15),
            new Product("Unibic Cookies", 25)
        };
    }

    private static Product selectProduct(Scanner scanner, Product[] products) {
        while (true) {
            displayMenu(products);
            int choice = readInteger(scanner, "Enter product number (0 to exit): ");

            if (choice == 0) {
                return null;
            }

            if (choice < 1 || choice > products.length) {
                System.out.println("Invalid product number. Please try again.");
                continue;
            }

            return products[choice - 1];
        }
    }

    private static void displayMenu(Product[] products) {
        System.out.println("\nAvailable Products:");
        for (int index = 0; index < products.length; index++) {
            System.out.println((index + 1) + ". " + products[index].describe());
        }
    }

    // Calculates the total cost using arithmetic operators.
    private static int calculateTotal(Product product, int quantity) {
        return product.getPrice() * quantity;
    }

    private static int readInteger(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);

            try {
                return scanner.nextInt();
            } catch (InputMismatchException exception) {
                System.out.println("Invalid input. Please enter a whole number.");
                scanner.next();
            }
        }
    }

    private static int readPositiveInteger(Scanner scanner, String prompt) {
        int value;

        do {
            value = readInteger(scanner, prompt);
            if (value <= 0) {
                System.out.println("Please enter a value greater than zero.");
            }
        } while (value <= 0);

        return value;
    }
}
