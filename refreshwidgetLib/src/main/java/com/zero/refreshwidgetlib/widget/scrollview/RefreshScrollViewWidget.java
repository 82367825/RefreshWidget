package com.zero.refreshwidgetlib.widget.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * @author linzewu
 * @date 16-7-6
 */
public class RefreshScrollViewWidget extends ScrollView {
    
    private static final int STATUS_NORMAL = 0;
    private static final int STATUS_REFRESH = 1;
    private static final int STATUS_RELEASE_TO_REFRESH = 2;
    private static final int STATUS_REFRESH_ING = 3;
    private static final int STATUS_LOAD_MORE = 4;
    private static final int STATUS_RELEASE_TO_LOAD_MORE = 5;
    private static final int STATUS_LOAD_MORE_ING = 6;
    
    public RefreshScrollViewWidget(Context context) {
        super(context);
    }

    public RefreshScrollViewWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshScrollViewWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
