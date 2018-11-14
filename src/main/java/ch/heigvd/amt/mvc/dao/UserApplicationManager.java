package ch.heigvd.amt.mvc.dao;

import ch.heigvd.amt.mvc.model.UserApplication;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Stateless
//@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class UserApplicationManager implements UserApplicationManagerLocal {

    private final String queryInsertApp = "INSERT INTO `application` (`name`, `description`,  `api_key`, `api_secret`)" +
            " VALUES (?, ?, ?, ?)";
    private final String queryGetInsertedApp = "SELECT `idApplication`, `name`, `description`, `api_key`, `api_secret` FROM `application`";

    @Resource(lookup = "AMT_DB")
    private DataSource database;

    @Override
    public UserApplication getApplication(int id) {
        return null;
    }

    @Override
    public UserApplication createApplication(String email, String name, String description) {
        try(Connection connection = database.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(queryInsertApp);
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setString(3, null);
            statement.setString(4, null);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try(Connection connection = database.getConnection()) {
            PreparedStatement statement2 = connection.prepareStatement(queryGetInsertedApp);
            ResultSet rs = statement2.executeQuery();
            rs.next();
            UserApplication userApk = new UserApplication(rs.getInt(1),
                                        rs.getString(2),
                                        rs.getString(3),
                                        rs.getString(4),
                                        rs.getString(5));
            return userApk;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateApplication(long id, String name, String description) {

    }

    @Override
    public void deleteApplication(long id, String apkName) {

    }
}
