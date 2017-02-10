package com.haofugang.waitpage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.haofugang.waitpage.utils.FileUtil;
import com.haofugang.waitpage.utils.MainDateCachesUtil;
import com.haofugang.waitpage.utils.Mysharedpreference;

import java.util.Timer;
import java.util.TimerTask;

import static com.haofugang.waitpage.R.id.gif;
/**
 * @postmusic
 * http://www.postmusic.cn/
 * @author  haofugang
 * 启动动画页
 */
public class WaitActivity extends Activity {

    private MainDateCachesUtil mainDateCachesUtil;
    private ImageView gf1;
    private String wait_icon_path;
    private String wait_icon_type;
    private TextView skip;
    private Timer timer;
    private String app_boot_url;
    private ImageView await_img; //固定的启动图
    private View advert_view;    // 广告页
    public final int ADVERTFLAGE = 10;
    public final int AWAITFLAGE = 11;
    private Timer advert_timer;
    private String OtherImg = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1486733583572&di=d32b6c089fb0f65163fe868db45d561f&imgtype=0&src=http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fitem%2F201507%2F31%2F20150731180246_HkyUz.thumb.700_0.jpeg";
    private String img_url = "";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.wait_activity);
        mainDateCachesUtil = new MainDateCachesUtil("", "", WaitActivity.this);
        initView();
        doThing();

    }

    private void initView() {

        gf1 = (ImageView) findViewById(gif);
        skip = (TextView) findViewById(R.id.skip);
        await_img = (ImageView) findViewById(R.id.await_img);
        advert_view = findViewById(R.id.advert_view);

        wait_icon_path = Mysharedpreference.getstring(WaitActivity.this, "wait_icon", "wait_icon_path");
        wait_icon_type = Mysharedpreference.getstring(WaitActivity.this, "wait_icon", "wait_icon_type");
        app_boot_url = Mysharedpreference.getstring(WaitActivity.this, "wait_icon", "app_boot_url");
        img_url = Mysharedpreference.getstring(WaitActivity.this, "wait_icon", "img_url");
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                JumpActivity();
            }
        });
        gf1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (StringUtils.isEmpty(app_boot_url)) {
                        return;
                    } else {
                        Intent intent = new Intent(WaitActivity.this, MainActivity.class);
                        intent.putExtra("link_url", app_boot_url);
                        startActivity(intent);
                        timer.cancel();
                        finish();
                    }
            }
        });
    }

    private void doThing() {

        //如果存的的没有图片路径就直接进行设置图片
        if (StringUtils.isEmpty(wait_icon_path)) {
            GetStartPictureRequest();
            SetWaitBackground();
        } else {
            if (!FileUtil.fileIsExists(wait_icon_path)) {
                String[] sp_body = {"", "0", ""};//第一个参数是 启动图的绝对路径  第三个是启动图的网络的地址用于判断是不是下次还要不要下载
                String[] sp_variable = {"wait_icon_path", "wait_icon_type", "img_url"};
                Mysharedpreference.mysharedpreference(WaitActivity.this, "wait_icon", sp_body, sp_variable);
            }
            GetStartPictureRequest();
            SetWaitBackground();

        }

    }


    @Override
    public void onBackPressed() {
    }

    public void GetStartPictureRequest() {
        /**
         * 使用者可以在此处进行网络请求并且进行获取请求下来的图片地址和跳转路径
         */
        if(OtherImg.equals(img_url))
        {
            mainDateCachesUtil.DownLoaderImg("http://himg2.huanqiu.com/attachment2010/2013/0411/20130411025542132.jpg", "https://github.com/pricnehao");
        }else {
            mainDateCachesUtil.DownLoaderImg(OtherImg, "http://weibo.com/3083667295/profile?topnav=1&wvr=6&is_all=1");
        }

    }


    private void SetWaitBackground() {

        wait_icon_type = StringUtils.isEmpty(wait_icon_type) ? "0" : wait_icon_type;
        if ((!StringUtils.isEmpty(wait_icon_path)) && wait_icon_type.equals("1")) {
            advert_view.setVisibility(View.GONE);
            await_img.setVisibility(View.VISIBLE);
            advert_timer = new Timer();
            advert_timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    Message message_data = new Message();
                    message_data.what = ADVERTFLAGE;
                    mHandler.sendMessage(message_data);
                }
            }, 1000);


            gf1.setScaleType(ImageView.ScaleType.FIT_XY);
            if (wait_icon_path.substring(wait_icon_path.lastIndexOf('.')).equals(".gif")) {
                Glide.with(this).load(wait_icon_path).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(gf1);
            } else {
                Glide.with(this).load(wait_icon_path).into(gf1);
            }

        } else {
            advert_view.setVisibility(View.GONE);
            await_img.setVisibility(View.VISIBLE);
            Glide.with(this).load(R.drawable.wait_icon).into(gf1);
            advert_timer = new Timer();
            advert_timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    JumpActivity();
                    Message message_data = new Message();
                    message_data.what = ADVERTFLAGE;
                    mHandler.sendMessage(message_data);

                }
            }, 1000);

        }
    }

    private void JumpActivity() {
        Intent intent = null;
        intent = new Intent(WaitActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            //取出msg，更新view
            switch (msg.what) {
                case ADVERTFLAGE:
                    advert_view.setVisibility(View.VISIBLE);
                    await_img.setVisibility(View.GONE);
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            JumpActivity();
                        }
                    }, 4000);
                    TimerDisplay();
                    break;
            }
        }

    };


    private void TimerDisplay() {
        CountDownTimer timerDisplay = new CountDownTimer(5000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                skip.setText(((millisUntilFinished / 1000) - 1) + "s  跳过");
            }

            @Override
            public void onFinish() {

            }
        };
        timerDisplay.start();
    }


}
