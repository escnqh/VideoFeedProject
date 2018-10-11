package com.meitu.niqihang.surfaceandtextureviewproject.presenter;

import com.meitu.niqihang.surfaceandtextureviewproject.base.BasePresenter;
import com.meitu.niqihang.surfaceandtextureviewproject.contract.FirstFragmentContract;
import com.meitu.niqihang.surfaceandtextureviewproject.entity.FeedInfoBean;
import com.meitu.niqihang.surfaceandtextureviewproject.model.FirstFragmentModel;

/**
 * @author nqh 2018/10/11.
 */
public class FirstFragmentPresenter extends BasePresenter<FirstFragmentContract.View> implements FirstFragmentContract.Presenter, FirstFragmentContract.InteractionListener {
    private FirstFragmentContract.View mView;
    private FirstFragmentContract.Model mModel;

    public FirstFragmentPresenter(FirstFragmentContract.View view) {
        this.mView = view;
        this.mModel = new FirstFragmentModel(this);
    }

    @Override
    public void requestShowFeedInfo() {
        mModel.loadFeedInfo();
    }

    @Override
    public void onRequestSuccess(FeedInfoBean feedInfoBean) {
        mView.showFeedInfo(feedInfoBean);
    }

    @Override
    public void onRequestFailed() {
        //加载失败
    }
}
