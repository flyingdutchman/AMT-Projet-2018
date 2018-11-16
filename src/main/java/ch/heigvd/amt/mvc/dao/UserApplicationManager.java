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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class UserApplicationManager implements UserApplicationManagerLocal {

    private final String QUERY_INSERT_APP = "INSERT INTO application (name, description,  api_key, api_secret, owner) " +
                                            "VALUES (?, ?, ?, ?, ?)";
    private final String QUERY_GET_APP = "SELECT idApplication, name, description, api_key, api_secret FROM application";
    private final String QUERY_GET_LAST_APP = "SELECT LAST_INSERT_ID()";
    private final String QUERY_GET_APPS_FROM_USER = "SELECT idApplication, name, description, api_key, api_secret FROM application WHERE email = ?";
    private final String QUERY_UPDATE_APP= "UPDATE application " +
                                           "SET name=?, description=? " +
                                           "WHERE id=?";
    private final String QUERY_DELETE_APP = "DELETE FROM application WHERE idApplication = ? AND owner = ?";

    @Resource(lookup = "amt_db")
    private DataSource database;

    @Override
    public UserApplication getApplication(long id) {
        try(Connection connection = database.getConnection()) {
            PreparedStatement getAppPrepStat = connection.prepareStatement(QUERY_GET_APP);
            ResultSet rs = getAppPrepStat.executeQuery();
            if(rs.next()) {
                if(rs.getLong(1) == id) {
                    return new UserApplication(rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6));
                }
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, UserApplication> getApplicationList(String owner) {
        try(Connection connection = database.getConnection()) {
            PreparedStatement getAppPrepStat = connection.prepareStatement(QUERY_GET_APPS_FROM_USER);
            ResultSet rs = getAppPrepStat.executeQuery();
            Map<String, UserApplication> userApplications = new HashMap<>();
            while(rs.next()) {
                userApplications.put(owner, getApplication(rs.getLong(1)));
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
                long idApp = rs.getLong(1);
                connection.close();
                return getApplication(idApp);
            }
            return null;
//            return new UserApplication(rs.getInt(1),
//                                        rs.getString(2),
//                                        rs.getString(3),
//                                        rs.getString(4),
//                                        rs.getString(5),
//                                        rs.getString(6));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateApplication(long id, String name, String description) {
        UserApplication userApp = getApplication(id);
        String oldName = userApp.getName();
        String oldDescription = userApp.getDescription();
        System.out.println("Before update");
        System.out.println("Id: " + id + ", Old values: oldName - " + oldName + ", oldDescription - " + oldDescription);
        System.out.println("Id: " + id + ", New values: name - " + oldName + ", description - " + oldDescription);
        try(Connection connection = database.getConnection()) {
            System.out.println("Connection...");
            PreparedStatement updateStatement = connection.prepareStatement(QUERY_UPDATE_APP);
            System.out.println("Connected");
            int count = 0;
            checkOldValues(oldName, name, updateStatement, ++count);
            checkOldValues(oldDescription, description, updateStatement, ++count);
            updateStatement.setLong(++count, id);
            System.out.println("Update set");

            updateStatement.executeUpdate();
            System.out.println("After Update");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void checkOldValues(String oldValue, String newValue, PreparedStatement updatePrepStat, int paramId) throws SQLException {
        if(!newValue.isEmpty() && !oldValue.equals(newValue)) {
            System.out.println("Changed: " + newValue);
            updatePrepStat.setString(paramId, newValue);
        }else {
            System.out.println("Same as before: " + oldValue);
            updatePrepStat.setString(paramId, oldValue);
        }
    }

    @Override
    public void deleteApplication(long id, String owner) {
        try(Connection connection = database.getConnection()) {
            PreparedStatement statement2 = connection.prepareStatement(QUERY_DELETE_APP);
            statement2.setLong(1, id);
            statement2.setString(1, owner);
            statement2.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
