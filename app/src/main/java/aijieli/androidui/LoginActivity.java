package aijieli.androidui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.xyre.client.business.index.bean.RegistRequest;
import com.xyre.client.business.index.bean.RegistResponse;
import com.xyre.client.common.net.Constant;
import com.xyre.client.common.net.OKHttpUtils;

import java.io.IOException;

import aijieli.common.net.UserCallback;
import chenlian.littleartist.MyApplication;
import chenlian.littleartist.R;
import okhttp3.Call;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


    }

    public static void login()
    {
        String url = Constant.baseUrl + "/user/login.json";
        RegistRequest registRequest = new RegistRequest();
        registRequest.type = "2";
        registRequest.token = "u84BJEcJMoEhg9jKVfDYq2HFRgVCaNeNl9L4Roo+sNI=";
        registRequest.setPhoneNo("15010913082");
        registRequest.setVerifyCode("827402");
        OKHttpUtils.getInstance().url(url).postJsonString(new Gson().toJson(registRequest)).postExecute(new UserCallback<String>() {


            @Override
            public void onSuccess(Call call, String response) {
                String res = (String) response;
                RegistResponse response2 = new Gson().fromJson(res, RegistResponse.class);
                MyApplication._t = response2.getUserInfo().getId();
            }

            @Override
            public void onFail(Call call, IOException e) {

            }


        });
    }
}
