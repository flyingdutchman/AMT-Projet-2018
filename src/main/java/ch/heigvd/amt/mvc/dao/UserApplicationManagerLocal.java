package ch.heigvd.amt.mvc.dao;

import ch.heigvd.amt.mvc.model.UserApplication;

import javax.ejb.Local;
import java.util.ArrayList;


/**
 * This interface is used to manage the Users's applications, and to know the information about them, as their name or
 * description.
 * @author Nathan Gonzalez, Jimmy Verdasca, Mika Pagani
 */
@Local
public interface UserApplicationManagerLocal {

    /**
     * This method returns an application using its id
     * @param id The id of the application
     * @return The application wanted, null if we can't find it
     */
    public UserApplication getApplication(int id);

    /**
     * This method return the applications of a user
     * @param owner The user we want the applications
     * @return The list of applications
     */
    public ArrayList<UserApplication> getApplicationList(String owner);

    /**
     * This method allows to create an application to the user
     * @param email The user that wants to create a new account
     * @param name The name given to the application
     * @param description The description about the application
     * @return The application we created
     */
    public UserApplication createApplication(String email, String name, String description);

    /**
     * This method is used to update the information about an application
     * @param id The id of the application we want to update
     * @param name The new name of the application
     * @param description The new description of the application
     */
    public void updateApplication(int id, String name, String description);

    /**
     * This method is used to delete an application
     * @param id The id of the application
     * @param owner The owner of the application
     */
    public void deleteApplication(int id, String owner);
}
