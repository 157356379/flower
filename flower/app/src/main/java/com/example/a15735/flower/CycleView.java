package com.example.a15735.flower;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class CycleView extends View {
    private Paint mpaint;
    private int x;
    private int y;
    private int mcolor;

    public CycleView(Context context) {
        this(context, null);
    }

    public CycleView(Context context, AttributeSet attrs) {
        this(context, null, 0);
    }

    public CycleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mpaint = new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setDither(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int x = getWidth() / 2;
        int y = getHeight() / 2;
        canvas.drawCircle(x, y, x, mpaint);
    }

    public void exchange(int color) {
        //赋值
        mcolor = color;
        mpaint.setColor(color);
        invalidate();
    }

    //获取当前颜色
    public int getcolor() {
        return mcolor;
    }
}
