package chenlian.littleartist.common;

/**
 * Created by chenlian on 16/12/1.
 */

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;


/**
 * 应用程序的环境
 *
 * @author 792793182@qq.com 2015-06-11
 */
public final class MyEnvironment {

    public static String TAG = "MyEnvironment";

    private Context applicationContext;

    private static class InstanceHolder {
        private static MyEnvironment instance = new MyEnvironment();
    }

    private MyEnvironment() {
    }

    public static MyEnvironment getInstance() {
        return InstanceHolder.instance;
    }

    /**
     * 注意：此方法只能在Application的onCreate方法中调用一次
     *
     * @param applicationContext ApplicationContext
     */
    public MyEnvironment init(Context applicationContext) {
        this.applicationContext = applicationContext;
        return this;
    }


    public static final int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    /**
     * 获取外部存储器的路径
     */
    public static String getExternalStorageDirectory() {
        return android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * 手机可用存储空间，不是存储卡空间，返回值以MB单位
     */
    public static long getAvailableSize(String path) {
        StatFs fileStats = new StatFs(path);
        fileStats.restat(path);
        return (long) fileStats.getAvailableBlocks() * (long) fileStats.getBlockSize() / 1024 / 1024;
    }

    /**
     * 获取SD卡的路径
     */
    public String getSDPath() {
        if (isExternalStorageAvailable()  && isExternalStorageReadable()) {
            return getExternalStorageDirectory() + "/" + applicationContext.getPackageName();
        }
        return "";
    }

    /**
     * 获取ROM上的私有目录
     */
    public String getInternalDir() {
        return applicationContext.getFilesDir().getAbsolutePath();
    }

    /**
     * 获取我们的私有的目录，优先在SD卡上
     */
    public String getMyDir() {
        String dir = getSDPath();
        if (TextUtils.isEmpty(dir)) {
            dir = getInternalDir();
        }


        File file = new File(dir);
        if (!file.exists()) {
            boolean b = file.mkdirs();
            Log.e(TAG, b?"true":"false");
        }

        return dir;
    }
}
