package com.zero.refreshwidget.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.zero.refreshwidget.R;
import com.zero.refreshwidgetlib.footer.FooterAnimView;
import com.zero.refreshwidgetlib.header.HeaderAnimView;
import com.zero.refreshwidgetlib.widget.RefreshListViewWidget;
import com.zero.refreshwidgetlib.widget.RefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author linzewu
 * @date 16-7-19
 */
public class ListViewDemoActivity extends Activity {

    private RefreshListViewWidget mRefreshListViewWidget;
    private ListViewAdapter mListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        initData();
        mListViewAdapter = new ListViewAdapter();
        mRefreshListViewWidget = (RefreshListViewWidget) findViewById(R.id.refresh_list);
        mRefreshListViewWidget.addHeaderView(new HeaderAnimView(ListViewDemoActivity.this));
        mRefreshListViewWidget.addFooterView(new FooterAnimView(ListViewDemoActivity.this));
        mRefreshListViewWidget.setAdapter(mListViewAdapter);
        mRefreshListViewWidget.setRefreshListener(new RefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }

            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        });
        mRefreshListViewWidget.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListViewDemoActivity.this, "click " + position + " item", 
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        for (int i = 0; i < TEST_DATA_NUMBER; i++) {
            mList.add("text" + i);
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
                    mainThreadRefreshData();
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
                    mainThreadLoadMoreData();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    
    private Handler mHandler = new Handler();

    /**
     * 避免在子线程中操作UI
     */
    private void mainThreadRefreshData() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mRefreshListViewWidget.completeRefresh();
                mListViewAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 避免在子线程中操作UI
     */
    private void mainThreadLoadMoreData() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mList.add("text" + (mList.size()));
                mRefreshListViewWidget.completeLoadMore();
                mListViewAdapter.notifyDataSetChanged();
            }
        });
    }
    
    /**
     * 测试的数据集合
     */
    private List<String> mList = new ArrayList<>();
    /**
     * 测试的数据数量
     */
    private static final int TEST_DATA_NUMBER = 15;

    private class ListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_view, null);
            }
            TextView textView = (TextView) convertView.findViewById(R.id.textview);
            textView.setText(mList.get(position));
            return convertView;
        }
    }
}
