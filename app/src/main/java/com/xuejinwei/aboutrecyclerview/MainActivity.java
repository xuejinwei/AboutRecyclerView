package com.xuejinwei.aboutrecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xuejinwei.aboutrecyclerview.adapter.CommonRVAdapter;
import com.xuejinwei.aboutrecyclerview.adapter.CommonViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mCommonRecyclerView;

    private CommonRVAdapter<String> mCommonRVAdapter;

    private List<String> mStringList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCommonRecyclerView = (RecyclerView) findViewById(R.id.rv_main);

        mStringList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mStringList.add("text--" + i);
        }
        mCommonRVAdapter = new CommonRVAdapter<String>(this, R.layout.item_text, mStringList) {
            @Override
            public void convert(CommonViewHolder gViewHolder, String s) {
                gViewHolder.setText(R.id.tv_text, s);

            }
        };
        mCommonRVAdapter.setOnGItemClickListener(new CommonRVAdapter.OnGItemClickListener<String>() {
            @Override
            public void onItemClick(String data, int position) {
                Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
            }
        });
        addHeaderAndFooter();
        mCommonRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mCommonRecyclerView.setAdapter(mCommonRVAdapter);

    }

    private void addHeaderAndFooter() {
        View viewAdapterHeader = LayoutInflater.from(this).inflate(R.layout.item_text, (ViewGroup) mCommonRecyclerView.getParent(), false);
        View viewAdapterFooter = LayoutInflater.from(this).inflate(R.layout.item_text, (ViewGroup) mCommonRecyclerView.getParent(), false);
        View viewRecyclerviewHeader = LayoutInflater.from(this).inflate(R.layout.item_text, (ViewGroup) mCommonRecyclerView.getParent(), false);
        View viewRecyclerviewFooter = LayoutInflater.from(this).inflate(R.layout.item_text, (ViewGroup) mCommonRecyclerView.getParent(), false);
        viewAdapterHeader.findViewById(R.id.tv_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "this is header,adapter", Toast.LENGTH_SHORT).show();
            }
        });
        viewAdapterFooter.findViewById(R.id.tv_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "this is footer,adapter", Toast.LENGTH_SHORT).show();
            }
        });
        viewRecyclerviewHeader.findViewById(R.id.tv_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "this is header,recyclerview", Toast.LENGTH_SHORT).show();
            }
        });
        viewRecyclerviewFooter.findViewById(R.id.tv_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "this is footer,recyclerview", Toast.LENGTH_SHORT).show();
            }
        });
        mCommonRVAdapter.addHeaderView(viewAdapterHeader);
        mCommonRVAdapter.addFooterView(viewAdapterFooter);
    }
}
