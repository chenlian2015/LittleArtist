package com.xyre.client.business.index.bean;

/**
 * Created by chenlian on 16/12/1.
 */

public class RegistResponse {

    // 失败信息
    public String msg;
    // 用户数据
    public UserInfo userInfo;
    // 访问结果
    public int code;

    public RegistResponse() {
    }

    public RegistResponse(int code, String msg, UserInfo userInfo) {
        this.code = code;
        this.msg = msg;
        this.userInfo = userInfo;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "RegistResponse{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", userInfo=" + userInfo +
                '}';
    }
}
