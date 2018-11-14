package ch.heigvd.amt.mvc.model;

import java.util.List;

public class Blacklist {
    private final String QUERY_INSERT_USER_INTO_BLACKLIST = "INSERT INTO blacklist (email) " +
                                                            "VALUES (?)";
    private final String QUERY_DELETE_USER_FROM_BLACKLIST = "DELETE FROM blacklist WHERE email=?";
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
