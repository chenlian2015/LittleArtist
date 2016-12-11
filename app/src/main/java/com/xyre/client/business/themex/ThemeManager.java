package com.xyre.client.business.themex;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import com.xyre.client.business.themex.bean.ThemeBean;
import com.xyre.client.business.themex.bean.ThemeBeanItem;
import com.xyre.client.business.themex.bean.ThemeBeanItemConveted;
import com.xyre.client.business.themex.myenum.ThemeDetailTypeEnum;
import com.xyre.client.business.themex.tool.ResourceManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by chenlian on 16/12/8.
 */
public class ThemeManager {
    public static String TAG = "ThemeManager";

    //底部5个主功能块容器
    Drawable view_tab_container;

    //非选中状态
    Drawable unselImagerIndex;
    Drawable unselImagerShop;
    Drawable unselImagerDoor;
    Drawable unselImagerMy;

    //选中状态
    Drawable seledImagerIndex;
    Drawable seledImagerShop;
    Drawable seledImagerDoor;
    Drawable seledImagerMy;

    //开门按钮
    public Drawable image_opendoor_keybg;
    public Drawable image_opendoor_key;
    public Drawable image_opendoor_tv;

    //>可配主题
    //标题容器,需要配置背景图
    private View index_house_info;
    // 标题栏中的小区名字
    private int tv_homeName;
    //pm 和 值
    private int pm25, weatherSmog;
    //天气图标和值
    private int weatherFont, weatherTemperature;

    //首页一级上面四个功能块,注意背景为圆边
    private Drawable unselContainerOne;
    //物业报修,iv
    private Drawable unselWuyebaoxiu;
    //上门服务,iv
    private Drawable unselShangmenfuwu;
    //联系物业,iv
    private Drawable unselLianxiwuye;
    //物业缴费,iv
    private Drawable unselWuyejiaofei;


    public Map<String, ThemeBeanItemConveted> mapThemeValueConverted = new HashMap<>();
    Map<String, View> mapThemeView = new HashMap<>();

    Drawable imageOpenDoor;

    private static ThemeManager instance;
    public static ThemeManager getInstance()
    {
        if(null == instance){
            instance = new ThemeManager();
        }
        return instance;
    }
    private ThemeManager()
    {}

    public boolean downLoadThemes(List<ThemeBean> lstThemeBeans, Context context)
    {

        if(null == lstThemeBeans)
        {
            return true;
        }

        Iterator<ThemeBean> it = lstThemeBeans.iterator();
        while (it.hasNext())
        {
            ThemeManager.getInstance().downLoadTheme(it.next(), context);
        }

        return true;
    }
    public boolean downLoadTheme(ThemeBean themeBean, Context context)
    {
        try {
            Iterator<ThemeBeanItem> it = themeBean.detailList.iterator();
            while (it.hasNext()) {
                ThemeBeanItem themeBeanItem = it.next();
                if (ThemeDetailTypeEnum.IMG.getCode().equals(themeBeanItem.type)) {
                    ResourceManager.getInstance(context).downloadFile(themeBean.id, themeBeanItem.value, themeBeanItem.code);
                }
            }
        }catch (Exception e)
        {
            Log.e(TAG, e.toString());
            return false;
        }
        return true;
    }

    public boolean isThemmeOnLocal(ThemeBean themeBean, Context context) {
        Iterator<ThemeBeanItem> it = themeBean.detailList.iterator();
        while (it.hasNext()) {
            ThemeBeanItem themeBeanItem = it.next();

            if (ThemeDetailTypeEnum.IMG.getCode().equals(themeBeanItem.type)) {
                if (!ResourceManager.getInstance(context).isFileExistInStorage(themeBean.id, themeBeanItem.code)) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean initTheme(ThemeBean themeBean, Context context) {
        try {
            Iterator<ThemeBeanItem> it = themeBean.detailList.iterator();
            while (it.hasNext()) {
                ThemeBeanItem themeBeanItem = it.next();
                ThemeBeanItemConveted themeBeanItemConveted = new ThemeBeanItemConveted();
                if (ThemeDetailTypeEnum.COLOR.getCode().equals(themeBeanItem.type)) {
                    themeBeanItemConveted.valuecolor = Color.parseColor(themeBeanItem.value);
                } else if (ThemeDetailTypeEnum.IMG.getCode().equals(themeBeanItem.type)) {
                    String filePathName = ResourceManager.getInstance(context).getFilePathName(themeBean.id, themeBeanItem.code);
                    themeBeanItemConveted.valueImg = new BitmapDrawable(context.getResources(), BitmapFactory.decodeFile(filePathName));
                }

                mapThemeValueConverted.put(themeBeanItem.code, themeBeanItemConveted);
            }


        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return false;
        }

        return true;
    }

}
