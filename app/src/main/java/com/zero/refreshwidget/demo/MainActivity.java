package com.zero.refreshwidget.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.zero.refreshwidget.R;
import com.zero.refreshwidget.RefreshListViewWidget;
import com.zero.refreshwidget.RefreshListener;
import com.zero.refreshwidget.header.HeaderAnimView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author linzewu
 * @date 16-6-29
 */
public class MainActivity extends Activity {
    
    private RefreshListViewWidget mRefreshListViewWidget;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRefreshListViewWidget = (RefreshListViewWidget) findViewById(R.id.refresh_list);
        mList.add("text1");
        mList.add("text2");
        mList.add("text3");
        mList.add("text4");
//        mList.add("text5");
//        mList.add("text6");
//        mList.add("text7");
//        mList.add("text8");
//        mList.add("text9");
//        mList.add("text10");
//        mList.add("text11");
//        mList.add("text12");
//        mRefreshListViewWidget.addHeaderView(new HeaderAnimView(mRefreshListViewWidget.getContext()));
        mRefreshListViewWidget.setAdapter(new DemoAdapter());
        mRefreshListViewWidget.setRefreshListener(new RefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(8000);
                            mRefreshListViewWidget.refreshComplete();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            @Override
            public void onLoadMore() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(8000);
                            mRefreshListViewWidget.loadMoreComplete();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
            

            @Override
            public void onScrolling() {

            }
        });
    }
    
    private List<String> mList = new ArrayList<>();
    
    private class DemoAdapter extends BaseAdapter {

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
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout
                        .item_list_view, null);
            }
            TextView textView = (TextView) convertView.findViewById(R.id.textview);
            textView.setText(mList.get(position));
            return convertView;
        }
    }
    
}
