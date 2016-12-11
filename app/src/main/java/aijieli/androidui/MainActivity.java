package aijieli.androidui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import aijieli.androidui.widget.AutoTextView;
import aijieli.androidui.widget.IAutoTextViewClickListener;
import chenlian.littleartist.R;


public class MainActivity extends AppCompatActivity implements OnClickListener, IAutoTextViewClickListener{

    AutoTextView autoTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> mList = new ArrayList<>();
        mList.add("垂直滚动TextView测试1");
        mList.add("垂直滚动TextView测试2");
        mList.add("垂直滚动TextView测试3");
        mList.add("垂直滚动TextView测试4");

        autoTextView = (AutoTextView) findViewById(R.id.id_autotextview_gonggao);
        autoTextView.setData(mList);
        autoTextView.initAutoTextView(this, autoTextView, autoTextView.getLayoutParams().height, px2dip(this, getResources().getDimension(R.dimen.dp750_26)), 3000, Color.GRAY);


        findViewById(R.id.id_iv_tip).setOnClickListener(this);
    }

    public static int px2dip(Context context, double pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5);
    }

    int count = 0;
    @Override
    public void onClick(View v) {
        if(count++%2==0) {
            autoTextView.setData(null);
        }else
        { List<String> mList = new ArrayList<>();
            mList.add("垂直滚动TextView测试1");
            mList.add("垂直滚动TextView测试2");
            mList.add("垂直滚动TextView测试3");
            mList.add("垂直滚动TextView测试4");
            autoTextView.setData(mList);
        }
    }

    @Override
    public void clicked(int position) {
        Toast.makeText(this, ""+position, Toast.LENGTH_SHORT).show();
    }
}
