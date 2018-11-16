package ch.heigvd.amt.mvc.dao;

import ch.heigvd.amt.mvc.model.User;
import ch.heigvd.amt.mvc.model.UserApplication;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class UsersManager implements UsersManagerLocal {
    private final String CHECK_RIGHT = "ADMIN";

    private final String QUERY_GET_ALL_USERS = "SELECT * FROM user ";
    private final String QUERY_GET_USER_BY_MAIL = QUERY_GET_ALL_USERS +
                                                  "WHERE `email`=?";
    private final String QUERY_INSERT_USER = "INSERT INTO user (`email`, `password`, `lastName`, `firstName`) " +
                                             "VALUES (?, ?, ?, ?)";
    private final String QUERY_UPDATE_USER = "UPDATE user " +
                                             "SET `email`=?, password=?, lastName=?, firstName=? " +
                                             "WHERE `email`=?";
    private final String QUERY_DELETE_USER = "DELETE FROM user WHERE email=?";
    private final String QUERY_SET_ISBANNED = "UPDATE `user` SET `banned` = ? WHERE `email` = ?";
    private final String QUERY_SET_RIGHT = "UPDATE `user` SET `right` = ? WHERE `email` = ?";

    @Resource(lookup = "amt_db")
    private DataSource database;

    @Resource(name = "java/mail/swhp")
    Session mailSession;

    @Override
    public boolean isAdmin(User user) {
        return user.getRight().equals(CHECK_RIGHT);
    }

    @Override
    public void sendEmail(String mailTo, String subject, String messageToSend) throws MessagingException, UnsupportedEncodingException {
        Message message = new MimeMessage(mailSession);
        message.setSubject(subject);
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
        message.setFrom(new InternetAddress("adamtprojectmin@gmail.com"));
        //message.setContent(messageToSend); //uncomment to send html
        message.setText(messageToSend); //uncomment to send plaintext
        Transport.send(message);
    }

    @Override
    public Map<String, User> getAllUsers() {
        Map<String, User> users = new HashMap<>();
        try(Connection connection = database.getConnection()) {
            PreparedStatement getPrepState = connection.prepareStatement(QUERY_GET_ALL_USERS);
            ResultSet rs = getPrepState.executeQuery();
            while(rs.next()) {
                users.put(rs.getString("email"),
                    new User(
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("lastName"),
                        rs.getString("firstName"),
                        rs.getString("right"),
                        rs.getBoolean("banned")
                ));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserByMail(String email) {
        try(Connection connection = database.getConnection()) {
            PreparedStatement statement2 = connection.prepareStatement(QUERY_GET_USER_BY_MAIL);
            statement2.setString(1, email);
            ResultSet rs = statement2.executeQuery();
            if(rs.next()) {
                return new User(
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("lastName"),
                    rs.getString("firstName"),
                    rs.getString("right"),
                    rs.getBoolean("banned")
                );
            }else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public User createAccount(String email, String password, String lastName, String firstName) throws RuntimeException {
        try(Connection connection = database.getConnection()) {
            PreparedStatement insertPrepState = connection.prepareStatement(QUERY_INSERT_USER);
            insertPrepState.setString(1, email);
            insertPrepState.setString(2, password);
            insertPrepState.setString(3, lastName);
            insertPrepState.setString(4, firstName);
            insertPrepState.execute();

            return getUserByMail(email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkOldValues(String oldValue, String newValue, PreparedStatement updatePrepStat, int i) throws SQLException {
        if(!newValue.isEmpty() && !oldValue.equals(newValue)) {
            updatePrepStat.setString(i, newValue);
        }else {
            updatePrepStat.setString(i, oldValue);
        }
    }

    @Override
    public void deleteUserAccount(String email) {
        try(Connection connection = database.getConnection()) {
            PreparedStatement statement2 = connection.prepareStatement(QUERY_DELETE_USER);
            statement2.setString(1, email);
            statement2.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateAccount(String oldEmail, String email, String password, String lastName, String firstName, boolean isBanned) {
        User user = getUserByMail(oldEmail);
        String oldPassword = user.getPassword();
        String oldLastName = user.getLastName();
        String oldFirstName = user.getFirstName();
        try(Connection connection = database.getConnection()) {
            PreparedStatement updateStatement = connection.prepareStatement(QUERY_UPDATE_USER);
            int count = 0;
            checkOldValues(oldEmail, email, updateStatement, ++count);
            checkOldValues(oldPassword, password, updateStatement, ++count);
            checkOldValues(oldLastName, lastName, updateStatement, ++count);
            checkOldValues(oldFirstName, firstName, updateStatement, ++count);
            updateStatement.setString(++count, oldEmail);

            updateStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setUserIsBanned(String email, boolean isBanned) {
        try(Connection connection = database.getConnection()) {
            PreparedStatement bannedStatement = connection.prepareStatement(QUERY_SET_ISBANNED);
            bannedStatement.setBoolean(1, isBanned);
            bannedStatement.setString(2, email);
            bannedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setUserRight(String email, String newRights) {
        System.out.println(newRights);
        System.out.println(email);
        try(Connection connection = database.getConnection()) {
            PreparedStatement bannedStatement = connection.prepareStatement(QUERY_SET_RIGHT);
            bannedStatement.setString(1, newRights);
            bannedStatement.setString(2, email);
            bannedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
