package com.meitu.niqihang.surfaceandtextureviewproject.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * 所有fragment的基类
 *
 * @author nqh 2018/10/10.
 */
public abstract class BaseFragment<V, P extends BasePresenter<V>> extends Fragment {
    protected P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    /**
     * 子类实现此构建方法
     *
     * @return 返回此view的Presenter
     */
    protected abstract P createPresenter();
}
