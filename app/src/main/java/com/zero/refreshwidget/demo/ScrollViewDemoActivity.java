package com.zero.refreshwidget.demo;

import android.app.Activity;
import android.os.Bundle;

import com.zero.refreshwidget.R;
import com.zero.refreshwidgetlib.widget.RefreshScrollViewWidget;

/**
 * @author linzewu
 * @date 16-7-19
 */
public class ScrollViewDemoActivity extends Activity {

    
    private RefreshScrollViewWidget mRefreshScrollViewWidget;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollview);

        mRefreshScrollViewWidget = (RefreshScrollViewWidget) findViewById(R.id.refresh_scrollview);
    }
}
