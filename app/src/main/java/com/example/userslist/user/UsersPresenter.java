package com.example.userslist.user;

import com.example.userslist.data.User;
import com.example.userslist.data.source.UsersDataSource;
import com.example.userslist.data.source.UsersRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by vinod on 9/16/2017.
 */


/**
 * Presenter retrieves data from the model and returns it formatted to the view
 */
public class UsersPresenter implements UserContract.Presenter {
    UsersRepository mUsersRepository;
    UserContract.View mUserView;

    @Inject
    public UsersPresenter(UsersRepository usersRepository, UserContract.View userView) {
        mUsersRepository = usersRepository;
        mUserView = userView;
    }

    @Override
    public void loadUsers() {
        mUserView.setLoadingIndicator(true);
        mUsersRepository.getUsers(new UsersDataSource.LoadUsersCallback() {
            @Override
            public void onUsersLoaded(List<User> users) {
                if (!users.isEmpty())
                    mUserView.setLoadingIndicator(false);
                mUserView.showUsers(users);
            }

            @Override
            public void onDataNotAvailable() {
                mUserView.setLoadingIndicator(false);
            }
        });
    }

    @Override
    public void start() {

    }
}
