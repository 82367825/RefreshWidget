package com.zero.refreshwidgetlib.widget;

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
}
