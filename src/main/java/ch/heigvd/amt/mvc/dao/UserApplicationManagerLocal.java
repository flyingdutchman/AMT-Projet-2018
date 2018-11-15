package ch.heigvd.amt.mvc.dao;

import ch.heigvd.amt.mvc.model.UserApplication;

import javax.ejb.Local;
import java.util.Map;

@Local
public interface UserApplicationManagerLocal {
    public UserApplication getApplication(long id);
    public Map<String, UserApplication> getApplicationList(String owner);
    public UserApplication createApplication(String email, String name, String description);
    public void updateApplication(long id, String name, String description);
    public void deleteApplication(long id, String owner);
}
