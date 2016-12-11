package aijieli.androidui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import aijieli.android.adapter.InterceptViewPager;
import aijieli.android.adapter.SmartManagerMessageAdapter;
import chenlian.littleartist.R;

public class RecycleViewActivity extends AppCompatActivity {

    private int[] imgIdArray;
    SmartManagerMessageAdapter messageAdapter;
    ImageView []mImageViews;
    InterceptViewPager viewPager;
    View header;


    void setData()
    {
         messageAdapter = new SmartManagerMessageAdapter();
        View messageView = null;

        header.findViewById(R.id.fl_index_view_smart_manager_message_layout).setVisibility(View.VISIBLE);

        viewPager.setAdapter(messageAdapter);

        for (int i=0; i<10;i++) {
            messageView = new SmartMessageView(getBaseContext());
            ((TextView)messageView.findViewById(R.id.id_tv)).setText("hello");
            ((ImageView)messageView.findViewById(R.id.id_iv)).setImageResource(R.drawable.cat);
            messageAdapter.addView(messageView);
        }
        messageAdapter.notifyDataSetChanged();
    }

    boolean  interceptGesture = true;
    private InterceptViewPager.OnConsumeTouchEventListener consumeTouchEventListener = new InterceptViewPager.OnConsumeTouchEventListener() {
        @Override
        public void onConsumeTouchEvent(int action) {
            if (action == MotionEvent.ACTION_MOVE) {
                interceptGesture = true;
            } else {
                interceptGesture = false;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recycle_view);
        final XRecyclerView xRecyclerView =  (XRecyclerView) findViewById(R.id.id_xrv);


        try {

            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            xRecyclerView.setLayoutManager(layoutManager);

            header = LayoutInflater.from(this).inflate(R.layout.recyclerview_header, xRecyclerView, false);
            viewPager = (InterceptViewPager) header.findViewById(R.id.id_vp);
            viewPager.setOnConsumeTouchEventListener(consumeTouchEventListener);
            //载入图片资源ID


            imgIdArray = new int[]{R.drawable.icon_discount_coupon, R.drawable.icon_discount_coupon};

            //将点点加入到ViewGroup中
            mImageViews = new ImageView[imgIdArray.length];
            for(int i=0; i<mImageViews.length; i++){
                ImageView imageView = new ImageView(this);
                mImageViews[i] = imageView;
                imageView.setBackgroundResource(imgIdArray[i]);
            }

            messageAdapter = new SmartManagerMessageAdapter();

            ImageView [] tmp = {mImageViews[0]};

            viewPager.setAdapter(messageAdapter);

            xRecyclerView.setPullRefreshEnabled(true);
            xRecyclerView.setLoadingMoreEnabled(true);


            xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setData();
                            messageAdapter.notifyDataSetChanged();
                            xRecyclerView.refreshComplete();


                        }
                    }, 2000);
                }

                @Override
                public void onLoadMore() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setData();
                            messageAdapter.notifyDataSetChanged();
                            xRecyclerView.loadMoreComplete();


                        }
                    }, 2000);
                }
            });
            xRecyclerView.setAdapter(new RecyclerView.Adapter() {

                @Override
                public int getItemViewType(int position) {
                    return 0;
                }

                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                    View v = LayoutInflater.from(RecycleViewActivity.this).inflate(R.layout.item, parent, false);
                    return new RecyclerView.ViewHolder(v){
                    };
                }

                @Override
                public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

                }

                @Override
                public int getItemCount() {
                    return 30;
                }
            });
        }catch (Exception e)
        {
            Log.e("", e.toString());
        }




    }


}
