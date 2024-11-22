//Imports.
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Inventory {

    //Initializing scanner and ArrayList.
    public static Scanner scan = new Scanner(System.in);
    private static ArrayList<Item> inventory;

    /* Two methods to display the info of an item. One of them displays the
    *  information of all items while the other displays information for a
    *  specific item.
    */
    //---------------------------------------------------------------------
    public static void displayInfo() {
        System.out.printf("%20s", "[NAME] | ");
        System.out.printf("%20s", "[LOCATION] | ");
        System.out.printf("%10s", "[STOCK] | ");
        System.out.printf("%15s", "[UNIT PRICE] |\n");

        for (Item item : inventory) {
            int stock = item.getStock();
            int price = item.getUPrice();

            System.out.printf("%20s", item.getName() + " | ");
            System.out.printf("%20s", item.getLocation() + " | ");
            System.out.printf("%10s", stock + " | ");
            System.out.printf("%16s", "$" + price + " | \n");
        }
    }
    public static void displayInfo(Item item) {
        System.out.printf("%20s", "[NAME] | ");
        System.out.printf("%20s", "[LOCATION] | ");
        System.out.printf("%10s", "[STOCK] | ");
        System.out.printf("%15s", "[UNIT PRICE] |\n");

        int stock = item.getStock();
        int price = item.getUPrice();

        System.out.printf("%20s", item.getName() + " | ");
        System.out.printf("%20s", item.getLocation() + " | ");
        System.out.printf("%10s", stock + " | ");
        System.out.printf("%16s", "$" + price + " | \n");
    }
    //---------------------------------------------------------------------

    /* Loads the inventory and purchase history. Shows an error if the purchase
    *  history isn't found.
    */
    public static void loadInventory() {
        inventory = new ArrayList<Item>();

        try {
            File file = new File("StoreInventory.txt");
            Scanner scan = new Scanner(file);

            while (scan.hasNextLine()) {
                String[] data = scan.nextLine().split(", ");

                String name = data[0];
                String location = data[1];
                int stock = Integer.valueOf(data[2]);
                int unitPrice = Integer.valueOf(data[3]);

                inventory.add(new Item(name, location, stock, unitPrice));
            }
            scan.close();

        } catch (FileNotFoundException e) {
            System.out.println("Purchase history not found.");
            e.printStackTrace();
        }
    }

    //Updates item info.
    public static void updateInventory() {
        try {
            FileWriter writer = new FileWriter("StoreInventory.txt");
            writer.write("");
            String data = "";

            for (Item item : inventory) {
                String name = item.getName();
                String location = item.getLocation();
                String stock = Integer.toString(item.getStock());
                String uPrice = Integer.toString(item.getUPrice());

                data += (name + ", " + location + ", " + stock + ", " + uPrice + ", " + "\n");
            }

            writer.write(data);
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //Adds an item to the inventory.
    public static void addItem() {
        scan.nextLine(); // collects any garbage input

        System.out.print("New item's name: ");
        String name = scan.nextLine();
        System.out.print("New item's store location: ");
        String location = scan.nextLine();
        System.out.print("New item's current stock: ");
        int stock = scan.nextInt();
        System.out.print("New item's unit price: ");
        int unitPrice = scan.nextInt();

        Item item = new Item(name, location, stock, unitPrice);
        inventory.add(item);
    }

    //Removes an item from the inventory.
    public static void removeItem() {
        scan.nextLine(); // collects any garbage input

        System.out.print("Enter the name of the item to be removed: ");
        String name = scan.nextLine();
        for (Item item : inventory) { 
            if (name.equals(item.getName())) {
                inventory.remove(item);
            }
        }

    }

    //Edits the info of an item.
    public static void editItem() {
        scan.nextLine(); // collects any garbage input

        System.out.print("Enter the name of the item to edit: ");
        String name = scan.nextLine();
        for (Item item : inventory) { 
            if (name.equals(item.getName())) {
                displayInfo(item);

                System.out.print("Set new name: ");
                item.setName(scan.nextLine());
                System.out.print("Set new location: ");
                item.setLocation(scan.nextLine());
                System.out.print("Set new stock: ");
                item.setStock(scan.nextInt());
                System.out.print("Set new unit price: ");
                item.setUPrice(scan.nextInt());
                return;
            }
        }

        // should only run if the inputted name matches none of the items
        System.out.println("No such item found.");
    }

}