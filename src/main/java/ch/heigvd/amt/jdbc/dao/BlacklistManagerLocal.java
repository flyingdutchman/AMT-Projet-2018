package ch.heigvd.amt.jdbc.dao;

import javax.ejb.Local;

@Local
public interface BlacklistManagerLocal {
    public boolean isUserInBlacklist(String email);
    public void setUserIntoBlacklist(String email);
    public void removeFromBlacklist(String email);
}
