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
    
    public static void menu() {
        Scanner in = new Scanner(System.in);
        String option;
        
        //Temp menu setup.
        do{
            System.out.println("~~~Profile Menu~~~\n");
            System.out.println("""
                               1. Authority
                               2. Name
                               3. Position
                               4. Wage
                               5. Return to Main Menu
                               """);
            String choice = in.next();
            option = choice;
            
            switch(choice) {
                case "1":
                    if(checkAuth() == true) {
                        
                    }
                    break;
                case "2":
                    break;
                case "3":
                    break;
                case "4":
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Invalid option. Try again");
                    break;
            }
        } while (option != "1" && option != "2" && option != "3" && option != "4" && option != "5");
    }
    
    public static boolean checkAuth(Staff aStaff, int authCompare) {
        if(aStaff.getAuth() == authCompare) {
            return true;
        } else {
            return false;
        }
    }
    
}