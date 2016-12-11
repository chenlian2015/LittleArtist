package aijieli.common.net;

import android.text.TextUtils;

import com.xyre.client.common.net.ResponseCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by chenlian on 16/12/8.
 */

public abstract class UserCallback<T> extends ResponseCallBack<T> {
    @Override
    public String parseNetworkResponse(Response response) throws IOException {
        String string = response.body().string();
        return string;
    }

    private void showDialog(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject jsonObject = new JSONObject(str);
                String code = jsonObject.getString("code");
                String msg = jsonObject.getString("msg");
                if (TextUtils.isEmpty(code)) {
                    return;
                }
                int c = Integer.parseInt(code);
                if (c >= 200 && c < 207) {
                    // 提票,显示退出对话框，对话框的上下文只能使用activity

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
