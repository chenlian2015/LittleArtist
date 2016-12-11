package com.xyre.client.common.store;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by chenlian on 16/12/9.
 */

public class SharedUtils {

    // 小米推送的注册id
    public static final String MID = "mid";
    // 小米ID是否成功获取过
    public static final String MIDISOK = "midisok";
    // 本地缓存的表名
    public static final String FILE_NAME = "share_ehome";
    // 本地缓存的小区信息的字段名
    public static final String KEY_HOMEINFO = "homeinfo";
    //  本地缓存的用户所在城市id
    public static final String KEY_CITYID = "cityID";

    // 本地缓存的_t用户id，加密过的，服务器传回来的
    public static final String KEY_T = "_t";
    // 本地城市列表
    public static final String KEY_CITYINFO = "cityinifo";
    public static final String KEY_DOOR_HARDFACTROY_TYPE = "key_door_hardfactroy_type";

    //小区是否开通门禁
    public static final String KEY_DOOR_COMMUNITY_OPENNED = "key_door_commnuity_openned";

    public static final String LOGINED_HUANXIN_USERNAME = "LoginedHuanXinUserName";
    // 分类列表
    public static final String KEY_CATEGROYLIST = "categroyinfo";

    public static final String MY_FRIST = "my_frist_into";
    // 个人中心第一次进来的蒙版
    public static final String MY_FRIST2 = "my_frist_into2";
    // 用户注册的手机号
    public static final String PHONE = "my_phone";
    // 推送收到的新优惠券信息是否有效,ok=有效
    public static final String QUAN = "quan";
    //首页首次蒙版
    public static final String HOME_FIRST = "home_first";

    // 临时数据，适用于5.1升级，清空之前5.0可能会存在的干扰数据,记录是不是第一次登陆,
    // 用来清除 MIDISOK 的数据，没能上传小米推送token的可以再次上传
    public static final String TEM_FRIST_5_1_0 = "5.1_frist";

    public static String TAG = "SharedUtils";

    public static String RERCENT_THEMES = "RERCENT_THEMES";

    public static String CURENT_THEME_ID = "CURENT_THEME_ID";
    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public static void put(Context context, String key, Object object) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(Context context, String key, Object defaultObject) {
        if(null == context)
        {
            Log.e(TAG, "null");
            return defaultObject;
        }

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     *
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }


}
