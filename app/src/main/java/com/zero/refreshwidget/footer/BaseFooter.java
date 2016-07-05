package com.zero.refreshwidget.footer;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * @author linzewu
 * @date 16-7-5
 */
public abstract class BaseFooter extends RelativeLayout implements FooterInterface {
    public BaseFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BaseFooter(Context context) {
        super(context);
    }
}
