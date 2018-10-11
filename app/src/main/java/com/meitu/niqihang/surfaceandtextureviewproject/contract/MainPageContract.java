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
        void getFeed();
    }

    public interface View {
        /**
         * feed界面刷新
         * @param feedInfoBeanList
         */
        void refreshFeed(List<FeedInfoBean> feedInfoBeanList);
    }

    public interface Presenter {
        /**
         * 请求feed流
         */
        void requestFeed();

        /**
         * 刷新feed流
         * @param feedInfoBeanList
         */
        void pushFeed(List<FeedInfoBean> feedInfoBeanList);
    }
}
