package com.zero.refreshwidget.header;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;

import com.zero.refreshwidget.utils.DrawUtils;

/**
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
    
    private static final float MAX_HEIGHT = 50;
    
    private static final int DEFAULT_COLOR = 0xFF00AAFF;
    
    private float mPercent;
    
    private Paint mPaint;
    
    private int mColor = DEFAULT_COLOR;
    
    private Path mPath;
    
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

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mColor);
    
        mPath = new Path();
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        mPath.reset();
        if (mCurrentStatus == STATUS_START_TO_REFRESH || mCurrentStatus == 
                STATUS_RELEASE_TO_REFRESH) {
            PointF leftPointF = new PointF(0, MAX_HEIGHT - MAX_HEIGHT * mPercent);
            PointF rightPointF = new PointF(getWidth(), MAX_HEIGHT - MAX_HEIGHT * mPercent);
            PointF controlPointF = new PointF(getWidth() / 2, MAX_HEIGHT);
            mPath.moveTo(leftPointF.x, leftPointF.y);
            mPath.quadTo(controlPointF.x, controlPointF.y, rightPointF.x, rightPointF.y);
            mPath.moveTo(leftPointF.x, leftPointF.y);
            canvas.drawPath(mPath, mPaint);
        }  else if (mCurrentStatus == STATUS_REFRESH_ING) {
            
        }
        super.onDraw(canvas);
    }

    @Override
    public void onRefresh(float percent) {
        Log.i(TAG, String.valueOf(percent));
        this.mCurrentStatus = STATUS_START_TO_REFRESH;
        this.mPercent = percent;
        invalidate();
    }

    @Override
    public void onReleaseToRefresh() {
        this.mCurrentStatus = STATUS_RELEASE_TO_REFRESH;
        invalidate();
    }

    @Override
    public void onRefreshing() {
        this.mCurrentStatus = STATUS_REFRESH_ING;
        invalidate();
    }
}
