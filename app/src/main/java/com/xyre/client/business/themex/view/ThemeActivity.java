package com.xyre.client.business.themex.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.xyre.client.business.themex.ThemeManager;
import com.xyre.client.business.themex.bean.ThemeBean;
import com.xyre.client.business.themex.helper.ThemeHelper;
import com.xyre.client.business.themex.myenum.ThemeDetailEnum;

import aijieli.androidui.LoginActivity;
import chenlian.littleartist.MainActivity;
import chenlian.littleartist.R;

public class ThemeActivity extends AppCompatActivity implements IThemeShow{

    public String TAG ="ThemeActiviey";
    Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        LoginActivity.login();

        MainActivity.verifyStoragePermissions(this);
        bt = (Button) findViewById(R.id.id_bt);

        findViewById(R.id.id_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemeHelper.context = getApplicationContext();
                ThemeHelper.iThemeShowMainActivity = ThemeActivity.this;
                new ThemeHelper().showTheme();
            }
        });
    }

    @Override
    public void showTheme(ThemeBean themeBean) {
        bt.setBackground(ThemeManager.getInstance().mapThemeValueConverted.get(ThemeDetailEnum.FOOT_BG_IMG.getCode()).valueImg);
        bt.setTextColor(ThemeManager.getInstance().mapThemeValueConverted.get("201").valuecolor);
    }
}
