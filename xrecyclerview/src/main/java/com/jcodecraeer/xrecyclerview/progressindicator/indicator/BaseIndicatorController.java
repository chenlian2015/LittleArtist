package com.jcodecraeer.xrecyclerview.progressindicator.indicator;

import android.animation.Animator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.jcodecraeer.xrecyclerview.R;

import java.util.List;

/**
 * Created by Jack on 2015/10/15.
 */
public abstract class BaseIndicatorController {


    private View mTarget;

    private List<Animator> mAnimators;


    public void setTarget(View target) {
        this.mTarget = target;
    }

    public View getTarget() {
        return mTarget;
    }

    public void setImageView() {
        if (mTarget == null) {
            return;
        }
        RelativeLayout relativeLayout = new RelativeLayout(mTarget.getContext());
        ImageView iv = new ImageView(mTarget.getContext());
        iv.setImageResource(R.drawable.ic_pulltofrefresh_inner);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(dp2px(25), dp2px(25));
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        iv.setLayoutParams(params);
        relativeLayout.addView(iv);
        ProgressBar progressBar = new ProgressBar(mTarget.getContext());
        Drawable drawable = mTarget.getContext().getResources().getDrawable(R.drawable.refresh_loading);
        progressBar.setIndeterminateDrawable(drawable);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(dp2px(30), dp2px(30));
        params1.addRule(RelativeLayout.CENTER_IN_PARENT);
        progressBar.setLayoutParams(params1);
        relativeLayout.addView(progressBar);
        if (mTarget != null) {
            if (mTarget.getParent() != null && mTarget.getParent() instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) mTarget.getParent();
                if (viewGroup.getChildCount() < 2) {
                    viewGroup.addView(relativeLayout);
                }
            }
        }
    }

    private int dp2px(int dpValue) {
        return (int) mTarget.getContext().getResources().getDisplayMetrics().density * dpValue;
    }

    public int getWidth() {
        return mTarget.getWidth();
    }

    public int getHeight() {
        return mTarget.getHeight();
    }

    public void postInvalidate() {
        mTarget.postInvalidate();
    }

    /**
     * draw indicator
     *
     * @param canvas
     * @param paint
     */
    public abstract void draw(Canvas canvas, Paint paint);

    /**
     * create animation or animations
     */
    public abstract List<Animator> createAnimation();

    public void initAnimation() {
        mAnimators = createAnimation();
    }

    /**
     * make animation to start or end when target
     * view was be Visible or Gone or Invisible.
     * make animation to cancel when target view
     * be onDetachedFromWindow.
     *
     * @param animStatus
     */
    public void setAnimationStatus(AnimStatus animStatus) {
        if (mAnimators == null) {
            return;
        }
        int count = mAnimators.size();
        for (int i = 0; i < count; i++) {
            Animator animator = mAnimators.get(i);
            boolean isRunning = animator.isRunning();
            switch (animStatus) {
                case START:
                    if (!isRunning) {
                        animator.start();
                    }
                    break;
                case END:
                    if (isRunning) {
                        animator.end();
                    }
                    break;
                case CANCEL:
                    if (isRunning) {
                        animator.cancel();
                    }
                    break;
            }
        }
    }


    public enum AnimStatus {
        START, END, CANCEL
    }


}
