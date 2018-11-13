package ch.heigvd.amt.mvc.model;

import java.util.List;

public class User {
    private final String RIGHT;

    private String email;
    private String password;
    private String lastName;
    private String firstName;
    private List<UserApplication> applicationList;
    private boolean inBlacklist = false;

    public User(String email,
                String password,
                String lastName,
                String firstName,
                String right,
                List<UserApplication> applicationList) {
        this.email = email;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.applicationList = applicationList;
        this.RIGHT = right;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public boolean isInBlacklist() {
        return inBlacklist;
    }

    public List<UserApplication> getApplicationList() {
        return applicationList;
    }

    public void setApplicationList(UserApplication apk) {
        this.applicationList.add(apk);
    }

    public String getRight() {
        return RIGHT;
    }
}
