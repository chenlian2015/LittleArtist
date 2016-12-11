package com.xyre.client.business.themex.myenum;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenlian on 16/12/8.
 */
public enum ThemeDetailEnum {

    BANNER_IMG(101, ThemeDetailTypeEnum.IMG.getCode(), "导航栏图片"),

    COMMUNITY_NAME_COLOR(201, ThemeDetailTypeEnum.COLOR.getCode(), "小区名称字色"),
    DROP_BAR_COLOR(202, ThemeDetailTypeEnum.IMG.getCode(), "下拉栏图标"),
    SMOG_COLOR(203, ThemeDetailTypeEnum.COLOR.getCode(), "小区雾霾和温度颜色"),
    SMOG__SUN_COLOR(204, ThemeDetailTypeEnum.COLOR.getCode(), "雾霾指数和太阳颜色"),

    FOOT_BUTTON_1_BEFORE_IMG(611, ThemeDetailTypeEnum.IMG.getCode(), "首页"),
    FOOT_BUTTON_1_AFTER_IMG(612, ThemeDetailTypeEnum.IMG.getCode(), "首页激活"),
    FOOT_BUTTON_2_BEFORE_IMG(621, ThemeDetailTypeEnum.IMG.getCode(), "商铺首页"),
    FOOT_BUTTON_2_AFTER_IMG(622, ThemeDetailTypeEnum.IMG.getCode(), "商铺首页激活"),
    FOOT_BUTTON_3_BEFORE_IMG(631, ThemeDetailTypeEnum.IMG.getCode(), "开门图标"),
    FOOT_BUTTON_3_AFTER_IMG(632, ThemeDetailTypeEnum.IMG.getCode(), "开门旋转图"),
    FOOT_BUTTON_4_BEFORE_IMG(641, ThemeDetailTypeEnum.IMG.getCode(), "服务"),
    FOOT_BUTTON_4_AFTER_IMG(642, ThemeDetailTypeEnum.IMG.getCode(), "服务激活"),
    FOOT_BUTTON_5_BEFORE_IMG(651, ThemeDetailTypeEnum.IMG.getCode(), "我的"),
    FOOT_BUTTON_5_AFTER_IMG(652, ThemeDetailTypeEnum.IMG.getCode(), "我的激活"),

    BG_IMG(701, ThemeDetailTypeEnum.IMG.getCode(), "一级功能背景图"),
    FOOT_BG_IMG(702, ThemeDetailTypeEnum.IMG.getCode(), "底部Tab图"),
    OPEN_BG_IMG(703, ThemeDetailTypeEnum.IMG.getCode(), "开门底图");


    public static final Map<String, ThemeDetailEnum> interToEnum = new HashMap<String, ThemeDetailEnum>();

    static {
        for (ThemeDetailEnum type : ThemeDetailEnum.values()) {
            interToEnum.put(type.getCode(), type);
        }
    }

    private String code;
    private String type;
    private String msg;

    private ThemeDetailEnum(int code, String type, String msg) {
        this.code = ""+code;
        this.type = type;
        this.msg = msg;
    }

    public static ThemeDetailEnum fromInteger(int code) {
        return interToEnum.get(code);
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}

