package cn.com.library.okhttps.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.com.library.okhttps.OkHttpUtils;
import cn.com.library.okhttps.callback.BitmapCallback;
import cn.com.library.okhttps.callback.FileCallBack;
import cn.com.library.okhttps.callback.GenericsCallback;
import cn.com.library.okhttps.callback.StringCallback;
import cn.com.library.okhttps.cookie.CookieJarImpl;
import okhttp3.Call;
import okhttp3.CookieJar;
import okhttp3.MediaType;
import okhttp3.Request;

/**
 * 文件名：OkHttpUsage
 * 描    述：okhttp的用法举例说明
 * 作    者：stt
 * 时    间：2017.09.14
 * 版    本：V1.1.0
 */
public class OkHttpUsage {
    private Context context;
    private static final String TAG = "PullRefreshActivity";
    private String mBaseUrl = "http://192.168.31.242:8888/okHttpServer/";
    private ProgressBar mProgressBar;


    public void getHtml(View view) {
        String url = "http://www.zhiyun-tech.com/App/Rider-M/changelog-zh.txt";
        url = "http://www.391k.com/api/xapi.ashx/info.json?key=bd_hyrzjjfb4modhj&size=10&page=1";
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    public void postString(View view) {
        String url = mBaseUrl + "user!postString";
        OkHttpUtils
                .postString()
                .url(url)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content((String) JSON.toJSON("zhy123"))
                .build()
                .execute(new MyStringCallback());

    }

    public void postFile(View view) {
        File file = new File(Environment.getExternalStorageDirectory(), "messenger_01.png");
        if (!file.exists()) {
            Toast.makeText(context, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = mBaseUrl + "user!postFile";
        OkHttpUtils
                .postFile()
                .url(url)
                .file(file)
                .build()
                .execute(new MyStringCallback());
    }

    public void getUser(View view) {
        String url = mBaseUrl + "user!getUser";
        OkHttpUtils
                .post()//
                .url(url)//
                .addParams("username", "hyman")//
                .addParams("password", "123")//
                .build()//
                .execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {

                    }
                });
    }

    public void getHttpsHtml(View view) {
        String url = "https://kyfw.12306.cn/otn/";

//                "https://12
//        url =3.125.219.144:8443/mobileConnect/MobileConnect/authLogin.action?systemid=100009&mobile=13260284063&pipe=2&reqtime=1422986580048&ispin=2";
        OkHttpUtils
                .get()//
                .url(url)//
                .id(101)
                .build()//
                .execute(new MyStringCallback());

    }

    public void getImage(View view) {
        String url = "http://images.csdn.net/20150817/1.jpg";
        OkHttpUtils
                .get()//
                .url(url)//
                .tag(this)//
                .build()//
                .connTimeOut(20000)
                .readTimeOut(20000)
                .writeTimeOut(20000)
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
//                        mTv.setText("onError:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Bitmap bitmap, int id) {
                        Log.e("TAG", "onResponse：complete");
//                        mImageView.setImageBitmap(bitmap);
                    }
                });
    }

    public void uploadFile(View view) {

        File file = new File(Environment.getExternalStorageDirectory(), "messenger_01.png");
        if (!file.exists()) {
            Toast.makeText(context, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("username", "张鸿洋");
        params.put("password", "123");

        Map<String, String> headers = new HashMap<>();
        headers.put("APP-Key", "APP-Secret222");
        headers.put("APP-Secret", "APP-Secret111");

        String url = mBaseUrl + "user!uploadFile";

        OkHttpUtils.post()//
                .addFile("mFile", "messenger_01.png", file)//
                .url(url)//
                .params(params)//
                .headers(headers)//
                .build()//
                .execute(new MyStringCallback());
    }


    public void multiFileUpload(View view) {
        File file = new File(Environment.getExternalStorageDirectory(), "messenger_01.png");
        File file2 = new File(Environment.getExternalStorageDirectory(), "test1#.txt");
        if (!file.exists()) {
            Toast.makeText(context, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("username", "张鸿洋");
        params.put("password", "123");

        String url = mBaseUrl + "user!uploadFile";
        OkHttpUtils.post()//
                .addFile("mFile", "messenger_01.png", file)//
                .addFile("mFile", "test1.txt", file2)//
                .url(url)
                .params(params)//
                .build()//
                .execute(new MyStringCallback());
    }


    public void downloadFile(View view) {
        String url = "https://github.com/hongyangAndroid/okhttp-utils/blob/master/okhttputils-2_4_1.jar?raw=true";
        OkHttpUtils//
                .get()//
                .url(url)//
                .build()//
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "gson-2.2.1.jar")//
                {

                    @Override
                    public void onBefore(Request request, int id) {
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        mProgressBar.setProgress((int) (100 * progress));
                        Log.e(TAG, "inProgress :" + (int) (100 * progress));
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError :" + e.getMessage());
                    }

                    @Override
                    public void onResponse(File file, int id) {
                        Log.e(TAG, "onResponse :" + file.getAbsolutePath());
                    }
                });
    }


    public void clearSession(View view) {
        CookieJar cookieJar = OkHttpUtils.getInstance().getOkHttpClient().cookieJar();
        if (cookieJar instanceof CookieJarImpl) {
            ((CookieJarImpl) cookieJar).getCookieStore().removeAll();
        }
    }


    public class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
        }

        @Override
        public void onAfter(int id) {
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
//            mTv.setText("onError:" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e(TAG, "onResponse：complete");
//            mTv.setText("onResponse:" + response);

            switch (id) {
                case 100:
                    Toast.makeText(context, "http", Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    Toast.makeText(context, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            Log.e(TAG, "inProgress:" + progress);
            mProgressBar.setProgress((int) (100 * progress));
        }
    }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        OkHttpUtils.getInstance().cancelTag(this);
//    }
}
