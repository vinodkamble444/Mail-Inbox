package com.example.userslist.di;

import android.content.Context;

import com.example.userslist.user.UserContract;
import com.example.userslist.user.UsersActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vinod on 9/17/2017.
 */
@Module
public class UsersActivityModule {
    private UsersActivity mActivity;

    public UsersActivityModule(UsersActivity activity) {
        mActivity = activity;
    }

    @Provides
    Context provideContext() {
        return mActivity;
    }

    @Provides
    UsersActivity provideUsersActivity() {
        return this.mActivity;
    }

    @Provides
    UserContract.View provideUserContract() {
        return this.mActivity;
    }
}
