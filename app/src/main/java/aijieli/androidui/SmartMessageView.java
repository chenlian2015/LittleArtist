package aijieli.androidui;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import chenlian.littleartist.R;

/**
 * Created by chenlian on 16/12/7.
 */
public class SmartMessageView extends LinearLayout {

    private ImageView picView;

    private TextView titleView;



    public SmartMessageView(Context context) {
        super(context);
        initView();
    }


    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.item, this);
        picView = (ImageView) findViewById(R.id.id_iv);
        titleView = (TextView) findViewById(R.id.id_tv);
 }
}
