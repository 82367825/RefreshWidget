package com.zero.refreshwidgetlib.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
        mLinearLayout.setOrientation(VERTICAL);
        mLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.MATCH_PARENT);
        ((ScrollView)mContentView).addView(mLinearLayout, mLayoutParams);
    }

    @Override
    public void addContentView(View child) {
        mLinearLayout.addView(child);
    }

    @Override
    public void addContentView(View view, LayoutParams layoutParams) {
        mLinearLayout.addView(view, layoutParams);
    }

    @Override
    public void addContentView(View child, int position) {
        mLinearLayout.addView(child, position);
    }

    @Override
    public void removeContentView(View view) {
        mLinearLayout.addView(view);
    }
    

    @Override
    public void setOnClickListener(OnClickListener l) {
        mContentView.setOnClickListener(l);
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        mContentView.setOnFocusChangeListener(l);
    }

    @Override
    public void setOnScrollChangeListener(OnScrollChangeListener l) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mContentView.setOnScrollChangeListener(l);
        }
    }

    @Override
    public void addOnLayoutChangeListener(OnLayoutChangeListener listener) {
        mContentView.addOnLayoutChangeListener(listener);
    }

    @Override
    public void scrollBy(int x, int y) {
        mContentView.scrollBy(x, y);
    }

    @Override
    public void scrollTo(int x, int y) {
        mContentView.scrollTo(x, y);
    }

    @Override
    public int getContentScrollX() {
        return mContentView.getScrollX();
    }

    @Override
    public int getContentScrollY() {
        return mContentView.getScrollY();
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
        View contentView = ((ScrollView)mContentView).getChildAt(0);
        int realHeight = contentView.getMeasuredHeight();
        mContentView.scrollTo(0, realHeight - mContentView.getHeight());
    }

    @Override
    protected void makeContentViewRestore() {
    }
}
