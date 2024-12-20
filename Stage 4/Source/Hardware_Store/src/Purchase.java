import java.util.LinkedList;
import java.util.ListIterator;
import java.time.LocalDate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Purchase {

    //-------------------------------------------------------------------------------------------
    private int id;
    private String user;
    private String date;
    private String item;
    private int amount;
    private int cost;

    public Purchase(int id, String user, String date, String item, int amount, int cost) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.item = item;
        this.amount = amount;
        this.cost = cost;
    }

    public void setItem(String itemName) {
        this.item = itemName;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
    
    public void setDate(String date) {
        this.date = date;
    }

    public void displayInfo() {
        System.out.printf(
                "[[ ORDER "+ id + " ]]" + "\n"+
                "DATE: " + date + "\n"+
                "PURCHASE OF: " + item + "\n"+
                "AMOUNT: " + amount + "\n"+
                "COST: $" + cost + "\n"+
                "BY USER: " + user + "\n"
        );
    }

    public int getID() {
        return this.id;
    }
    public String getUser() {
        return this.user;
    }
    public String getDate() {
        return this.date;
    }
    public String getItem() {
        return this.item;
    }
    public int getAmount() {
        return this.amount;
    }
    public int getCost() {
        return this.cost;
    }
    //-------------------------------------------------------------------------------------------



    public static LinkedList<Purchase> purchaseHistory = new LinkedList<Purchase>();
    private static int MAX_ID = 1;

    public static Scanner scan = new Scanner(System.in);

    public static void loadPurchases() {
        try {
            File file = new File(Main.CURRENT_DIRECTORY + "\\PurchaseHistory.txt");
            Scanner scan = new Scanner(file);

            while (scan.hasNextLine()) {
                String[] data = scan.nextLine().split(", ");

                int id = Integer.valueOf(data[0]);
                String user = data[1];
                String date = data[2];
                String item = data[3];
                int amount = Integer.valueOf(data[4]);
                int cost = Integer.valueOf(data[5]);

                purchaseHistory.add(new Purchase(id, user, date, item, amount, cost));

                if (id > MAX_ID) {
                    MAX_ID = id;
                }
            }
            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println("Purchase history not found.");
            e.printStackTrace();
        }
    }

    public static void updatePurchases() {
        try {
            FileWriter writer = new FileWriter("PurchaseHistory.txt");
            writer.write("");
            String data = "";

            for (Purchase aPurchase : purchaseHistory) {
                String id = Integer.toString(aPurchase.getID());
                String user = aPurchase.getUser();
                String date = aPurchase.getDate();
                String item = aPurchase.getItem();
                String amount = Integer.toString(aPurchase.getAmount());
                String cost = Integer.toString(aPurchase.getCost());

                data += (id + ", " + user + ", " + date + ", " + item + ", " + amount + ", " + cost + ", " + "\n");
            }

            writer.write(data);
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void makeOrder(String item, int amount, int cost, String date) {
        int id = MAX_ID + 1;
        String user = Main.CURRENT_PROFILE.getUsername();

        purchaseHistory.add(new Purchase(id, user, date, item, amount, cost));
    }

    public static void removeOrder(int ID) {
        //purchaseHistory.remove(currentPurchase);
    }

    public static void editOrder(int selectedID, String itemName, int amount, int cost, String date) {
        for (Purchase aPurchase : purchaseHistory) {
            if (selectedID == aPurchase.getID()) {
                aPurchase.setItem(itemName);
                aPurchase.setAmount(amount);
                aPurchase.setCost(cost);
                aPurchase.setDate(date);
                break;
            }
        }
    }
}