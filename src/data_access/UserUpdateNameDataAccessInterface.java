package data_access;

import entity.User;

import java.sql.SQLException;

public interface UserUpdateNameDataAccessInterface {
    void updateUserName(User user, String name) throws SQLException;
}
