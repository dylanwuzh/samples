package com.wuzhendev.measureview;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

public class MainActivity extends Activity {

    static final String TAG = "MeasureView";

    private View view;
    private View hidden;

    private static int sGlobalCount;
    private static int sPreDrawCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = findViewById(R.id.root);
        hidden = findViewById(R.id.hidden);

        /*
         * measure
         */
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        String message = "measure, width: " + view.getMeasuredWidth() + ", height: " +
                         view.getMeasuredHeight() + "\n";
        Log.i(TAG, message);

        /*
         * ViewTreeObserver.OnGlobalLayoutListener
         * 布局改变时或视图的可视状态改变时该事件会被调用，比如软键盘弹出、视图隐藏
         */
        view.getViewTreeObserver().addOnGlobalLayoutListener(wholeGlobalListener);
        hidden.postDelayed(new Runnable() {

            @Override
            public void run() {
                hidden.setVisibility(View.GONE); // hide View
            }
        }, 3000);

        view.getViewTreeObserver()
            .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT >= 16) {
                        view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                    else {
                        view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }

                    String message = "onGlobalLayout, width: " + view.getWidth() + ", height: " +
                                     view.getHeight() + "\n";
                    Log.i(TAG, message);
                }
            });

        /*
         * ViewTreeObserver.OnPreDrawListener
         * 视图将要绘制时该事件会被调用
         */
        view.getViewTreeObserver().addOnPreDrawListener(wholePreDrawListener);
        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {
                view.getViewTreeObserver().removeOnPreDrawListener(this);

                String message = "onPreDraw, width: " + view.getWidth() + ", height: " +
                                 view.getHeight() + "\n";
                Log.i(TAG, message);

                return true;
            }
        });

        /*
         * View.post
         */
        view.post(new Runnable() {

            @Override
            public void run() {
                String message = "View.post(), width: " + view.getWidth() + ", height: " +
                                 view.getHeight() + "\n";
                Log.i(TAG, message);
            }
        });

        /*
         * View.OnLayoutChangeListener
         */
        view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {

            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom,
                    int oldLeft, int oldTop, int oldRight, int oldBottom) {

                view.removeOnLayoutChangeListener(this);

                String message = "View.OnLayoutChangeListener, width: " + view.getWidth() +
                                 ", height: " +
                                 view.getHeight() + "\n";
                Log.i(TAG, message);
            }
        });

    }

    private final ViewTreeObserver.OnGlobalLayoutListener wholeGlobalListener
            = new ViewTreeObserver.OnGlobalLayoutListener() {

        @Override
        public void onGlobalLayout() {
            sGlobalCount++;
            Log.d(TAG, "onGlobalLayout invoked: " + sGlobalCount + " times");
        }
    };

    private final ViewTreeObserver.OnPreDrawListener wholePreDrawListener
            = new ViewTreeObserver.OnPreDrawListener() {

        @Override
        public boolean onPreDraw() {
            sPreDrawCount++;
            Log.d(TAG, "onPreDraw invoked: " + sPreDrawCount + " times");
            return true;
        }
    };
}
