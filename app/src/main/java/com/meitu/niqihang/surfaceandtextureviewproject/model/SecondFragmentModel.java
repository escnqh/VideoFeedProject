package com.meitu.niqihang.surfaceandtextureviewproject.model;

import com.meitu.niqihang.surfaceandtextureviewproject.contract.SecondFragmentContract;
import com.meitu.niqihang.surfaceandtextureviewproject.entity.FeedInfoBean;
import com.meitu.niqihang.surfaceandtextureviewproject.entity.VideoBean;
import com.meitu.niqihang.surfaceandtextureviewproject.utils.Contracts;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nqh 2018/10/11.
 */
public class SecondFragmentModel implements SecondFragmentContract.Model {
    private SecondFragmentContract.InteractionListener mListener;
    private FeedInfoBean mFeedInfoBean;
    private final int mVideoCount = 5;

    public SecondFragmentModel(SecondFragmentContract.InteractionListener listener) {
        this.mListener = listener;
        mFeedInfoBean = new FeedInfoBean();
        mFeedInfoBean.setFeedType(Contracts.TEXTURE_VIEW_KEY);
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
