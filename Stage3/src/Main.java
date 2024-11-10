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
                menu();
                //scan.close();
                return;
            }
        }
        
        int choice = 0;
        while (choice != 1 || choice != 2) {
            System.out.println("Wrong username and/or password.");
            System.out.println("""
                1. Return to login")
                2. Exit program")
                """);

            choice = scan.nextInt();

            switch (choice) {
                case 1:
                    //scan.close();
                    login();
                    break;
                case 2:
                    //scan.close();
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    public static void menu() {
        System.out.println("""
            1. Store menu")
            2. Profile menu")
            3. Return to login")
            4. Exit")
            """);
        Scanner scan = new Scanner(System.in);
        int choice = 0;

        choice = scan.nextInt();
        scan.nextLine();
        switch (choice) {
            case 1:
                //scan.close();
                Store.menu();
                break;
            case 2:
                //Profile.menu();
                break;
            case 3:
                //scan.close();
                login();
                break;
            case 4:
                //scan.close();
                return;
            default:
                System.out.println("Invalid option.");
                menu();
                break;
        }
    }

    public static void main(String[] args) {
        loadProfiles();
        login();
    }
}