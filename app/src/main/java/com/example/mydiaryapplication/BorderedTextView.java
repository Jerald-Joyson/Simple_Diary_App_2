package com.example.mydiaryapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

public class BorderedTextView extends androidx.appcompat.widget.AppCompatTextView {
    public BorderedTextView(Context context) {
        super(context);
    }

    public BorderedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BorderedTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Draw the border
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(Color.BLUE);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

        // Call the superclass's onDraw() method
        super.onDraw(canvas);
    }
}
