import java.util.LinkedList;
import java.util.ListIterator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Purchase  {

    private static LinkedList<Purchase> purchaseHistory = new LinkedList<Purchase>();
    private static ListIterator<Purchase> iterator;
    private static Purchase currentPurchase;

    private int id;
    private String user;
    private String date;
    private String item;
    private int amount;
    private int cost;

    public static void loadPurchases() {
        try {
            File file = new File("PurchaseHistory.txt");
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
            }
            scan.close();

            iterator = purchaseHistory.listIterator();
        } catch (FileNotFoundException e) {
            System.out.println("Purchase history not found.");
            e.printStackTrace();
        }
    }

    public static void readPrev() {
        if (iterator.previous().getID() == currentPurchase.getID()) {
            currentPurchase = iterator.previous();
        }
        currentPurchase.displayInfo();
    }

    public static void readNext() {
        currentPurchase = iterator.next();
        currentPurchase.displayInfo();
    }

    public Purchase(int id, String user, String date, String item, int amount, int cost) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.item = item;
        this.amount = amount;
        this.cost = cost;
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
}