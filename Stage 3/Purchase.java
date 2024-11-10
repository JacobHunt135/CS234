import java.util.LinkedList;
import java.util.ListIterator;
import java.time.LocalDate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Purchase  {

    private static LinkedList<Purchase> purchaseHistory = new LinkedList<Purchase>();
    private static ListIterator<Purchase> iterator;
    private static Purchase currentPurchase;
    private static int MAX_ID = 1;

    public static Scanner scan = new Scanner(System.in);

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

                if (id > MAX_ID) {
                    MAX_ID = id;
                }
            }
            scan.close();

            iterator = purchaseHistory.listIterator();
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

    public static void readPrev() {
        if (iterator.hasPrevious()) {
            if (iterator.previous().getID() == currentPurchase.getID()) {
                currentPurchase = iterator.previous();
            }
            currentPurchase.displayInfo();
        } else {
            System.out.println("Reached beginning of purchase history.");
        }
    }

    public static void readNext() {
        if (iterator.hasNext()) {
            currentPurchase = iterator.next();
            currentPurchase.displayInfo();
        } else {
            System.out.println("Reached end of purchase history.");
        }
    }

    public static void makeOrder() {
        scan.nextLine(); // collects any garbage input

        int id = MAX_ID + 1;
        String user = Main.CURRENT_PROFILE.getUsername();
        String date = LocalDate.now().toString();
        System.out.print("Name of item ordered: ");
        String item = scan.nextLine();
        System.out.print("Amount ordered: ");
        int amount = scan.nextInt();
        System.out.print("Cost of order: ");
        int cost = scan.nextInt();

        purchaseHistory.add(new Purchase(id, user, date, item, amount, cost));
        iterator = purchaseHistory.listIterator();
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

    public static void menu() {
        loadPurchases();
        int user_input = -1;

        /**
         * Enter a do-while loop to always show menu options as long as we dont choose to exit.
         */
        do{
            System.out.println("""
                \n~~~ Purchases ~~~
                1. Display next purchase
                2. Display previous purchase
                3. Make an order
                4. Return to Store menu
                """);
            
            user_input = scan.nextInt();

            switch(user_input) {
                case 1: // read next purchase
                    readNext();
                    break;
                case 2: // read previous purchase
                    readPrev();
                    break;
                case 3: // make a new purchase
                    makeOrder();
                    break;
                case 4: // back to store menu
                    // scan.close();
                    System.out.println("\n<--");
                    break;
            }
        } while(user_input != 4);
        // scanner.close();

        updatePurchases();
    }
}