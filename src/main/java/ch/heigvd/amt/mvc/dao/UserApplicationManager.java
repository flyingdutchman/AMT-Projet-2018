package ch.heigvd.amt.mvc.dao;

import ch.heigvd.amt.mvc.model.UserApplication;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * This class is used to manage the Users's applications, and to know the information about them, as their name or
 * description.
 * @author Nathan Gonzalez, Jimmy Verdasca, Mika Pagani
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class UserApplicationManager implements UserApplicationManagerLocal {

    /**
     * Queries used to insert, select, update and delete elements of an application in the database
     */
    // Query to inserts information of a new application into the database
    private final String QUERY_INSERT_APP = "INSERT INTO `application` (`name`, `description`,  `api_key`, `api_secret`, `owner`) " +
                                            "VALUES (?, ?, ?, ?, ?)";
    // Query that gets all the information of one application
    private final String QUERY_GET_APP = "SELECT * FROM `application` WHERE `idApplication` = ?";
    // Query that gets all the information of last application inserted
    private final String QUERY_GET_LAST_APP = "SELECT LAST_INSERT_ID()";
    // Query that gets all the information of all applications of a User
    private final String QUERY_GET_APPS_FROM_USER = "SELECT * FROM `application` WHERE `owner` = ?";
    // Query that updates the information of one application
    private final String QUERY_UPDATE_APP= "UPDATE `application` " +
                                           "SET `name`=?, `description`=? " +
                                           "WHERE `idApplication`=?";
    // Query that deletes all the information of one application
    private final String QUERY_DELETE_APP = "DELETE FROM `application` WHERE `idApplication` = ? AND `owner` = ?";

    @Resource(lookup = "AMT_DB")
    private DataSource database;

    @Override
    public UserApplication getApplication(int id) {
        try(Connection connection = database.getConnection()) {
            PreparedStatement getAppPrepStat = connection.prepareStatement(QUERY_GET_APP);
            getAppPrepStat.setLong(1, id);
            ResultSet rs = getAppPrepStat.executeQuery();
            if(rs.next()) {
                return new UserApplication(rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6));
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<UserApplication> getApplicationList(String owner) {
        try(Connection connection = database.getConnection()) {
            PreparedStatement getAppPrepStat = connection.prepareStatement(QUERY_GET_APPS_FROM_USER);
            getAppPrepStat.setString(1, owner);
            ResultSet rs = getAppPrepStat.executeQuery();
            ArrayList<UserApplication> userApplications = new ArrayList<>();
            while(rs.next()) {
                UserApplication ua = new UserApplication(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6)
                );
                userApplications.add(ua);
            }
            return userApplications;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserApplication createApplication(String email, String name, String description) {
        try(Connection connection = database.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(QUERY_INSERT_APP);
            statement.setString(1, name);
            statement.setString(2, description);
            UUID UUID_API_KEY = UUID.randomUUID();
            statement.setString(3, UUID_API_KEY.toString());
            UUID UUID_API_PRIVATE = UUID.randomUUID();
            statement.setString(4, UUID_API_PRIVATE.toString());
            statement.setString(5, email);
            statement.execute();

            PreparedStatement getIdAppPrepStat = connection.prepareStatement(QUERY_GET_LAST_APP);
            ResultSet rs = getIdAppPrepStat.executeQuery();
            if(rs.next()) {
                int idApp = rs.getInt(1);
                connection.close();
                return getApplication(idApp);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateApplication(int id, String name, String description) {
        try(Connection connection = database.getConnection()) {
            PreparedStatement updateStatement = connection.prepareStatement(QUERY_UPDATE_APP);
            updateStatement.setString(1, name);
            updateStatement.setString(2, description);
            updateStatement.setInt(3, id);
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteApplication(int id, String owner) {
        try(Connection connection = database.getConnection()) {
            PreparedStatement statement2 = connection.prepareStatement(QUERY_DELETE_APP);
            statement2.setLong(1, id);
            statement2.setString(2, owner);
            statement2.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
