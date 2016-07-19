package com.zero.refreshwidgetlib.footer;

/**
 * @author linzewu
 * @date 16-7-5
 */
public interface FooterInterface {
    /**
     * 正在加载更多
     */
    void onLoadMore(float percent);

    /**
     * 松手加载更多
     */
    void onReleaseToLoadMore();

    /**
     * 
     */
    void onLoadMoreIng();

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
