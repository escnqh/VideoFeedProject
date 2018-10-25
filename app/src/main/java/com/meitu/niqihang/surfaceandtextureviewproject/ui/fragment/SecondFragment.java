package com.meitu.niqihang.surfaceandtextureviewproject.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meitu.niqihang.surfaceandtextureviewproject.R;
import com.meitu.niqihang.surfaceandtextureviewproject.base.BaseFragment;
import com.meitu.niqihang.surfaceandtextureviewproject.contract.SecondFragmentContract;
import com.meitu.niqihang.surfaceandtextureviewproject.entity.FeedInfoBean;
import com.meitu.niqihang.surfaceandtextureviewproject.entity.VideoBean;
import com.meitu.niqihang.surfaceandtextureviewproject.presenter.SecondFragmentPresenter;
import com.meitu.niqihang.surfaceandtextureviewproject.ui.adapter.NewTexturePageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nqh 2018/10/10.
 */
public class SecondFragment extends BaseFragment<SecondFragmentContract.View, SecondFragmentPresenter> implements SecondFragmentContract.View, SecondFragmentContract.Presenter {
    private FeedInfoBean mFeedInfoBean;
    private RecyclerView mRecyclerView;
    private NewTexturePageAdapter mTexturePageAdapter;
    private List<VideoBean> mVideoBeans = new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public SecondFragment() {

    }

    public static SecondFragment newInstance(FeedInfoBean feedInfoBean) {
        SecondFragment secondFragment = new SecondFragment();
        secondFragment.setFeedInfoBean(feedInfoBean);
        return secondFragment;
    }

    public void setFeedInfoBean(FeedInfoBean feedInfoBean) {
        this.mFeedInfoBean = feedInfoBean;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.sr_second);
        mRecyclerView = view.findViewById(R.id.rv_second);
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        if (null != mFeedInfoBean) {
            mVideoBeans = mFeedInfoBean.getVideoFeed();
        }
        mTexturePageAdapter = new NewTexturePageAdapter(this.getContext(), mVideoBeans, mRecyclerView);
        mTexturePageAdapter.setHasStableIds(true);
        mRecyclerView.setAdapter(mTexturePageAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestShowFeedInfo();
            }
        });
    }

    @Override
    protected SecondFragmentPresenter createPresenter() {
        return new SecondFragmentPresenter(this);
    }

    @Override
    public void showFeedInfo(FeedInfoBean feedInfoBean) {
        if (null != feedInfoBean) {
            mVideoBeans.addAll(feedInfoBean.getVideoFeed());
            mTexturePageAdapter.notifyDataSetChanged();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void requestShowFeedInfo() {
        mPresenter.requestShowFeedInfo();
    }

    @Override
    public void start() {

    }
}
