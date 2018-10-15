package com.meitu.niqihang.surfaceandtextureviewproject.model;

import com.meitu.niqihang.surfaceandtextureviewproject.contract.FirstFragmentContract;
import com.meitu.niqihang.surfaceandtextureviewproject.entity.FeedInfoBean;
import com.meitu.niqihang.surfaceandtextureviewproject.entity.VideoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nqh 2018/10/11.
 */
public class FirstFragmentModel implements FirstFragmentContract.Model {
    private FirstFragmentContract.InteractionListener mListener;
    private FeedInfoBean mFeedInfoBean;
    private final int mVideoCount = 5;

    public FirstFragmentModel(FirstFragmentContract.InteractionListener listener) {
        this.mListener = listener;
        //todo 加载FeedInfoBean
        mFeedInfoBean = new FeedInfoBean();
        mFeedInfoBean.setFeedType("SurfaceView");
        VideoBean videoBean = new VideoBean();
        List<VideoBean> videoBeans = new ArrayList<>();
        for (int i = 0; i < mVideoCount; i++) {
            videoBean.setVideoName("下拉刷新的video  " + i);
            videoBean.setVideoPath("http://mvvideo10.meitudata.com/5bbde5f16daa56665.mp4");
            videoBeans.add(videoBean);
        }
        mFeedInfoBean.setVideoFeed(videoBeans);
    }

    @Override
    public void loadFeedInfo() {
        if (mFeedInfoBean != null) {
            mListener.onRequestSuccess(mFeedInfoBean);
        } else {
            mListener.onRequestFailed();
        }

    }
}
