package com.zero.refreshwidget.header;

import android.content.Context;
import android.util.AttributeSet;

/**
 * @author linzewu
 * @date 16-7-6
 */
public class HeaderImageView extends BaseHeader {
    public HeaderImageView(Context context) {
        super(context);
    }

    public HeaderImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeaderImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onRefresh(float percent) {
        
    }

    @Override
    public void onReleaseToRefresh() {

    }

    @Override
    public void onRefreshing() {

    }
}
