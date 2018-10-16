package com.meitu.niqihang.surfaceandtextureviewproject.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.meitu.niqihang.surfaceandtextureviewproject.MyApplication;
import com.meitu.niqihang.surfaceandtextureviewproject.R;
import com.meitu.niqihang.surfaceandtextureviewproject.entity.VideoBean;

import java.io.IOException;
import java.util.List;

/**
 * SurfaceView+RecyclerView页面适配器
 * todo 监听用户锁屏停止视频播放
 *
 * @author nqh 2018/10/10.
 */
public class SurfacePageAdapter extends RecyclerView.Adapter<SurfacePageAdapter.MainPageViewHolder> {
    private static final String TAG = "SurfacePageAdapter";
    private Context mContext;
    private List<VideoBean> mDatas;
    private MediaPlayer mMediaPlayer;
    private int currentPosition = -1;
    private int mDuration;
    private Handler mHandler = new Handler();

    public SurfacePageAdapter(Context context, List<VideoBean> datas) {
        this.mContext = context;
        this.mDatas = datas;
        this.mMediaPlayer = MyApplication.getInstance().getMediaPlayer();
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mMediaPlayer.stop();
                mHandler.removeCallbacksAndMessages(null);
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
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    @NonNull
    @Override
    public MainPageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.view_surface, viewGroup, false);
        MainPageViewHolder mainPageViewHolder = null;
        try {
            mainPageViewHolder = new MainPageViewHolder(itemView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mainPageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MainPageViewHolder mainPageViewHolder, int position) {
        final VideoBean bean = mDatas.get(position);
        //显示播放视频名称
        mainPageViewHolder.mTitleTv.setText(bean.getVideoName());
        //
        Drawable bitmap = mContext.getResources().getDrawable(R.drawable.icon_play);
        //

        mainPageViewHolder.mSeekBar.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        mainPageViewHolder.mThumbIv.setImageDrawable(bitmap);
        mainPageViewHolder.mThumbIv.setTag(position);
        mainPageViewHolder.mThumbIv.setOnClickListener(new View.OnClickListener() {
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
                mHandler.removeCallbacksAndMessages(null);
            }
            mainPageViewHolder.mSurfaceView.setVisibility(View.VISIBLE);
            mainPageViewHolder.mThumbIv.setVisibility(View.INVISIBLE);
            SurfaceHolder surfaceHolder = mainPageViewHolder.mSurfaceView.getHolder();
            surfaceHolder.addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    Log.i(TAG, "SurfaceView is created!");
                    mMediaPlayer.reset();
                    mMediaPlayer.setDisplay(holder);
                    try {
                        mMediaPlayer.setDataSource(bean.getVideoPath());
                        //异步加载资源
                        mMediaPlayer.prepareAsync();
                        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mDuration = mMediaPlayer.getDuration();
                                mainPageViewHolder.mSeekBar.setMax(mDuration);
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mainPageViewHolder.mSeekBar.setProgress(mMediaPlayer.getCurrentPosition());
                                        mHandler.postDelayed(this, 1000);
                                    }
                                });
                                mMediaPlayer.start();
                            }
                        });
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
                    if (mMediaPlayer.isPlaying()) {
                        mMediaPlayer.pause();
                        mHandler.removeCallbacksAndMessages(null);
                    }
                }
            });
        } else {
            mainPageViewHolder.mSurfaceView.setVisibility(View.INVISIBLE);
            mainPageViewHolder.mThumbIv.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MainPageViewHolder extends RecyclerView.ViewHolder {
        private SurfaceView mSurfaceView;
        private SeekBar mSeekBar;
        private ImageView mThumbIv;
        private TextView mTitleTv;


        public MainPageViewHolder(@NonNull View itemView) {
            super(itemView);
            mSurfaceView = itemView.findViewById(R.id.surface_view);
            mThumbIv = itemView.findViewById(R.id.item_iv);
            mTitleTv = itemView.findViewById(R.id.item_title);
            mSeekBar = itemView.findViewById(R.id.sb_video);
        }
    }

    private SeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if (mMediaPlayer != null) {
                mMediaPlayer.seekTo(seekBar.getProgress());
            }
        }
    };
}
