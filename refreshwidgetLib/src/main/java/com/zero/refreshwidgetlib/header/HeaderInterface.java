package com.zero.refreshwidgetlib.header;

/**
 * @author linzewu
 * @date 16-6-29
 */
public interface HeaderInterface {
    /**
     * 正在下拉刷新
     * @param percent 下拉完成比例
     */
    void onRefresh(float percent);

    /**
     * 松手刷新
     */
    void onReleaseToRefresh();

    /**
     * 正在刷新
     */
    void onRefreshIng();

    /**
     * 设置HeaderView完成显示比例
     * @param percent
     */
    void setPercent(float percent);

    /**
     * 获取HeaderView完成比例
     * @return
     */
    float getPercent();
}
