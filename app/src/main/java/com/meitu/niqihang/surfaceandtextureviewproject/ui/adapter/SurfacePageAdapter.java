package com.meitu.niqihang.surfaceandtextureviewproject.ui.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meitu.niqihang.surfaceandtextureviewproject.R;
import com.meitu.niqihang.surfaceandtextureviewproject.entity.VideoBean;

import java.io.IOException;
import java.util.List;

/**
 * SurfaceView+RecyclerView页面适配器
 *
 * @author nqh 2018/10/10.
 */
public class SurfacePageAdapter extends RecyclerView.Adapter<SurfacePageAdapter.MainpageViewholder> {
    private static final String TAG = "SurfacePageAdapter";
    private Context mContext;
    private List<VideoBean> mDatas;
    private MediaPlayer mMediaPlayer;
    private int currentPosition = -1;

    public SurfacePageAdapter(Context context, List<VideoBean> datas) {
        this.mContext = context;
        this.mDatas = datas;
        this.mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mMediaPlayer.stop();
            }
        });
    }

    /**
     * 当宿主被销毁时关闭MediaPlayer资源
     */
    public void destroyPlayer() {
        if (null != mMediaPlayer) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @NonNull
    @Override
    public MainpageViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.view_surface, viewGroup, false);
        return new MainpageViewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MainpageViewholder mainpageViewholder, int position) {
        final VideoBean bean = mDatas.get(position);
        //显示播放视频名称
        mainpageViewholder.mTitleTv.setText(bean.getVideoName());
        //
        mainpageViewholder.mThumbIv.setTag(position);
        mainpageViewholder.mThumbIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer pos = (Integer) v.getTag();
                currentPosition = pos;
                notifyDataSetChanged();
            }
        });
        if (currentPosition == position) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mainpageViewholder.mSurfaceView.setVisibility(View.VISIBLE);
            mainpageViewholder.mThumbIv.setVisibility(View.INVISIBLE);
            SurfaceHolder surfaceHolder = mainpageViewholder.mSurfaceView.getHolder();
            surfaceHolder.addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    mMediaPlayer.reset();
                    mMediaPlayer.setDisplay(holder);
                    try {
                        mMediaPlayer.setDataSource(bean.getVideoPath());
                        mMediaPlayer.prepare();
                        mMediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                    Log.i(TAG, "SurfaceView is changed!");
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    //处理MediaPlayer的生命周期
                    Log.i(TAG, "SurfaceView is Destroyed!");
                    if (mMediaPlayer.isPlaying()){
                        mMediaPlayer.stop();
                    }
                }
            });
        } else {
            mainpageViewholder.mSurfaceView.setVisibility(View.INVISIBLE);
            mainpageViewholder.mThumbIv.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MainpageViewholder extends RecyclerView.ViewHolder {
        private SurfaceView mSurfaceView;
        private ImageView mThumbIv;
        private TextView mTitleTv;


        public MainpageViewholder(@NonNull View itemView) {
            super(itemView);
            mSurfaceView = itemView.findViewById(R.id.surface_view);
            mThumbIv = itemView.findViewById(R.id.item_iv);
            mTitleTv = itemView.findViewById(R.id.item_title);
        }
    }

}
