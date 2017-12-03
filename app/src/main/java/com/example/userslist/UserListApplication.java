package com.example.userslist;

import android.app.Application;
import android.content.Context;

import com.example.userslist.di.DaggerUserComponent;
import com.example.userslist.di.UserComponent;
import com.example.userslist.di.UserRepositoryModule;

/**
 * Created by vinod on 9/17/2017.
 */

public class UserListApplication extends Application {
    UserComponent mUserComponent;

    public static UserListApplication get(Context context) {
        return (UserListApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mUserComponent = createUserComponent();
        mUserComponent.inject(this);
    }

    private UserComponent createUserComponent() {
        return DaggerUserComponent.builder().userRepositoryModule(new UserRepositoryModule(this)).build();
    }

    public UserComponent getDaggerComponent() {
        return mUserComponent == null ? createUserComponent() : mUserComponent;
    }
}
