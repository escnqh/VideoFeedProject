package com.meitu.niqihang.surfaceandtextureviewproject.contract;

import com.meitu.niqihang.surfaceandtextureviewproject.entity.FeedInfoBean;

/**
 * @author nqh 2018/10/11.
 */
public class SecondFragmentContract {
    public interface Model {
        /**
         * 加载feed页面信息
         */
        void loadFeedInfo();
    }

    public interface View {
        /**
         * 展示
         *
         * @param feedInfoBean feed页面信息
         */
        void showFeedInfo(FeedInfoBean feedInfoBean);
    }

    public interface Presenter {
        /**
         * 申请加载
         */
        void requestShowFeedInfo();

        /**
         * 进入时的初始化
         */
        void start();
    }

    public interface InteractionListener {
        /**
         * 成功加载
         *
         * @param feedInfoBean
         */
        void onRequestSuccess(FeedInfoBean feedInfoBean);

        /**
         * 加载失败
         */
        void onRequestFailed();
    }
}
