package entity;

import java.util.ArrayList;

public class CommonUserFactory implements UserFactory{
    public User createUser(String name, String Password, String email, float userRating, String studentNumber){
        return new CommonUser(name, Password, email, userRating, studentNumber);
    }
}
