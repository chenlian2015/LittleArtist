package com.xyre.client.business.index.bean;

/**
 * Created by chenlian on 16/12/1.
 */

public class UserInfo {

    // 主键
    public String id;
    // 电话
    public String phone;
    // openid
    public String openid;
    // 用户名
    public String username;
    // 用户头像
    public String imageid;
    // 老用户ID
    public String uuid;
    // idCode
    public String idcode;
    // idName
    public String idname;
    // 生日
    public String birthday;
    // 性别
    public String gender;
    // 状态
    public String state;
    // 上次登录时间
    public String lastLoginTime;
    // 创基时间
    public String createTime;


    public UserInfo() {
    }

    public UserInfo(String id, String phone, String openid, String username, String imageid, String uuid, String idcode, String idname, String birthday, String gender, String state, String lastLoginTime, String createTime) {
        this.id = id;
        this.phone = phone;
        this.openid = openid;
        this.username = username;
        this.imageid = imageid;
        this.uuid = uuid;
        this.idcode = idcode;
        this.idname = idname;
        this.birthday = birthday;
        this.gender = gender;
        this.state = state;
        this.lastLoginTime = lastLoginTime;
        this.createTime = createTime;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageid() {
        return imageid;
    }

    public void setImageid(String imageid) {
        this.imageid = imageid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public String getIdname() {
        return idname;
    }

    public void setIdname(String idname) {
        this.idname = idname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}
