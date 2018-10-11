package com.meitu.niqihang.surfaceandtextureviewproject.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.meitu.niqihang.surfaceandtextureviewproject.R;
import com.meitu.niqihang.surfaceandtextureviewproject.base.BaseActivity;
import com.meitu.niqihang.surfaceandtextureviewproject.contract.MainPageContract;
import com.meitu.niqihang.surfaceandtextureviewproject.entity.FeedInfoBean;
import com.meitu.niqihang.surfaceandtextureviewproject.presenter.MainPagePresenter;
import com.meitu.niqihang.surfaceandtextureviewproject.ui.adapter.MainPagePagerAdapter;
import com.meitu.niqihang.surfaceandtextureviewproject.ui.fragment.FirstFragment;
import com.meitu.niqihang.surfaceandtextureviewproject.ui.fragment.SecondFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author niqihang on 2018/10/10
 */
public class MainPageActivity extends BaseActivity<MainPageContract.View, MainPagePresenter> implements MainPageContract.View, MainPageContract.Presenter {
    private static final String TAG = "MainPageActivity";
    private MainPagePagerAdapter mMainPagePagerAdapter;
    private FirstFragment mFirstFragment;
    private List<Fragment> mFragments = new ArrayList<>();
    private SecondFragment mSecondFragment;
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
        for (int i = 0; i < feedInfoBeanList.size(); i++) {
            if (feedInfoBeanList.get(i) != null) {
                if (feedInfoBeanList.get(i).getFeedType().equals("SurfaceView")) {

                }
            }
        }
    }


    @Override
    public void showFeed(List<FeedInfoBean> feedInfoBeanList) {

    }
}
