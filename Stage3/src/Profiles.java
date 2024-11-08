import java.util.ArrayList;
import java.util.Scanner;
        
public class Profiles {
    
    private ArrayList<Staff> staff;

    public Profiles() {
        staff = new ArrayList<Staff>();
    }
    
    public void addStaff(Staff aStaff) {
        staff.add(aStaff);
    }
    
    public void removeStaff(Staff aStaff) {
        staff.remove(aStaff);
    }
    
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
                    
                    break;
                case "2":
                    break;
                case "3":
                    break;
                case "4":
                    break;
                case "5":
                    return;
                case "6":
                    break;
                case "7":
                    return;
                default:
                    System.out.println("Invalid option. Try again");
                    break;
            }
        }
    }
    
    /*
    0 - admin
    1 - employee
    2 - observer
    */
    public static boolean checkAuth(Staff aStaff, int authCompare) {
        return aStaff.getAuth() == authCompare;
    }
    
}