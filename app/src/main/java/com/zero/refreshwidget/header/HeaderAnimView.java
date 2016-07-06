package com.zero.refreshwidget.header;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

/**
 * @author linzewu
 * @date 16-6-29
 */
public class HeaderAnimView extends BaseHeader{

    private static final int STATUS_START_TO_REFRESH = 1;
    
    private static final int STATUS_REFRESH_ING = 2;
    
    private int mCurrentStatus;
    
    private int mColor;
    
    public HeaderAnimView(Context context) {
        super(context);
    }

    public HeaderAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeaderAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void onRefresh(int percent) {
        
    }

    @Override
    public void onReleaseToRefresh() {

    }

    @Override
    public void onRefreshing() {
        
    }
}
