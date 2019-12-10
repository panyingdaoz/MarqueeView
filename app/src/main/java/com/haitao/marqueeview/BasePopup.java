//package com.haitao.marqueeview;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.view.Gravity;
//import android.view.View;
//import android.view.animation.Animation;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.Spinner;
//
//import com.socks.library.KLog;
//
//import razerdp.basepopup.BasePopupWindow;
//
///**
// * 类具体作用
// *
// * @author Administrator
// * @date 2018/12/11/011.
// */
//public class BasePopup extends BasePopupWindow implements AdapterView.OnItemSelectedListener, View.OnClickListener {
//
//    private Spinner textColor;
//    private Spinner textSize;
//    private Button mTextBackground;
//    private Button mPreview;
//    private Button mExitSettings;
//
//    //    private boolean isFirst;
////    private String code;
//    private Context context;
//    private Activity activity;
//
//    public BasePopup(Context context, Activity activity) {
//        super(context);
//        KLog.e("对象：" + context);
//        this.context = context;
//        this.activity = activity;
////        code = "";
//        LinearLayout.LayoutParams pp;
//
//        mPreview = findViewById(R.id.Preview);
//        textSize = findViewById(R.id.spinTextSize);
//        textColor = findViewById(R.id.spinTextColor);
//        mExitSettings = findViewById(R.id.ExitSettings);
//        mTextBackground = findViewById(R.id.TextBackground);
//        //自动打开输入法
////        setAutoShowInputMethod(mPassword, true);
//        setPopupGravity(Gravity.CENTER);
//
//        mPreview.setOnClickListener(this);
//        mExitSettings.setOnClickListener(this);
//        mTextBackground.setOnClickListener(this);
//
//    }
//
//    /**
//     * 必须实现，这里返回您的contentView
//     * 为了让库更加准确的做出适配，强烈建议使用createPopupById()进行inflate
//     */
//    @Override
//    public View onCreateContentView() {
//        View view = createPopupById(R.layout.text_settings);
////        boolean isFirst = SpUtil.readBoolean(Const.BASE_POPUP_TYPE);
////        if (isFirst) {
////            view = createPopupById(R.layout.dialog_proxy_binding);
////        } else {
////            view = createPopupById(R.layout.dialog_customer_keyboard);
////        }
//        KLog.e("返回的View：" + view);
//        return view;
//    }
//
//    /**
//     * 以下为可选代码（非必须实现）
//     * 返回作用于PopupWindow的show和dismiss动画，本库提供了默认的几款动画，这里可以自由实现
//     */
//    @Override
//    protected Animation onCreateShowAnimation() {
//        return getDefaultScaleAnimation(true);
//    }
//
//    @Override
//    protected Animation onCreateDismissAnimation() {
//        return getDefaultScaleAnimation(false);
//    }
//
//    private void onClickLocalFile() {
//        Intent intent = new Intent();
////        if (Build.VERSION.SDK_INT < KITKAT) {
////            intent.setAction(Intent.ACTION_GET_CONTENT);
////            intent.setType("image/*");
////        } else {
//        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("image/*");
////        }
//        activity.startActivityForResult(Intent.createChooser(intent, "选择要导入的图片"), 0);
//
//
////        String selectedFilepath = GetPathFromUri.getPath(context, intent.getData());
//        KLog.e("获取的路径：" + intent.getData());
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.TextBackground:
//                KLog.e("点击设置背景");
//                onClickLocalFile();
//                break;
//            case R.id.Preview:
//                KLog.e("开始预览效果");
//                break;
//            case R.id.ExitSettings:
//                KLog.e("退出设置");
//                break;
//            default:
//                break;
//        }
//    }
//
//    @Override
//    public BasePopupWindow setPopupFadeEnable(boolean isPopupFadeAnimate) {
//        return super.setPopupFadeEnable(isPopupFadeAnimate);
//    }
//
//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        String content = parent.getItemAtPosition(position).toString();
//        switch (parent.getId()) {
//            case R.id.spinTextColor:
//                KLog.e("选择颜色的是：" + content);
//                break;
//            case R.id.spinTextSize:
//                KLog.e("选择大小的是：" + content);
//                break;
//            default:
//                break;
//        }
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
//}