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
    public User createAccount(String email, String password, String lastName, String firstName);
    public void deleteUserAccount(String email);
    public void updateAccount(String oldEmail, String email, String password, String lastName, String firstName, boolean isBanned);
    public void setUserIsBanned(String email, boolean isBanned);
    public void sendEmail(String mailTo, String subject, String messageToSend) throws MessagingException, UnsupportedEncodingException;
    public void setUserRight(String email, String newRights);
}
