package com.meitu.niqihang.surfaceandtextureviewproject.model;

import com.meitu.niqihang.surfaceandtextureviewproject.contract.FirstFragmentContract;

/**
 * @author nqh 2018/10/11.
 */
public class FirstFragmentModel implements FirstFragmentContract.Model {
    private FirstFragmentContract.Presenter mPresenter;

    public FirstFragmentModel(FirstFragmentContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}
