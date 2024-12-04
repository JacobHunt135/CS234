import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import java.util.LinkedList;
public class Main  {

    //---------------------------------------------------------------
    // AUTHORITY LEVELS
    /*  0 <- Admin ( admin and manager )
     *  1 <- Employee
     *  2 <- Observer
     */
    public static String CURRENT_DIRECTORY = System.getProperty("user.dir");
    public static Profile CURRENT_PROFILE;

    public static int AUTH_REQ_STOCK = 2;
    public static int AUTH_REQ_ORDER = 2;
    public static int AUTH_REQ_STAFF = 1;
    public static int AUTH_REQ_PROFILE = 0;

    public static final int ADMIN_AUTH_LEVEL = 0;
    //---------------------------------------------------------------

    private static Scanner scan = new Scanner(System.in);
    private static LinkedList<Profile> profiles = new LinkedList<Profile>();
    
    public static void loadProfiles() {
        try {
            File file = new File(CURRENT_DIRECTORY + "\\Profiles.txt");
            Scanner scan = new Scanner(file);

            while (scan.hasNextLine()) {
                String[] data = scan.nextLine().split(", ");

                String username = data[0];
                String password = data[1];
                int authority = Integer.valueOf(data[2]);

                profiles.add(new Profile(username, password, authority));
            }
            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println("Profiles not found.");
            e.printStackTrace();
        }
    }

    public static void updateProfiles() {
        try {
            FileWriter writer = new FileWriter("Profiles.txt");
            writer.write("");
            String data = "";

            for (Profile prof : profiles) {
                String username = prof.getUsername();
                String password = prof.getPassword();
                String auth = Integer.toString(prof.getAuthority());

                data += (username + ", " + password + ", " + auth + "\n");
            }

            writer.write(data);
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // Choice constants
    final static int CHOICE_LOGIN_RETURN_TO_LOGIN = 1;
    final static int CHOICE_LOGIN_EXIT = 2;

    public static void login() {
        // maybe we can use a static scanner here instead?
        Scanner scan = new Scanner(System.in);

        String username;
        String password;

        System.out.println("[LOGIN MENU]\nEnter username:");
        username = scan.nextLine();
        System.out.println("Enter password:");
        password = scan.nextLine();

        for (Profile profile : profiles) {
            if (profile.loginProfile(username, password) == true) {
                CURRENT_PROFILE = profile;
                menu();
                return;
            }
        }
        
        int choice = 0;
        while (choice != CHOICE_LOGIN_RETURN_TO_LOGIN || choice != CHOICE_LOGIN_EXIT) {
            System.out.println("Wrong username and/or password.");
            System.out.println("""
                1. Return to login")
                2. Exit program")
                """);

            choice = scan.nextInt();

            switch (choice) {
                case CHOICE_LOGIN_RETURN_TO_LOGIN:
                    login();
                    break;
                case CHOICE_LOGIN_EXIT:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    //------------------------------------------------------------------------------------
    // PROFILE HANDLING
    //------------------------------------------------------------------------------------
    //---------------------------------------------------------------------
    public static void displayInfo() {
        for (Profile prof : profiles) {
            System.out.println(prof.getUsername() + ", " + prof.getPassword() + ", " + Integer.toString(prof.getAuthority()));
        }
    }
    public static void displayInfo(Profile prof) {
        System.out.println(prof.getUsername() + ", " + prof.getPassword() + ", " + Integer.toString(prof.getAuthority()));
    }
    //---------------------------------------------------------------------

    public static void addProfile() {
        scan.nextLine(); // collects any garbage input

        System.out.print("New profile's username: ");
        String username = scan.nextLine();
        System.out.print("New profile's password: ");
        String password = scan.nextLine();
        System.out.print("New profile's authority level: ");
        int auth = scan.nextInt();
        if (auth <= ADMIN_AUTH_LEVEL) {
            System.out.println("Exceeds maximum auth level. Invalid.");
            return;
        }

        profiles.add(new Profile(username, password, auth));
    }

    public static void removeProfile() {
        scan.nextLine(); // collects any garbage input

        System.out.print("Enter the username of the profile to be removed: ");
        String username = scan.nextLine();
        for (Profile prof : profiles) { 
            if (username.equals("ADMINISTRATOR")) {
                System.out.println("Cannot remove administrator account. Invalid.");
                return;
            } else if (username.equals(prof.getUsername())) {
                profiles.remove(prof);
            }
        }
    }

    public static void editProfile() {
        scan.nextLine(); // collects any garbage input

        System.out.print("Enter the name of the item to edit: ");
        String username = scan.nextLine();

        if (username.equals("ADMINISTRATOR")) {
            System.out.println("Cannot edit administrator account. Invalid.");
            return;
        }

        for (Profile prof : profiles) { 
            if (username.equals(prof.getUsername())) {
                displayInfo(prof);

                System.out.print("Set new username: ");
                prof.setUsername(scan.nextLine());
                System.out.print("Set new password: ");
                prof.setPassword(scan.nextLine());
                System.out.print("Set new authority level: ");
                prof.setAuthority(scan.nextInt());
                return;
            }
        }

        // should only run if the inputted name matches none of the items
        System.out.println("No such profile found.");
    }

    public static void profileMenu() {
        int user_input = -1;

        /**
         * Enter a do-while loop to always show menu options as long as we dont choose
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
    //------------------------------------------------------------------------------------
    //
    //------------------------------------------------------------------------------------

    // Choice constants
    static final int CHOICE_MENU_STORE = 1;        
    static final int CHOICE_MENU_STAFF = 2;
    static final int CHOICE_MENU_PROFILE = 3;
    static final int CHOICE_MENU_RETURN_TO_LOGIN = 4;
    static final int CHOICE_MENU_EXIT = 5;

    public static void menu() {
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

    public static void main(String[] args) {
        String dir = System.getProperty("user.dir");
        System.out.println("current dir = " + dir);

        loadProfiles();
        login();
    }
}