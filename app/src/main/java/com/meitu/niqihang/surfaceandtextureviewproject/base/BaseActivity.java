package com.meitu.niqihang.surfaceandtextureviewproject.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * 所有activity的基类
 *
 * @author nqh 2018/10/10.
 */
public abstract class BaseActivity<V, P extends BasePresenter<V>> extends AppCompatActivity {

    protected P mPresenter;
    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    /**
     * 子类实现构建此Activity的Presenter
     *
     * @return 返回此View的Presenter
     */
    protected abstract P createPresenter();
}
