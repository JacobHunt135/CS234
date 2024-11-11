public class Profile {

    private String username;
    private String password;
    private int authority;

    //Constructor for a new profile.
    public Profile(String username, String password, int authority) {
        this.username = username;
        this.password = password;
        this.authority = authority;
    }

    //Checks if the login information is correct for both username and password.
    public boolean loginProfile(String username, String password) {
        if (username.equals(this.username) && password.equals(this.password)) {
            return true;
        } else {
            return false;
        }
    }
        
    //Authority check.
    public boolean checkAuthority(int authorityRequired){
        return authority <= authorityRequired;
    }

    //Getter methods.
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public int getAuthority() {
        return authority;
    }

    //Setter methods.
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setAuthority(int authority) {
        this.authority = authority;
    }
}