package com.zero.refreshwidget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.zero.refreshwidget.footer.BaseFooter;
import com.zero.refreshwidget.header.BaseHeader;
import com.zero.refreshwidget.header.HeaderTextView;

/**
 * Refresh ListView
 * @author linzewu
 * @date 16-6-29
 */
public class RefreshListViewWidget extends RefreshWidget{
    
    public RefreshListViewWidget(Context context) {
        super(context);
    }

    public RefreshListViewWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshListViewWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    
    private static final String TAG = "RefreshListViewWidget";

    private BaseHeader mHeaderView;
    private BaseFooter mFooterView;
    private ListView mContentView;
    
    private LayoutParams mHeaderLayoutParams;
    private LayoutParams mFooterLayoutParams;
    private LayoutParams mContentLayoutParams;
    
    private int mWidth;
    private int mHeight;
    
    private int mHeaderWidth;
    private int mHeaderHeight;
    
    private float mHeaderPullProportion = 3f;
    
    private int mFooterWidth;
    private int mFooterHeight;
    
    private float mFooterPullProportion = 1.5f;

    /**
     * The Handler of the Main Thread
     */
    private Handler mMainThreadHandler = new Handler();

    /**
     * Distance in pixels a touch can wander before we think the user is scrolling
     * <br>判断为触摸移动的距离
     */ 
    private int mTouchSlop;
    
    private RefreshListener mRefreshListener;
    
    public void setRefreshListener(RefreshListener refreshListener) {
        this.mRefreshListener = refreshListener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mHeaderView = new HeaderTextView(getContext());
        mHeaderLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.WRAP_CONTENT);
        mHeaderView.onRefresh(0);
        addView(mHeaderView, mHeaderLayoutParams);
        mContentView = new ListView(getContext());
        mContentLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.WRAP_CONTENT);
        addView(mContentView, mContentLayoutParams);
        
//        mFooterView = new HeaderTextView(getContext());
//        mFooterLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
//                .LayoutParams.WRAP_CONTENT);
//        addView(mFooterView, mFooterLayoutParams);

        mCurrentStatus = STATUS_NORMAL;
    }

    private long mRefreshCompleteDuration = 300;
    
    /**
     * 刷新完成, 回弹
     */
    public void refreshComplete() {
        mMainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, -mHeaderHeight);
                valueAnimator.setDuration(mRefreshCompleteDuration);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue();
                        setHeaderTopMargin((int) value);
                    }
                });
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.start();
            }
        });
        mCurrentStatus = STATUS_NORMAL;
    }

    /**
     * 加载更多完成
     */
    public void loadMoreComplete() {
        
    }
    
    public void addHeaderView() {
        
    }
    
    public void addFooterView() {
        
    }
    
    public void setAdapter(BaseAdapter adapter) {
        mContentView.setAdapter(adapter);
    }
    
    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        mContentView.setOnItemClickListener(onItemClickListener);
    }
    
    public void setDividerHeight(int dividerHeight) {
        mContentView.setDividerHeight(dividerHeight);
    }
    
    public void setSelection(int selection) {
        mContentView.setSelection(selection);
    }
    
    private boolean hasInitParams = false;
    
    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        if(!hasInitParams) {
            mWidth = w;
            mHeight = h;
            mHeaderWidth = mHeaderView.getMeasuredWidth();
            mHeaderHeight = mHeaderView.getMeasuredHeight();
            if( mHeaderHeight > 0 ){
                mHeaderLayoutParams.setMargins(0, -mHeaderHeight, 0, 0);
                mHeaderView.setLayoutParams(mHeaderLayoutParams);
            }
            hasInitParams = true;
        }
    }
    
    private float mDownY;
    private float mMoveY;
    
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveY = ev.getRawY();
                /* 如果不属于触摸滑动范围，则跳出 */
                if (Math.abs(mDownY - mMoveY) < mTouchSlop) return false;

                /* 如果处于顶部，且继续下拉，进入下拉刷新状态,同时拦截触摸事件 */
                if (mCurrentStatus == STATUS_NORMAL && isReachHeader() && mMoveY - mDownY > 0) {
                    mCurrentStatus = STATUS_REFRESH;
                    mHeaderView.onRefresh(0);
                    return true;
                } else if (mCurrentStatus == STATUS_NORMAL && isReachFooter() && mMoveY - mDownY < 0) {
                    /* 如果处于底部，且继续上拉，进入上拉加载更多状态，同时拦截触摸事件 */
                    mCurrentStatus = STATUS_LOAD_MORE;
                    return true;
                }
                break;
        }
        /* 其他情况不拦截，默认返回false */
        return super.onInterceptTouchEvent(ev);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mMoveY = event.getRawY();
                /* 下拉刷新状态 且正在向下滑动 */
                if ((mCurrentStatus == STATUS_REFRESH || mCurrentStatus == STATUS_RELEASE_TO_REFRESH)
                        && mMoveY - mDownY >= 0) {
                    if (mMoveY - mDownY > mHeaderHeight * mHeaderPullProportion) {
                        mCurrentStatus = STATUS_RELEASE_TO_REFRESH;
                        mHeaderView.onReleaseToRefresh();
                        setHeaderTopMargin(0);
                    } else {
                        mCurrentStatus = STATUS_REFRESH;
                        mHeaderView.onRefresh(0);
                        setHeaderTopMargin(-mHeaderHeight + (int) ((mMoveY - mDownY) / mHeaderPullProportion));
                    }
                } 
                /* 上拉加载状态 */
                if (mCurrentStatus == STATUS_LOAD_MORE || mCurrentStatus == 
                        STATUS_RELEASE_TO_LOAD_MORE) {
                    if (mMoveY - mDownY > 0) break;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mCurrentStatus == STATUS_RELEASE_TO_REFRESH) {
                    mCurrentStatus = STATUS_LOAD_MORE;
                    mHeaderView.onRefreshing();
                    if (mRefreshListener != null) mRefreshListener.onRefresh();
                } else if (mCurrentStatus == STATUS_RELEASE_TO_LOAD_MORE) {

                } else {
                    mCurrentStatus = STATUS_NORMAL;
                    setHeaderTopMargin(-mHeaderHeight);
                }
                break;
        }
        return true;
    }

    /**
     * 设置headerView的上边距，这里边距为负值
     * @param margin 上边距
     */
    private void setHeaderTopMargin(int margin) {
        mHeaderLayoutParams.setMargins(0, margin, 0, 0);
        mHeaderView.setLayoutParams(mHeaderLayoutParams);
    }
    
    private void setFooterBottomMargin(int margin) {
        
    }

    @Override
    public void setRefreshEnabled(boolean enabled) {
        this.mRefreshEnabled = enabled;
    }

    @Override
    public void setLoadMoreEnabled(boolean enabled) {
        this.mLoadMoreEnabled = enabled;
    }
    
    /**
     * 是否滑到了ListView的顶部
     * @return
     */
    private boolean isReachHeader() {
        return mContentView.getFirstVisiblePosition() == 0;
    }
    
    /**
     * 是否滑到了ListView的底部
     * @return 
     */
    private boolean isReachFooter() {
        return mContentView.getLastVisiblePosition() == mContentView.getCount() - 1;
    }
    
}
