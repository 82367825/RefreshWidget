package com.zero.refreshwidgetlib.widget;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.WrapperListAdapter;

/**
 * @author linzewu
 * @date 16-7-19
 */
public interface RefreshListViewInterface {

    /**
     * Register a callback to be invoked when an item in this AdapterView has
     * been clicked.
     *
     * @param listener The callback that will be invoked.
     */
    void setOnItemClickListener(@Nullable AdapterView.OnItemClickListener listener);
    
    /**
     * Sets the data behind this ListView.
     *
     * The adapter passed to this method may be wrapped by a {@link WrapperListAdapter},
     * depending on the ListView features currently in use. For instance, adding
     * headers and/or footers will cause the adapter to be wrapped.
     *
     * @param adapter The ListAdapter which is responsible for maintaining the
     *        data backing this list and for producing a view to represent an
     *        item in that data set.
     */
    void setAdapter(ListAdapter adapter);

    /**
     * Set the listener that will receive notifications every time the list scrolls.
     *
     * @param l the scroll listener
     */
    void setOnScrollListener(AbsListView.OnScrollListener l);

    /**
     * Sets the currently selected item. If in touch mode, the item will not be selected
     * but it will still be positioned appropriately. If the specified selection position
     * is less than 0, then the item at position 0 will be selected.
     *
     * @param position Index (starting at 0) of the data item to be selected.
     */
    void setSelection(int position);

    /**
     * Return the position of the currently selected item within the adapter's data set
     *
     * @return int Position
     */
    int getSelectedItemPosition();


    /**
     * @return Returns the height of the divider that will be drawn between each item in the list.
     */
    int getDividerHeight();

    /**
     * Sets the height of the divider that will be drawn between each item in the list. Calling
     * this will override the intrinsic height as set by {@link #setDivider(Drawable)}
     *
     * @param height The new height of the divider in pixels.
     */
    void setDividerHeight(int height);

    /**
     * Sets the drawable that will be drawn between each item in the list.
     * <p>
     * <strong>Note:</strong> If the drawable does not have an intrinsic
     * height, you should also call {@link #setDividerHeight(int)}.
     *
     * @param divider the drawable to use
     * @attr ref R.styleable#ListView_divider
     */
    void setDivider(@Nullable Drawable divider);


    /**
     * Returns the drawable that will be drawn between each item in the list.
     *
     * @return the current drawable drawn between list elements
     * @attr ref R.styleable#ListView_divider
     */
    Drawable getDivider();

    /**
     * Smoothly scroll to the specified adapter position. The view will
     * scroll such that the indicated position is displayed.
     * @param position Scroll to this adapter position.
     */
    void smoothScrollToPosition(int position);

    /**
     * Smoothly scroll to the specified adapter position offset. The view will
     * scroll such that the indicated position is displayed.
     * @param offset The amount to offset from the adapter position to scroll to.
     */
    void smoothScrollByOffset(int offset);
}
