package ch.heigvd.amt.mvc.model;

public class User {
    private final String RIGHT;

    private String email;
    private String password;
    private String lastName;
    private String firstName;
    private boolean isBanned = false;

    public User(String email,
                String password,
                String lastName,
                String firstName,
                String right,
                boolean isBanned
    ) {
        this.email = email;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.RIGHT = right;
        this.isBanned = isBanned;
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

    public boolean isBanned() {
        return isBanned;
    }

    public String getRight() {
        return RIGHT;
    }
}
