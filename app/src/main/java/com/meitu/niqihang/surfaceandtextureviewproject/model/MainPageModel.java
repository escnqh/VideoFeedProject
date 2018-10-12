package com.meitu.niqihang.surfaceandtextureviewproject.model;

import com.meitu.niqihang.surfaceandtextureviewproject.contract.MainPageContract;
import com.meitu.niqihang.surfaceandtextureviewproject.entity.FeedInfoBean;
import com.meitu.niqihang.surfaceandtextureviewproject.entity.VideoBean;
import com.meitu.niqihang.surfaceandtextureviewproject.presenter.MainPagePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nqh 2018/10/10.
 */
public class MainPageModel implements MainPageContract.Model {
    private MainPagePresenter mMainPagePresenter;
    private List<FeedInfoBean> mFeedInfoBeanList;

    public MainPageModel(MainPagePresenter mainPagePresenter) {
        this.mMainPagePresenter = mainPagePresenter;
        mFeedInfoBeanList = new ArrayList<>();
        FeedInfoBean feedInfoBean = new FeedInfoBean();
        feedInfoBean.setFeedType("SurfaceView");
        VideoBean videoBean = new VideoBean();
        List<VideoBean> videoBeans = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            videoBean.setVideoName("video  " + i);
            videoBean.setVideoPath("/storage/0004-AB81/DCIM/Camera/VID_20181012_102556.mp4");
            videoBeans.add(videoBean);
        }
        feedInfoBean.setVideoFeed(videoBeans);
        mFeedInfoBeanList.add(feedInfoBean);
    }

    @Override
    public void loadFeed() {
        mMainPagePresenter.pushFeed(mFeedInfoBeanList);
    }
}
