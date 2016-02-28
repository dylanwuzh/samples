package com.wuzhendev.motiondemo;

import android.app.Activity;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnTouchListener {

    private static final String TAG = "com.wuzhendev.Motion";

    private TextView tvLocation;
    private TextView tvMotion;
    private View     viewMotion;

    private RectF mMotionViewRect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLocation = (TextView) findViewById(R.id.tv_location);
        tvMotion = (TextView) findViewById(R.id.tv_motion);
        viewMotion = findViewById(R.id.view_motion);
        findViewById(R.id.lay_root).setOnTouchListener(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
            mMotionViewRect = calcViewScreenLocation(viewMotion);

            int left = (int) mMotionViewRect.left;
            int top = (int) mMotionViewRect.top;
            int right = (int) (left + mMotionViewRect.width());
            int bottom = (int) (top + mMotionViewRect.height());
            tvLocation.setText(
                    "Motion View location on screen: " + left + ", " + top + ", " + right + ", " +
                    bottom);
            logViewLocationInfos(viewMotion);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getRawX();
        float y = event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                tvMotion.setText("Motion Down on screen x: " + x + ", y: " + y + "\n");
                tvMotion.append("Motion Down is in View: " + mMotionViewRect.contains(x, y));
                break;

            case MotionEvent.ACTION_MOVE:
                tvMotion.setText("Motion Move on screen x: " + x + ", y: " + y + "\n");
                tvMotion.append("Motion Move is in View: " + mMotionViewRect.contains(x, y));
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                tvMotion.setText("");
                break;

            default:
                break;
        }
        return true;
    }

    /**
     * 计算指定的 View 在屏幕中的坐标。
     */
    public static RectF calcViewScreenLocation(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return new RectF(location[0], location[1], location[0] + view.getWidth(),
                location[1] + view.getHeight());
    }

    private void logViewLocationInfos(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        Log.i(TAG, "view on screen: " + location[0] + ", " + location[1]);

        int[] location1 = new int[2];
        view.getLocationInWindow(location1);
        Log.i(TAG, "view in window: " + location1[0] + ", " + location1[1]);

        Rect r = new Rect();
        view.getGlobalVisibleRect(r);
        Log.i(TAG, "view :" + r);
    }
}
