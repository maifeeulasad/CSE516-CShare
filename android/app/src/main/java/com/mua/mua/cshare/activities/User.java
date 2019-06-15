package com.mua.mua.cshare.activities;

import java.util.Vector;

public class User {

    /*


    Maifee Ul Asad - works here


     */




    private String userID;//mac-address of the first device + random string of length 3 + 00 or might be increased + random string of length 2
    private String displayName;//can be changed once in a month to limit data usage, 01.xx.xxxx no need to store previous change date
    private int reputation;
    private UserType userType;
    private Vector<Integer> ratings;


}
