
public class Menus {
    
    //Menu for the profiles.
    public static void profileMenu() {
        int user_input = -1;

        /**
         * Enter a do-while loop to always show menu options as long as we don't choose
         */
        do {
            System.out.println("""
                \n~~~ profile menu ~~~
                1. View profiles
                2. Add profile
                3. Remove profile
                4. Edit profile
                5. Return to main menu
                """);
        
            // verify there is an input to get
            if (scan.hasNextInt()){
                user_input = scan.nextInt();
            }

            switch(user_input){
                case 1:
                    displayInfo();
                    break;
                case 2:
                    addProfile();
                    break;
                case 3:
                    removeProfile();
                    break;
                case 4:
                    editProfile();
                case 5: // back to menu
                // scan.close();
                System.out.println("\n<--");
                break;
            }
        } while(user_input != 5);
    }

    // Choice constants
    static final int CHOICE_MENU_STORE = 1;        
    static final int CHOICE_MENU_STAFF = 2;
    static final int CHOICE_MENU_PROFILE = 3;
    static final int CHOICE_MENU_RETURN_TO_LOGIN = 4;
    static final int CHOICE_MENU_EXIT = 5;

    public static void mainMenu() {
        int user_input = -1;

        /**
         * Enter a do-while loop to always show menu options as long as we dont choose
         */
        do {
            System.out.println("""
                \n~~~ menu ~~~
                1. Store Menu
                2. Staff Menu
                3. Profile Menu
                4. Return to Login
                5. Exit Program
                """);
        
            // verify there is an input to get
            if (scan.hasNextInt()){
                user_input = scan.nextInt();
            }

            switch(user_input){
                case CHOICE_MENU_STORE:
                    Store.menu();
                    break;
                case CHOICE_MENU_STAFF:
                    Staff.menu();
                    break;
                case CHOICE_MENU_PROFILE:
                    if (! Main.CURRENT_PROFILE.checkAuthority(Main.AUTH_REQ_PROFILE)){
                        System.out.println("Insufficient Authority...");
                    } else {
                        profileMenu();
                    }
                    break;
                case CHOICE_MENU_RETURN_TO_LOGIN:
                    login();
                case CHOICE_MENU_EXIT:
                    // program exit point. 
                    updateProfiles();
                    System.out.println("\nExiting program!");
                    System.exit(0);
                    break;
            }
        } while(user_input != CHOICE_MENU_EXIT);
        scan.close();
    }
    
    //Menu for navigating and displaying the inventory.
    public static void Inventorymenu() {
        loadInventory();
        int user_input = -1;

        /**
         * Enter a do-while loop to always show menu options as long as we dont choose to exit.
         */
        do{
            System.out.println("""
                \n~~~ Inventory ~~~
                1. Display Inventory
                2. Add Item
                3. Remove Item
                4. Edit Item
                5. Return to Store menu
                """);
            
            user_input = scan.nextInt();

            switch(user_input) {
                case 1: // display inventory / stock menu
                    displayInfo();
                    break;
                case 2: // add item
                    addItem();
                    break;
                case 3: // remove item
                    removeItem();
                    break;
                case 4: // edit item
                    editItem();
                    break;
                case 5: // back to store menu
                    // scan.close();
                    System.out.println("\n<--");
                    break;
            }
        } while(user_input != 5);
        // scanner.close();

        updateInventory();
    }
    
    //Menu to navigate purchases.
    public static void purchaseMenu() {
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
    
        public static void staffMenu() {
        loadStaff();
        int user_input = -1;

        /**
         * Enter a do-while loop to always show menu options as long as we don't choose to exit.
         */
        do{
            System.out.println("""
                \n~~~Staff Menu~~~
                1. Display Staff
                2. Add Staff
                3. Remove Staff
                4. Edit Staff
                5. Return to Main Menu
                """);
            
            user_input = scan.nextInt();

            switch(user_input) {
                case 1: // display inventory / stock menu
                    displayInfo();
                    break;
                case 2: // add item
                    addEmp();
                    break;
                case 3: // remove item
                    removeEmp();
                    break;
                case 4: // edit item
                    editEmp();
                    break;
                case 5: // back to store menu
                    // scan.close();
                    System.out.println("\n<--");
                    break;
            }
        } while(user_input != 5);
        // scanner.close();

        updateStaff();
    }
        
    //Menu for navigating store options.
    public static void storeMenu() {

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