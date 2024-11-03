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
            System.out.println("Profile Menu");
            String choice = in.next();
            option = choice;
            
            switch(choice) {
                case "1":
                    break;
                case "2":
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        } while (option != "1" && option != "2");
    }
    
    
    
}