import java.util.ArrayList;
import java.util.Scanner;
        
public class Profiles {
    
    private ArrayList<Staff> staff;
    public static Staff currentStaff;

    public static int AUTH_REQ_ADD_STAFF = 0;
    public static int AUTH_REQ_REMOVE_STAFF = 0;

    public Profiles() {
        staff = new ArrayList<Staff>();
    }
    
    public void addStaff(Staff aStaff) {
        staff.add(aStaff);
    }
    
    public void removeStaff(Staff aStaff) {
        staff.remove(aStaff);
    }
    
    /*
     * options
     * -> 
     */

    public static void menu() {
        Scanner in = new Scanner(System.in);
        String option = "0";
        
        //Temp menu setup.
        while (option != "7") {
            System.out.println("~~~Profile Menu~~~\n");
            System.out.println("""
                               1. Check Authority
                               2. Check Name
                               3. Check Position
                               4. Check Wage
                               5. Add Staff
                               6. Remove Staff
                               7. Return to Main Menu
                               """);
            String choice = in.next();
            option = choice;
            
            switch(choice) {
                case "1":
                    System.out.println("User's authority is "+currentStaff.getAuth());
                    break;
                case "2":
                    System.out.println("User's name is "+currentStaff.getName());
                    break;
                case "3":
                System.out.println("User's position is "+currentStaff.getPosition());
                    break;
                case "4":
                    System.out.println("User's wage is "+currentStaff.getWage());
                    break;
                case "5":
                    if (! currentStaff.checkAuthority(AUTH_REQ_ADD_STAFF)){
                        System.out.println("Insufficient Authority...");
                        break;
                    }
                    System.out.println("Added staff member") ; // debug line
                    break;
                case "6":
                    if (! currentStaff.checkAuthority(AUTH_REQ_REMOVE_STAFF)){
                        System.out.println("Insufficient Authority...");
                        break;
                    }
                    System.out.println("Removed staff member") ; // debug line
                    break;
                case "7":
                    return;
                default:
                    System.out.println("Invalid option. Try again");
                    break;
            }
        }
    }
    
    // public static boolean checkAuth(Staff aStaff, int authCompare) {
        //Changed how the authority is checked to be simpler.
        // return aStaff.getAuth() == authCompare;
//        if(aStaff.getAuth() == authCompare) {
//            return true;
//        } else {
//            return false;
//        }
    }
    
    //Attempting to implement IDs since using names might cause issues.
    // public void option1(Staff aStaff) {
    //     Scanner IDGet = new Scanner(System.in);
    //     System.out.println("What is the name of the person you want to check their authority: ");
    //     int ID = IDGet.nextInt();
        
    //     if (ID == aStaff.getID()) {
    //         System.out.println("The person's authority level is: " + aStaff.getAuth());
    //     } else {
    //         System.out.println("INVALID ID");
    //     }
    // }
    
