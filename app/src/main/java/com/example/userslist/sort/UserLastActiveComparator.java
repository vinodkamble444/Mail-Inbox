package com.example.userslist.sort;


import com.example.userslist.data.User;

import java.util.Comparator;

/**
 * Created by vinod on 9/15/2017.
 */
/**
 * Performs sort by ACS/DESC on lastActive property
 */
public class UserLastActiveComparator implements Comparator<User> {
    private final int sortType;

    public UserLastActiveComparator(int sortType) {
        this.sortType = sortType;
    }

    public int compare(User o1, User o2) {
        int compare;
        if (o1.getLastActive() == null)
            compare = (o2.getLastActive() == null) ? 0 : sortType;
        else if (o2.getLastActive() == null)
            compare = (-1) * sortType;
        else
            compare = o1.getLastActive().compareTo(o2.getLastActive());

        return compare * sortType;
    }

}
