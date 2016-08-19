package com.wuzhen.scanqrcode.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.kokozu.app.BaseActivity;
import com.kokozu.lib.qrcode.camera.CameraManager;
import com.kokozu.lib.qrcode.view.QrCodeScanView;
import com.wuzhen.scanqrcode.R;

public class MainActivity extends BaseActivity implements QrCodeScanView.IOnScanQrCodeListener, View.OnClickListener {

    QrCodeScanView qrCodeScanView;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CameraManager.init(this);

        qrCodeScanView = (QrCodeScanView) findViewById(R.id.qrcode_scan_view);
        qrCodeScanView.onCreate(this);
        qrCodeScanView.setOnClickListener(this);
        qrCodeScanView.setIOnScanQrCodeListener(this);

        getSupportActionBar().setTitle("扫码");
    }

    @Override
    protected void onResume() {
        super.onResume();

        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                qrCodeScanView.onResume(MainActivity.this);
            }
        }, 300);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                qrCodeScanView.onPause();
            }
        }, 300);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        qrCodeScanView.onDestroy();
    }

    @Override
    public void onScanCompleted(boolean isSucceed, String resultString, Bitmap resultBitmap) {
        Log.e("test", "resultString: " + resultString);

        Intent intent = new Intent(mContext, SnsPreviewActivity.class);
        intent.putExtra("data", resultString);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        qrCodeScanView.onResume(this);
    }
}
