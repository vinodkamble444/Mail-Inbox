package com.example.userslist.data.source.local;

import android.content.Context;

import com.example.userslist.data.User;
import com.example.userslist.data.source.UsersDataSource;
import com.example.userslist.data.source.UsersRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by vinod on 9/17/2017.
 */
public class UserLocalDataSourceTest {
    private static List<User> USERS =new ArrayList();

    private UsersRepository mUsersRepository;

    @Mock
    private UsersDataSource mUsersDataSource;

    @Mock
    private UserLocalDataSource mUserLocalDataSource;

    @Mock
    private Context mContext;

    @Mock
    private UsersDataSource.LoadUsersCallback mLoadTasksCallback;

    @Captor
    private ArgumentCaptor<UsersDataSource.LoadUsersCallback> mUsersCallbackCaptor;


    @Before
    public void setupTasksRepository() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test

        //USERS.add(new User("abc","abc@gmail.com"));
        //USERS.add(new User("xyz","xyz@gmail.com"));
    }

    @Test
    public void getUsers_DataAvailble() {

        mUserLocalDataSource.getUsers(mLoadTasksCallback);

        setUsersAvailable(mUserLocalDataSource, USERS);

        verify(mUserLocalDataSource).getUsers(any(UsersDataSource.LoadUsersCallback.class));
    }
    @Test
    public void getUsers_NoDataAvailble() {

        mUserLocalDataSource.getUsers(mLoadTasksCallback);

        setUsersNotAvailable(mUserLocalDataSource);

        verify(mUserLocalDataSource).getUsers(any(UsersDataSource.LoadUsersCallback.class));
    }

    private void setUsersNotAvailable(UsersDataSource dataSource) {
        verify(dataSource).getUsers(mUsersCallbackCaptor.capture());
        mUsersCallbackCaptor.getValue().onDataNotAvailable();
    }

    private void setUsersAvailable(UsersDataSource dataSource, List<User> users) {
        verify(dataSource).getUsers(mUsersCallbackCaptor.capture());
        mUsersCallbackCaptor.getValue().onUsersLoaded(users);
    }

}