package ch.heigvd.amt.jdbc.dao;

import ch.heigvd.amt.jdbc.model.User;
import ch.heigvd.amt.jdbc.model.UserApplication;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class UsersManager implements UsersManagerLocal {
    private final String CHECK_RIGHT = "ADMIN";
    private final String queryInsertUser = "INSERT INTO `user` (`email`, `password`, `lastName`, `firstName`)" +
                                                    " VALUES (?, ?, ?, ?)";
    private final String queryGetInsertedUser = "SELECT `email`, `password`, `firstName`, `lastName`, `right` FROM `user`" +
                                                    "WHERE `right`='DEVELOPER' && `email`=?";

    @Resource(name = "jdbc/AMT_DB")
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
        try(Connection connection = database.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(queryInsertUser);
            statement.setString(1, email);
            statement.setString(2, password);
            statement.setString(3, lastName);
            statement.setString(4, firstName);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try(Connection connection = database.getConnection()) {
            PreparedStatement statement2 = connection.prepareStatement(queryGetInsertedUser);
            statement2.setString(1, email);
            ResultSet rs = statement2.executeQuery();
            rs.next();
            User user = new User(rs.getString(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                getApplicationList(email));
            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
    public void updateAccount(String oldEmail, String email, String password, String lastName, String firstName) {
        User user = getUserByMail(oldEmail);

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
        List<UserApplication> userApplications;
        User user = getUserByMail(email);
        if(user == null) {
            return null;
        }
        userApplications = user.getApplicationList();
        return userApplications;
    }
}
