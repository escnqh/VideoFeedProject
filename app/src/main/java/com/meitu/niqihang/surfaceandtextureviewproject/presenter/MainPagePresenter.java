package com.meitu.niqihang.surfaceandtextureviewproject.presenter;

import com.meitu.niqihang.surfaceandtextureviewproject.base.BasePresenter;
import com.meitu.niqihang.surfaceandtextureviewproject.contract.MainPageContract;
import com.meitu.niqihang.surfaceandtextureviewproject.entity.FeedInfoBean;
import com.meitu.niqihang.surfaceandtextureviewproject.model.MainPageModel;

import java.util.List;

/**
 * @author nqh 2018/10/10.
 */
public class MainPagePresenter extends BasePresenter<MainPageContract.View> implements MainPageContract.Presenter {
    private MainPageContract.View mView;
    private MainPageContract.Model mModel;

    public MainPagePresenter(MainPageContract.View view) {
        this.mView = view;
        this.mModel = new MainPageModel(this);
    }

    @Override
    public void requestFeed() {
        mModel.getFeed();
    }

    @Override
    public void pushFeed(List<FeedInfoBean> feedInfoBeanList) {
        mView.refreshFeed(feedInfoBeanList);
    }

}
