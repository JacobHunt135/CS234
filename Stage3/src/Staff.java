public class Staff {
    
    private int authority;
    private String name;
    private String position;
    private float wage;
    
    public Staff(int authority, String name, String position, float wage){
        this.authority = authority;
        this.name = name;
        this.position = position;
        this.wage = wage;
    }
    
    //Getters for staff info.
    public int getAuth() {
        return authority;
    }
    
    public String getName() {
        return name;
    }
    
    public String getPosition() {
        return position;
    }
    
    public float getWage() {
        return wage;
    }
    
    //Setters for staff info.
    public void setAuth(int authority) {
        this.authority = authority;
    }
    
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