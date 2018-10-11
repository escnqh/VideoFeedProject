package com.meitu.niqihang.surfaceandtextureviewproject.entity;

import java.util.List;

/**
 * 包含了feed页需要的视屏feed信息
 *
 * @author nqh 2018/10/10.
 */
public class FeedInfoBean {

    /**
     * 该Feed内的Video信息
     */
    private List<VideoBean> mVideoFeed;
    /**
     * 该Feed流呈现Type
     */
    private String mFeedType;

    public List<VideoBean> getVideoFeed() {
        return mVideoFeed;
    }

    public void setVideoFeed(List<VideoBean> videoFeed) {
        mVideoFeed = videoFeed;
    }

    public String getFeedType() {
        return mFeedType;
    }

    public void setFeedType(String feedType) {
        mFeedType = feedType;
    }
}
