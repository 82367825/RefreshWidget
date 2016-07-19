package com.zero.refreshwidgetlib.widget.scrollview;

import com.zero.refreshwidgetlib.footer.BaseFooter;
import com.zero.refreshwidgetlib.header.BaseHeader;

/**
 * @author linzewu
 * @date 16-7-19
 */
public class RefreshScrollViewInterface {
    /**
     * 设置下拉刷新功能是否可用
     * @param enabled
     */
    void setRefreshEnabled(boolean enabled);

    /**
     * 设置上拉加载功能是否可用
     * @param enabled
     */
    void setLoadMoreEnabled(boolean enabled);

    /**
     * 添加下拉刷新组件
     * @param baseHeader
     */
    void addHeaderView(BaseHeader baseHeader);

    /**
     * 添加上拉加载组件
     * @param baseFooter
     */
    void addFooterView(BaseFooter baseFooter);

    /**
     * 下拉刷新完成
     */
    void completeRefresh();

    /**
     * 上拉加载完成
     */
    void completeLoadMore();
}
