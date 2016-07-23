package com.zero.refreshwidgetlib.widget;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @author linzewu
 * @date 16-7-21
 */
public interface RefreshScrollViewInterface {

    /**
     * Register a callback to be invoked when this view is clicked. If this view is not
     * clickable, it becomes clickable.
     *
     * @param l The callback that will run
     *
     */
    void setOnClickListener(@Nullable View.OnClickListener l);

    /**
     * {@inheritDoc}
     *
     * <p>This version also clamps the scrolling to the bounds of our child.
     */
    void scrollTo(int x, int y);

    /**
     * Move the scrolled position of your view. This will cause a call to
     * and the view will be
     * invalidated.
     * @param x the amount of pixels to scroll by horizontally
     * @param y the amount of pixels to scroll by vertically
     */
    void scrollBy(int x, int y);

    /**
     * Return the scrolled left position of this view. This is the left edge of
     * the displayed part of your view. You do not need to draw any pixels
     * farther left, since those are outside of the frame of your view on
     * screen.
     *
     * @return The left edge of the displayed part of your view, in pixels.
     */
    int getContentScrollX();

    /**
     * Return the scrolled top position of this view. This is the top edge of
     * the displayed part of your view. You do not need to draw any pixels above
     * it, since those are outside of the frame of your view on screen.
     *
     * @return The top edge of the displayed part of your view, in pixels.
     */
    int getContentScrollY();

    /**
     * Set the horizontal scrolled position of your view. This will cause a call to
     * and the view will be
     * invalidated.
     * @param value the x position to scroll to
     */
    void setScrollX(int value);

    /**
     * Set the vertical scrolled position of your view. This will cause a call to
     * and the view will be
     * invalidated.
     * @param value the y position to scroll to
     */
    void setScrollY(int value);

    /**
     * Register a callback to be invoked when the scroll X or Y positions of
     * this view change.
     * <p>
     * <b>Note:</b> Some views handle scrolling independently from View and may
     * have their own separate listeners for scroll-type events. For example,
     * {@link android.widget.ListView ListView} allows clients to register an
     * {@link android.widget.ListView#setOnScrollListener(android.widget.AbsListView.OnScrollListener) AbsListView.OnScrollListener}
     * to listen for changes in list scroll position.
     *
     * @param l The listener to notify when the scroll X or Y position changes.
     * @see android.view.View#getScrollX()
     * @see android.view.View#getScrollY()
     */
    void setOnScrollChangeListener(View.OnScrollChangeListener l);

    /**
     * Register a callback to be invoked when focus of this view changed.
     *
     * @param l The callback that will run.
     */
    void setOnFocusChangeListener(View.OnFocusChangeListener l);

    /**
     * Add a listener that will be called when the bounds of the view change due to
     * layout processing.
     *
     * @param listener The listener that will be called when layout bounds change.
     */
    void addOnLayoutChangeListener(View.OnLayoutChangeListener listener); 
        
    /**
     * 添加视图
     * @param view
     */
    void addContentView(View view);

    /**
     * 移除视图
     * @param view
     */
    void removeContentView(View view);

    /**
     * 添加视图
     * @param view
     * @param position
     */
    void addContentView(View view, int position);

    /**
     * 添加视图
     * @param view
     * @param layoutParams
     */
    void addContentView(View view, LinearLayout.LayoutParams layoutParams);

}
