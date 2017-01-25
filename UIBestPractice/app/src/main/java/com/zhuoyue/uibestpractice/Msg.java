package com.zhuoyue.uibestpractice;

/**Msg类
 * Created by zhuoyue on 2017/1/25.
 */

public class Msg {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SENT = 1;

    private int type;
    private String content;

    public Msg(String content,int type){
        this.type = type;
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}
