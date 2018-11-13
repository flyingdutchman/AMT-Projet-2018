package ch.heigvd.amt.jdbc.dao;

import ch.heigvd.amt.jdbc.model.UserApplication;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

@Stateless
public class UserApplicationManager implements UserApplicationManagerLocal {
    @Resource(name = "jdbc/AMT_DB")
    private DataSource database;

    @Override
    public UserApplication getApplication(String name) {
        return null;
    }

    @Override
    public void createApplication(String name, String description) {

    }

    @Override
    public void updateApplication(long id, String name, String description) {

    }

    @Override
    public void deleteApplication(long id, String apkName) {

    }
}
