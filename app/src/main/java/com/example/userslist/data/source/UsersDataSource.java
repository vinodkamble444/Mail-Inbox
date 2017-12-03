package com.example.userslist.data.source;

import android.support.annotation.NonNull;

import com.example.userslist.data.User;

import java.util.List;

/**
 * Created by vinod on 9/16/2017.
 */

public interface UsersDataSource {

    interface LoadUsersCallback {

        void onUsersLoaded(List<User> users);

        void onDataNotAvailable();
    }

    void getUsers(@NonNull LoadUsersCallback callback);


}
