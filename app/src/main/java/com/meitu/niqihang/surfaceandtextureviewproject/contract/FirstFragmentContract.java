package com.meitu.niqihang.surfaceandtextureviewproject.contract;

import com.meitu.niqihang.surfaceandtextureviewproject.entity.FeedInfoBean;

/**
 * @author nqh 2018/10/11.
 */
public class FirstFragmentContract {
    public interface Model {
        /**
         * 加载feed页面的信息
         */
        void loadFeedInfo();
    }

    public interface View {
        /**
         * 展示feed页面的信息
         */
        void showFeedInfo(FeedInfoBean feedInfoBean);
    }

    public interface Presenter {
        /**
         * 申请加载feed页
         */
        void requestShowFeedInfo();
    }

    public interface InteractionListener {
        /**
         * 加载成功的回调
         */
        void onRequestSuccess(FeedInfoBean feedInfoBean);

        /**
         * 加载失败的回调
         */
        void onRequestFailed();
    }
}
