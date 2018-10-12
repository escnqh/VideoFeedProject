package com.meitu.niqihang.surfaceandtextureviewproject.model;

import com.meitu.niqihang.surfaceandtextureviewproject.contract.MainPageContract;
import com.meitu.niqihang.surfaceandtextureviewproject.entity.FeedInfoBean;
import com.meitu.niqihang.surfaceandtextureviewproject.entity.VideoBean;
import com.meitu.niqihang.surfaceandtextureviewproject.presenter.MainPagePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 线上视频地址： https://www.bilibili.com/video/av33556107/
 * 本地视频地址： /storage/0004-AB81/DCIM/Camera/VID_20181012_102556.mp4
 *
 * @author nqh 2018/10/10.
 */
public class MainPageModel implements MainPageContract.Model {
    private MainPagePresenter mMainPagePresenter;
    private List<FeedInfoBean> mFeedInfoBeanList;
    private final int mVideoCount = 5;

    public MainPageModel(MainPagePresenter mainPagePresenter) {
        this.mMainPagePresenter = mainPagePresenter;
        mFeedInfoBeanList = new ArrayList<>();
        FeedInfoBean feedInfoBean = new FeedInfoBean();
        feedInfoBean.setFeedType("SurfaceView");
        List<VideoBean> videoBeans = new ArrayList<>();
        for (int i = 0; i < mVideoCount; i++) {
            VideoBean videoBean = new VideoBean();
            videoBean.setVideoName("video  " + i);
            videoBean.setVideoPath("/storage/0004-AB81/DCIM/Camera/VID_20181012_102556.mp4");
            videoBeans.add(videoBean);
        }
        feedInfoBean.setVideoFeed(videoBeans);
        mFeedInfoBeanList.add(feedInfoBean);
        FeedInfoBean feedInfoBean2 = new FeedInfoBean();
        feedInfoBean2.setFeedType("TextureView");
        mFeedInfoBeanList.add(feedInfoBean2);
    }

    @Override
    public void loadFeed() {
        mMainPagePresenter.pushFeed(mFeedInfoBeanList);
    }
}
