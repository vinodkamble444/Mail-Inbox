package com.example.userslist.di;

import com.example.userslist.user.UsersActivity;

import dagger.Component;

/**
 * Created by vinod on 9/17/2017.
 */
@Component(dependencies = UserComponent.class, modules = UsersActivityModule.class)
public interface ActivityComponent {

    void inject(UsersActivity usersActivity);

}
