package com.haitao.marqueeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.socks.library.KLog;

/**
 * @author panyingdao
 * @date 2018-5-15.
 */
public class MarqueeView extends SurfaceView implements SurfaceHolder.Callback {
    public Context mContext;
    /**
     * 字体大小
     */
    private float mTextSize = 100;
    /**
     * 字体的颜色
     */
    private int mTextColor = Color.RED;
    /**
     * 是否重复滚动
     */
    private boolean mIsRepeat;
    /**
     * 开始滚动的位置  0是从最左面开始    1是从最末尾开始
     */
    private int mStartPoint;
    /**
     * 滚动方向 0 向左滚动   1向右滚动
     */
    private int mDirection;
    /**
     * 滚动速度
     */
    private int mSpeed;

    private SurfaceHolder holder;

    private TextPaint mTextPaint;

    private MarqueeViewThread mThread;

    private String margueeString;

    private int textWidth = 0, textHeight = 0;

    /**
     * 当前x的位置
     */
    public int currentX = 0;
    /**
     * 每一步滚动的距离
     */
    public int sepX = 2;

    public MarqueeView(Context context) {
        this(context, null);
    }

    public MarqueeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarqueeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MarqueeView, defStyleAttr, 0);
        getTextColor();

        mTextColor = a.getColor(R.styleable.MarqueeView_textcolor, mTextColor);
        mIsRepeat = a.getBoolean(R.styleable.MarqueeView_isRepeat, false);
        mStartPoint = a.getInt(R.styleable.MarqueeView_startPoint, 0);
        mDirection = a.getInt(R.styleable.MarqueeView_direction, 0);
        mSpeed = a.getInt(R.styleable.MarqueeView_speed, 20);
        mTextSize = SpUtil.readInt(Const.TEXT_SIZE);
        KLog.e("文字大小：" + mTextSize);
        if (mTextSize == 0) {
            mTextSize = a.getDimension(R.styleable.MarqueeView_textSize, 48);
        }
        a.recycle();

        holder = this.getHolder();
        holder.addCallback(this);
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        //使surfaceview放到最顶层
        setZOrderOnTop(true);
        //使窗口支持透明度
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
    }

    private void getTextColor() {
        String color = SpUtil.readString(Const.TEXT_COLOR);
        switch (color) {
            case "黑色":
                mTextColor = Color.BLACK;
                break;
            case "白色":
                mTextColor = Color.WHITE;
                break;
            case "黄色":
                mTextColor = Color.YELLOW;
                break;
            case "蓝色":
                mTextColor = Color.BLUE;
                break;
            case "绿色":
                mTextColor = Color.GREEN;
                break;
            case "灰色":
                mTextColor = Color.GRAY;
            case "红色":
                mTextColor = Color.MAGENTA;
                break;
            default:
                break;
        }
    }

    public void setText(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            SpUtil.writeString(Const.MARQUEE_CONTEXT, msg);
            getTextColor();
            measurementsText(msg);
        }
    }

    public void setTextSize(int number) {
        if (number > 0) {
            mTextSize = number;
            SpUtil.writeInt(Const.TEXT_SIZE, number);
            measurementsText2();
        }
    }

    protected void measurementsText(String msg) {
        margueeString = msg;
        mTextPaint.setTextSize(mTextSize);
        KLog.e("字体颜色：" + mTextColor);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setStrokeWidth(0.5f);
        mTextPaint.setFakeBoldText(true);
        textWidth = (int) mTextPaint.measureText(margueeString);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        textHeight = (int) fontMetrics.bottom;
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        assert wm != null;
        int width = wm.getDefaultDisplay().getWidth();
//        KLog.e("屏幕宽度："+ width);
//        KLog.e("文字长度："+ textWidth);
        if (mStartPoint == 0) {
            currentX = 0;
        } else {
            currentX = width - getPaddingLeft() - getPaddingRight();
        }
    }

    protected void measurementsText2() {
//        margueeString = msg;
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setStrokeWidth(0.5f);
        mTextPaint.setFakeBoldText(true);
        textWidth = (int) mTextPaint.measureText(margueeString);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        textHeight = (int) fontMetrics.bottom;
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        assert wm != null;
        int width = wm.getDefaultDisplay().getWidth();
//        KLog.e("屏幕宽度："+ width);
//        KLog.e("文字长度："+ textWidth);
        if (mStartPoint == 0) {
            currentX = 0;
        } else {
            currentX = width - getPaddingLeft() - getPaddingRight();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.holder = holder;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mThread != null) {
            mThread.isRun = true;
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mThread != null) {
            mThread.isRun = false;
        }
    }

    /**
     * 开始滚动
     */
    public void startScroll() {

        if (mThread != null && mThread.isRun) {
            return;
        }
        //创建一个绘图线程
        mThread = new MarqueeViewThread(holder);
        mThread.start();
    }

    /**
     * 停止滚动
     */
    public void stopScroll() {
        if (mThread != null) {
            mThread.isRun = false;
            mThread.interrupt();
        }
        mThread = null;
    }

    /**
     * 线程
     */
    class MarqueeViewThread extends Thread {

        private final SurfaceHolder holder;
        /**
         * 是否在运行
         */
        private boolean isRun;

        private MarqueeViewThread(SurfaceHolder holder) {
            this.holder = holder;
            isRun = true;
        }

        private void onDraw() {
            try {
                synchronized (holder) {
                    if (TextUtils.isEmpty(margueeString)) {
                        // 睡眠时间为1秒
                        Thread.sleep(500);
                        return;
                    }
                    Canvas canvas = holder.lockCanvas();
                    int paddingLeft = getPaddingLeft();
                    int paddingTop = getPaddingTop();
                    KLog.e("内边距top：" + paddingTop);
                    int paddingRight = getPaddingRight();
                    int paddingBottom = getPaddingBottom();
                    KLog.e("内边距bottom：" + paddingBottom);

                    int contentWidth = getWidth() - paddingLeft - paddingRight;
                    int contentHeight = getHeight() - paddingTop - paddingBottom;
                    KLog.e("实际高：" + getHeight());
                    KLog.e("高：" + contentHeight);
                    KLog.e("宽：" + contentWidth);
                    //中心线
                    int centeYLine = paddingTop + contentHeight / 2;
                    KLog.e("中心线：" + centeYLine);
                    //向左滚动
                    if (mDirection == 0) {
                        if (currentX <= -textWidth) {
                            currentX = contentWidth;
                        } else {
                            currentX -= sepX;
                        }
                    } else {//  向右滚动
                        if (currentX >= contentWidth) {
                            currentX = -textWidth;
                        } else {
                            currentX += sepX;
                        }
                    }

                    if (canvas != null) {
                        //绘制透明色
                        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
//                        canvas.drawText(margueeString, currentX, centeYLine + dip2px(getContext(), textHeight) / 2, mTextPaint);
                        KLog.e("文字中心：" + dip2px(getContext(), textHeight) / 2);
                        canvas.drawText(margueeString, currentX, 540 + dip2px(getContext(), textHeight), mTextPaint);
                        //结束锁定画图，并提交改变。
                        holder.unlockCanvasAndPost(canvas);
                    }
                    int a = textWidth / margueeString.trim().length();
                    int b = a / sepX;
                    int c = mSpeed / b == 0 ? 1 : mSpeed / b;
                    //睡眠时间为移动的频率
                    Thread.sleep(c);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while (isRun) {
                onDraw();
            }
        }

    }

    /**
     * dip转换为px
     *
     * @param context 对象
     * @param dpValue 值
     * @return 结果
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
