package ch.heigvd.amt.mvc.model;

/**
 * This class is contains all the information an Application can have.
 * @author Nathan Gonzalez, Jimmy Verdasca, Mika Pagani
 */
public class UserApplication {
    // Api keys of the application
    private final String API_KEY;
    private final String API_PRIVATE;

    private int id;             // Application's ID
    private String name;        // Application's name
    private String description; // Application's description
    private String owner;       // Application's owner

    /**
     * UserApplication's constructor
     * @param id Application's ID
     * @param name Application's name
     * @param description Application's description
     * @param apiKey Application's api key
     * @param apiPrivate Application's api private
     * @param owner Application's owner
     */
    public UserApplication(int id,
                           String name,
                           String description,
                           String apiKey,
                           String apiPrivate,
                           String owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.API_KEY = apiKey;
        this.API_PRIVATE = apiPrivate;
        this.owner = owner;
    }

    /**
     * ID's getter method
     * @return Application's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Name's getter method
     * @return Application's name
     */
    public String getName() {
        return name;
    }

    /**
     * Name's setter method
     * @param name The new application's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Description's getter method
     * @return Application's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Description's setter method
     * @param description The new application's description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * API_KEY's getter method
     * @return Application's API_KEY
     */
    public String getAPI_KEY() {
        return API_KEY;
    }

    /**
     * API_PRIVATE's getter method
     * @return Application's API_PRIVATE
     */
    public String getAPI_PRIVATE() {
        return API_PRIVATE;
    }

    /**
     * Owner's getter method
     * @return Application's owner
     */
    public String getOwner() {
        return owner;
    }
}
