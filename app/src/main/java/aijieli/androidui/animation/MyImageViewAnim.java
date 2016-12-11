package aijieli.androidui.animation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.ImageView;

import chenlian.littleartist.R;

/**
 * Created by chenlian on 16/12/4.
 */
public class MyImageViewAnim extends ImageView {

    Bitmap bitmap;

    int width;
    int height;

    Context context;
    public MyImageViewAnim(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void initBitmap()
    {
        bitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.icon_discount_coupon);

        width = bitmap.getWidth();
        height = bitmap.getHeight();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        Matrix matrix = getMatrix();
        canvas.drawBitmap(bitmap, 0, 0, null);
        matrix.reset();
        matrix.postScale(50f, 50f);
        //matrix.preTranslate(-width/2, -height/2);
      //  matrix.postTranslate(width/2, height/2);
        canvas.drawBitmap(bitmap, matrix, null);
        super.onDraw(canvas);
    }
}
