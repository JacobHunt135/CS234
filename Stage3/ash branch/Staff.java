public class Staff {
    
    private int authority;
    private String name;
    private String position;
    private float wage;
    
    // AUTHORITY LEVELS
    /*  0 <- Admin ( admin and manager )
     *  1 <- Employee
     *  2 <- Observer
     */

    public Staff(int authority,String name, String position, float wage){
        this.authority = authority;
        this.name = name;
        this.position = position;
        this.wage = wage;
    }
    
    // authority check
    public Boolean checkAuthority(int authorityRequired){
        return authority <= authorityRequired;
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