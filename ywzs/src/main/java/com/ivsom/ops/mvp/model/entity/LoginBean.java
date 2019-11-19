package com.ivsom.ops.mvp.model.entity;

/**
 * Create by HanN on 2019/11/1
 * 注释: 登录成功
 */
public class LoginBean {
    private int status;
    private String sid;
    private String uid;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
