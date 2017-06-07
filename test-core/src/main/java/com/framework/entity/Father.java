package com.framework.entity;

/**
 * Created by HR on 2017/6/7.
 */
public class Father {
    public String getFvar1() {
        return Fvar1;
    }

    public void setFvar1(String fvar1) {
        Fvar1 = fvar1;
    }

    public String getFvar2() {
        return Fvar2;
    }

    public void setFvar2(String fvar2) {
        Fvar2 = fvar2;
    }

    public String getFvarPub() {
        return FvarPub;
    }

    public void setFvarPub(String fvarPub) {
        FvarPub = fvarPub;
    }

    private String Fvar1;
    private String Fvar2;
    public  String FvarPub;

    public void run(){
        System.out.println("Father Run-----------");
    }

}
