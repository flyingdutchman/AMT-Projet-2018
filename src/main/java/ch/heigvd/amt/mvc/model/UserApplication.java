package ch.heigvd.amt.mvc.model;

public class UserApplication {
    private final String API_KEY;
    private final String API_PRIVATE;

    private long id;
    private String name;
    private String description;
    private String owner;

    public UserApplication(long id, String name, String description, String apiKey, String apiPrivate, String owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.API_KEY = apiKey;
        this.API_PRIVATE = apiPrivate;
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAPI_KEY() {
        return API_KEY;
    }

    public String getAPI_PRIVATE() {
        return API_PRIVATE;
    }

    public String getOwner() {
        return owner;
    }
}
