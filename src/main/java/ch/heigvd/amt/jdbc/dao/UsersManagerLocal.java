package ch.heigvd.amt.jdbc.dao;

import ch.heigvd.amt.jdbc.model.User;
import ch.heigvd.amt.jdbc.model.UserApplication;

import javax.ejb.Local;
import java.util.List;

@Local
public interface UsersManagerLocal {
    public boolean isAdmin(User user);
    public List<User> getUsers();
    public User createAccount(String email, String password, String lastName, String firstName);
    public User getUserByMail(String email);
    public void updateAccount(String oldEmail, String email, String password, String lastName, String firstName);
    public void deleteUserAccount(String email);
    public List<UserApplication> getApplicationList(String email);
}
