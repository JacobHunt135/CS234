public class Employee extends Data_Item {
    
    private String position;
    private float wage;

    public Employee(String name, String position, float wage){
        this.name = name;
        this.position = position;
        this.wage = wage;
    }

    public void displayInfo() {
        
    }

    //Getters for Employee info.
    public String getName() {
        return name;
    }
    public String getPosition() {
        return position;
    }
    public float getWage() {
        return wage;
    }
    
    //Setters for Employee info.
    public void setName(String name) {
        this.name = name;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public void setWage(float wage) {
        this.wage = wage;
    }
}