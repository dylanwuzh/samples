package com.wuzhendev.measureview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

/**
 * 描述该类的功能
 *
 * @author wuzhen
 * @version Version 1.0, 2016-02-04
 */
public class MeasureView extends RelativeLayout {

    public MeasureView(Context context) {
        super(context);
    }

    public MeasureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MeasureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        Log.i(MainActivity.TAG, "onLayout, width: " + getWidth() + ", height: " + getHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Log.i(MainActivity.TAG, "onSizeChanged, width: " + getWidth() + ", height: " + getHeight());
    }
}
