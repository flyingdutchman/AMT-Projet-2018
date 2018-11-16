package ch.heigvd.amt.mvc.dao;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class BlacklistManager implements BlacklistManagerLocal {
    private final String QUERY_INSERT_USER_INTO_BLACKLIST = "INSERT INTO blacklist (email) " +
                                                            "VALUES (?)";
    private final String QUERY_GET_ALL_BLACKLIST_USERS = "SELECT * FROM blacklist ";
    private final String QUERY_DELETE_USER_FROM_BLACKLIST = "DELETE FROM blacklist WHERE email=?";

    @Resource(lookup = "amt_db")
    private DataSource database;

    @Override
    public boolean isUserInBlacklist(String email) {
        try(Connection connection = database.getConnection()) {
            PreparedStatement getBlacklistPrepStat = connection.prepareStatement(QUERY_GET_ALL_BLACKLIST_USERS);
            ResultSet rs = getBlacklistPrepStat.executeQuery();
            while(rs.next()) {
                if(rs.getString(2).equals(email)) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setUserIntoBlacklist(String email) {
        try(Connection connection = database.getConnection()) {
            PreparedStatement setBlacklistPrepStat = insertDeleteUser(connection,
                                                                    QUERY_INSERT_USER_INTO_BLACKLIST,
                                                                    1,
                                                                    email);
            setBlacklistPrepStat.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeFromBlacklist(String email) {
        try(Connection connection = database.getConnection()) {
            PreparedStatement deleteFromBlacklistPrepStat = insertDeleteUser(connection,
                                                                            QUERY_DELETE_USER_FROM_BLACKLIST,
                                                                            1,
                                                                            email);
            deleteFromBlacklistPrepStat.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private PreparedStatement insertDeleteUser(Connection connection,
                                               String query,
                                               int paramId,
                                               String user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(paramId, user);
        return preparedStatement;
    }
}
