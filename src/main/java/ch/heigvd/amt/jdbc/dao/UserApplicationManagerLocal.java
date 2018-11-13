package ch.heigvd.amt.jdbc.dao;

import ch.heigvd.amt.jdbc.model.UserApplication;

import javax.ejb.Local;

@Local
public interface UserApplicationManagerLocal {
    public UserApplication getApplication(int id);
    public UserApplication createApplication(String name, String description);
    public void updateApplication(long id, String name, String description);
    public void deleteApplication(long id, String apkName);
}
