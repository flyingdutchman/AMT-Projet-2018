package ch.heigvd.amt.jdbc.dao;

import ch.heigvd.amt.jdbc.model.UserApplication;

import javax.ejb.Stateless;
import javax.sql.DataSource;

@Stateless
public class UserApplicationManager implements UserApplicationManagerLocal {
    private DataSource database;

    @Override
    public UserApplication getApplication(String name) {
        return null;
    }

    @Override
    public void createApplication(String name, String description) {

    }

    @Override
    public void updateApplication(String name, String description) {

    }

    @Override
    public void deleteApplication(String apkName) {

    }
}
