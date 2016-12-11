package com.xyre.client.business.index.bean;


/**
 * ==========================================
 * <p/>
 * 版    权 ： 北京爱接力科技有限公司
 * <p/>
 * 作    者 ： iwen
 * <p/>
 * 版    本 ： 1.0
 * <p/>
 * 创建日期 ： on 2016/3/4  10:06
 * <p/>
 * 描    述 ：
 * 手机注册数据模型
 * 非必须参数填写 ""
 * <p/>
 * <p/>
 * 修订历史 ：
 * <p/>
 * ==========================================
 */
public class RegistRequest {

    // 注册类型，1：微信， 2 ：手机
    public String type;
    // 手机号
    public String phoneNo;
    // 验证码
    public String verifyCode;
    // 开放平台ID
    public String openId;
    // 微信用户头像
    public String headUrl;
    // 用户名称
    public String userName;
    // 小米推送的token
    public String token;

    public RegistRequest() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public RegistRequest(String type, String phoneNo, String verifyCode, String openId, String headUrl, String userName) {
        this.type = type;
        this.phoneNo = phoneNo;
        this.verifyCode = verifyCode;
        this.openId = openId;
        this.headUrl = headUrl;
        this.userName = userName;
    }

    public RegistRequest(String type, String phoneNo, String verifyCode, String openId, String headUrl, String userName, String token) {
        this.type = type;
        this.phoneNo = phoneNo;
        this.verifyCode = verifyCode;
        this.openId = openId;
        this.headUrl = headUrl;
        this.userName = userName;
        this.token = token;
    }

    @Override
    public String toString() {
        return "RegistRequest{" +
                "type='" + type + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", verifyCode='" + verifyCode + '\'' +
                ", openId='" + openId + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", userName='" + userName + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
