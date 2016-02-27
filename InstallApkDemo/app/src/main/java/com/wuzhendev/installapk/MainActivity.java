package com.wuzhendev.installapk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_install).setOnClickListener(this);
        findViewById(R.id.btn_uninstall).setOnClickListener(this);
    }

    private void copyApk2SDCard() {
        String filePath = createApkSDFilePath(this);
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.mkdirs();
            }
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            fos = new FileOutputStream(file);
            is = getAssets().open("installed.apk");

            byte[] bytes = new byte[1024];
            int count;
            while ((count = is.read(bytes)) != -1) {
                fos.write(bytes, 0, count);
            }
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(fos);
            close(is);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_install:
                copyApk2SDCard();
                installApk(this, createApkSDFilePath(this));
                break;

            case R.id.btn_uninstall:
                uninstallApk(this, "com.wuzhendev.installed");
                break;

            default:
                break;
        }
    }

    /**
     * 安装 APK。
     *
     * @param filePath
     *         APK 文件路径
     */
    public static void installApk(Context context, String filePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(filePath)),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 卸载 APK。
     *
     * @param packageName
     *         应用的包名
     */
    public static void uninstallApk(Context context, String packageName) {
        Uri packageURI = Uri.parse("package:" + packageName);
        Intent intent = new Intent(Intent.ACTION_DELETE, packageURI);
        context.startActivity(intent);
    }

    private static String createApkSDFilePath(Context context) {
        return Environment.getExternalStorageDirectory() + "/" + context.getPackageName() + "/" +
               "installed.apk";
    }

    private static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
