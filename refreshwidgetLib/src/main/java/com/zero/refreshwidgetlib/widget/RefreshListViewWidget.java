package com.zero.refreshwidgetlib.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;


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
 * Refresh ListView
 * @author linzewu
 * @date 16-6-29
 */
public class RefreshListViewWidget extends BaseRefreshWidget implements RefreshListViewInterface{
    
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

    @Override
    protected View getContentView() {
        return new ListView(getContext());
    }

    @Override
    protected void makeContentViewToFooter() {
        ((ListView)mContentView).setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
    }

    @Override
    protected void makeContentViewRestore() {
        ((ListView)mContentView).setTranscriptMode(AbsListView.TRANSCRIPT_MODE_DISABLED);
    }

    /**
     * 是否滑到了ListView的顶部
     * @return
     */
    protected boolean isReachHeader() {
        return ((ListView)mContentView).getFirstVisiblePosition() == 0;
    }

    /**
     * 是否滑到了ListView的底部
     * @return
     */
    protected boolean isReachFooter() {
        return ((ListView)mContentView).getLastVisiblePosition() == ((ListView)mContentView).getCount() - 1
                && ((ListView)mContentView).getFirstVisiblePosition() != 0;
    }

    @Override
    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        ((ListView)mContentView).setOnItemClickListener(onItemClickListener);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        ((ListView)mContentView).setAdapter(adapter);
    }

    @Override
    public void setOnScrollListener(AbsListView.OnScrollListener l) {
        ((ListView)mContentView).setOnScrollListener(l);
    }

    @Override
    public void setDividerHeight(int dividerHeight) {
        ((ListView)mContentView).setDividerHeight(dividerHeight);
    }

    @Override
    public void setDivider(@Nullable Drawable divider) {
        ((ListView)mContentView).setDivider(divider);
    }

    @Override
    public Drawable getDivider() {
        return ((ListView)mContentView).getDivider();
    }

    @Override
    public void smoothScrollToPosition(int position) {
        ((ListView)mContentView).smoothScrollToPosition(position);
    }

    @Override
    public void smoothScrollByOffset(int offset) {
        ((ListView)mContentView).smoothScrollByOffset(offset);
    }

    public void setSelection(int selection) {
        ((ListView)mContentView).setSelection(selection);
    }

    @Override
    public int getSelectedItemPosition() {
        return ((ListView)mContentView).getSelectedItemPosition();
    }

    @Override
    public int getDividerHeight() {
        return ((ListView)mContentView).getDividerHeight();
    }
}
