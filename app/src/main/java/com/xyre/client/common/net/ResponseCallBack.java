package com.xyre.client.common.net;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by chenlian on 16/12/1.
 */
public abstract class ResponseCallBack<T> {
    public abstract void onSuccess(Call call, T response);

    public abstract void onFail(Call call, IOException e);

    public abstract String parseNetworkResponse(Response response) throws Exception;

}