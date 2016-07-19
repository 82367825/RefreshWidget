package com.zero.refreshwidgetlib.widget.scrollview;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.zero.refreshwidgetlib.footer.BaseFooter;
import com.zero.refreshwidgetlib.header.BaseHeader;
import com.zero.refreshwidgetlib.widget.RefreshListener;

/**
 * @author linzewu
 * @date 16-7-6
 */
public class RefreshScrollViewWidget extends ScrollView {

    private final static int STATUS_NORMAL = 0x01;

    private final static int STATUS_REFRESH = 0x02;

    private final static int STATUS_RELEASE_TO_REFRESH = 0x03;

    private final static int STATUS_REFRESH_ING = 0x04;

    private final static int STATUS_LOAD_MORE = 0x05;

    private final static int STATUS_RELEASE_TO_LOAD_MORE = 0x06;

    private final static int STATUS_LOAD_MORE_ING = 0x07;

    private int mCurrentStatus;

    private boolean mRefreshEnabled = true;
    private boolean mLoadMoreEnabled = true;

    protected BaseHeader mHeaderView;
    protected BaseFooter mFooterView;
    protected LinearLayout.LayoutParams mHeaderLayoutParams;
    protected LinearLayout.LayoutParams mFooterLayoutParams;
    protected int mHeaderHeight;
    protected float mHeaderPullProportion = 3f;
    protected int mFooterHeight;
    protected float mFooterPullProportion = 3f;

    /**
     * The Handler of the Main Thread
     */
    private Handler mMainThreadHandler = new Handler();

    /**
     * Distance in pixels a touch can wander before we think the user is scrolling
     */
    private int mTouchSlop;

    private RefreshListener mRefreshListener;

    public void setRefreshListener(RefreshListener refreshListener) {
        this.mRefreshListener = refreshListener;
    }
    
    public RefreshScrollViewWidget(Context context) {
        super(context);
    }

    public RefreshScrollViewWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshScrollViewWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 滑动到头部
     * @return
     */
    private boolean isReachHeader(){
        return this.getScrollY() == 0;
    }

    /**
     * 滑动到底部
     * @return
     */
    private boolean isReachFooter(){
        return this.getScrollY() + getHeight() >= this.computeVerticalScrollRange();
    }
}
