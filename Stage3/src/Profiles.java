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
        
        do{
            System.out.println("~~~Profile Menu~~~\n");
            System.out.println("""
                               1. Authority
                               2. Name
                               3. Position
                               4. Wage
                               """);
            String choice = in.next();
            option = choice;
            
            switch(choice) {
                case "1":
                    break;
                case "2":
                    break;
                default:
                    System.out.println("Invalid option. Try again");
                    break;
            }
        } while (option != "1" && option != "2");
    }
    
    public static boolean checkAuth(Staff aStaff, int authCompare) {
        if(aStaff.getAuth() == authCompare) {
            return true;
        } else {
            return false;
        }
    }
    
}