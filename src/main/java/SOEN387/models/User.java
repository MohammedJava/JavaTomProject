package SOEN387.models;

public class User {

    private int id;
    private String passcode;
    private boolean isStaff;

    public User(int id, String passcode, boolean isStaff) {
        this.id = id;
        this.passcode = passcode;
        this.isStaff = isStaff;
    }

    // Getters and setters for attributes

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public boolean isStaff() {
        return isStaff;
    }

    public void setIsStaff(boolean isStaff) {
        this.isStaff = isStaff;
    }

}
