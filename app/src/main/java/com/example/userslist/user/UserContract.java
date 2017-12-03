package com.example.userslist.user;

import com.example.userslist.BasePresenter;
import com.example.userslist.BaseView;
import com.example.userslist.data.User;

import java.util.List;

/**
 * Created by vinod on 9/15/2017.
 */

public interface UserContract {

    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);

        void showUsers(List<User> Users);

        void showLoadingUsersError();

        boolean isIsActive();

    }

    interface Presenter extends BasePresenter {

        void loadUsers();


    }

}
