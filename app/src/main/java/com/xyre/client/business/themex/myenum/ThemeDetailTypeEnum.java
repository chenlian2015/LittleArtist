package com.xyre.client.business.themex.myenum;

/**
 * Created by chenlian on 16/12/8.
 */

public enum ThemeDetailTypeEnum {
    IMG("1", "图片"),
    COLOR("2", "颜色");

    String code;
    String msg;

    ThemeDetailTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode()
    {
        return code;
    }
}
