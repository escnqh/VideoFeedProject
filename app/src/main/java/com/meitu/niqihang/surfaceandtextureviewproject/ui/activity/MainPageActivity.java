package com.meitu.niqihang.surfaceandtextureviewproject.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.meitu.niqihang.surfaceandtextureviewproject.R;
import com.meitu.niqihang.surfaceandtextureviewproject.base.BaseActivity;
import com.meitu.niqihang.surfaceandtextureviewproject.contract.MainPageContract;
import com.meitu.niqihang.surfaceandtextureviewproject.entity.FeedInfoBean;
import com.meitu.niqihang.surfaceandtextureviewproject.presenter.MainPagePresenter;
import com.meitu.niqihang.surfaceandtextureviewproject.ui.adapter.MainPagePagerAdapter;
import com.meitu.niqihang.surfaceandtextureviewproject.ui.fragment.FirstFragment;
import com.meitu.niqihang.surfaceandtextureviewproject.ui.fragment.SecondFragment;
import com.meitu.niqihang.surfaceandtextureviewproject.utils.Contracts;
import com.meitu.niqihang.surfaceandtextureviewproject.utils.MediaPlayerManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author niqihang on 2018/10/10
 */
public class MainPageActivity extends BaseActivity<MainPageContract.View, MainPagePresenter> implements MainPageContract.View, MainPageContract.Presenter {
    private static final String TAG = "MainPageActivity";
    private MainPagePagerAdapter mMainPagePagerAdapter;
    private List<Fragment> mFragments = new ArrayList<>();
    private ViewPager mViewPager;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        mViewPager = findViewById(R.id.vp_main_page);
        mFragmentManager = getSupportFragmentManager();
        mMainPagePagerAdapter = new MainPagePagerAdapter(mFragmentManager, mFragments);
        mViewPager.setAdapter(mMainPagePagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                MediaPlayerManager.getInstance().releaseMediaPlayer();
                Log.i(TAG,"page scrolled");
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        requestFeed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        MediaPlayerManager.getInstance().releaseMediaPlayer();
    }

    @Override
    protected MainPagePresenter createPresenter() {
        return new MainPagePresenter(this);
    }


    @Override
    public void requestFeed() {
        mPresenter.requestFeed();
    }

    @Override
    public void pushFeed(List<FeedInfoBean> feedInfoBeanList) {
        mPresenter.pushFeed(feedInfoBeanList);
    }


    @Override
    public void showFeed(List<FeedInfoBean> feedInfoBeanList) {
        for (int i = 0; i < feedInfoBeanList.size(); i++) {
            if (feedInfoBeanList.get(i) != null) {
                if (feedInfoBeanList.get(i).getFeedType().equals(Contracts.SURFACE_VIEW_KEY)) {
                    mMainPagePagerAdapter.addFragment(FirstFragment.newInstance(feedInfoBeanList.get(i)));
                    mMainPagePagerAdapter.notifyDataSetChanged();
                } else if (feedInfoBeanList.get(i).getFeedType().equals(Contracts.TEXTURE_VIEW_KEY)) {
                    mMainPagePagerAdapter.addFragment(SecondFragment.newInstance(feedInfoBeanList.get(i)));
                    mMainPagePagerAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}
