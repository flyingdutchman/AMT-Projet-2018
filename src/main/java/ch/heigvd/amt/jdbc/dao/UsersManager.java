package ch.heigvd.amt.jdbc.dao;

import ch.heigvd.amt.jdbc.model.User;

import java.util.List;

public interface UsersManager {
    public List<User> findUsers();

}
