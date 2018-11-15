package ch.heigvd.amt.mvc.model;

import java.util.List;

public class Blacklist {
    private List<String> usersList;

    public List<String> getUsersList() {
        return usersList;
    }

    public void setUsersList(String email) {
        usersList.add(email);
    }

    public void removeUser(String email) {
        usersList.remove(email);
    }
}
