package com.meitu.niqihang.surfaceandtextureviewproject.presenter;

import com.meitu.niqihang.surfaceandtextureviewproject.base.BasePresenter;
import com.meitu.niqihang.surfaceandtextureviewproject.contract.SecondFragmentContract;
import com.meitu.niqihang.surfaceandtextureviewproject.entity.FeedInfoBean;
import com.meitu.niqihang.surfaceandtextureviewproject.model.SecondFragmentModel;
import com.meitu.niqihang.surfaceandtextureviewproject.ui.fragment.SecondFragment;

/**
 * @author nqh 2018/10/11.
 */
public class SecondFragmentPresenter extends BasePresenter<SecondFragmentContract.View> implements SecondFragmentContract.Presenter, SecondFragmentContract.InteractionListener {
    private SecondFragmentContract.View mView;
    private SecondFragmentContract.Model mModel;


    public SecondFragmentPresenter(SecondFragmentContract.View view) {
        this.mModel = new SecondFragmentModel(this);
        this.mView = view;
    }

    @Override
    public void requestShowFeedInfo() {
        mModel.loadFeedInfo();
    }

    @Override
    public void start() {
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
