package com.example.luo.tinklabsdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：luo on 2018/4/28 11:06
 * 邮箱：luoforszzk@gmail.com
 */

public class TinkFragment extends Fragment {

    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    private List<DemoTypeBean> mData;
    private MultipItemAdapter ada;
    private boolean isLoadingMore;//是否正在进行“加载更多”的操作，避免重复发起请求


    private int[] icons = {
            R.mipmap.demo_pic_1,
            R.mipmap.demo_pic_2,
            R.mipmap.demo_pic_3,
            R.mipmap.demo_pic_4,
            R.mipmap.demo_pic_5,
            R.mipmap.demo_pic_6,
            R.mipmap.demo_pic_7,
            R.mipmap.demo_pic_8,
            R.mipmap.demo_pic_9
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_layout, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView();
        return view;
    }

    private void initData() {
        mData = new ArrayList<DemoTypeBean>();
//        随机数 用来标记item界面的类型
        Random random = new Random();
        for (int i = 0; i < icons.length; i++) {
            DemoTypeBean more = new DemoTypeBean();
            more.pic = icons[i];
            more.name = "food" + (i + 1) + "图文描述";
            more.type = random.nextInt(2);
            mData.add(more);
        }
    }

    private void initView() {
        // 进度动画颜色
        mSrlRefresh.setColorSchemeResources(R.color.orange, R.color.green, R.color.lite_blue, R.color.colorAccent);
        // 下拉刷新
        mSrlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrlRefresh.postDelayed(new Runnable() { // 发送延迟消息到消息队列
                    @Override
                    public void run() {
                        mSrlRefresh.setRefreshing(false); // 是否显示刷新进度;false:不显示
                    }
                }, 1000);
            }
        });
        mRvList.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvList.setHasFixedSize(true);
        ada = new MultipItemAdapter(getContext(), mData);
        //设置上拉加载更多的监听
        mRvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                //获取最后一个完全显示的itemPosition
                int lastItemPosition = manager.findLastCompletelyVisibleItemPosition();
                int itemCount = manager.getItemCount();
                // 判断是否滑动到了最后一个item，并且是向上滑动
                // dy大于0表示正在向上滑动，小于等于0表示停止或向下滑动
                if (lastItemPosition == (itemCount - 1) && dy > 0) {
                    //加载更多
                    onLoadMore();
                }
            }
        });
        mRvList.setAdapter(ada);
    }

    private void onLoadMore() {
        if (mSrlRefresh.isRefreshing())
            return;
        //如果正在加载更多数据，则不重复发起请求
        if (!isLoadingMore) {
            isLoadingMore = true;
            Random random = new Random();
            List<DemoTypeBean> moreDatas = new ArrayList<DemoTypeBean>();
            for (int i = 0; i < icons.length; i++) {
                DemoTypeBean more = new DemoTypeBean();
                more.pic = icons[i];
                more.name = "food" + (i + 1) + "图文描述";
                more.type = random.nextInt(2);
                moreDatas.add(more);
            }
            ada.notifyItemRangeInserted(mData.size(), moreDatas.size());
            mData.addAll(moreDatas.size(), moreDatas);
            ada.notifyItemRangeChanged(moreDatas.size(), moreDatas.size());

            isLoadingMore = false;
        }
    }

}
