package ch.heigvd.amt.jdbc.dao;

import ch.heigvd.amt.jdbc.model.UserApplication;

import javax.ejb.Local;

@Local
public interface UserApplicationManagerLocal {
    public UserApplication getApplication(String name);
    public void createApplication(String name, String description);
    public void updateApplication(String name, String description);
    public void deleteApplication(String apkName);
}
