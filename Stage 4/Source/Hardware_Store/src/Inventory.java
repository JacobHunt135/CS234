import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Inventory {

    public static Scanner scan = new Scanner(System.in);
    public static ArrayList<Item> inventory;

    //---------------------------------------------------------------------

    public static void loadInventory() {
        inventory = new ArrayList<Item>();

        try {
            File file = new File(Main.CURRENT_DIRECTORY + "\\StoreInventory.txt");
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

    public static void addItem(String name, String location, int stock, int unitPrice) {
        Item item = new Item(name, location, stock, unitPrice);
        inventory.add(item);
    }

    public static void removeItem(String name) {
        Item foundItem = null;
        for (Item item : inventory) { 
            if (name.equals(item.getName())) {
                foundItem = item;
                break;
            }
        }
        if (foundItem != null) { inventory.remove(foundItem); } else { System.out.println("Item not found."); }
    }

    public static void editItem(String name, String newName, String location, int stock, int unitPrice) {
        for (Item item : inventory) { 
            if (name.equals(item.getName())) {
                item.setName(newName);
                item.setLocation(location);
                item.setStock(stock);
                item.setUPrice(unitPrice);
                return;
            }
        }

        // should only run if the inputted name matches none of the items
        System.out.println("No such item found.");
    }
}