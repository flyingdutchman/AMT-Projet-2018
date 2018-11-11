package ch.heigvd.amt.jdbc.dao;

import ch.heigvd.amt.jdbc.model.User;

public interface UsersManager {
    public List<User> findUsers();

}
