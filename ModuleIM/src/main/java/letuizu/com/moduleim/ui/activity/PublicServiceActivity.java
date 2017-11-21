package letuizu.com.moduleim.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import letuizu.com.moduleim.R;


public class PublicServiceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_list);
        setTitle(R.string.de_actionbar_set_public);
        Button rightButton = getHeadRightButton();
        rightButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.de_ic_add));
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PublicServiceActivity.this, PublicServiceSearchActivity.class);
                startActivity(intent);
            }
        });
    }


}
