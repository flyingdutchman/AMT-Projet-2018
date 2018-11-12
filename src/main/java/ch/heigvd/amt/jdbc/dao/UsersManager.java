package ch.heigvd.amt.jdbc.dao;

import ch.heigvd.amt.jdbc.model.User;
import ch.heigvd.amt.jdbc.model.UserApplication;

import java.util.List;

public interface UsersManager {
    public List<User> findUsers();
    public List<UserApplication> findApplications();
}
