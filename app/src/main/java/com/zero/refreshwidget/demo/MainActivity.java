package com.zero.refreshwidget.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.zero.refreshwidget.R;
import com.zero.refreshwidgetlib.footer.FooterAnimView;
import com.zero.refreshwidgetlib.header.HeaderAnimView;
import com.zero.refreshwidgetlib.widget.RefreshListViewWidget;
import com.zero.refreshwidgetlib.widget.RefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author linzewu
 * @date 16-6-29
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button_listview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListViewDemoActivity.class));
            }
        });
        findViewById(R.id.button_gridview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GridViewDemoActivity.class));
            }
        });
        findViewById(R.id.button_scrollview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ScrollViewDemoActivity.class));
            }
        });
    }
    
}
