package com.example.qiangge.selfview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.qiangge.qiangge.R;

/**
 * Created by qiangge on 2016/8/4.
 */
public class SelfView extends View {
    private Paint paintOut;
    private Paint paintInner;
    int width ;
    int height;
    public SelfView(Context context,AttributeSet attributeSet) {
        super(context, attributeSet);
        paintOut = new Paint();
        paintOut.setAntiAlias(false);
        paintOut.setColor(Color.GREEN);
        paintOut.setStyle(Paint.Style.FILL);
        paintInner = new Paint();
        paintInner.setAntiAlias(false);
        paintInner.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth() / 2,getHeight() / 2);
        canvas.drawCircle(0, 0, 100, paintOut);

        Path path = new Path();
        path.addCircle(0, 0, 50, Path.Direction.CW);
        canvas.drawPath(path, paintInner);

        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.red));
        canvas.drawCircle(0, 0, 10, paint);
        RectF rectF = new RectF(0,0,200,200);
        canvas.clipRect(rectF);
    }


}
