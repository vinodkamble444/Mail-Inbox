package com.example.userslist.data.source.local;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;

import com.example.userslist.data.User;
import com.example.userslist.data.source.UsersDataSource;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by vinod on 9/16/2017.
 */

public class UserLocalDataSource implements UsersDataSource {
    private final AssetManager mAssetManager;

    @Inject
    public UserLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
        mAssetManager = context.getAssets();
    }

    @Override
    public void getUsers(@NonNull LoadUsersCallback callback) {
        List<User> userList;
        userList = readDataFromJSONFile();
        if (userList.isEmpty()) {
            callback.onDataNotAvailable();
        } else {
            callback.onUsersLoaded(userList);
        }
    }

    private List<User> readDataFromJSONFile() {
        List<User> userList = null;
        try {
            Type REVIEW_TYPE = new TypeToken<List<User>>() {
            }.getType();
            InputStream ims = mAssetManager.open("data.json");
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(ims);
            userList = gson.fromJson(reader, REVIEW_TYPE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

}

