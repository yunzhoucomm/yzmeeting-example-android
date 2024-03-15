package com.yunzhou.yunzhoumeeting;


public class Utils {

    public static int generateRandomUserId(){
        return (int)((Math.random()*9+1)*100);
    }
}
