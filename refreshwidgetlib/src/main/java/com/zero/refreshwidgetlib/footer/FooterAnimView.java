package com.zero.refreshwidgetlib.footer;

import android.content.Context;
import android.util.AttributeSet;

/**
 * @author linzewu
 * @date 16/7/8
 */
public class FooterAnimView extends BaseFooter {
    public FooterAnimView(Context context) {
        super(context);
    }

    public FooterAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FooterAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    

    @Override
    public void onLoadMore(float percent) {
        
    }

    @Override
    public void onReleaseToLoadMore() {

    }

    @Override
    public void onLoadMoreIng() {
        
    }
}
