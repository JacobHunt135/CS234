import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
        
public class Staff {

    public static Scanner scan = new Scanner(System.in);
    private static ArrayList<Employee> employees;

    //---------------------------------------------------------------------

    public static void loadStaff() {
        employees = new ArrayList<Employee>();

        try {
            File file = new File(Main.CURRENT_DIRECTORY + "\\Staff.txt");
            Scanner scan = new Scanner(file);

            while (scan.hasNextLine()) {
                String[] data = scan.nextLine().split(", ");

                String name = data[0];
                String position = data[1];
                float wage = Float.valueOf(data[2]);

                employees.add(new Employee(name, position, wage));
            }
            scan.close();

        } catch (FileNotFoundException e) {
            System.out.println("Staff file not found.");
            e.printStackTrace();
        }
    }

    public static void updateStaff() {
        try {
            FileWriter writer = new FileWriter("Staff.txt");
            writer.write("");
            String data = "";

            for (Employee emp : employees) {
                String name = emp.getName();
                String position = emp.getPosition();
                String wage = Float.toString(emp.getWage());

                data += (name + ", " + position + ", " + wage + ", " + "\n");
            }

            writer.write(data);
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    public static void addEmp(String name, String position, float wage) {
        if (! Main.CURRENT_PROFILE.checkAuthority(Main.AUTH_REQ_STAFF)){
            System.out.println("Insufficient Authority...");
        } else {
            Employee emp = new Employee(name, position, wage);
            employees.add(emp);
        }
    }
    
    public static void removeEmp(String name) {
        if (! Main.CURRENT_PROFILE.checkAuthority(Main.AUTH_REQ_STAFF)){
            System.out.println("Insufficient Authority...");
        } else {
            for (Employee emp : employees) { 
                if (name.equals(emp.getName())) {
                    employees.remove(emp);
                }
            }
        }
    }

    public static void editEmp(String name, String newName, String position, float wage) {
        for (Employee emp : employees) { 
            if (name.equals(emp.getName())) {
                emp.setName(newName);
                emp.setPosition(position);
                emp.setWage(wage);
                return;
            }
        }

        // should only run if the inputted name matches none of the employees
        System.out.println("No such employee found.");
    }
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
    
