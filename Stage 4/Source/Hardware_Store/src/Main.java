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

    public static LinkedList<Profile> profiles = new LinkedList<Profile>();
    
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

    public static boolean login(String username, String password) {
        for (Profile profile : profiles) {
            if (profile.loginProfile(username, password) == true) {
                CURRENT_PROFILE = profile;
                return true;
            }
        }
        
        return false;
    }

    //------------------------------------------------------------------------------------
    // PROFILE HANDLING
    //------------------------------------------------------------------------------------

    public static void addProfile(String username, String password, int auth) {
        if (auth <= ADMIN_AUTH_LEVEL) {
            System.out.println("Exceeds maximum auth level. Invalid.");
            return;
        }

        profiles.add(new Profile(username, password, auth));
    }

    public static void removeProfile(String username) {
        for (Profile prof : profiles) { 
            if (username.equals("ADMINISTRATOR")) {
                System.out.println("Cannot remove administrator account. Invalid.");
                return;
            } else if (username.equals(prof.getUsername())) {
                profiles.remove(prof);
            }
        }
    }

    public static void editProfile(String username, String newUser, String newPass, int auth) {
        if (username.equals("ADMINISTRATOR")) {
            System.out.println("Cannot edit administrator account. Invalid.");
            return;
        }

        for (Profile prof : profiles) { 
            if (username.equals(prof.getUsername())) {
                prof.setUsername(newUser);
                prof.setPassword(newPass);
                prof.setAuthority(auth);
                return;
            }
        }

        // should only run if the inputted name matches none of the items
        System.out.println("No such profile found.");
    }

    public static void main(String[] args) {
        String dir = System.getProperty("user.dir");
        System.out.println("current dir = " + dir);

        loadProfiles();
        
        //---------------------------------------------------------------------
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginUI().setVisible(true);
            }
        });
        //---------------------------------------------------------------------
    }
}