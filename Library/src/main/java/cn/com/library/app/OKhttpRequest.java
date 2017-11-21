package cn.com.library.app;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.library.okhttps.OkHttpUtils;
import cn.com.library.okhttps.callback.BitmapCallback;
import cn.com.library.okhttps.callback.FileCallBack;
import cn.com.library.okhttps.callback.GenericsCallback;
import cn.com.library.okhttps.callback.StringCallback;
import cn.com.library.okhttps.utils.JsonGenericsSerializator;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;

/**
 * 文件名：OKhttpRequest
 * 描    述：okhttp的网络请求，各种请求的方式
 * 作    者：stt
 * 时    间：2017.10.19
 * 版    本：V1.0.0
 */
public class OKhttpRequest {
    private Activity activity;
    private String mBaseUrl = "http://192.168.31.242:8888/okHttpServer/";

    private static final String TAG = "MainActivity";

    private TextView mTv;
    private ImageView mImageView;
    private ProgressBar mProgressBar;


    public class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
            activity.setTitle("loading...");
        }

        @Override
        public void onAfter(int id) {
            activity.setTitle("Sample-okHttp");
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
            mTv.setText("onError:" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e(TAG, "onResponse：complete");
            mTv.setText("onResponse:" + response);

            switch (id) {
                case 100:
                    Toast.makeText(activity, "http", Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    Toast.makeText(activity, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            Log.e(TAG, "inProgress:" + progress);
            mProgressBar.setProgress((int) (100 * progress));
        }
    }


    public void getHtml(View view) {
        String url = "http://www.zhiyun-tech.com/App/Rider-M/changelog-zh.txt";
        url = "http://www.391k.com/api/xapi.ashx/info.json?key=bd_hyrzjjfb4modhj&size=10&page=1";
        OkHttpUtils.get().url(url).id(100).build().execute(new MyStringCallback());
    }


    public void postString(View view) {
        String url = mBaseUrl + "user!postString";
        OkHttpUtils
                .postString()
                .url(url)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
//                .content(new Gson().toJson(new User("zhy", "123")))
                .build()
                .execute(new MyStringCallback());

    }

    public void postFile(View view) {
        File file = new File(Environment.getExternalStorageDirectory(), "123456.jpg");
        if (!file.exists()) {
            Toast.makeText(activity, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = "http://www.letuizutest.com/index.php/home/video/uploads_img";
        OkHttpUtils
                .postFile()
                .url(url)
                .file(file)
                .build()
                .execute(new MyStringCallback());


    }

    public void getUser(View view) {
        String url = mBaseUrl + "user!getUser";
//        OkHttpUtils
//                .post()//
//                .url(url)//
//                .addParams("username", "hyman")//
//                .addParams("password", "123")//
//                .build()//
//                .execute(new GenericsCallback<User>(new JsonGenericsSerializator()) {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        mTv.setText("onError:" + e.getMessage());
//                    }
//
//                    @Override
//                    public void onResponse(User response, int id) {
//                        mTv.setText("onResponse:" + response);
//                    }
//                });
    }


    public void getUsers(View view) {
        /*Map<String, String> params = new HashMap<String, String>();
        params.put("name", "zhy");
        String url = mBaseUrl + "user!getUsers";
        OkHttpUtils//
                .post()//
                .url(url)//
//                .params(params)//
                .build()//
                .execute(new ListUserCallback()//
                {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mTv.setText("onError:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(List<User> response, int id) {
                        mTv.setText("onResponse:" + response);
                    }
                });*/
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
        mTv.setText("");
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
                        mTv.setText("onError:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Bitmap bitmap, int id) {
                        Log.e("TAG", "onResponse：complete");
                        mImageView.setImageBitmap(bitmap);
                    }
                });
    }


    public void uploadFile(View view) {

        File file = new File(Environment.getExternalStorageDirectory(), "123456.jpg");
        if (!file.exists()) {
            Toast.makeText(activity, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("username", "张鸿洋");
        params.put("password", "123");

        Map<String, String> headers = new HashMap<>();
        headers.put("APP-Key", "APP-Secret222");
        headers.put("APP-Secret", "APP-Secret111");

//        String url = mBaseUrl + "user!uploadFile";
        String url = "http://www.letuizutest.com/index.php/home/video/uploads_img";

        OkHttpUtils.post()//
                .addFile("mFile", "123456.jpg", file)//
                .url(url)//
                .params(params)//
                .headers(headers)//
                .build()//
                .execute(new MyStringCallback());
    }

    public void multiFileUpload(View view) {
        File file = new File(Environment.getExternalStorageDirectory(), "123456.jpg");
        File file2 = new File(Environment.getExternalStorageDirectory(), "test1#.txt");
        if (!file.exists()) {
            Toast.makeText(activity, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, Object> params = new HashMap<>();
        String url = "http://www.letuizutest.com/index.php/home/video/uploads_img";
        OkHttpUtils.post().addFile("mFile", "messenger_01.png", file).addFile("mFile", "test1.txt", file2).url(url).params(params).build().execute(new MyStringCallback());
    }


    public void downloadFile(View view) {
        String url = "https://github.com/hongyangAndroid/okhttp-utils/blob/master/okhttputils-2_4_1.jar?raw=true";
        OkHttpUtils.get().url(url).build().execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "gson-2.2.1.jar") {

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

    public void otherRequestDemo(View view) {
        //also can use delete ,head , patch
        String url = "https://github.com/hongyangAndroid/okhttp-utils/blob/master/okhttputils-2_4_1.jar?raw=true";
        OkHttpUtils
                .put()//
                .url("http://11111.com")
                .requestBody
                        ("may be something")//
                .build()//
                .execute(new MyStringCallback());
        try {
            OkHttpUtils.head().url(url).addParams("name", "zhy").build().execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void request() {
        Map<String, Object> params = new HashMap<>();
        try {
            params.put("Name", "600056");
            params.put("PassWord", "123");
            params.put("Module", "XBGD");
            //
            params.put("DeviceID", "7dd45a6ce4ad4a71bc5ffab35a273e6d");
//            params.put("DeviceID", PushServiceFactory.getCloudPushService().getDeviceId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpUtils.post().url("http://nwpu-ism.dlax.com.cn/API/Login/LoginbySSO").params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
//                tv.setText(e.getMessage().toString());
            }

            @Override
            public void onResponse(String response, int id) {
//                tv.setText(response);
            }
        });
    }
}
