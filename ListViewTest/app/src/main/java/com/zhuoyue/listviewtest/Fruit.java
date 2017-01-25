package com.zhuoyue.listviewtest;

/**Fruit类
 * Created by zhuoyue on 2017/1/24.
 */

public class Fruit {
    private String name;
    private int picID;

    public Fruit(String name,int picID){
        this.name = name;
        this.picID = picID;
    }

    public int getPicID() {
        return picID;
    }

    public String getName() {
        return name;
    }
}
