package com.zero.refreshwidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.zero.refreshwidget.header.HeaderTextView;

/**
 * Refresh ListView
 * @author linzewu
 * @date 16-6-29
 */
public class RefreshListViewWidget extends RefreshWidget implements AbsListView.OnScrollListener{
    
    public RefreshListViewWidget(Context context) {
        super(context);
    }

    public RefreshListViewWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshListViewWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private View mHeaderView;
    private View mFooterView;
    private ListView mContentView;
    
    private LayoutParams mHeaderLayoutParams;
    private LayoutParams mFooterLayoutParams;
    private LayoutParams mContentLayoutParams;
    
    private int mWidth;
    private int mHeight;
    
    private int mHeaderWidth;
    private int mHeaderHeight;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        mHeaderView = new HeaderTextView(getContext());
        mHeaderLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.WRAP_CONTENT);
        ((HeaderTextView)mHeaderView).onRefresh(0);
        addView(mHeaderView, mHeaderLayoutParams);
        mContentView = new ListView(getContext());
        mContentLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.WRAP_CONTENT);
        addView(mContentView, mContentLayoutParams);
        mFooterView = new TextView(getContext());
        mFooterLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.WRAP_CONTENT);
        addView(mFooterView, mFooterLayoutParams);

        mCurrentStatus = STATUS_NORMAL;
        mContentView.setOnScrollListener(this);
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
                mDownY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveY = ev.getY();
                /* 如果处于顶部，且继续下拉，进入下拉刷新状态,同时拦截触摸事件 */
                if (mCurrentStatus == STATUS_NORMAL && isReachHeader() && mMoveY - mDownY > 0) {
                    mCurrentStatus = STATUS_REFRESH;
                    return true;
                } else if (mCurrentStatus == STATUS_NORMAL && isReachFoot() && mMoveY - mDownY < 0) {
                    /* 如果处于底部，且继续上拉，进入上拉加载更多状态，同时拦截触摸事件 */
                    mCurrentStatus = STATUS_LOAD_MORE;
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveY = event.getY();
                /* 下拉刷新状态 */
                if (mCurrentStatus == STATUS_REFRESH || mCurrentStatus == STATUS_RELEASE_TO_REFRESH) {
                    if (mMoveY - mDownY < 0) break;
                    if (mMoveY - mDownY > mHeaderHeight) {
                        mCurrentStatus = STATUS_RELEASE_TO_REFRESH;
                        setHeaderViewMargin(0);
                    } else {
                        mCurrentStatus = STATUS_REFRESH;
                        setHeaderViewMargin((int) (mDownY - mMoveY));
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

                } else if (mCurrentStatus == STATUS_RELEASE_TO_LOAD_MORE) {

                }
                mCurrentStatus = STATUS_NORMAL;
                setHeaderViewMargin(-mHeaderHeight);
                break;
        }
        return true;
    }

    /**
     * 设置headerView的上边距，这里边距为负值
     * @param margin
     */
    private void setHeaderViewMargin(int margin) {
        mHeaderLayoutParams.setMargins(0, margin, 0, 0);
        mHeaderView.setLayoutParams(mHeaderLayoutParams);
    }
    
    private void setFooterViewMargin(int margin) {
        
    }

    @Override
    public void setRefreshEnabled(boolean enabled) {
        
    }

    @Override
    public void setLoadMoreEnabled(boolean enabled) {

    }

    private int mCurrentScrollState;      //记录当前的滚动状态 
    
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        mCurrentScrollState = scrollState;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (!mRefreshEnabled) {
            return ;
        }
//        if (mCurrentStatus == STATUS_NORMAL && isReachHeader()) {
//            
//        } else if (mCurrentStatus == STATUS_REFRESH) {
//            
//        } else if (mCurrentStatus == STATUS_RELEASE_TO_REFRESH) {
//            
//        } else if (mCurrentStatus == STATUS_LOAD_MORE) {
//            
//        } else if (mCurrentStatus == STATUS_RELEASE_TO_LOAD_MORE) {
//            
//        }
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
    private boolean isReachFoot() {
        return mContentView.getLastVisiblePosition() == mContentView.getCount() - 1;
    }
}
