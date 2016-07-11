package com.zero.refreshwidget.header;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zero.refreshwidget.utils.DrawUtils;

/**
 * @author linzewu
 * @date 16-6-29
 */
public class HeaderTextView extends BaseHeader implements HeaderInterface{
    
    private final static String TEXT_START_TO_REFRESH = "下拉刷新";
    
    private final static String TEXT_RELEASE_TO_REFRESH = "松手刷新";
    
    private final static String TEXT_REFRESHING = "正在刷新...";
    
    private final static int TEXT_COLOR = 0x88000000;
    
    private final static int TEXT_SIZE = 10;
    
    private final static int TEXT_PADDING = 15;
    
    private TextView mTextView;
    
    public HeaderTextView(Context context) {
        super(context);
        init();
    }

    public HeaderTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HeaderTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    private void init() {
        mTextView = new TextView(getContext());
        addView(mTextView);
    }
    

    @Override
    public void onRefresh(float percent) {
        mTextView.setTextColor(TEXT_COLOR);
        mTextView.setTextSize(DrawUtils.dip2px(getContext(), TEXT_SIZE));
        setPadding(DrawUtils.dip2px(getContext(), TEXT_PADDING), DrawUtils.dip2px(getContext(), TEXT_PADDING),
                DrawUtils.dip2px(getContext(), TEXT_PADDING), DrawUtils.dip2px(getContext(), TEXT_PADDING));
        setGravity(Gravity.CENTER);
        mTextView.setText(TEXT_START_TO_REFRESH);
    }

    @Override
    public void onReleaseToRefresh() {
        mTextView.setTextColor(TEXT_COLOR);
        mTextView.setTextSize(DrawUtils.dip2px(getContext(), TEXT_SIZE));
        setPadding(DrawUtils.dip2px(getContext(), TEXT_PADDING), DrawUtils.dip2px(getContext(), TEXT_PADDING),
                DrawUtils.dip2px(getContext(), TEXT_PADDING), DrawUtils.dip2px(getContext(), TEXT_PADDING));
        setGravity(Gravity.CENTER);
        mTextView.setText(TEXT_RELEASE_TO_REFRESH);
    }

    @Override
    public void onRefreshIng() {
        mTextView.setTextColor(TEXT_COLOR);
        mTextView.setTextSize(DrawUtils.dip2px(getContext(), TEXT_SIZE));
        setPadding(DrawUtils.dip2px(getContext(), TEXT_PADDING), DrawUtils.dip2px(getContext(), TEXT_PADDING),
                DrawUtils.dip2px(getContext(), TEXT_PADDING), DrawUtils.dip2px(getContext(), TEXT_PADDING));
        setGravity(Gravity.CENTER);
        mTextView.setText(TEXT_REFRESHING);
    }
    
    
}
