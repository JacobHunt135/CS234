import java.util.ArrayList;

public class Inventory {
    
    private ArrayList<Item> Inventory = new ArrayList<Item>();

    public void displayInfo() {
        System.out.printf("%20s", "[NAME] | ");
        System.out.printf("%20s", "[LOCATION] | ");
        System.out.printf("%10s", "[STOCK] | ");
        System.out.printf("%15s", "[UNIT PRICE] |\n");
        for (Item item : Inventory) {
            int stock = item.getStock();
            int price = item.getUPrice();
            //System.out.printf(
                //item.getName() + "        | " + item.getLocation() + "        | " + stock + "        | " + price + "\n"
            //);
            System.out.printf("%20s", item.getName() + " | ");
            System.out.printf("%20s", item.getLocation() + " | ");
            System.out.printf("%10s", stock + " | ");
            System.out.printf("%16s", "$" + price + " | \n");
        }
    }

    public void addItem(String name, String location, int stock, int unitPrice) {
        Item item = new Item(name, location, stock, unitPrice);
        Inventory.add(item);
    }

    public void removeItem(String name) {
        for (Item item : Inventory) {
            if (name.equals(item.getName())) {
                Inventory.remove(item);
            }
        }

    }

    public void editName(String name, String newName) {
        for (Item item : Inventory) {
            if (name.equals(item.getName())) {
                item.setName(newName);
            }
        }
    }
    
    public void editLocation(String name, String newLocation) {
        for (Item item : Inventory) {
            if (name.equals(item.getName())) {
                item.setLocation(newLocation);
            }
        }
    }

    public void editStock(String name, int newStock) {
        for (Item item : Inventory) {
            if (name.equals(item.getName())) {
                item.setStock(newStock);
            }
        }
    }

    public void editUPrice(String name, int newPrice) {
        for (Item item : Inventory) {
            if (name.equals(item.getName())) {
                item.setUPrice(newPrice);
            }
        }
    }
}