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
        String option;
        
        //Temp menu setup.
        do{
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
        } while (option != "1" && option != "2" && option != "3" && option != "4" && option != "5" && option != "6" && option != "7");
    }
    
    public static boolean checkAuth(Staff aStaff, int authCompare) {
        //Changed how the authority is checked to be simpler.
        return aStaff.getAuth() == authCompare;
//        if(aStaff.getAuth() == authCompare) {
//            return true;
//        } else {
//            return false;
//        }
    }
    
    //Attempting to implement IDs since using names might cause issues.
    public void option1(Staff aStaff) {
        Scanner IDGet = new Scanner(System.in);
        System.out.println("What is the name of the person you want to check their authority: ");
        int ID = IDGet.nextInt();
        
        if (ID == aStaff.getID()) {
            System.out.println("The person's authority level is: " + aStaff.getAuth());
        } else {
            System.out.println("INVALID ID");
        }
    }
    
}