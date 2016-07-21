package com.zero.refreshwidgetlib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * @author linzewu
 * @date 16-7-21
 */
public class RefreshScrollViewWidget extends BaseRefreshWidget implements 
        RefreshScrollViewInterface {
    
    private LinearLayout mLinearLayout;
    private ViewGroup.LayoutParams mLayoutParams;
    
    public RefreshScrollViewWidget(Context context) {
        super(context);
        initScrollView();
    }

    public RefreshScrollViewWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        initScrollView();
    }

    public RefreshScrollViewWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initScrollView();
    }

    private void initScrollView() {
        mLinearLayout = new LinearLayout(getContext());
        mLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.MATCH_PARENT);
        ((ScrollView)mContentView).addView(mLinearLayout, mLayoutParams);
    }

    @Override
    public void addView(View child) {
        mLinearLayout.addView(child);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        mLinearLayout.addView(child, params);
    }

    @Override
    public void removeView(View view) {
        mLinearLayout.addView(view);
    }

    /**
     * 滑动到头部
     * @return
     */
    protected boolean isReachHeader(){
        return mContentView.getScrollY() == 0;
    }

    /**
     * 滑动到底部
     * @return
     */
    protected boolean isReachFooter(){
        View contentView = ((ScrollView)mContentView).getChildAt(0);
        return contentView.getMeasuredHeight() <= mContentView.getScrollY() + mContentView.getHeight();
    }


    @Override
    protected View getContentView() {
        return new ScrollView(getContext());
    }

    @Override
    protected void makeContentViewToFooter() {
        mContentView.post(new Runnable() {
            @Override
            public void run() {
                ((ScrollView)mContentView).fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    @Override
    protected void makeContentViewRestore() {
        mContentView.post(new Runnable() {
            @Override
            public void run() {
                ((ScrollView)mContentView).fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }
}
