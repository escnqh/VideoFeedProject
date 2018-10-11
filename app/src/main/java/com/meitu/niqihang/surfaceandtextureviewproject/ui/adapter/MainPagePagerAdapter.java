package com.meitu.niqihang.surfaceandtextureviewproject.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * 主页面ViewPager适配器
 *
 * @author nqh 2018/10/10.
 */
public class MainPagePagerAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = "MainPagePagerAdapter";
    private List<Fragment> mFragments;


    public MainPagePagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.mFragments = fragments;
    }

    /**
     * 向页面队列中添加Fragment对象
     *
     * @param fragment 想要添加的fragment
     */
    public void addFragment(Fragment fragment) {
        mFragments.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Object fragment = super.instantiateItem(container, position);
        mFragments.set(position, (Fragment) fragment);
        return fragment;
    }
}
