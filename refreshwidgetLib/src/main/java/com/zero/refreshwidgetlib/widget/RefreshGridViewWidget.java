package com.zero.refreshwidgetlib.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
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
 * RefreshGridView
 * @author linzewu
 * @date 16-6-29
 */
public class RefreshGridViewWidget extends BaseRefreshWidget implements RefreshGridViewInterface{
    public RefreshGridViewWidget(Context context) {
        super(context);
    }

    public RefreshGridViewWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RefreshGridViewWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected boolean isReachHeader() {
        return ((GridView)mContentView).getFirstVisiblePosition() == 0;
    }

    @Override
    protected boolean isReachFooter() {
        return ((GridView)mContentView).getLastVisiblePosition() == ((GridView)mContentView).getCount() - 1
                && ((GridView)mContentView).getFirstVisiblePosition() != 0;
    }

    @Override
    protected View getContentView() {
        return new GridView(getContext());
    }

    @Override
    protected void makeContentViewToFooter() {
        ((GridView)mContentView).setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
    }

    @Override
    protected void makeContentViewRestore() {
        ((GridView)mContentView).setTranscriptMode(AbsListView.TRANSCRIPT_MODE_DISABLED);
    }

    @Override
    public void setOnItemClickListener(@Nullable AdapterView.OnItemClickListener listener) {
        ((GridView)mContentView).setOnItemClickListener(listener);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        ((GridView)mContentView).setAdapter(adapter);
    }

    @Override
    public void setOnScrollListener(AbsListView.OnScrollListener l) {
        ((GridView)mContentView).setOnScrollListener(l);
    }

    @Override
    public void setSelection(int position) {
        ((GridView)mContentView).setSelection(position);
    }

    @Override
    public int getSelectedItemPosition() {
        return ((GridView)mContentView).getSelectedItemPosition();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int getColumnWidth() {
        return ((GridView)mContentView).getColumnWidth();
    }

    @Override
    public void setNumColumns(int numColumns) {
        ((GridView)mContentView).setNumColumns(numColumns);
    }

    @Override
    public int getNumColumns() {
        return ((GridView)mContentView).getNumColumns();
    }


    @Override
    public void smoothScrollToPosition(int position) {
        ((GridView)mContentView).smoothScrollToPosition(position);
    }

    @Override
    public void smoothScrollByOffset(int offset) {
        ((GridView)mContentView).smoothScrollByOffset(offset);
    }
}
