package com.xuejinwei.aboutrecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.xuejinwei.aboutrecyclerview.adapter.CommonRVAdapter;
import com.xuejinwei.aboutrecyclerview.adapter.CommonViewHolder;
import com.xuejinwei.aboutrecyclerview.recyclerview.CommonRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private CommonRecyclerView mCommonRecyclerView;

    private CommonRVAdapter<String> mCommonRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCommonRecyclerView = (CommonRecyclerView) findViewById(R.id.crv_main);

        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            stringList.add("text" + i);
        }
        mCommonRVAdapter = new CommonRVAdapter<String>(this, R.layout.item_text, stringList) {
            @Override
            public void convert(CommonViewHolder gViewHolder, String s) {
                gViewHolder.setText(R.id.tv_text, s);

            }
        };

        mCommonRVAdapter.setOnGItemClickListener(new CommonRVAdapter.OnGItemClickListener<String>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, String data, int position) {
                Log.i("-----position-----:", position + "");
            }
        });
        mCommonRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCommonRecyclerView.setAdapter(mCommonRVAdapter);

    }
}
