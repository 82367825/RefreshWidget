package com.zero.refreshwidget;

/**
 * @author linzewu
 * @date 16-6-29
 */
public interface RefreshListener {
    /**
     * calling when refreshing data
     */
    void onRefresh();

    /**
     * calling when loading more data
     */
    void onLoadMore();

    /**
     * calling when the content view is scrolling
     */
    void onScrolling();
}
