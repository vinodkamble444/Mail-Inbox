package com.example.userslist.sort;


import com.example.userslist.data.User;

import java.util.Comparator;

/**
 * Created by vinod on 9/16/2017.
 */
/**
 * Performs sort by ACS/DESC on Name property
 */
public class UserNameComparator implements Comparator<User> {
    private final int sortType;

    public UserNameComparator(int sortType) {
        this.sortType = sortType;
    }

    public int compare(User o1, User o2) {
        int compare;
        if (o1.getName() == null)
            compare = (o2.getName() == null) ? 0 : sortType;
        else if (o2.getName() == null)
            compare = (-1) * sortType;
        else
            compare = o1.getName().compareTo(o2.getName());

        return compare * sortType;
    }

}
