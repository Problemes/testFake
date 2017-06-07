package com.framework.entity;

import java.io.Serializable;

/**
 * Created by HR on 2017/6/7.
 */
public class Model extends Father implements Serializable {

    public String getVar1() {
        return var1;
    }

    public void setVar1(String var1) {
        this.var1 = var1;
    }

    public String getVar2() {
        return var2;
    }

    public void setVar2(String var2) {
        this.var2 = var2;
    }

    private String var1;
    private String var2;

    public void run(){
        System.out.println("Exec------> run");
    }
    public void run(int a){
        System.out.println("Exec------> run + " + a);
    }
}
