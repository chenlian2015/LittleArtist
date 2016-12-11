package com.xyre.client.common.net;

import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import chenlian.littleartist.MyApplication;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by chenlian on 16/12/1.
 */

public class OKHttpUtils {
    private static final String TAG = "ehome" + OKHttpUtils.class.getSimpleName();

    private static OKHttpUtils mInstance;

    private OkHttpClient mOKHttpClient;

    private Handler mHandler;

    private static String _url;

    private int _method;

    private static Map<String, String> _headers;

    private RequestBody _requestBody;

    private static final int DEFAUTL_TIMEOUT = 30000;

    private static final int READ_TIMEOUT = 30000;

    private static final int WRITE_TIMEOUT = 30000;

    private OKHttpUtils() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        mHandler = new Handler(Looper.getMainLooper());

        builder.retryOnConnectionFailure(false)
                .connectTimeout(DEFAUTL_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS);
        mOKHttpClient = builder.build();
    }

    public static OKHttpUtils getInstance() {
        if (mInstance == null) {
            synchronized (OKHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new OKHttpUtils();
                }
            }
        }

        if (_headers == null) {
            _headers = new HashMap<>();
        }
        _headers.put("machine", android.os.Build.MODEL);
        _headers.put("imei", "1");
        _headers.put("app_version", "5.7");
        _headers.put("platform", "android");
        _headers.put("platform_version", "6.0");
        _headers.put("channel", "cl");
        // 获取_t缓存的信息


        _headers.put("_t", MyApplication._t);


        // 获取小区id的缓存信息
        _headers.put("community_id", "55");

        return mInstance;
    }

    public OKHttpUtils headers(String... headerPairs) {
        if (_headers == null) {
            _headers = new HashMap<>();
        }
        if (headerPairs.length < 2) {
            return mInstance;
        }
        for (int i = 0; i < headerPairs.length - 1; i += 2) {
            _headers.put(headerPairs[i], headerPairs[i + 1]);
        }
        return mInstance;
    }

    public OKHttpUtils method(int method) {
        _method = method;
        return mInstance;
    }

    public OKHttpUtils url(String url) {
        _url = url;
        _requestBody = null;
        return mInstance;
    }

    /**
     * 上传jsonMap
     *
     * @param kvPairs 参数
     * @return this
     */
    public OKHttpUtils postJsonMap(Object... kvPairs) {
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < kvPairs.length - 1; i += 2) {
            try {
                jsonObject.put(kvPairs[i].toString(), kvPairs[i + 1]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        _requestBody = RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), jsonObject.toString());
        return mInstance;
    }

    /**
     * get方法添加请求参数
     *
     * @param kvPairs 参数
     * @return 拼接url后返回mInstance
     */
    public OKHttpUtils getMethodAddParams(Object... kvPairs) {
        StringBuilder tempParams = new StringBuilder();
        for (int i = 0; i < kvPairs.length - 1; i += 2) {
            try {
                if (i > 0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s", kvPairs[i].toString(), URLEncoder.encode(kvPairs[i + 1].toString(), "utf-8")));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (tempParams.length() > 0) {
            _url = String.format("%s?%s", _url, tempParams.toString());
        }
        return mInstance;
    }

    /**
     * 上传json字符串
     *
     * @param jsonStr json字符串
     * @return this
     */
    public OKHttpUtils postJsonString(String jsonStr) {
        _requestBody = RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), jsonStr.toString());
        return mInstance;
    }

    public OKHttpUtils postImage(File file) {
        MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
        _requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("files", null, new MultipartBody.Builder()
                        .addPart(Headers.of("Content-Disposition", "form-data; filename=" + file.getName()),
                                RequestBody.create(MEDIA_TYPE_PNG, file))
                        .build())
                .build();
        return mInstance;
    }

    /**
     * 上传图片
     *
     * @return this
     */
    public void postImageAndExecute(Uri uri, final ResponseCallBack responseCallBack) {
        MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
        File f = new File(getRealPathFromURI(uri));
        MultipartBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("img", f.getName(), RequestBody.create(MEDIA_TYPE_PNG, f)).build();
        try {
            Request request = new Request.Builder().headers(Headers.of(_headers)).post(requestBody).url(_url).build();
            Call call = mOKHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    sendFailResponse(call, e, responseCallBack);
                }

                @Override
                public void onResponse(Call call, Response response) {
                    try {
                        String o = responseCallBack.parseNetworkResponse(response);
                        sendSuccessResponse(call, o, responseCallBack);
                    } catch (Exception e) {
                        Log.i(TAG, e.toString());
                    }
                }
            });
        } catch (Exception e) {
            Log.i(TAG, e.toString());
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = MyApplication.getInstance().getApplicationContext().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            ;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    /**
     * 上传单文件
     *
     * @param file 文件
     * @return this
     */
    public OKHttpUtils postFile(File file) {
        _requestBody = RequestBody.create(MediaType.parse("text/x-markdown; charset=utf-8"), file);

        return mInstance;
    }


    /**
     * post提交
     *
     * @param responseCallBack 响应回调
     */
    public void postExecute(final ResponseCallBack responseCallBack) {
        try {
            if (_requestBody == null) {
                _requestBody = RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), "");
            }
            Request request = new Request.Builder().headers(Headers.of(_headers)).post(_requestBody).url(_url).build();
            Call call = mOKHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    sendFailResponse(call, e, responseCallBack);
                }

                @Override
                public void onResponse(Call call, Response response) {
                    try {
                        String o = responseCallBack.parseNetworkResponse(response);
                        sendSuccessResponse(call, o, responseCallBack);
                    } catch (Exception e) {
                        Log.i(TAG, e.toString());
                    }
                }
            });
        } catch (Exception e) {
            Log.i(TAG, e.toString());
        }
    }

    /**
     * get提交
     *
     * @param responseCallBack 响应回调
     */
    public void getExecute(final ResponseCallBack responseCallBack) {
        try {
            Request request = null;
            //为了解决okhttp的get请求丢包时出现EOFEXCEPTION的问题
            if (Build.VERSION.SDK != null && Build.VERSION.SDK_INT > 13) {
                request = new Request.Builder().headers(Headers.of(_headers)).addHeader("Connection", "close").url(_url).get().build();
            } else {
                request = new Request.Builder().headers(Headers.of(_headers)).url(_url).get().build();
            }
            Call call = mOKHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    sendFailResponse(call, e, responseCallBack);
                }

                @Override
                public void onResponse(Call call, Response response) {
                    try {
                        String o = responseCallBack.parseNetworkResponse(response);
                        sendSuccessResponse(call, o, responseCallBack);
                    } catch (Exception e) {
                        Log.i(TAG, e.toString());
                    }
                }
            });
        } catch (Exception e) {
            Log.i(TAG, e.toString());
        }
    }

    /**
     * 发送fail的响应
     *
     * @param call             call对象
     * @param e                IOException
     * @param responseCallBack 响应回调
     */
    public void sendFailResponse(final Call call, final IOException e, final ResponseCallBack responseCallBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                responseCallBack.onFail(call, e);
            }
        });
    }

    /**
     * 发送success的响应
     *
     * @param call             call对象
     * @param o                object类型
     * @param responseCallBack 回调
     */
    public void sendSuccessResponse(final Call call, final Object o, final ResponseCallBack responseCallBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                responseCallBack.onSuccess(call, o);
            }
        });

    }
}
