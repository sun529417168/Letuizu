package letuizu.com.modulea;

import android.os.Bundle;
import android.widget.TextView;

import com.github.mzule.activityrouter.annotation.Router;

import cn.com.library.base.BaseActivity;

/**
 * Created by letuizu on 2017/10/19.
 */
@Router("zhiBo")
public class ModuleActivity extends BaseActivity {
    TextView tv;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_modulea);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        tv = findViewById(R.id.modulea_textview);
        String name = getIntent().getStringExtra("name");
        String age = getIntent().getStringExtra("age");
        tv.setText(name + age);
    }

    @Override
    protected void init() {

    }
}
