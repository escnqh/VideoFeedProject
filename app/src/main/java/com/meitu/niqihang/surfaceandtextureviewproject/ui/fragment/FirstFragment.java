package com.meitu.niqihang.surfaceandtextureviewproject.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meitu.niqihang.surfaceandtextureviewproject.R;
import com.meitu.niqihang.surfaceandtextureviewproject.base.BaseFragment;
import com.meitu.niqihang.surfaceandtextureviewproject.contract.FirstFragmentContract;
import com.meitu.niqihang.surfaceandtextureviewproject.entity.FeedInfoBean;
import com.meitu.niqihang.surfaceandtextureviewproject.entity.VideoBean;
import com.meitu.niqihang.surfaceandtextureviewproject.presenter.FirstFragmentPresenter;
import com.meitu.niqihang.surfaceandtextureviewproject.ui.adapter.SurfacePageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nqh 2018/10/10.
 */
public class FirstFragment extends BaseFragment<FirstFragmentContract.View, FirstFragmentPresenter> implements FirstFragmentContract.View, FirstFragmentContract.Presenter {
    private FeedInfoBean mFeedInfoBean;
    private RecyclerView mRecyclerView;
    private SurfacePageAdapter mSurfacePageAdapter;
    private List<VideoBean> mVideoBeans = new ArrayList<>();

    public FirstFragment() {

    }

    public static FirstFragment newInstance(FeedInfoBean feedInfoBean) {
        FirstFragment firstFragment = new FirstFragment();
        firstFragment.setFeedInfoBean(feedInfoBean);
        return firstFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mSurfacePageAdapter) {
            //check并关闭播放器资源
            mSurfacePageAdapter.destroyPlayer();
        }
    }

    private void initView(View view) {
        mRecyclerView = view.findViewById(R.id.rv_first);
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mSurfacePageAdapter = new SurfacePageAdapter(this.getContext(), mVideoBeans);
    }

    @Override
    protected FirstFragmentPresenter createPresenter() {
        return new FirstFragmentPresenter(this);
    }

    public void setFeedInfoBean(FeedInfoBean feedInfoBean) {
        this.mFeedInfoBean = feedInfoBean;
    }


    @Override
    public void showFeedInfo(FeedInfoBean feedInfoBean) {
        if (null != feedInfoBean) {
            mVideoBeans.addAll(feedInfoBean.getVideoFeed());
            mSurfacePageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void requestShowFeedInfo() {
        mPresenter.requestShowFeedInfo();
    }
}
