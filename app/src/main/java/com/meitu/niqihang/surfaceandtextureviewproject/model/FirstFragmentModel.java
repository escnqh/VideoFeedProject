package com.meitu.niqihang.surfaceandtextureviewproject.model;

import com.meitu.niqihang.surfaceandtextureviewproject.contract.FirstFragmentContract;
import com.meitu.niqihang.surfaceandtextureviewproject.entity.FeedInfoBean;

/**
 * @author nqh 2018/10/11.
 */
public class FirstFragmentModel implements FirstFragmentContract.Model {
    private FirstFragmentContract.InteractionListener mListener;
    private FeedInfoBean mFeedInfoBean;

    public FirstFragmentModel(FirstFragmentContract.InteractionListener listener) {
        this.mListener = listener;
        //todo 加载FeedInfoBean
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
