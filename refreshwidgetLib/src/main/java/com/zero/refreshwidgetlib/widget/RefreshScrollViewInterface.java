package com.zero.refreshwidgetlib.widget;

import android.view.View;

/**
 * @author linzewu
 * @date 16-7-21
 */
public interface RefreshScrollViewInterface {
    /**
     * 添加视图
     * @param view
     */
    void addView(View view);

    /**
     * 移除视图
     * @param view
     */
    void removeView(View view);

    /**
     * 添加视图
     * @param view
     * @param position
     */
    void addView(View view, int position);
}
