//Imports.
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
    public static Profile CURRENT_PROFILE;

    public static int AUTH_REQ_STOCK = 2;
    public static int AUTH_REQ_ORDER = 2;
    public static int AUTH_REQ_STAFF = 1;
    public static int AUTH_REQ_PROFILE = 0;

    public static final int ADMIN_AUTH_LEVEL = 0;
    //---------------------------------------------------------------

    //Initializing scanner and LinkedList.
    private static Scanner scan = new Scanner(System.in);
    private static LinkedList<Profile> profiles = new LinkedList<Profile>();
    
    /* Method that finds and reads a .txt file in the same folder to access
    *  who is in the database. If there is no .txt file, it Prints a message
    *  saying such.
    */
    public static void loadProfiles() {
        try {
            File file = new File("Profiles.txt");
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

    /* Method that updates the Profiles.txt with new information and prints an
    *  error message if an error has occured.
    */
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

    /* Initial login to the program that uses the info int the Profiles.txt
    *  file to determine if a user is with the store. If an incorrect username
    *  or password is given, then options to exit the program or to try again
    *  are presented to the user.
    */
    public static void login() {
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

    //Adds a new profile to the Profiles.txt file through the Profile class.
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

    //Removes a profile from the Profiles.txt file through the Profile class.
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

    /* Enables the user to edit profiles other than the administrator account.
    *  Only the username, password, and authority level can be changed.
    */
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

        System.out.println("No such profile found.");
    }

    //Main method to load the profiles and initialize login.
    public static void main(String[] args) {
        loadProfiles();
        login();
    }
}