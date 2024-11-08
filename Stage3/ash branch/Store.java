import java.util.ArrayList;
import java.util.Scanner;

public class Store {
    public static int AUTH_REQ_STOCK = 2;
    public static int AUTH_REQ_ORDER = 2;

    public static Inventory storeInventory = new Inventory();
    public static ArrayList<Item> orderHistory = new ArrayList<Item>();
    public static Staff currentStaff;

    public static Scanner scan = new Scanner(System.in);
    

    // -- core methods
        // Lower value of authority determiens higher access.
        // for example, auth 0 is admin, while auth 1 would be employee

    // public static Boolean canStock(){
    //     return currentStaff.getAuth() <= AUTH_REQ_STOCK;
    // }

    // public static Boolean canOrder(){
    //         return currentStaff.getAuth() <= AUTH_REQ_ORDER;
    // }

    public static void showPurchaseHistory(){
        ;
    }

    // -- set-get

    public static void setCurrentStaff(Staff newCurrentStaff){
        currentStaff = newCurrentStaff;
    }
    
    public static Staff getCurrentStaff(){
        return currentStaff;
    }

    // -- option methods

    private static void optionStock(){
        if (!currentStaff.checkAuthority(AUTH_REQ_ORDER)){
            System.out.println("Insufficient Authority...");
        }
        else{
            System.out.println("Stocked items");
        }
        ;
    }

    private static void optionOrder(){
        ;
    }

    private static void optionDisplayInfo(){
        storeInventory.displayInfo();
    }   
    static int user_input = -1;
    public static void menu() {

        /**
         * Enter a do-while loop to always show menu options as long as we dont choose to exit.
         */
        do{
            System.out.println("\n\n~~~ store menu ~~~\n1. Display Inventory\n2. Stock Item\n3. Order Item\n4. Back to main menu");
            
            user_input = scan.nextInt();

            switch(user_input){
                case 1: // display inventory    
                    optionDisplayInfo();
                    break;
                case 2: // read next purchase
                    optionStock();
                    break;
                case 3: // read previous purchase
                    optionOrder();
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
