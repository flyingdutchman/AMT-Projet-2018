package ch.heigvd.amt.jdbc.dao;

import ch.heigvd.amt.jdbc.model.User;
import ch.heigvd.amt.jdbc.model.UserApplication;

import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class UsersManager implements UsersManagerLocal {
    private final String CHECK_RIGHT = "ADMIN";
    private DataSource database;

    @Override
    public boolean isAdmin(User user) {
        return user.getRight().contains(CHECK_RIGHT);

    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
//        users.add();
        return users;
    }

    @Override
    public User createAccount(String email, String password, String lastName, String firstName) {
//        User user = new User(email, password, lastName, firstName);
        return null;
    }

    @Override
    public User getUserByMail(String email) {
        for(int i = 0; i < getUsers().size(); ++i) {
            if(getUsers().get(i).getEmail().contains(email)){
                return getUsers().get(i);
            }
        }
        return null;
    }

    @Override
    public void updateAccount(String email, String password, String lastName, String firstName) {
        User user = getUserByMail(email);

        if(!email.isEmpty()) {
            user.setEmail(email);
        }

        if(!password.isEmpty()) {
            user.setPassword(password);
        }

        if(!email.isEmpty()) {
            user.setLastName(lastName);
        }

        if(!email.isEmpty()) {
            user.setFirstName(firstName);
        }
    }

    @Override
    public void deleteUserAccount(String email) {
        getUsers().remove(getUserByMail(email));
    }

    @Override
    public List<UserApplication> getApplicationList(String email) {
        List<UserApplication> userApplications = new ArrayList<>();
        return userApplications;
    }
}
