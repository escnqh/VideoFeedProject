package com.meitu.niqihang.surfaceandtextureviewproject.presenter;

import com.meitu.niqihang.surfaceandtextureviewproject.base.BasePresenter;
import com.meitu.niqihang.surfaceandtextureviewproject.contract.FirstFragmentContract;
import com.meitu.niqihang.surfaceandtextureviewproject.model.FirstFragmentModel;

/**
 * @author nqh 2018/10/11.
 */
public class FirstFragmentPresenter extends BasePresenter<FirstFragmentContract.View> implements FirstFragmentContract.Presenter {
    private FirstFragmentContract.View mView;
    private FirstFragmentContract.Model mModel;

    public FirstFragmentPresenter(FirstFragmentContract.View view) {
        this.mView = view;
        mModel = new FirstFragmentModel(this);
    }
}
