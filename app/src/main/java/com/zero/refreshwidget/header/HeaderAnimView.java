package com.zero.refreshwidget.header;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.LinearInterpolator;

import com.zero.refreshwidget.utils.DrawUtils;

/**
 * Anim HeaderView
 * @author linzewu
 * @date 16-6-29
 */
public class HeaderAnimView extends BaseHeader{

    private static final String TAG = "HeaderAnimView";
    
    private static final int STATUS_NORMAL = 0;
    private static final int STATUS_START_TO_REFRESH = 1;
    private static final int STATUS_RELEASE_TO_REFRESH = 2;
    private static final int STATUS_REFRESH_ING = 3;
    
    private int mCurrentStatus = STATUS_NORMAL;
    
    private static final float MAX_HEIGHT = 100;
    
    private static final int DEFAULT_COLOR = 0x6600AAFF;
    
    private int mColor = DEFAULT_COLOR;
    private Paint mPaint;
    private Path mPath;

    protected float mWidth;

    protected float mHeight;

    protected float mRefreshingHeight;

    protected boolean mHasInit = false;

    /**
     * 下拉曲线的变量点
     */
    private PointF mLeftPoint,mRightPoint,mControlPoint;

    /**
     * 刷新波浪的变量点
     */
    private PointF mPointF1,mPointF2,mPointF3,mPointF4;
    
    public HeaderAnimView(Context context) {
        super(context);
        init();
    }

    public HeaderAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HeaderAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
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
            mRefreshingHeight = 0.8f * mHeight;
        }
    }
    
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
    }
    
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        mPath.reset();
        if (mCurrentStatus == STATUS_START_TO_REFRESH || mCurrentStatus ==
                STATUS_RELEASE_TO_REFRESH) {
            mLeftPoint.x = 0;
            mLeftPoint.y = mHeight - mHeight * mPercent;
            mRightPoint.x = mWidth;
            mRightPoint.y = mHeight - mHeight * mPercent;
            mControlPoint.x = mWidth / 2;
            mControlPoint.y = mHeight + mHeight * mPercent * 0.9f;
            mPath.moveTo(mLeftPoint.x, mLeftPoint.y);
            mPath.quadTo(mControlPoint.x, mControlPoint.y, mRightPoint.x, mRightPoint.y);
            mPath.moveTo(mLeftPoint.x, mLeftPoint.y);
            canvas.drawPath(mPath, mPaint);
        }  else if (mCurrentStatus == STATUS_REFRESH_ING) {
            
        }
    }

    @Override
    public void onRefresh(float percent) {
        this.mCurrentStatus = STATUS_START_TO_REFRESH;
        this.mPercent = percent;
        invalidate();
    }

    @Override
    public void onReleaseToRefresh() {
        this.mCurrentStatus = STATUS_RELEASE_TO_REFRESH;
        this.mPercent = 1;
        invalidate();
    }

    @Override
    public void onRefreshing() {
        this.mCurrentStatus = STATUS_REFRESH_ING;
        invalidate();
    }
    
    private static final long WAVE_ANIM_TIME = 2000;
    
    /**
     * draw the wave
     */
    private void drawWave() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat();
        valueAnimator.setDuration(WAVE_ANIM_TIME);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });
        valueAnimator.start();
    }
}
