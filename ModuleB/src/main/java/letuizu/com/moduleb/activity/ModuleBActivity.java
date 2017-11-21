package letuizu.com.moduleb.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mzule.activityrouter.annotation.Router;

import java.util.HashMap;
import java.util.Map;

import cn.com.library.base.BaseActivity;
import cn.com.library.okhttps.OkHttpUtils;
import cn.com.library.okhttps.callback.GenericsCallback;
import cn.com.library.okhttps.utils.JsonGenericsSerializator;
import letuizu.com.moduleb.R;
import okhttp3.Call;

/**
 * 文件名：ModuleBActivity
 * 描    述：测试组件化开发的类
 * 作    者：stt
 * 时    间：2017.10.15
 * 版    本：V1.0.0
 */
@Router("dianBo")
public class ModuleBActivity extends BaseActivity {
    TextView tv, commentInfo;
    Button startBtn, endBtn;
    boolean isFlag = false;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_moduleb);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        tv = getView(R.id.moduleb_textview);
        String name = getIntent().getStringExtra("name");
        int age = getIntent().getIntExtra("age", 0);
        tv.setText(name + age);
    }

    @Override
    protected void init() {
        startBtn = getView(R.id.start_insert_comment);
        endBtn = getView(R.id.end_insert_comment);
        commentInfo = getView(R.id.comment_info);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < 100; i++) {
                    request(i);
                }
            }
        });

        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    private void request(final int index) {
        Map<String, Object> params = new HashMap<>();
        try {
            params.put("video_id", index);
            params.put("user_id", index);
            params.put("comment_content", "测试循环插入数据");
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpUtils.post().url("https://wechatdev.letuizu.com/index.php/Ltadmin/comment/comment_add").params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(String response, int id) {
                commentInfo.setText(response + "这是第：" + index + "个");
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                commentInfo.setText(e.getMessage().toString() + index);
            }
        });
    }
}

