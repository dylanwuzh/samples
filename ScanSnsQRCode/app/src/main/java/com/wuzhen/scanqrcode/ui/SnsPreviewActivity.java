package com.wuzhen.scanqrcode.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.kokozu.app.BaseActivity;
import com.kokozu.net.HttpRequestManager;
import com.kokozu.net.RequestParams;
import com.kokozu.net.SimpleRespondListener;
import com.kokozu.net.result.HttpResult;
import com.wuzhen.scanqrcode.R;
import com.wuzhen.scanqrcode.net.MovieRequest;
import com.wuzhen.scanqrcode.net.NetworkResponse;

/**
 * @author wuzhen
 * @version Version 1.0, 2016-04-19
 */
public class SnsPreviewActivity extends BaseActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sns_preview);

        webView = (WebView) findViewById(R.id.web_view);
        settingWebView();

        getSupportActionBar().setTitle("帖子预览");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String url = getIntent().getStringExtra("data");
        sendQueryPostDetail(url);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();

        webView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

        webView.onPause();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void settingWebView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setMinimumFontSize(16);

        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(false);

        settings.setDatabaseEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
        // 开启 DOM storage API 功能
        settings.setDomStorageEnabled(true);
    }

    private void sendQueryPostDetail(String url) {
        showProgressDialog();

        HttpRequestManager.getInstance().getJSON(new MovieRequest(this, url, new RequestParams()), "", true, new SimpleRespondListener<NetworkResponse>() {

            @Override
            public void onSuccess(NetworkResponse data, HttpResult result) {
                dismissProgressDialog();

                if (!TextUtils.isEmpty(data.result))
                    webView.loadDataWithBaseURL(null, data.result, "text/html", "utf-8", null);
                else
                    Toast.makeText(mContext, "该帖子没有数据", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int status, String error, HttpResult result) {
                dismissProgressDialog();
                Toast.makeText(mContext, "查询帖子详情失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
