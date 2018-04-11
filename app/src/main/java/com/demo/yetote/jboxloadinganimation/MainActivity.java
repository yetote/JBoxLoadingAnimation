package com.demo.yetote.jboxloadinganimation;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private LoadingView loadingView;
    FrameLayout.LayoutParams layoutParams;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                ImageView imageView = new ImageView(MainActivity.this);
                imageView.setImageResource(imgs[(int) msg.obj]);
                imageView.setTag(R.id.circle_tag, true);
                loadingView.addView(imageView, layoutParams);
            }
                loadingView.invalidate();
        }
    };
    private int[] imgs = new int[]{
            R.mipmap.l,
            R.mipmap.m,
            R.mipmap.n,
            R.mipmap.o,
            R.mipmap.p,
            R.mipmap.q,
            R.mipmap.r,
            R.mipmap.s,
            R.mipmap.t,
            R.mipmap.l,
            R.mipmap.m,
            R.mipmap.n,
            R.mipmap.o,
            R.mipmap.p,
            R.mipmap.q,
            R.mipmap.r,
            R.mipmap.s,
            R.mipmap.t, R.mipmap.l,
            R.mipmap.m,
            R.mipmap.n,
            R.mipmap.o,
            R.mipmap.p,
            R.mipmap.q,
            R.mipmap.r,
            R.mipmap.s,

    };
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadingView = findViewById(R.id.loading);
        layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK;

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < imgs.length; i++) {

//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    Message msg=new Message();
                    msg.obj=i;
                    msg.what=1;
                    handler.sendMessage(msg);
                }
            }
        }).start();


    }


}
