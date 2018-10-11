package com.meitu.niqihang.surfaceandtextureviewproject.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * 所有presenter的基类
 *
 * @author nqh 2018/10/10.
 */
public abstract class BasePresenter<V> {
    protected Reference<V> mVReference;


    /**
     * 与View建立关系
     *
     * @param view
     */
    protected void attachView(V view) {
        mVReference = new WeakReference<>(view);
    }

    /**
     * @return 获取View
     */
    protected V getView() {
        return mVReference.get();
    }

    /**
     * @return 是否与View建立了关系
     */
    public boolean isViewAttached() {
        return mVReference != null && mVReference.get() != null;
    }


    /**
     * 与View解除关系
     */
    public void detachView() {
        if (mVReference != null) {
            mVReference.clear();
            mVReference = null;
        }
    }
}
