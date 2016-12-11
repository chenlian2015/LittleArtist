package aijieli.android.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by chenlian on 16/12/7.
 */

public class InterceptViewPager extends ViewPager {

    private OnConsumeTouchEventListener listener;

    public InterceptViewPager(Context context) {
        super(context);
    }

    public InterceptViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (listener != null) {
            listener.onConsumeTouchEvent(ev.getAction());
        }
        return super.onTouchEvent(ev);
    }

    public void setOnConsumeTouchEventListener(OnConsumeTouchEventListener listener) {
        this.listener = listener;
    }

    public interface OnConsumeTouchEventListener {
        void onConsumeTouchEvent(int action);
    }
}
