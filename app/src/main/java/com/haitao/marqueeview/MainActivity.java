package com.haitao.marqueeview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import java.util.Objects;

import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

/**
 * @author 86185
 */
public class MainActivity extends AppCompatActivity {

    private MarqueeView mMarqueeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);
//        StatusBarUtil.setStatusBarLayoutStyle(this, false);
        mMarqueeView = findViewById(R.id.mMarqueeView);
        mMarqueeView.setText("我爱你！");
        mMarqueeView.startScroll();

//        AdvertistingView
    }

    public void setTextSize(View view) {
        mMarqueeView.setTextSize(100);
    }

    /**
     * 隐藏导航栏
     */
    public static void setNavigationBar(Activity activity, int visible) {
        View decorView = activity.getWindow().getDecorView();
        //显示NavigationBar
        if (View.GONE == visible) {
            decorView.setSystemUiVisibility(SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }

    /**
     * 设置Activity的statusBar隐藏
     */
    public static void statusBarHide(Activity activity) {
        // 代表 5.0 及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            ActionBar actionBar = activity.getActionBar();
            assert actionBar != null;
            actionBar.hide();
            return;
        }

        // versionCode > 4.4  and versionCode < 5.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

    }
}
