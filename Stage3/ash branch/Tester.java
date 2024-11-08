import java.util.Scanner;

public class Tester {
    public static Inventory storeInventory = new Inventory();
    public static Scanner scan = new Scanner(System.in);

    public static void option1() {
        storeInventory.displayInfo();
    }

    public static void option2() {
        Purchase.readNext();
    }

    public static void option3() {
        Purchase.readPrev();
    }

    public static void menu() {
        System.out.println("""
            1. Display inventory")
            2. Read next purchase")
            3. Read previous purchase")
            4. Exit")
            """);
        int choice = 0;
        while (choice != 4 ) {

            choice = scan.nextInt();
            switch (choice) {
                case 1:
                    option1();
                    break;
                case 2:
                    option2();
                    break;
                case 3:
                    option3();
                    break;
                case 4:
                    scan.close();
                    return;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
            menu();
        }
    }

    public static void main(String[] args) {

        storeInventory.addItem("Wrench", "Shelf A3", 17, 3);
        storeInventory.addItem("Screwdriver", "Shelf A2", 11, 4);
        storeInventory.addItem("Tape", "Shelf C3", 6, 2);
        storeInventory.addItem("Bolts", "Shelf D6", 300, 1);

        //Purchase purchase = new Purchase(001, "achterbt", "11/1/24", "Wrench", 70, 340000);
        //purchase.displayInfo();

        Purchase.loadPurchases();

        menu();
    }
}
