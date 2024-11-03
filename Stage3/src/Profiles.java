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
    }
    
}