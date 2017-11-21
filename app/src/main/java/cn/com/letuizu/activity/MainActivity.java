package cn.com.letuizu.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.github.mzule.activityrouter.router.Routers;
import com.yanzhenjie.alertdialog.AlertDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;

import cn.com.letuizu.R;
import cn.com.library.base.BaseActivity;
import cn.com.library.permission.RationaleActivity;
import cn.com.library.utils.ToastUtils;
import io.rong.imkit.RongIM;
import letuizu.com.modulea.ShareActivity;
import letuizu.com.moduleb.activity.LiveModeActivity;
import letuizu.com.moduleb.activity.SkinActivity;
import letuizu.com.moduleim.other.SealAppContext;

/**
 * 文件名：MainActivity
 * 描    述：主界面
 * 作    者：stt
 * 时    间：2016.12.30
 * 版    本：V1.0.0
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button btna, btnb, permission, aliPay, share, dianBo, zhiBo, imBtn;
    private static final int REQUEST_CODE_PERMISSION = 100;
    private static final int REQUEST_CODE_SETTING = 300;
    //SD卡权限
    private static final int REQUEST_CODE_SDCARD = 103;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {

    }

    @Override
    protected void init() {
        btna = getView(R.id.button1);
        btnb = getView(R.id.button2);
        permission = getView(R.id.button3);
        aliPay = getView(R.id.button4);
        share = getView(R.id.button5);
        dianBo = getView(R.id.button6);
        zhiBo = getView(R.id.button7);
        btna.setOnClickListener(this);
        btnb.setOnClickListener(this);
        permission.setOnClickListener(this);
        aliPay.setOnClickListener(this);
        share.setOnClickListener(this);
        dianBo.setOnClickListener(this);
        zhiBo.setOnClickListener(this);
        imBtn = getView(R.id.button8);
        imBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent in = null;
        switch (view.getId()) {
            case R.id.button1:
                Routers.open(MainActivity.this, "modularization://zhiBo?name=suntengteng?age=16");
                break;
            case R.id.button2:
//                Routers.open(MainActivity.this, "modularization://dianBo");
//                Routers.open(MainActivity.this, "letuizu.com.moduleb.activity.ModuleBActivity");
                in = new Intent();
                in.setClassName(this, "letuizu.com.moduleb.activity.ModuleBActivity");
                in.putExtra("name", "孙腾腾");
                in.putExtra("age", 18);
                startActivity(in);
                break;
            case R.id.button3:
                // 申请权限。
                AndPermission.with(MainActivity.this)
                        .requestCode(REQUEST_CODE_PERMISSION)
                        .permission(Permission.PHONE, Permission.CAMERA)
                        .callback(permissionListener)
                        // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                        // 这样避免用户勾选不再提示，导致以后无法申请权限。
                        // 你也可以不设置。
                        .rationale(rationaleListener)
                        .start();
                break;
            case R.id.button4:
                in = new Intent();
                in.setClass(MainActivity.this, AliPayActivity.class);
                startActivity(in);
                break;
            case R.id.button5:
                in = new Intent();
                in.setClass(MainActivity.this, ShareActivity.class);
                startActivity(in);
                break;
            case R.id.button6:
                // 申请权限。
                AndPermission.with(MainActivity.this)
                        .requestCode(REQUEST_CODE_SDCARD)
                        .permission(Permission.STORAGE)
                        .callback(permissionListener)
                        // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                        // 这样避免用户勾选不再提示，导致以后无法申请权限。
                        // 你也可以不设置。
                        .rationale(rationaleListener)
                        .start();
                break;
            case R.id.button7:
                in = new Intent();
                in.setClass(MainActivity.this, LiveModeActivity.class);
                startActivity(in);
                break;
            case R.id.button8:
                in = new Intent();
                in.setClassName(this, "letuizu.com.moduleim.ui.activity.SplashActivity");
                startActivity(in);
                break;
        }
    }

    /**
     * 回调监听。
     */
    private PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
            switch (requestCode) {
                case REQUEST_CODE_PERMISSION:
                    Toast.makeText(MainActivity.this, cn.com.library.R.string.successfully, Toast.LENGTH_SHORT).show();
                    break;
                case REQUEST_CODE_SDCARD:
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, SkinActivity.class);
                    startActivity(intent);
                    break;
            }
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            switch (requestCode) {
                case REQUEST_CODE_PERMISSION: {
                    Toast.makeText(MainActivity.this, cn.com.library.R.string.failure, Toast.LENGTH_SHORT).show();
                    break;
                }
            }

            // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
            if (AndPermission.hasAlwaysDeniedPermission(MainActivity.this, deniedPermissions)) {
                // 第一种：用默认的提示语。
//                AndPermission.defaultSettingDialog(MainActivity.this, REQUEST_CODE_SETTING).show();

                // 第二种：用自定义的提示语。
                AndPermission.defaultSettingDialog(MainActivity.this, REQUEST_CODE_SETTING)
                        .setTitle("权限申请失败")
                        .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
                        .setPositiveButton("好，去设置")
                        .show();

//            第三种：自定义dialog样式。
//            SettingService settingHandle = AndPermission.defineSettingDialog(this, REQUEST_CODE_SETTING);
//            你的dialog点击了确定调用：
//            settingHandle.execute();
//            你的dialog点击了取消调用：
//            settingHandle.cancel();
            }
        }
    };

    /**
     * Rationale支持，这里自定义对话框。
     */
    private RationaleListener rationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
            // 这里使用自定义对话框，如果不想自定义，用AndPermission默认对话框：
            // AndPermission.rationaleDialog(Context, Rationale).show();

            // 自定义对话框。
            AlertDialog.newBuilder(MainActivity.this)
                    .setTitle(cn.com.library.R.string.title_dialog)
                    .setMessage(cn.com.library.R.string.message_permission_failed)
                    .setPositiveButton(cn.com.library.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            rationale.resume();
                        }
                    })
                    .setNegativeButton(cn.com.library.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            rationale.cancel();
                        }
                    }).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_SETTING: {
                Toast.makeText(this, cn.com.library.R.string.message_setting_back, Toast.LENGTH_LONG).show();
                break;
            }
        }
    }
}
