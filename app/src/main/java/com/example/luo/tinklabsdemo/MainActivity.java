package com.example.luo.tinklabsdemo;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    @BindView(R.id.tinkpage)
    ViewPager mTinkPager;

    private String[] titles = {"CITY GUIDE", "SHOP", "EAT"};
    private List<TinkFragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mFragments = new ArrayList<>();
        mFragments.add(new TinkFragment());
        mFragments.add(new TinkFragment());
        mFragments.add(new TinkFragment());

        mTinkPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                TinkFragment tinkFragment = new TinkFragment();
                Bundle bundle = new Bundle();
                bundle.putString("title", titles[position]);
                tinkFragment.setArguments(bundle);
                return tinkFragment;
            }

            @Override
            public int getCount() {
                return titles.length;
            }
        });

        mTablayout.setupWithViewPager(mTinkPager);
        for (int i = 0; i < mTablayout.getTabCount(); i++) {
            mTablayout.getTabAt(i).setText(titles[i]);
        }
    }
}
