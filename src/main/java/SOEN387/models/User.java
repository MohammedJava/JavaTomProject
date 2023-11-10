package SOEN387.models;

public class User {
	
    private int id;
    private String username;
    private String email;
    private String password;
    private boolean isStaff; 

    public User(int id, String username, String email, String password, boolean isStaff) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isStaff = isStaff;
    }

    // Getters and setters for attributes

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStaff() {
        return isStaff;
    }

    public void setIsStaff(boolean isStaff) {
        this.isStaff = isStaff;
    }

    // Additional methods for user-related functionality
}

