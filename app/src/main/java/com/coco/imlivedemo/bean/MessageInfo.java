package com.coco.imlivedemo.bean;

/**
 * Created by ydx on 18-5-31.
 */

public class MessageInfo {
    private String msg_other;

    public MessageInfo(String msg_other, String msg_self) {
        this.msg_other = msg_other;
        this.msg_self = msg_self;
    }

    private String msg_self;

    public String getMsg_other() {
        return msg_other;
    }

    public void setMsg_other(String msg_other) {
        this.msg_other = msg_other;
    }

    public String getMsg_self() {
        return msg_self;
    }

    public void setMsg_self(String msg_self) {
        this.msg_self = msg_self;
    }



}
