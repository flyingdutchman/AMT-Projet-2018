package ch.heigvd.amt.mvc.model;

/**
 * This class is contains all the information a User can have.
 * @author Nathan Gonzalez, Jimmy Verdasca, Mika Pagani
 */
public class User {
    private String email;               // The email of a user's account
    private String password;            // The password of a user's account
    private String lastName;            // The last name of a user's account
    private String firstName;           // The first name of a user's account
    private String right;               // The right of a user's account
    private boolean isBanned = false;   // To know if the user is banned or not

    /**
     * Constructor of User's class
     * @param email User's email
     * @param password User's password
     * @param lastName User's last name
     * @param firstName User's first name
     * @param right User's right
     * @param isBanned If user is banned or not
     */
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
        this.right = right;
        this.isBanned = isBanned;
    }

    /**
     * Email's getter method
     * @return User's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Email's setter method
     * @param email The new user's email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Password's getter method
     * @return User's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Password's setter method
     * @param password The new user's password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Last name's getter method
     * @return User's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Last name's setter method
     * @param lastName The new user's last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * First name's getter method
     * @return User's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * First name's setter method
     * @param firstName The new user's first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * If user is banned getter method
     * @return True if is banned, false otherwise
     */
    public boolean isBanned() {
        return isBanned;
    }

    /**
     * Right's getter method
     * @return User's right
     */
    public String getRight() {
        return right;
    }
}
