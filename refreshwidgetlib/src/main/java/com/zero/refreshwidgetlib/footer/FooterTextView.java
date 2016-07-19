package com.zero.refreshwidgetlib.footer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zero.refreshwidgetlib.utils.DrawUtils;

/**
 * @author linzewu
 * @date 16-7-6
 */
public class FooterTextView extends BaseFooter {

    private final static String TEXT_START_TO_LOAD_MORE = "上拉加载更多";

    private final static String TEXT_RELEASE_TO_LOAD_MORE = "松手加载";

    private final static String TEXT_LOAD_MORE_ING = "正在加载...";

    private final static int TEXT_COLOR = 0x88000000;

    private final static int TEXT_SIZE = 10;

    private final static int TEXT_PADDING = 15;

    private TextView mTextView;
    
    public FooterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FooterTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public FooterTextView(Context context) {
        super(context);
        init();
    }
                    
    private void init() {
        mTextView = new TextView(getContext());
        addView(mTextView, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }


    @Override
    public void onLoadMore(float percent) {
        super.onLoadMore(percent);
        mTextView.setTextColor(TEXT_COLOR);
        mTextView.setTextSize(DrawUtils.dip2px(getContext(), TEXT_SIZE));
        setPadding(DrawUtils.dip2px(getContext(), TEXT_PADDING), DrawUtils.dip2px(getContext(), TEXT_PADDING),
                DrawUtils.dip2px(getContext(), TEXT_PADDING), DrawUtils.dip2px(getContext(), TEXT_PADDING));
        setGravity(Gravity.CENTER);
        mTextView.setText(TEXT_START_TO_LOAD_MORE);
    }

    @Override
    public void onReleaseToLoadMore() {
        super.onReleaseToLoadMore();
        mTextView.setTextColor(TEXT_COLOR);
        mTextView.setTextSize(DrawUtils.dip2px(getContext(), TEXT_SIZE));
        setPadding(DrawUtils.dip2px(getContext(), TEXT_PADDING), DrawUtils.dip2px(getContext(), TEXT_PADDING),
                DrawUtils.dip2px(getContext(), TEXT_PADDING), DrawUtils.dip2px(getContext(), TEXT_PADDING));
        setGravity(Gravity.CENTER);
        mTextView.setText(TEXT_RELEASE_TO_LOAD_MORE);
    }

    @Override
    public void onLoadMoreIng() {
        super.onLoadMoreIng();
        mTextView.setTextColor(TEXT_COLOR);
        mTextView.setTextSize(DrawUtils.dip2px(getContext(), TEXT_SIZE));
        setPadding(DrawUtils.dip2px(getContext(), TEXT_PADDING), DrawUtils.dip2px(getContext(), TEXT_PADDING),
                DrawUtils.dip2px(getContext(), TEXT_PADDING), DrawUtils.dip2px(getContext(), TEXT_PADDING));
        setGravity(Gravity.CENTER);
        mTextView.setText(TEXT_LOAD_MORE_ING);
    }
}
