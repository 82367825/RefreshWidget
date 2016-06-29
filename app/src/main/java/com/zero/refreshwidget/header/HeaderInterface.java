package com.zero.refreshwidget.header;

/**
 * @author linzewu
 * @date 16-6-29
 */
public interface HeaderInterface {
    /**
     * 正在下拉刷新
     * @param percent 下拉完成比例
     */
    void onRefresh(int percent);

    /**
     * 松手刷新
     */
    void onReleaseToRefresh();
    
}
