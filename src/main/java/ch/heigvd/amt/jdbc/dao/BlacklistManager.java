package ch.heigvd.amt.jdbc.dao;

import ch.heigvd.amt.jdbc.model.Blacklist;

import javax.ejb.Stateless;
import javax.sql.DataSource;

@Stateless
public class BlacklistManager implements BlacklistManagerLocal {
    private DataSource database;

    @Override
    public boolean isUserInBlacklist(String email) {
        for(int i = 0; i < Blacklist.getUsersList().size(); ++i) {
            if(Blacklist.getUsersList().get(i).contains(email)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setUserIntoBlacklist(String email) {
        Blacklist.setUsersList(email);
    }

    @Override
    public void removeFromBlacklist(String email) {
        Blacklist.removeUser(email);
    }
}
