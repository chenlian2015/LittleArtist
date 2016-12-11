package aijieli.androidui.widget;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.List;

/**
 * Created by chenlian on 16/12/4.
 */

public class AutoTextView extends TextSwitcher implements ViewSwitcher.ViewFactory, View.OnClickListener {

    public static String TAG = "AutoTextView";
    private float mFontSize;
    private Context mContext;
    // mInUp,mOutUp分别构成向下翻页的进出动画
    private Rotate3dAnimation mInUp;
    private Rotate3dAnimation mOutUp;

    // mInDown,mOutDown分别构成向下翻页的进出动画
    private Rotate3dAnimation mInDown;
    private Rotate3dAnimation mOutDown;

    private int mHeight;

    public AutoTextView(Context context) {
        this(context, null);
    }

    private Handler mHandler = new Handler();
    private AutoTextView mTextView;

    private List<String> mList;
    private int timer;
    private int textColor;
    private int mTextCurrentIndex;

    IAutoTextViewClickListener iAutoTextViewClickListener;

    public void initAutoTextView(IAutoTextViewClickListener iAutoTextViewClickListenerPar, AutoTextView autoTextView, int height, int fontSize, int timerPar, int textColorPar) {
        try {

            iAutoTextViewClickListener = iAutoTextViewClickListenerPar;
            mTextView = autoTextView;


            this.timer = timerPar;
            mHandler.postDelayed(runnable, timer);
            mHeight = height;
            mFontSize = fontSize;
            textColor = textColorPar;

            setFactory(this);
            if(null != mList && mList.size()>0) {
                mTextView.setText(mList.get(0));
            }
            init();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            if (null == mList || mList.size() == 0) {
                mTextCurrentIndex = 0;
            } else {
                mTextView.next();
                mTextCurrentIndex++;
                if (mTextCurrentIndex >= Integer.MAX_VALUE || mTextCurrentIndex > mList.size()) {
                    mTextCurrentIndex = 0;
                }

                if (null != mList && mList.size() != 0) {
                    mTextView.setText(mList.get(mTextCurrentIndex % (mList.size())));
                }
            }

            mHandler.postDelayed(this, timer);
        }
    };

    public AutoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
        mContext = context;
    }

    private void init() {

        mInUp = createAnim(true, true);
        mOutUp = createAnim(false, true);
        mInDown = createAnim(true, false);
        mOutDown = createAnim(false, false);

        setInAnimation(mInUp);
        setOutAnimation(mOutUp);
    }

    private Rotate3dAnimation createAnim(
            boolean turnIn, boolean turnUp) {
        final Rotate3dAnimation rotation = new Rotate3dAnimation(
                turnIn, turnUp);
        // 动画持续时间
        rotation.setDuration(timer);
        rotation.setFillAfter(false);
        rotation.setInterpolator(new AccelerateInterpolator());
        return rotation;
    }


    // 这里返回的TextView，就是我们看到的View
    @Override
    public View makeView() {
        TextView t = new TextView(mContext);
        t.setHeight(mHeight);
        t.setGravity(Gravity.LEFT);
        t.setTextSize(mFontSize);

        // 设置文字颜色
        t.setTextColor(textColor);
        return t;
    }


    // 定义动作，向上滚动翻页
    public void next() {
        if (getInAnimation() != mInUp) {
            setInAnimation(mInUp);
        }
        if (getOutAnimation() != mOutUp) {
            setOutAnimation(mOutUp);
        }
    }

    @Override
    public void onClick(View v) {
        if (null != iAutoTextViewClickListener && null != mList && mList.size() > 0) {
            iAutoTextViewClickListener.clicked(mTextCurrentIndex % (mList.size()));
        }
    }


    public void setData(List<String> mListPar) {

        if (null == mListPar || mListPar.size() == 0) {
            this.mList.clear();
            return;
        }

        this.mList = mListPar;
    }

    class Rotate3dAnimation extends Animation {

        private float mCenterY;
        private final boolean mTurnIn;
        private final boolean mTurnUp;
        private Camera mCamera;

        public Rotate3dAnimation(boolean turnIn, boolean turnUp) {
            mTurnIn = turnIn;
            mTurnUp = turnUp;
        }

        @Override
        public void initialize(int width, int height, int parentWidth,
                               int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            mCamera = new Camera();
            mCenterY = getHeight() / 2;
        }

        @Override
        protected void applyTransformation(float interpolatedTime,
                                           Transformation t) {

            final float centerY = mCenterY * 2;
            final Camera camera = mCamera;
            final int derection = mTurnUp ? 1 : -1;

            final Matrix matrix = t.getMatrix();

            camera.save();

            if (mTurnIn) {
                camera.translate(0.0f, derection * centerY
                        * (interpolatedTime - 1.0f), 0.0f);
            } else {
                camera.translate(0.0f, derection * centerY
                        * (interpolatedTime), 0.0f);
            }

            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate(0, -centerY);
            matrix.postTranslate(0, centerY);
        }
    }
}
