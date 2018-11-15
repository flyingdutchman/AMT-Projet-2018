package ch.heigvd.amt.mvc.dao;

import ch.heigvd.amt.mvc.model.User;
import ch.heigvd.amt.mvc.model.UserApplication;

import javax.ejb.Local;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@Local
public interface UsersManagerLocal {

    public boolean isAdmin(User user);
    public Map<String, User> getAllUsers();
    public User getUserByMail(String email);

    /**
     *
     * @param email
     * @param password
     * @param lastName
     * @param firstName
     * @return
     * @throws RuntimeException When the email already exists in the database
     * TODO catch error into Servlet to inform user that that mail is already used
     */
    public User createAccount(String email, String password, String lastName, String firstName);
    public void updateAccount(String oldEmail, String email, String password, String lastName, String firstName);
    public void deleteUserAccount(String email);
    public Map<String, UserApplication> getApplicationList(String email);
    public void sendEmail(String mailTo, String messageToSend) throws MessagingException, UnsupportedEncodingException;
}
