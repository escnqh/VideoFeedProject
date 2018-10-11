package com.meitu.niqihang.surfaceandtextureviewproject.contract;

import com.meitu.niqihang.surfaceandtextureviewproject.entity.FeedInfoBean;

import java.util.List;

/**
 * @author nqh 2018/10/10.
 */
public class MainPageContract {
    public interface Model {
        /**
         * 获取feed流信息
         */
        void loadFeed();
    }

    public interface View {
        /**
         * feed界面刷新
         *
         * @param feedInfoBeanList
         */
        void showFeed(List<FeedInfoBean> feedInfoBeanList);
    }

    public interface Presenter {
        /**
         * 请求feed流
         */
        void requestFeed();

        /**
         * 刷新feed流
         *
         * @param feedInfoBeanList
         */
        void pushFeed(List<FeedInfoBean> feedInfoBeanList);
    }

    public interface InteractionListener {
        /**
         * 请求成功的回调
         */
        void onRequestSuccess();

        /**
         * 请求失败的回调
         */
        void onRequestFailed();
    }
}
