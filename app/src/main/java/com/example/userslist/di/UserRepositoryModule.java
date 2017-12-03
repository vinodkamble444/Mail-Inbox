package com.example.userslist.di;

import android.app.Application;
import android.content.Context;

import com.example.userslist.data.source.UsersDataSource;
import com.example.userslist.data.source.UsersRepository;
import com.example.userslist.data.source.local.UserLocalDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vinod on 9/17/2017.
 */
@Module
public class UserRepositoryModule {

    private Context context;

    public UserRepositoryModule(Application app) {
        this.context = app;
    }

    @Provides
    Context providesContext() {
        return context;
    }

    @Provides
    @Singleton
    UserLocalDataSource provideUserLocalDataSource(Context context) {
        return new UserLocalDataSource(context);
    }

    @Provides
    @Singleton
    UsersDataSource provideUserDataSource(Context context) {
        return new UserLocalDataSource(context);
    }

    @Provides
    @Singleton
    UsersRepository provideUsersRepository(UserLocalDataSource userLocalDataSource) {
        return new UsersRepository(userLocalDataSource);
    }

}
