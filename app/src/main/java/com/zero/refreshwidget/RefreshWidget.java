package com.zero.refreshwidget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * @author linzewu
 * @date 16-6-29
 */
public abstract class RefreshWidget extends LinearLayout implements RefreshInterface{
    
    protected final static int STATUS_NORMAL = 0x01;
    
    protected final static int STATUS_REFRESH = 0x02;
    
    protected final static int STATUS_RELEASE_TO_REFRESH = 0x03;
    
    protected final static int STATUS_REFRESH_ING = 0x04;
    
    protected final static int STATUS_LOAD_MORE = 0x05;
    
    protected final static int STATUS_RELEASE_TO_LOAD_MORE = 0x06;
    
    protected final static int STATUS_LOAD_MORE_ING = 0x07;
    
    protected int mCurrentStatus;
    
    protected final static int DEFAULT_DISTANCE_TO_REFRESH = 200;
    
    protected final static int DEFAULT_DISTANCE_TO_LOAD_MORE = 150;
    
    protected int mDistanceToRefresh;
    
    protected int mDistanceToLoadMore;
    
    protected int mCurrentHeaderTopMargin;
    
    protected int mCurrentFooterTopMargin;
    
    protected boolean mRefreshEnabled = true;
    
    protected boolean mLoadMoreEnabled = true;
    
    public RefreshWidget(Context context) {
        super(context);
        setOrientation(VERTICAL);
    }

    public RefreshWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
    }

    public RefreshWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
    }
    
}
