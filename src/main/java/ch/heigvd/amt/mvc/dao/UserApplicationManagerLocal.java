package ch.heigvd.amt.mvc.dao;

import ch.heigvd.amt.mvc.model.UserApplication;

import javax.ejb.Local;
import java.util.ArrayList;
import java.util.Map;

@Local
public interface UserApplicationManagerLocal {
    public UserApplication getApplication(int id);
    public ArrayList<UserApplication> getApplicationList(String owner);
    public UserApplication createApplication(String email, String name, String description);
    public void updateApplication(int id, String name, String description);
    public void deleteApplication(int id, String owner);
}
