package com.zero.refreshwidget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * RefreshGridView
 * @author linzewu
 * @date 16-6-29
 */
public class RefreshGridViewWidget extends RefreshWidget{
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
    public void setRefreshEnabled(boolean enabled) {
        
    }

    @Override
    public void setLoadMoreEnabled(boolean enabled) {

    }
}
