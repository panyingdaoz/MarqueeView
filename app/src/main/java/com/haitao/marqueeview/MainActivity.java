package com.haitao.marqueeview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

//import com.dalong.marqueeview.MarqueeView;

/**
 * @author 86185
 */
public class MainActivity extends AppCompatActivity {

    private MarqueeView mMarqueeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        mMarqueeView = findViewById(R.id.mMarqueeView);
        mMarqueeView.setText("我爱你！");
        mMarqueeView.startScroll();

//        AdvertistingView
    }

    public void setTextSize(View view) {
        mMarqueeView.setTextSize(100);
    }
}
