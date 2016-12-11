package com.xyre.client.business.themex.helper;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.xyre.client.business.themex.ThemeManager;
import com.xyre.client.business.themex.bean.CurrrentThemeResponse;
import com.xyre.client.business.themex.bean.RecentThemesResponse;
import com.xyre.client.business.themex.bean.ThemeBean;
import com.xyre.client.business.themex.view.IThemeShow;
import com.xyre.client.common.net.Constant;
import com.xyre.client.common.net.OKHttpUtils;
import com.xyre.client.common.store.SharedUtils;

import java.io.IOException;
import java.util.Iterator;

import aijieli.common.net.UserCallback;
import okhttp3.Call;

/**
 * Created by chenlian on 16/12/8.
 */
public class ThemeHelper {

    private static String TAG = "ThemeHelper";
    public static Context context;
    public static IThemeShow iThemeShowMainActivity;
    public static IThemeShow iThemeShowIndexFragment;

    public void doTheme(ThemeBean themeBean) {
        if (null == themeBean || null == themeBean.detailList) {
            return;
        }

        long cur = System.currentTimeMillis();
        try {
            if (Long.parseLong(themeBean.startTime) > cur ||
                    Long.parseLong(themeBean.endTime) < cur) {
                return;
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        if (ThemeManager.getInstance().isThemmeOnLocal(themeBean, context)) {
            ThemeManager.getInstance().initTheme(themeBean, context);
            if (null != iThemeShowMainActivity) {
                iThemeShowMainActivity.showTheme(themeBean);
            }

            if (null != iThemeShowIndexFragment) {
                iThemeShowIndexFragment.showTheme(themeBean);
            }
        } else {
            ThemeManager.getInstance().downLoadTheme(themeBean, context);
        }
    }

    public void getLatestThemes() {
        String url = Constant.baseUrl + "/index/theme/getThemes.json";
        OKHttpUtils.getInstance().url(url).postExecute(new UserCallback<String>() {

            @Override
            public void onSuccess(Call call, String response) {

                String res = (String) response;
                if (TextUtils.isEmpty(response)) {
                    return;
                }

                try {
                    RecentThemesResponse response2 = new Gson().fromJson(res, RecentThemesResponse.class);

                    SharedUtils.put(context, SharedUtils.RERCENT_THEMES, res);

                    if (null != response2) {
                        ThemeManager.getInstance().downLoadThemes(response2.data, context);
                    }
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }

            @Override
            public void onFail(Call call, IOException e) {

            }
        });
    }

    public void showTheme() {
        String themeID = (String) SharedUtils.get(context, SharedUtils.CURENT_THEME_ID, "");
        if (!TextUtils.isEmpty(themeID)) {
            String strRecentThemes = (String) SharedUtils.get(context, SharedUtils.RERCENT_THEMES, "");
            RecentThemesResponse recentThemesResponse = new Gson().fromJson(strRecentThemes, RecentThemesResponse.class);

            boolean hasDoTheme = false;
            if (null != recentThemesResponse &&
                    null != recentThemesResponse.data) {
                Iterator<ThemeBean> it = recentThemesResponse.data.iterator();
                while (it.hasNext()) {
                    ThemeBean themeBean = it.next();
                    if (themeID.equals(themeBean.id)) {
                        doTheme(themeBean);
                        //iThemeShowMainActivity.showTheme(themeBean);
                        hasDoTheme = true;
                        break;
                    }
                }
            }

            if (!hasDoTheme) {
                getLatestThemes();
            }

        }

        //无论何时,都更新最新的当前主题ID
        getThemeIDToLocal();

    }

    public void getThemeIDToLocal() {

        String url = Constant.baseUrl + "/index/theme/getThemeDetail.json";
        OKHttpUtils.getInstance().url(url).postExecute(new UserCallback<String>() {

            @Override
            public void onSuccess(Call call, String response) {
                String res = (String) response;
                if (TextUtils.isEmpty(response)) {
                    return;
                }

                try {
                    CurrrentThemeResponse currentThemeResponse = new Gson().fromJson(res, CurrrentThemeResponse.class);
                    if (null != currentThemeResponse && null != currentThemeResponse.data &&
                            !TextUtils.isEmpty(currentThemeResponse.data.id)) {
                        SharedUtils.put(context, SharedUtils.CURENT_THEME_ID, currentThemeResponse.data.id);
                    }
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }

            @Override
            public void onFail(Call call, IOException e) {

            }

        });
    }


}
