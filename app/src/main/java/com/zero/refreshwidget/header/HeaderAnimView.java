package com.zero.refreshwidget.header;

import android.content.Context;
import android.util.AttributeSet;

/**
 * @author linzewu
 * @date 16-6-29
 */
public class HeaderAnimView extends BaseHeader{

    public HeaderAnimView(Context context) {
        super(context);
    }

    public HeaderAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeaderAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onRefresh(int percent) {
        
    }

    @Override
    public void onReleaseToRefresh() {

    }
}
