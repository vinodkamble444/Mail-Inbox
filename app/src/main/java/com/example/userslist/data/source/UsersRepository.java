package com.example.userslist.data.source;

import android.support.annotation.NonNull;

import com.example.userslist.data.source.local.UserLocalDataSource;

import javax.inject.Inject;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by vinod on 9/16/2017.
 */

public class UsersRepository implements UsersDataSource {
    private final UsersDataSource mUsersLocalDataSource;

    @Inject
    public UsersRepository(@NonNull UserLocalDataSource usersLocalDataSource) {
        mUsersLocalDataSource = checkNotNull(usersLocalDataSource);
    }

    @Override
    public void getUsers(@NonNull LoadUsersCallback callback) {
        mUsersLocalDataSource.getUsers(callback);
    }

}


