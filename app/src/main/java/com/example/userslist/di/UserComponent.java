package com.example.userslist.di;

import com.example.userslist.UserListApplication;

import dagger.Component;

/**
 * Created by vinod on 9/17/2017.
 */
@Component(modules = {UserRepositoryModule.class})
public interface UserComponent {

    void inject(UserListApplication userListApplication);

}
