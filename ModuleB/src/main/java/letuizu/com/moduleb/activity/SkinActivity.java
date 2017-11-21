package letuizu.com.moduleb.activity;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aliyun.vodplayer.media.AliyunLocalSource;
import com.aliyun.vodplayer.media.AliyunPlayAuth;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.aliyun.vodplayer.utils.VcPlayerLog;
import com.aliyun.vodplayerview.utils.ScreenUtils;
import com.aliyun.vodplayerview.widget.AliyunVodPlayerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.com.library.base.BaseActivity;
import letuizu.com.moduleb.R;
import letuizu.com.moduleb.util.CleanLeakUtils;
import letuizu.com.moduleb.util.ScreenStatusController;

/**
 * 文件名：SkinActivity
 * 描    述：视频点播的类
 * 作    者：stt
 * 时    间：2017.11.3
 * 版    本：V1.0.0
 */
public class SkinActivity extends BaseActivity {
    private AliyunVodPlayerView mAliyunVodPlayerView = null;
    private SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SS");
    private List<String> logStrs = new ArrayList<>();

    private String mVid = null;
    private String mAuthinfo = null;

    private AliyunPlayAuth mPlayAuth = null;
    private AliyunLocalSource mLocalSource = null;

    private ScreenStatusController mScreenStatusController = null;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_skin);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {

    }

    @Override
    protected void init() {
        findViewById(R.id.blue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAliyunVodPlayerView.setTheme(AliyunVodPlayerView.Theme.Blue);
            }
        });
        findViewById(R.id.green).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAliyunVodPlayerView.setTheme(AliyunVodPlayerView.Theme.Green);
            }
        });
        findViewById(R.id.orange).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAliyunVodPlayerView.setTheme(AliyunVodPlayerView.Theme.Orange);
            }
        });
        findViewById(R.id.red).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAliyunVodPlayerView.setTheme(AliyunVodPlayerView.Theme.Red);
            }
        });
        mAliyunVodPlayerView = (AliyunVodPlayerView) findViewById(R.id.video_view);
        String sdDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test_save_cache";
        mAliyunVodPlayerView.setPlayingCache(true, sdDir, 60 * 60 /*时长, s */, 300 /*大小，MB*/);
        mAliyunVodPlayerView.setTheme(AliyunVodPlayerView.Theme.Orange);
        mAliyunVodPlayerView.setOnPreparedListener(new IAliyunVodPlayer.OnPreparedListener() {//播放准备开始
            @Override
            public void onPrepared() {
                logStrs.add(format.format(new Date()) + getString(R.string.log_prepare_success));
                Toast.makeText(SkinActivity.this.getApplicationContext(), R.string.toast_prepare_success, Toast.LENGTH_SHORT).show();
            }
        });

        mAliyunVodPlayerView.setOnCompletionListener(new IAliyunVodPlayer.OnCompletionListener() {//播放完毕
            @Override
            public void onCompletion() {
                logStrs.add(format.format(new Date()) + getString(R.string.log_play_completion));
                Toast.makeText(SkinActivity.this.getApplicationContext(), R.string.toast_play_compleion, Toast.LENGTH_SHORT).show();
            }
        });

        mAliyunVodPlayerView.setOnFirstFrameStartListener(new IAliyunVodPlayer.OnFirstFrameStartListener() {//播放第一贞开始
            @Override
            public void onFirstFrameStart() {
                Map<String, String> debugInfo = mAliyunVodPlayerView.getAllDebugInfo();
                long createPts = 0;
                if (debugInfo.get("create_player") != null) {
                    String time = debugInfo.get("create_player");
                    createPts = (long) Double.parseDouble(time);
                    logStrs.add(format.format(new Date(createPts)) + getString(R.string.log_player_create_success));
                }
                if (debugInfo.get("open-url") != null) {
                    String time = debugInfo.get("open-url");
                    long openPts = (long) Double.parseDouble(time) + createPts;
                    logStrs.add(format.format(new Date(openPts)) + getString(R.string.log_open_url_success));
                }
                if (debugInfo.get("find-stream") != null) {
                    String time = debugInfo.get("find-stream");
                    long findPts = (long) Double.parseDouble(time) + createPts;
                    logStrs.add(format.format(new Date(findPts)) + getString(R.string.log_request_stream_success));
                }
                if (debugInfo.get("open-stream") != null) {
                    String time = debugInfo.get("open-stream");
                    long openPts = (long) Double.parseDouble(time) + createPts;
                    logStrs.add(format.format(new Date(openPts)) + getString(R.string.log_start_open_stream));
                }
                logStrs.add(format.format(new Date()) + getString(R.string.log_first_frame_played));
            }
        });

        mAliyunVodPlayerView.setOnChangeQualityListener(new IAliyunVodPlayer.OnChangeQualityListener() {
            @Override
            public void onChangeQualitySuccess(String finalQuality) {
                logStrs.add(format.format(new Date()) + getString(R.string.log_change_quality_success));
                Toast.makeText(SkinActivity.this.getApplicationContext(), getString(R.string.log_change_quality_success), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChangeQualityFail(int code, String msg) {
                logStrs.add(format.format(new Date()) + getString(R.string.log_change_quality_fail) + " : " + msg);
                Toast.makeText(SkinActivity.this.getApplicationContext(), getString(R.string.log_change_quality_fail), Toast.LENGTH_SHORT).show();
            }
        });

        mAliyunVodPlayerView.setOnStoppedListner(new IAliyunVodPlayer.OnStoppedListener() {//播放停止按钮
            @Override
            public void onStopped() {
                Toast.makeText(SkinActivity.this.getApplicationContext(), R.string.log_play_stopped, Toast.LENGTH_SHORT).show();
            }
        });


        mAliyunVodPlayerView.enableNativeLog();

        setPlaySource();

        mScreenStatusController = new ScreenStatusController(this);
        mScreenStatusController.setScreenStatusListener(new ScreenStatusController.ScreenStatusListener() {
            @Override
            public void onScreenOn() {
            }

            @Override
            public void onScreenOff() {

            }
        });

        mScreenStatusController.startListen();
    }

    private void setPlaySource() {
//        String type = getIntent().getStringExtra("type");
        String type = "localSource";
        if ("authInfo".equals(type)) {
            //auth方式
            //NOTE： 注意过期时间。特别是重播的时候，可能已经过期。所以重播的时候最好重新请求一次服务器。
            mVid = getIntent().getStringExtra("vid");
            String authInfo = getIntent().getStringExtra("authinfo");
            AliyunPlayAuth.AliyunPlayAuthBuilder aliyunPlayAuthBuilder = new AliyunPlayAuth.AliyunPlayAuthBuilder();
            aliyunPlayAuthBuilder.setVid(mVid);
            aliyunPlayAuthBuilder.setPlayAuth(authInfo);
            aliyunPlayAuthBuilder.setQuality(IAliyunVodPlayer.QualityValue.QUALITY_ORIGINAL);
            mPlayAuth = aliyunPlayAuthBuilder.build();
            mAliyunVodPlayerView.setAuthInfo(mPlayAuth);
        } else if ("localSource".equals(type)) {
            //本地播放
            String url = "http://player.alicdn.com/video/aliyunmedia.mp4";
            AliyunLocalSource.AliyunLocalSourceBuilder asb = new AliyunLocalSource.AliyunLocalSourceBuilder();
            asb.setSource(url);
            mLocalSource = asb.build();
            mAliyunVodPlayerView.setLocalSource(mLocalSource);
            mAliyunVodPlayerView.setAutoPlay(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        updatePlayerViewMode();
        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.resume();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.stop();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("lfj1019", " orientation = " + getResources().getConfiguration().orientation);
        updatePlayerViewMode();
    }

    private void updatePlayerViewMode() {
        if (mAliyunVodPlayerView != null) {
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {                //转为竖屏了。
                //显示状态栏
                if (Build.DEVICE.equalsIgnoreCase("mx5")
                        || Build.DEVICE.equalsIgnoreCase("Redmi Note2")
                        || Build.DEVICE.equalsIgnoreCase("Z00A_1")
                        || Build.DEVICE.equalsIgnoreCase("hwH60-L02")) {
//                    getSupportActionBar().show();
                } else if (!(Build.DEVICE.equalsIgnoreCase("V4") && Build.MANUFACTURER.equalsIgnoreCase("Meitu"))) {
//                    getSupportActionBar().show();
                }
                this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                mAliyunVodPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                //设置view的布局，宽高之类
                LinearLayout.LayoutParams aliVcVideoViewLayoutParams = (LinearLayout.LayoutParams) mAliyunVodPlayerView.getLayoutParams();
                aliVcVideoViewLayoutParams.height = (int) (ScreenUtils.getWidth(this) * 9.0f / 16);
                aliVcVideoViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                if (!(Build.DEVICE.equalsIgnoreCase("V4") && Build.MANUFACTURER.equalsIgnoreCase("Meitu"))) {
//                    aliVcVideoViewLayoutParams.topMargin = getSupportActionBar().getHeight();
                }

            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {                //转到横屏了。
                //隐藏状态栏
                if (Build.DEVICE.equalsIgnoreCase("mx5")
                        || Build.DEVICE.equalsIgnoreCase("Redmi Note2")
                        || Build.DEVICE.equalsIgnoreCase("Z00A_1")
                        || Build.DEVICE.equalsIgnoreCase("hwH60-L02")) {
//                    getSupportActionBar().hide();
                } else if (!(Build.DEVICE.equalsIgnoreCase("V4") && Build.MANUFACTURER.equalsIgnoreCase("Meitu"))) {
//                    getSupportActionBar().hide();
                    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    mAliyunVodPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                }

                //设置view的布局，宽高
                LinearLayout.LayoutParams aliVcVideoViewLayoutParams = (LinearLayout.LayoutParams) mAliyunVodPlayerView.getLayoutParams();
                aliVcVideoViewLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                aliVcVideoViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                if (!(Build.DEVICE.equalsIgnoreCase("V4") && Build.MANUFACTURER.equalsIgnoreCase("Meitu"))) {
                    aliVcVideoViewLayoutParams.topMargin = 0;
                }

            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.destroy();
            mAliyunVodPlayerView = null;
        }

        CleanLeakUtils.fixInputMethodManagerLeak(this);
        mScreenStatusController.stopListen();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAliyunVodPlayerView != null) {
            boolean handler = mAliyunVodPlayerView.onKeyDown(keyCode, event);
            if (!handler) {
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //解决某些手机上锁屏之后会出现标题栏的问题。
        VcPlayerLog.d("lfj1030", "onWindowFocusChanged = " + hasFocus);
        updatePlayerViewMode();
    }
}
