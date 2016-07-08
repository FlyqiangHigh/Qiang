package com.example.qiangge.selfview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class Draw extends View{
	private Paint paint;  
    Path path;  
	public Draw(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		//canvas.drawColor(Color.WHITE);
		 paint = new Paint();  
         paint.setStyle(Paint.Style.FILL);  
        
         paint.setStrokeWidth(4); 
         paint.setColor(Color.WHITE);
         path = new Path();  
         path.moveTo(0, 0);
         path.lineTo(10, 10);
         path.lineTo(20, 0);
         path.lineTo(30, 10);
         path.lineTo(40, 0);
         path.lineTo(38, 20);
         path.lineTo(2, 20);
        
         path.close();
         canvas.drawPath(path, paint);
         Path path1 = new Path();
         path1.moveTo(2, 21);
         path1.lineTo(38, 21);
         path1.lineTo(38, 25);
         path1.lineTo(2, 25);
         path1.close();
         canvas.drawPath(path1,paint);
         
	}

}
