package data_access.interfaces;

import entity.user.User;

import java.sql.SQLException;

public interface UserUpdateNameDataAccessInterface {
    void updateUserName(User user, String name) throws SQLException;
}
