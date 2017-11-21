package cn.com.letuizu.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;

import java.util.Map;

import cn.com.letuizu.R;
import cn.com.letuizu.alipay.OrderInfoUtil2_0;
import cn.com.letuizu.alipay.PayResult;
import cn.com.library.base.BaseActivity;

/**
 * 文件名：BaseActivity
 * 描    述：activity的基类，一些共同的方法写在这
 * 作    者：stt
 * 时    间：2016.10.20
 * 版    本：V1.0.0
 */
public class AliPayActivity extends BaseActivity {
    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2017101909394700";

    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    public static final String PID = "2088821207648237";
    /**
     * 支付宝账户登录授权业务：入参target_id值
     */
    public static final String TARGET_ID = "letuizutechnology@letuizu.com";

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /**
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA2_PRIVATE = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCm999oQxZpBMMqJLvCnrEJnQcFqZcDt6fFPjwQMZPJHMQJVmfYKH/y2wtuzoQ5rQUZYlaI38MeBCruMj1AiVYOqfw7FYJNveL3tM4BgmRW+C6xBi5jpGvCC172lCkHYY7Hr1MbMNzK4m2y+Pg2BPP5l33mWuq+x4Q3+ny46+5IM19AaZ7KAeMJebvnW153xDwc+FXEojFh1TLgfYN/rp3VN/rl3zwiK7i8I2WzcQFYlYAhGUdwC9nhKR4v+b0UKSD8FcPmsJzraclDsTNV05BX8p2maTsZhLQDsotmdgo2r5OrJR9qaBC+JRkH65khsXTDPWkXCk3vtWT2qFthvLLDAgMBAAECggEBAIGwvTZGDe0qxtFAURtdmhPmhaLPVL6Nc+JQceT+LVWkY10BYY3T/3ZK0DmUbb5C91f9O0v7ypnREOuN2Gf9hPvE62eVUG5EVTykkvM3LesjwVi9fWDmQNErdeq/sJLMI1RViY7wSD/K2vyOSHXlKQCwvBkme3oOjVPR2/jjQWWQRr2abtV8G0uhIp3iJ3sLWbeaJEzAf71UTjFwtFsf8BCcU5Q0FHiUhsPmMUPvWZ3GU+QGIsi1uYt+r1zEQ6A/HOPJqZNDZSeDe3349XvFIAzKyOKN77L92kn9Sw96cbZJUSnFeaJu75XZ+mU8MplNvT+MQXXl9kQnEXn/d0wmlrkCgYEA5V8lm6eqnlhn8cloOErBfGhzr51nPIEF+TqzzEQSWXmBWtRFXtrt+j6ByeaumFmjmo+bXpXbHwV6IrXhrY3pexEE131UzFHQge6M8icw6WqUc5Af5PvjZCfem8eUFR4yShK7vgTjg7vczFP27O6CUTBBpomQl/NMmaZwmmSXoLUCgYEAuloduXXG5H+uyvtclbUbEK1Kz7Amc3F5BqKMwCFFrqSUzfC+UOazMGZh7k34h3nSu98PAa0VOaR9RtLkefmmW/ZtxtNLTsHgrMt/DOwjoY/MrsuEPVAtuXz0DQGPBMHa55XQQEqQi3nkzhzNSBINMhTQX/0tSiyZsBDnPQzySJcCgYAbCkJ2vSdiD24BlQrSSA3TSsUImPfIrDU2EnOLyyKWpbpYKNocupZ/f8rxMF642yhsWi2o0uXIHG8vlF2MhkRWG8WEWeQbSjHYf3TiPziG6+egUAdSiVdfQUv+9WJBsxOeo+K6Hf1sVENNW0Cq4ds/Ev9KUMFZCHepTXPM37r4WQKBgQCphYT4y31tLAH0QQmOXrwKRbqCiHnSFu3D7Wcg+KONJccAOPrIWbW0V87KnD9z7oyXv2jFW5CtaUdVgE+77jE6A0kFz/z33kLmrhAbz0zykf/fGuBnbmLKFDNnCX3zE+5xMcXKVop7dx1/qo/L+i/q2ShdydDDYyNEGXxh+z8EbQKBgQDLevWTQSS4A4lB6bQ1pHVnw6/RuylKdQtXrHEIl+Qv6hzmK17xZjvc2J5huvYPSSu2s47AVnCV+fCexkAh+4tAhwwrxXMj2mad9+Xtq80Eon/4fVzM5SdiYUQ88AJCJKEVN5jpFeCDMsPeHUtuBFVrzj1oelDIJarKm0q8sbXSeA==";
    public static final String RSA_PRIVATE = "";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_alipay);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {

    }

    @Override
    protected void init() {
        getView(R.id.activity_alipay_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payV2(view);
            }
        });
    }
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    Log.i("resultStatus",resultStatus);
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(AliPayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(AliPayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };
    /**
     * 支付宝支付业务
     *
     * @param v
     */
    public void payV2(View v) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialoginterface, int i) {
                    //
                    return;
                }
            }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(AliPayActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
