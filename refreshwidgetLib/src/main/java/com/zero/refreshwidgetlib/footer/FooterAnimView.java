package com.zero.refreshwidgetlib.footer;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.zero.refreshwidgetlib.utils.DrawUtils;

/**
 * @author linzewu
 * @date 16/7/8
 */
public class FooterAnimView extends BaseFooter {
    public FooterAnimView(Context context) {
        super(context);
        init();
    }

    public FooterAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public FooterAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private static final int STATUS_NORMAL = 0;
    private static final int STATUS_START_TO_LOAD_MORE = 1;
    private static final int STATUS_RELEASE_TO_LOAD_MORE = 2;
    private static final int STATUS_LOAD_MORE_ING = 3;

    private int mCurrentStatus = STATUS_NORMAL;

    private static final float MAX_HEIGHT = 100;
    private static final int DEFAULT_COLOR = 0x4400AAFF;

    private int mColor = DEFAULT_COLOR;

    private Paint mPaint;
    private Path mPath;

    protected float mWidth;
    protected float mHeight;

    protected boolean mHasInit = false;

    private Handler mMainThreadHandler = new Handler();

    /**
     * 上拉曲线的变量点
     */
    private PointF mLeftPoint,mRightPoint,mControlPoint;

    /**
     * 加载波浪的变量点
     */
    private PointF mPointF1,mPointF2,mPointF3,mPointF4,mPointF5;

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mColor);
        
        mPath = new Path();
        mLeftPoint = new PointF();
        mRightPoint = new PointF();
        mControlPoint = new PointF();

        mPointF1 = new PointF();
        mPointF2 = new PointF();
        mPointF3 = new PointF();
        mPointF4 = new PointF();
        mPointF5 = new PointF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, DrawUtils.dip2px(getContext(), MAX_HEIGHT));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        if (!mHasInit) {
            mWidth = w;
            mHeight = h;
        }
    }
    
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        mPath.reset();
        if (mCurrentStatus == STATUS_START_TO_LOAD_MORE || mCurrentStatus ==
                STATUS_RELEASE_TO_LOAD_MORE) {
            mLeftPoint.x = 0;
            mLeftPoint.y = mHeight * mPercent;
            mRightPoint.x = mWidth;
            mRightPoint.y = mHeight * mPercent;
            mControlPoint.x = mWidth / 2;
            mControlPoint.y = -mHeight * mPercent * 0.9f;
            mPath.moveTo(mLeftPoint.x, mLeftPoint.y);
            mPath.quadTo(mControlPoint.x, mControlPoint.y, mRightPoint.x, mRightPoint.y);
            mPath.moveTo(mLeftPoint.x, mLeftPoint.y);
            canvas.drawPath(mPath, mPaint);
        }  else if (mCurrentStatus == STATUS_LOAD_MORE_ING) {
            /* 绘制波浪 */
            mPath.moveTo(mPointF1.x, mPointF1.y);
            mPath.quadTo((mPointF1.x + mPointF2.x) / 2, mPointF2.y + 20, mPointF2.x, mPointF2.y);
            mPath.quadTo((mPointF2.x + mPointF3.x) / 2, mPointF3.y - 20, mPointF3.x, mPointF3.y);
            mPath.quadTo((mPointF3.x + mPointF4.x) / 2, mPointF4.y + 20, mPointF4.x, mPointF4.y);
            mPath.quadTo((mPointF4.x + mPointF5.x) / 2, mPointF5.y - 20, mPointF5.x, mPointF5.y);
            mPath.lineTo(mPointF5.x, mHeight);
            mPath.lineTo(mPointF1.x, mHeight);
            mPath.lineTo(mPointF1.x, mPointF1.y);
            canvas.drawPath(mPath, mPaint);
        }
    }
    
    @Override
    public void onLoadMore(float percent) {
        super.onLoadMore(percent);
        this.mCurrentStatus = STATUS_START_TO_LOAD_MORE;
        this.mPercent = percent;
        invalidate();
    }

    @Override
    public void onReleaseToLoadMore() {
        super.onReleaseToLoadMore();
        this.mCurrentStatus = STATUS_RELEASE_TO_LOAD_MORE;
        this.mPercent = 1;
        invalidate();
    }

    @Override
    public void onLoadMoreIng() {
       super.onLoadMoreIng();
        this.mCurrentStatus = STATUS_LOAD_MORE_ING;
        drawWave();
    }

    /**
     * draw the wave
     */
    private void drawWave() {
        mPointF1.x = -mWidth;
        mPointF1.y = 0.7f * mHeight;
        mMainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(-mWidth, 0);
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.setDuration(1500);
                valueAnimator.setRepeatMode(ValueAnimator.RESTART);
                valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mPointF1.x = (float) animation.getAnimatedValue();
                        mPointF1.y = 0.7f * mHeight;
                        mPointF2.x = mPointF1.x + mWidth * 0.5f;
                        mPointF2.y = mPointF1.y;
                        mPointF3.x = mPointF1.x + mWidth;
                        mPointF3.y = mPointF1.y;
                        mPointF4.x = mPointF1.x + mWidth * 1.5f;
                        mPointF4.y = mPointF1.y;
                        mPointF5.x = mPointF1.x + mWidth * 2;
                        mPointF5.y = mPointF1.y;
                        invalidate();
                    }
                });
                valueAnimator.start();
            }
        });
    }
}
