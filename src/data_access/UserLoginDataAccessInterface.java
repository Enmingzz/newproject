package data_access;

import entity.User;

public interface UserLoginDataAccessInterface {
    boolean existsByName(String identifier);

    void get(User user);
}
