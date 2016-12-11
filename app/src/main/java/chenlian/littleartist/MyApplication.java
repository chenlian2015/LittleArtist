package chenlian.littleartist;

/**
 * Created by chenlian on 16/11/24.
 */

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * Created by chenlian on 16/11/24.
 */
public class MyApplication extends Application {

    public static String TAG = "MyApplication";

    // user your appid the key.
    private static final String APP_ID = "2882303761517529213";
    // user your appid the key.
    private static final String APP_KEY = "5871752975213";

    private static MyApplication instance;

    public static MyApplication getInstance()
    {
        return instance;
    }

    public static String _t = "";
    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

//        if (shouldInit()) {
//            MiPushClient.registerPush(this, APP_ID, APP_KEY);
//        }

        if (sHandler == null) {
            sHandler = new DemoHandler(getApplicationContext());
        }

        //LoginActivity.login();

        registerUncaughtExceptionHandler(getApplicationContext());
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    public static void registerUncaughtExceptionHandler(Context context) {
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler(
                context));
    }

    /**
     * 未捕获异常处理器
     */
    private static class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

        private Context context;

        public MyUncaughtExceptionHandler(Context context) {
            this.context = context;
        }

        @Override
        public void uncaughtException(Thread thread, final Throwable ex) {
            // 使用 Toast 来显示异常信息
            Log.e("Myapplicatiton", ex.toString());
        }
    }

    private static DemoHandler sHandler = null;

    public static DemoHandler getHandler() {
        return sHandler;
    }


    public static class DemoHandler extends Handler {

        private Context context;

        public DemoHandler(Context context) {
            this.context = context;
        }

        @Override
        public void handleMessage(Message msg) {
            String s = (String) msg.obj;

            if (!TextUtils.isEmpty(s)) {
                Toast.makeText(context, s, Toast.LENGTH_LONG).show();
            }
        }
    }
}