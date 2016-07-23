package com.zero.refreshwidget.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zero.refreshwidget.R;
import com.zero.refreshwidgetlib.widget.RefreshListener;
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
        mRefreshScrollViewWidget.setRefreshListener(new RefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }

            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        });
        for (int i = 0 ; i < 15; i++) {
            TextView textView = new TextView(ScrollViewDemoActivity.this);
            textView.setText("test");
            textView.setPadding(80, 80, 80, 80);
            textView.setBackgroundColor(0x88666666);
            mRefreshScrollViewWidget.addContentView(textView, new LinearLayout.LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    private void refreshData() {
        /* 模拟刷新数据,这里只做睡眠操作 */
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    /* 等待4秒 */
                    Thread.sleep(4000);
                    mRefreshScrollViewWidget.completeRefresh();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void loadMoreData() {
        /* 模拟加载更多数据,这里只做睡眠操作 */
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    /* 等待4秒 */
                    Thread.sleep(4000);
                    mRefreshScrollViewWidget.completeLoadMore();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
