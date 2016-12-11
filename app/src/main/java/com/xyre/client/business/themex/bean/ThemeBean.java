package com.xyre.client.business.themex.bean;

import java.util.List;

/**
 * Created by chenlian on 16/12/1.
 */
public class ThemeBean {
    public String id;
    public String name;
    public String state;//1可用  2禁用
    public String startTime;
    public String endTime;
    public List<ThemeBeanItem> detailList;
}
