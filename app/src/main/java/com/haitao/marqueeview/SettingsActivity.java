package com.haitao.marqueeview;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.haitao.marqueeview.utils.GetPathFromUri;
import com.haitao.marqueeview.utils.SpUtil;
import com.socks.library.KLog;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author 86185
 */
public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private int screenWidth;
    private int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.text_settings);

        Spinner textSize = findViewById(R.id.spinTextSize);
        Spinner textColor = findViewById(R.id.spinTextColor);
        Button mExitSettings = findViewById(R.id.ExitSettings);
        Button mTextBackground = findViewById(R.id.TextBackground);

        mExitSettings.setOnClickListener(this);
        mTextBackground.setOnClickListener(this);
        textSize.setOnItemSelectedListener(this);
        textColor.setOnItemSelectedListener(this);

        getScreenWidthHeigth();
    }

    /**
     * 获取屏幕的分辨率
     */
    private void getScreenWidthHeigth() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        assert wm != null;
        Display display = wm.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getRealMetrics(dm);
        wm.getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        KLog.e("宽：" + screenWidth);
        KLog.e("高：" + screenHeight);
        float scale = this.getResources().getDisplayMetrics().density;
        int screenWidth2 = (int) (screenWidth / scale + 0.5f);
        int screenHeight2 = (int) (screenHeight / scale + 0.5f);
        KLog.e("宽：" + screenWidth2);
        KLog.e("高：" + screenHeight2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        KLog.e("接收状态2：" + requestCode);
        if (requestCode == 0) {
            assert data != null;
            String selectedFilepath = GetPathFromUri.getPath(this, data.getData());
            KLog.e("Select file: " + selectedFilepath);
            if (selectedFilepath != null && !"".equals(selectedFilepath)) {
                KLog.e("图片路径：" + selectedFilepath);
                SpUtil.writeString(Const.TEXT_BACKGROUND, selectedFilepath);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.TextBackground:
                KLog.e("点击设置背景");
                onClickLocalFile();
                break;
            case R.id.ExitSettings:
                KLog.e("退出设置");
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }

    private void onClickLocalFile() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "选择要导入的图片"), 0);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String content = parent.getItemAtPosition(position).toString();
        switch (parent.getId()) {
            case R.id.spinTextColor:
                SpUtil.writeString(Const.TEXT_COLOR, content);
                KLog.e("选择颜色的是：" + content);
                break;
            case R.id.spinTextSize:
                int textSize;
                double d = div(Integer.parseInt(content), 15, 2);
                KLog.e("x小数：" + d);
                if (screenWidth > screenHeight) {
                    textSize = (int) (screenHeight * d);
                } else {
                    textSize = (int) (screenWidth * d);

                }
                SpUtil.writeInt(Const.TEXT_SIZE, textSize);
                KLog.e("选择大小的是：" + textSize);
                break;
            case R.id.SpinnerBackground:
                switch (content) {
                    case "启用":
                        SpUtil.writeBoolean(Const.IS_BACKGROUND, true);
                        break;
                    case "不启用":
                        SpUtil.writeBoolean(Const.IS_BACKGROUND, false);
                        break;
                    default:
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "小数位数必须为正整数或零");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
