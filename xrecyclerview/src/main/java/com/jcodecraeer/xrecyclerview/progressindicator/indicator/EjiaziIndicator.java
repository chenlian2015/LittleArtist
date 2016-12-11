package com.jcodecraeer.xrecyclerview.progressindicator.indicator;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 2015/10/20.
 */
public class EjiaziIndicator extends BaseIndicatorController {


    @Override
    public void draw(Canvas canvas, Paint paint) {
       setImageView();

    }

    @Override
    public List<Animator> createAnimation() {
        List<Animator> animators = new ArrayList<>();
        ObjectAnimator rotateAnim = ObjectAnimator.ofFloat(getTarget(), "rotation", 0f, 360f);
        rotateAnim.setDuration(600);
        rotateAnim.setRepeatCount(-1);
        rotateAnim.start();
        animators.add(rotateAnim);
        return animators;
    }


}
