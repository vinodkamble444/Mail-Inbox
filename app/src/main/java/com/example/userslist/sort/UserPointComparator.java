package com.example.userslist.sort;

import com.example.userslist.data.User;

import java.util.Comparator;

/**
 * Created by vinod on 9/16/2017.
 */
/**
 * Performs sort by ACS/DESC on Points property
 */
public class UserPointComparator implements Comparator<User> {
    private final int sortType;

    public UserPointComparator(int sortType) {
        this.sortType = sortType;
    }

    public int compare(User o1, User o2) {
        int compare;
        if (o1.getPoint() == null)
            compare = (o2.getPoint() == null) ? 0 : sortType;
        else if (o2.getPoint() == null)
            compare = (-1) * sortType;
        else
            compare = o1.getPoint().compareTo(o2.getPoint());
        return compare * sortType;
    }
}
