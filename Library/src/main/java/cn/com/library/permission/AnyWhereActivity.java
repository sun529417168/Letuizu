/*
 * Copyright © Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.com.library.permission;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import cn.com.library.R;

/**
 * 文件名：AnyWhereActivity
 * 描    述：动态申请权限的任何方法的举例说明
 * 作    者：stt
 * 时    间：2017.09.14
 * 版    本：V1.0.0
 */
public class AnyWhereActivity extends AppCompatActivity implements View.OnClickListener {

    private PermissionRequest permissionRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anywhere);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.btn_req).setOnClickListener(this);
        permissionRequest = new PermissionRequest(this, new PermissionRequest.PermissionCallback() {
            @Override
            public void onSuccessful() {
                Toast.makeText(AnyWhereActivity.this, R.string.successfully, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure() {
                Toast.makeText(AnyWhereActivity.this, R.string.failure, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_req) {
            permissionRequest.request();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home: {
                finish();
                break;
            }
        }
        return true;
    }
}
