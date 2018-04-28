package com.example.luo.tinklabsdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：luo on 2018/4/28 14:30
 * 邮箱：luoforszzk@gmail.com
 */

public class MultipItemAdapter extends RecyclerView.Adapter {

    public static final int TYPE_ONLY_IMAGE = 0;
    public static final int TYPE_TEXT_IMAGE = 1;
    private List<DemoTypeBean> mData;
    private Context mContext;

    public MultipItemAdapter(Context mContext, List<DemoTypeBean> data) {
        this.mContext = mContext;
        this.mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建不同的 ViewHolder
        View view;
        //根据viewtype来创建条目
        if (viewType == TYPE_ONLY_IMAGE) {
            view = View.inflate(parent.getContext(), R.layout.item_only_image_layout, null);
            return new OnlyImageHolder(view);
        } else if (viewType == TYPE_TEXT_IMAGE) {
            view = View.inflate(parent.getContext(), R.layout.item_text_image_layout, null);
            return new TextImageHolder(view);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OnlyImageHolder) {
            ((OnlyImageHolder) holder).itemImg.setImageResource(mData.get(position).pic);
        } else if (holder instanceof TextImageHolder) {
            ((TextImageHolder) holder).itemImg.setImageResource(mData.get(position).pic);
            ((TextImageHolder) holder).itemContent.setText(mData.get(position).name);
        }
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    //根据条件返回条目的类型
    @Override
    public int getItemViewType(int position) {
        DemoTypeBean demoTypeBean = mData.get(position);
        if (demoTypeBean.type == 0) {
            return TYPE_ONLY_IMAGE;
        } else if (demoTypeBean.type == 1) {
            return TYPE_TEXT_IMAGE;
        } else {
            return 0;
        }
    }

    public class OnlyImageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_item_only_img)
        ImageView itemImg;

        public OnlyImageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class TextImageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_item_text_img)
        ImageView itemImg;
        @BindView(R.id.iv_item_text_content)
        TextView itemContent;

        public TextImageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
