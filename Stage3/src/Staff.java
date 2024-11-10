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
    public static void displayInfo() {
        System.out.printf("%20s", "[NAME] | ");
        System.out.printf("%20s", "[POSITION] | ");
        System.out.printf("%10s", "[WAGE] | \n");
        for (Employee emp : employees) {
            float wage = emp.getWage();

            System.out.printf("%20s", emp.getName() + " | ");
            System.out.printf("%20s", emp.getPosition() + " | ");
            System.out.printf("%10s", wage + " | \n");
        }
    }
    public static void displayInfo(Employee emp) {
        System.out.printf("%20s", "[NAME] | ");
        System.out.printf("%20s", "[POSITION] | ");
        System.out.printf("%10s", "[WAGE] | \n");

        float wage = emp.getWage();

        System.out.printf("%20s", emp.getName() + " | ");
        System.out.printf("%20s", emp.getPosition() + " | ");
        System.out.printf("%10s", wage + " | \n");
    }
    //---------------------------------------------------------------------

    public static void loadStaff() {
        employees = new ArrayList<Employee>();

        try {
            File file = new File("Staff.txt");
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
    
    public static void addEmp() {
        if (! Main.CURRENT_PROFILE.checkAuthority(Main.AUTH_REQ_STAFF)){
            System.out.println("Insufficient Authority...");
        } else {
            scan.nextLine(); // collects any garbage input

            System.out.print("New employee's name: ");
            String name = scan.nextLine();
            System.out.print("New employee's position: ");
            String position = scan.nextLine();
            System.out.print("New employee's wage: ");
            float wage = scan.nextFloat();
    
            Employee emp = new Employee(name, position, wage);
            employees.remove(emp);
        }
    }
    
    public static void removeEmp() {
        if (! Main.CURRENT_PROFILE.checkAuthority(Main.AUTH_REQ_STAFF)){
            System.out.println("Insufficient Authority...");
        } else {
            scan.nextLine(); // collects any garbage input

            System.out.print("Enter the name of the employee to remove: ");
            String name = scan.nextLine();
            for (Employee emp : employees) { 
                if (name.equals(emp.getName())) {
                    employees.remove(emp);
                }
            }
        }
    }

    public static void editEmp() {
        scan.nextLine(); // collects any garbage input

        System.out.print("Enter the name of the employee to edit: ");
        String name = scan.nextLine();
        for (Employee emp : employees) { 
            if (name.equals(emp.getName())) {
                displayInfo(emp);

                System.out.print("Set new employee name: ");
                emp.setName(scan.nextLine());
                System.out.print("Set new position: ");
                emp.setPosition(scan.nextLine());
                System.out.print("Set new wage: ");
                emp.setWage(scan.nextFloat());
                return;
            }
        }

        // should only run if the inputted name matches none of the employees
        System.out.println("No such employee found.");
    }
    
    /*
     * options
     * -> 
     */

    public static void menu() {
        loadStaff();
        int user_input = -1;

        /**
         * Enter a do-while loop to always show menu options as long as we dont choose to exit.
         */
        do{
            System.out.println("""
                \n~~~Staff Menu~~~
                1. Display Staff
                2. Add Staff
                3. Remove Staff
                4. Edit Staff
                5. Return to Main Menu
                """);
            
            user_input = scan.nextInt();

            switch(user_input) {
                case 1: // display inventory / stock menu
                    displayInfo();
                    break;
                case 2: // add item
                    addEmp();
                    break;
                case 3: // remove item
                    removeEmp();
                    break;
                case 4: // edit item
                    editEmp();
                    break;
                case 5: // back to store menu
                    // scan.close();
                    System.out.println("\n<--");
                    break;
            }
        } while(user_input != 5);
        // scanner.close();

        updateStaff();
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
    
