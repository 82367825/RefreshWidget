package com.zero.refreshwidgetlib.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import com.zero.refreshwidgetlib.footer.BaseFooter;
import com.zero.refreshwidgetlib.footer.FooterTextView;
import com.zero.refreshwidgetlib.header.BaseHeader;
import com.zero.refreshwidgetlib.header.HeaderTextView;

/**
 * @author linzewu
 * @date 16-6-29
 */
public abstract class BaseRefreshWidget extends LinearLayout implements BaseRefreshWidgetInterface {
    
    private static final String TAG = "BaseRefreshWidget";
    
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
    protected View mContentView;
    protected LayoutParams mHeaderLayoutParams;
    protected LayoutParams mFooterLayoutParams;
    protected LayoutParams mContentLayoutParams;
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
    
    public BaseRefreshWidget(Context context) {
        super(context);
        setOrientation(VERTICAL);
        init();
    }

    public BaseRefreshWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        init();
    }

    public BaseRefreshWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        init();
    }

    private void init() {
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

        mHeaderView = new HeaderTextView(getContext());
        mHeaderLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mHeaderView.onRefresh(0);
        addView(mHeaderView, mHeaderLayoutParams);


        mContentView = getContentView();
        mContentLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(mContentView, mContentLayoutParams);


        mFooterView = new FooterTextView(getContext());
        mFooterLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mFooterView.onLoadMore(0);
        addView(mFooterView, mFooterLayoutParams);

        mCurrentStatus = STATUS_NORMAL;
    }
    
    /**
     * 刷新完成,回弹
     */
    @Override
    public void completeRefresh() {
        headerCompleteRefreshTask();
        mCurrentStatus = STATUS_NORMAL;
    }

    /**
     * 加载更多完成
     */
    @Override
    public void completeLoadMore() {
        footerCompleteLoadMoreTask();
        mCurrentStatus = STATUS_NORMAL;
    }

    @Override
    public void setRefreshEnabled(boolean enabled) {
        this.mRefreshEnabled = enabled;
    }

    @Override
    public void setLoadMoreEnabled(boolean enabled) {
        this.mLoadMoreEnabled = enabled;
    }

    @Override
    public void addHeaderView(BaseHeader headerView) {
        removeView(mHeaderView);
        this.mHeaderView = headerView;
        hasCustomHeaderFooterViewInit = false;
        addView(mHeaderView, 0);
        mHeaderView.onRefresh(0);
    }

    @Override
    public void addFooterView(BaseFooter footerView) {
        removeView(mFooterView);
        this.mFooterView = footerView;
        hasCustomHeaderFooterViewInit = false;
        addView(mFooterView, 2);
        mFooterView.onLoadMore(0);
    }
    
    /**
     * 默认HeaderView和默认FooterView的宽高值是否已经获取
     */
    private boolean hasDefaultHeaderFooterViewInit = false;

    /**
     * 自定义HeaderView和默认FooterView的宽高值是否已经获取
     */
    private boolean hasCustomHeaderFooterViewInit = true;

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        if(!hasDefaultHeaderFooterViewInit || !hasCustomHeaderFooterViewInit) {
            mHeaderHeight = mHeaderView.getMeasuredHeight();
            if( mHeaderHeight > 0 ){
                mHeaderLayoutParams.setMargins(0, -mHeaderHeight, 0, 0);
                mHeaderView.setLayoutParams(mHeaderLayoutParams);
            }

            mFooterView.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            mFooterHeight = mFooterView.getMeasuredHeight();
            if( mFooterHeight > 0 ){
                mFooterLayoutParams.setMargins(0, 0, 0, 0);
                mFooterView.setLayoutParams(mContentLayoutParams);
            }
            hasDefaultHeaderFooterViewInit = true;
            hasCustomHeaderFooterViewInit = true;

            Log.i(TAG, "HeaderView Height:" + mHeaderHeight);
            Log.i(TAG, "FooterView Height:" + mFooterHeight);
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
                if (mCurrentStatus == STATUS_NORMAL && isReachHeader() && mMoveY - mDownY > 0 && 
                        mRefreshEnabled) {
                    mCurrentStatus = STATUS_REFRESH;
                    mHeaderView.onRefresh(0);
                    return true;
                } else if (mCurrentStatus == STATUS_NORMAL && isReachFooter() && mMoveY - mDownY 
                        < 0 && mLoadMoreEnabled) {
                    /* 如果处于底部，且继续上拉，进入上拉加载更多状态，同时拦截触摸事件 */
                    mCurrentStatus = STATUS_LOAD_MORE;
                    mFooterView.onLoadMore(0);
                    
                    /* 加上这一句，可以使得ContentView在上拉的过程中保持滑到最底部的状态 */
                    makeContentViewToFooter();
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
                        && mMoveY - mDownY >= 0 ) {
                    if (mMoveY - mDownY > mHeaderHeight * mHeaderPullProportion) {
                        mCurrentStatus = STATUS_RELEASE_TO_REFRESH;
                        mHeaderView.onReleaseToRefresh();
                        setHeaderTopMargin(0);
                        setHeaderBottomMargin((int) (mMoveY - mDownY - mHeaderHeight * mHeaderPullProportion));
                    } else {
                        mCurrentStatus = STATUS_REFRESH;
                        mHeaderView.onRefresh((mMoveY- mDownY) / ((float)mHeaderHeight * mHeaderPullProportion));
                        setHeaderTopMargin(-mHeaderHeight + (int) ((mMoveY - mDownY) / mHeaderPullProportion));
                        setHeaderBottomMargin(0);
                    }
                } 
                /* 上拉加载状态 且正在向上滑动 */
                if ((mCurrentStatus == STATUS_LOAD_MORE || mCurrentStatus ==
                        STATUS_RELEASE_TO_LOAD_MORE) && mMoveY - mDownY <= 0)  {
                    if (mDownY - mMoveY > mFooterHeight * mFooterPullProportion) {
                        mCurrentStatus = STATUS_RELEASE_TO_LOAD_MORE;
                        mFooterView.onReleaseToLoadMore();

                        setContentViewBottomMargin((int) ((mDownY - mMoveY) / mFooterPullProportion));
                        setFooterTopMargin(-mFooterHeight);
                    } else {
                        mCurrentStatus = STATUS_LOAD_MORE;
                        mFooterView.onLoadMore((mDownY - mMoveY) / ((float)mFooterHeight *
                                mFooterPullProportion));

                        setContentViewBottomMargin((int) ((mDownY - mMoveY) / mFooterPullProportion));
                        setFooterTopMargin((int)((-mFooterHeight) * mFooterView.getPercent()));
                    }
                    /* 加上这一句，可以使得ContentView在上拉的过程中保持滑到最底部的状态 */
                    makeContentViewToFooter();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mCurrentStatus == STATUS_RELEASE_TO_REFRESH) {
                    mCurrentStatus = STATUS_REFRESH_ING;
                    mHeaderView.onRefreshIng();
                    headerRefreshTask();
                    if (mRefreshListener != null) mRefreshListener.onRefresh();
                } else if (mCurrentStatus == STATUS_RELEASE_TO_LOAD_MORE) {
                    mCurrentStatus = STATUS_LOAD_MORE_ING;
                    mFooterView.onLoadMoreIng();
                    footerLoadMoreTask();
                    if (mRefreshListener != null) mRefreshListener.onLoadMore();
                } else if (mCurrentStatus == STATUS_REFRESH){
                    mCurrentStatus = STATUS_NORMAL;
                    headerCancelRefreshTask();
                } else if (mCurrentStatus == STATUS_LOAD_MORE){
                    mCurrentStatus = STATUS_NORMAL;
                    footerCancelLoadMoreTask();
                }
                /* 还原ContentView状态 */
                makeContentViewRestore();
                break;
        }
        Log.i(TAG, "Current Status: " + mCurrentStatus);
        return true;
    }

    /**
     * 设置headerView的上边距，这里边距为负值
     * @param margin 上边距
     */
    private void setHeaderTopMargin(int margin) {
        mHeaderLayoutParams.topMargin = margin;
        mHeaderView.setLayoutParams(mHeaderLayoutParams);
    }

    /**
     * 设置headerView的下边距，这里边距为正值
     * 为了保证下拉过程能够继续往下拉
     * @param margin
     */
    private void setHeaderBottomMargin(int margin) {
        mHeaderLayoutParams.bottomMargin = margin;
        mHeaderView.setLayoutParams(mHeaderLayoutParams);
    }

    private void setContentViewBottomMargin(int margin) {
        mContentLayoutParams.bottomMargin = margin;
        mContentView.setLayoutParams(mContentLayoutParams);
    }
    
    private void setFooterTopMargin(int margin) {
        mFooterLayoutParams.topMargin = margin;
        mFooterView.setLayoutParams(mFooterLayoutParams);
    }
    
    /**
     * 是否滑到了顶部
     * @return
     */
    protected abstract boolean isReachHeader();

    /**
     * 是否滑到了底部
     * @return
     */
    protected abstract boolean isReachFooter();

    /**
     * 获取ContentView
     * @return
     */
    protected abstract View getContentView();

    /**
     * 使得ContentView滑动到最底部
     */
    protected abstract void makeContentViewToFooter();

    /**
     * 使得ContentView恢复原状
     */
    protected abstract void makeContentViewRestore();
    

    private static final long HEADER_CANCEL_REFRESH_TIME = 300;

    private static final long HEADER_REFRESH_TIME = 300;

    private static final long HEADER_COMPLETE_REFRESH_TIME = 300;

    private static final long FOOTER_CANCEL_LOAD_MORE_TIME = 300;

    private static final long FOOTER_LOAD_MORE_TIME = 300;

    private static final long FOOTER_COMPLETE_LOAD_MORE_TIME = 300;

    /**
     * 下拉刷新任务
     */
    private void headerRefreshTask() {
        final int value = mHeaderLayoutParams.bottomMargin;
        mMainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(value,
                        0);
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.setDuration(HEADER_REFRESH_TIME);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue();
                        setHeaderBottomMargin((int) value);
                    }
                });
                valueAnimator.start();
            }
        });
    }

    /**
     * 上拉加载更多任务
     */
    private void footerLoadMoreTask() {
        final int value = mContentLayoutParams.bottomMargin;
        mMainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(value, mFooterHeight);
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.setDuration(FOOTER_LOAD_MORE_TIME);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue();
                        setContentViewBottomMargin((int) value);
                    }
                });
                valueAnimator.start();
            }
        });
    }

    /**
     * 下拉刷新完成任务
     */
    private void headerCompleteRefreshTask() {
        mMainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, -mHeaderHeight);
                valueAnimator.setDuration(HEADER_COMPLETE_REFRESH_TIME);
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
    }

    /**
     * 上拉加载更多完成任务
     */
    private void footerCompleteLoadMoreTask() {
        mMainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(mFooterHeight, 0);
                valueAnimator.setDuration(FOOTER_COMPLETE_LOAD_MORE_TIME);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue();
                        setContentViewBottomMargin((int) value);
                        setFooterTopMargin((int) ((1 - animation.getAnimatedFraction()) * (-mFooterHeight)));
                    }
                });
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.start();
            }
        });
    }

    /**
     * 取消下拉刷新任务
     */
    private void headerCancelRefreshTask() {
        final int value = mHeaderLayoutParams.topMargin;
        final float percentValue = mHeaderView.getPercent();
        mMainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(value,
                        -mHeaderHeight);
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.setDuration(HEADER_CANCEL_REFRESH_TIME);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue();
                        setHeaderTopMargin((int) value);
                        setHeaderBottomMargin(0);
                        mHeaderView.onRefresh(percentValue * (1 - animation.getAnimatedFraction()));
                    }
                });
                valueAnimator.start();
            }
        });
    }

    /**
     * 取消上拉加载更多任务
     */
    private void footerCancelLoadMoreTask() {
        final int value = mContentLayoutParams.bottomMargin;
        final float percentValue = mFooterView.getPercent();
        mMainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(value, 0);
                valueAnimator.setDuration(FOOTER_CANCEL_LOAD_MORE_TIME);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue();
                        setContentViewBottomMargin((int) value);
                        mFooterView.onLoadMore(percentValue * (1 - animation.getAnimatedFraction()));
                        setFooterTopMargin((int) (mFooterView.getPercent() * (-mFooterHeight)));
                    }
                });
                valueAnimator.start();
            }
        });
    }
    
}
