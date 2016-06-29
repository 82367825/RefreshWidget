package com.zero.refreshwidget.header;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import com.zero.refreshwidget.utils.DrawUtils;

/**
 * @author linzewu
 * @date 16-6-29
 */
public class HeaderTextView extends TextView implements HeaderInterface{
    
    private final static String TEXT_START_TO_REFRESH = "下拉加载更多";
    
    private final static String TEXT_RELEASE_TO_REFRESH = "松手刷新";
    
    private final static int TEXT_COLOR = 0x88000000;
    
    private final static int TEXT_SIZE = 10;
    
    private final static int TEXT_PADDING = 10;
    
    public HeaderTextView(Context context) {
        super(context);
    }

    public HeaderTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeaderTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }
    

    @Override
    public void onRefresh(int percent) {
        setTextColor(TEXT_COLOR);
        setTextSize(DrawUtils.dip2px(getContext(), TEXT_SIZE));
        setPadding(DrawUtils.dip2px(getContext(), TEXT_PADDING), DrawUtils.dip2px(getContext(), TEXT_PADDING),
                DrawUtils.dip2px(getContext(), TEXT_PADDING), DrawUtils.dip2px(getContext(), TEXT_PADDING));
        setGravity(Gravity.CENTER);
        setText(TEXT_START_TO_REFRESH);
    }

    @Override
    public void onReleaseToRefresh() {
        setTextColor(TEXT_COLOR);
        setTextSize(DrawUtils.dip2px(getContext(), TEXT_SIZE));
        setPadding(DrawUtils.dip2px(getContext(), TEXT_PADDING), DrawUtils.dip2px(getContext(), TEXT_PADDING),
                DrawUtils.dip2px(getContext(), TEXT_PADDING), DrawUtils.dip2px(getContext(), TEXT_PADDING));
        setGravity(Gravity.CENTER);
        setText(TEXT_RELEASE_TO_REFRESH);
    }
}
