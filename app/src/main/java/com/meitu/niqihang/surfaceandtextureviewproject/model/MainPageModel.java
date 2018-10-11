package com.meitu.niqihang.surfaceandtextureviewproject.model;

import com.meitu.niqihang.surfaceandtextureviewproject.contract.MainPageContract;
import com.meitu.niqihang.surfaceandtextureviewproject.entity.FeedInfoBean;
import com.meitu.niqihang.surfaceandtextureviewproject.presenter.MainPagePresenter;

import java.util.List;

/**
 * @author nqh 2018/10/10.
 */
public class MainPageModel implements MainPageContract.Model {
    private MainPagePresenter mMainPagePresenter;
    private List<FeedInfoBean> mFeedInfoBeanList;

    public MainPageModel(MainPagePresenter mainPagePresenter) {
        this.mMainPagePresenter = mainPagePresenter;
    }

    @Override
    public void getFeed() {
        mMainPagePresenter.pushFeed(mFeedInfoBeanList);
    }
}
