package ch.heigvd.amt.mvc.dao;

import ch.heigvd.amt.mvc.model.User;

import javax.ejb.Local;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * This interface is used to manage the Users's accounts, and to know the information about them, as their rights, if
 * they are banned, etc.
 * @author Nathan Gonzalez, Jimmy Verdasca, Mika Pagani
 */
@Local
public interface UsersManagerLocal {

    /**
     * This method is used to know the rights of a selected User. This way we can know if we let the User access to
     * some parameters or not
     * @param user The User we want to know the rights
     * @return True if it's an admin, false if not
     */
    public boolean isAdmin(User user);

    /**
     * This method return all the Users that are in the database, so we can show to an admin which Users are registered
     * in the database
     * @return The list of Users in the database
     */
    public Map<String, User> getAllUsers();

    /**
     * This method is used to return us a User after giving it's mail. This way we can do what we need with the user
     * @param email The mail of the wanted User
     * @return The User we wanted selected by mail, null if we don't find the User
     */
    public User getUserByMail(String email);

    /**
     * This method let us register an account in the server if we want to create applications as the project is for.
     * @param email The email to be registered
     * @param password The password to be registered
     * @param lastName The last name to be registered
     * @param firstName The first name to be registered
     * @return The User after being registered
     */
    public User createAccount(String email, String password, String lastName, String firstName);

    /**
     * This method is used to update a User's account. The account is uploaded only if one of the fields are changed
     * @param oldEmail The account we want to update
     * @param email The new email we want to have
     * @param password The new password we want to have
     * @param lastName The new last name we want to have
     * @param firstName The new first name we want to have
     */
    public void updateAccount(String oldEmail, String email, String password, String lastName, String firstName);

    /**
     * This method allows us to delete a User account
     * @param email The mail of the account we want to delete
     */
    public void deleteUserAccount(String email);

    /**
     * This method allows the Admin to ban a User's account
     * @param email The account we want to ban
     * @param isBanned We set this attribute to true to ban the User
     */
    public void setUserIsBanned(String email, boolean isBanned);

    /**
     * This method lets us to change the rights of an account
     * @param email The mail of the account we want to change the right
     * @param newRights Which right we want to set to th user
     */
    public void setUserRight(String email, String newRights);

    /**
     * This method is used to send an email to an account to reset their password
     * @param mailTo Who's mail we want to send
     * @param subject The subject of the mail
     * @param messageToSend The content of the mail
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    public void sendEmail(String mailTo, String subject, String messageToSend) throws MessagingException, UnsupportedEncodingException;
}
