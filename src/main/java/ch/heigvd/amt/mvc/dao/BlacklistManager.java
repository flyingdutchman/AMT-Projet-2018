package ch.heigvd.amt.mvc.dao;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.sql.DataSource;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class BlacklistManager implements BlacklistManagerLocal {
    private final String QUERY_INSERT_USER_INTO_BLACKLIST = "INSERT INTO blacklist (email) " +
            "VALUES (?)";
    private final String QUERY_GET_ALL_BLACKLIST_USERS = "SELECT * FROM user ";
    private final String QUERY_DELETE_USER_FROM_BLACKLIST = "DELETE FROM blacklist WHERE email=?";

    @Resource(lookup = "AMT_DB")
    private DataSource database;

    @Override
    public boolean isUserInBlacklist(String email) {
        return false;
    }

    @Override
    public void setUserIntoBlacklist(String email) {

    }

    @Override
    public void removeFromBlacklist(String email) {

    }
}
