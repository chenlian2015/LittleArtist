package com.xyre.client.business.themex.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import chenlian.littleartist.common.MyEnvironment;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by chenlian on 16/12/1.
 */
public class ResourceManager {

    public static String TAG = "ResourceManager";
    public static String drawableImage = "http://yuhuayuan.oss-cn-shanghai.aliyuncs.com/%E5%AE%89%E8%A3%85.png";
    public static Context context;

    private static ResourceManager instance;

    private ResourceManager(Context contextPar) {
        this.context = contextPar;
    }

    public static ResourceManager getInstance(Context context) {
        if (null == instance) {
            instance = new ResourceManager(context);
        }
        return instance;
    }

    public File getThemeStoragePath(String themeId) {
        MyEnvironment.getInstance().init(context);
        String dir = MyEnvironment.getInstance().getMyDir();
        File file = new File(dir + "/" + themeId);
        if (!file.exists()) {
            boolean b = file.mkdirs();
            if (!b) {
                return null;
            }
        }
        return file;
    }

    public String getFilePathName(String themeId, String fileName) {
        String thmeStoragePath = getThemeStoragePath(themeId).getAbsolutePath();
        File f = new File(thmeStoragePath, fileName);
        return f.getAbsolutePath();
    }

    public boolean isFileExistInStorage(String themeId, String fileName) {
        String thmeStoragePath = getThemeStoragePath(themeId).getAbsolutePath();
        File f = new File(thmeStoragePath, fileName);
        return f.exists();
    }

    public boolean delThemeFile(String themeId, String fileName) {
        String thmeStoragePath = getThemeStoragePath(themeId).getAbsolutePath();
        File f = new File(thmeStoragePath, fileName);
        return f.delete();
    }


    /*
    * 返回值为空"",则表示文件尚未下载,需要异步下载,否则返回本地已经缓存的文件路径
    * */
    public void downloadFile(final String themeId, final String urlImage, final String fileName) {

        if (null == context) {
            Log.e(TAG, "null == context");
            return;
        }

        if (!isFileExistInStorage(themeId, fileName)) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    Request request = new Request.Builder()
                            .url(urlImage)
                            .build();

                    try {
                        OkHttpClient client = new OkHttpClient();
                        Response response = client.newCall(request).execute();
                        InputStream inputStream = response.body().byteStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                        File file = getThemeStoragePath(themeId);
                        FileOutputStream out = new FileOutputStream(new File(file, fileName));
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

                        out.close();
                    } catch (Exception e) {
                        delThemeFile(themeId, fileName);
                        Log.e(TAG, e.toString());
                    }
                }
            }).start();
        }
    }
}
