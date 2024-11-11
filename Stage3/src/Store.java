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

    //Menu for navigating store options.
    public static void menu() {

        int user_input = -1;

        /**
         * Enter a do-while loop to always show menu options as long as we dont choose to exit.
         */
        do{
            System.out.println("""
                \n~~~ store menu ~~~
                1. Inventory screen
                2. Order screen
                3. Return to main menu
                """);
            
            user_input = scan.nextInt();

            switch(user_input) {
                case 1: // display inventory / stock menu
                    optionStock();
                    break;
                case 2: // display purchase history / order menu
                    optionOrder();
                    break;
                case 3: // back to menu
                    // scan.close();
                    System.out.println("\n<--");
                    break;
            }
        } while(user_input != 3);
        // scanner.close();
    }
}
