package ch.heigvd.amt.jdbc.model;

import java.util.List;

public class Blacklist {
    private static List<String> usersList;

    public static List<String> getUsersList() {
        return usersList;
    }

    public static void setUsersList(String email) {
        usersList.add(email);
    }

    public static void removeUser(String email) {
        usersList.remove(email);
    }
}
