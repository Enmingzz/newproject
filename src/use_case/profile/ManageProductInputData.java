package use_case.profile;

import entity.user.User;

public class ManageProductInputData {

    private User user;

    public ManageProductInputData(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
