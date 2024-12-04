//Import.
import java.util.Scanner;

public class Store {

    //Initializing scanner.
    public static Scanner scan = new Scanner(System.in);

    //Checks authority for access to inventory.
    private static void optionStock(){
        if (! Main.CURRENT_PROFILE.checkAuthority(Main.AUTH_REQ_STOCK)){
            System.out.println("Insufficient Authority...");
        } else {
            Inventory.menu();
        }
    }

    //Checks authority for access to purchases.
    private static void optionOrder(){
        if (! Main.CURRENT_PROFILE.checkAuthority(Main.AUTH_REQ_ORDER)){
            System.out.println("Insufficient Authority...");
        } else {
            Purchase.menu();
        }
    }

}