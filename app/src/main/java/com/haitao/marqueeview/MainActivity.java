package com.haitao.marqueeview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.haitao.marqueeview.utils.SpUtil;

import java.util.Objects;

/**
 * @author 86185
 */
public class MainActivity extends AppCompatActivity {

    /**
     * 点击次数
     */
    private int counts = 5;
    long[] mHits = new long[counts];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);
        LinearLayout mLinear = findViewById(R.id.line1);
        MarqueeView mMarqueeView = findViewById(R.id.mMarqueeView);
        mMarqueeView.setText("我爱你！");
        mMarqueeView.startScroll();
        boolean isBackround = SpUtil.readBoolean(Const.IS_BACKGROUND);
        if (isBackround) {
            String imagePath = SpUtil.readString(Const.TEXT_BACKGROUND);
            if (!TextUtils.isEmpty(imagePath)) {
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                mLinear.setBackground(new BitmapDrawable(getResources(), bitmap));
            }
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == KeyEvent.ACTION_DOWN) {
            //todo 在此处调用
            exitAfterMany();
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 连续点击多次退出
     */
    private void exitAfterMany() {
        /*
          实现双击方法
          src 拷贝的源数组
          srcPos 从源数组的那个位置开始拷贝.
          dst 目标数组
          dstPos 从目标数组的那个位子开始写数据
          length 拷贝的元素的个数
         */
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        //实现左移，然后最后一个位置更新距离开机的时间，如果最后一个时间和最开始时间小于DURATION，即连续5次点击
        //System.currentTimeMillis()
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();

        /*
           规定有效时间
         */
        long duration = 3 * 1000;
        if ((mHits[mHits.length - 1] - mHits[0] <= duration)) {
            String tips = "您已在[" + duration + "]ms内连续点击【" + mHits.length + "】次了！！！";
            Toast.makeText(MainActivity.this, tips, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
    }

}
