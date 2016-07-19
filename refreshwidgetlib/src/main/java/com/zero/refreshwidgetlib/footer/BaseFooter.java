package com.zero.refreshwidgetlib.footer;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * @author linzewu
 * @date 16-7-5
 */
public abstract class BaseFooter extends RelativeLayout implements FooterInterface {
    
    private float mPercent;

    @Override
    public void setPercent(float percent) {
        mPercent = percent;
    }

    @Override
    public float getPercent() {
        return mPercent;
    }

    public BaseFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BaseFooter(Context context) {
        super(context);
    }

    @Override
    public void onLoadMore(float percent) {
        this.mPercent = percent;
    }

    @Override
    public void onLoadMoreIng() {
        
    }

    @Override
    public void onReleaseToLoadMore() {
        
    }
}
