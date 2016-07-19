package com.zero.refreshwidgetlib.header;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * BaseHeader
 * @author linzewu
 * @date 16-7-5
 */
public abstract class BaseHeader extends RelativeLayout implements HeaderInterface{
    
    protected float mPercent;

    @Override
    public float getPercent() {
        return mPercent;
    }

    @Override
    public void setPercent(float percent) {
        mPercent = percent;
    }

    public BaseHeader(Context context) {
        super(context);
    }

    public BaseHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onRefresh(float percent) {
        this.mPercent = percent;
    }

    @Override
    public void onRefreshIng() {
        
    }

    @Override
    public void onReleaseToRefresh() {
        
    }
}
