import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.util.LinkedList;

public class Main  {

    private static LinkedList<Profile> profiles = new LinkedList<Profile>();
    private static Profile currentProfile;

    
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
            System.out.println("Purchase history not found.");
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


    // Choice constants
    static final int CHOICE_MENU_STORE = 1;        
    static final int CHOICE_MENU_PROFILE = 2;
    static final int CHOICE_MENU_RETURN_TO_LOGIN = 3;
    static final int CHOICE_MENU_EXIT = 4;

    public static void menu() {
        Scanner scan = new Scanner(System.in);
        int user_input = -1;


        /**
         * Enter a do-while loop to always show menu options as long as we dont choose
         */
        do{
            System.out.println("\n\n~~~ menu ~~~\n1. Store Menu\n2. Profile Menu\n3. Return to Login\n4. Exit Program");
        
        // verify there is an input to get
        if (scan.hasNextInt()){
            user_input = scan.nextInt();
        }

            switch(user_input){
                case CHOICE_MENU_STORE:
                    Store.menu();
                    break;
                case CHOICE_MENU_PROFILE:
                    break;
                case CHOICE_MENU_RETURN_TO_LOGIN:
                    login();
                case CHOICE_MENU_EXIT:
                    // program exit point. 
                    System.out.println("\nExiting program!");
                    System.exit(0);
                    break;
            }
        }
        while(user_input != CHOICE_MENU_EXIT);
        scan.close();
    }

    public static void main(String[] args) {
        loadProfiles();
        login();
    }
}