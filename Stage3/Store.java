import java.util.Scanner;

public class Store {
    public static int AUTH_REQ_STOCK = 2;
    public static int AUTH_REQ_ORDER = 2;

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

    static int user_input = -1;
    public static void menu() {

        /**
         * Enter a do-while loop to always show menu options as long as we dont choose to exit.
         */
        do{
            System.out.println("\n\n~~~ store menu ~~~\n1. Display Inventory\n2. Read next purchase\n3. Read previous purchase\n4. Back to main menu");
            
            user_input = scan.nextInt();

            switch(user_input){
                case 1: // display inventory
                    option1();
                    break;
                case 2: // read next purchase
                    option2();
                    break;
                case 3: // read previous purchase
                    option3();
                case 4: // back to menu
                // scan.close();
                    System.out.println("\n<--");
                    break;
            }
        }
        while(user_input != 4);
        // scanner.close();
    }

    /* 
    
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
    */
}
