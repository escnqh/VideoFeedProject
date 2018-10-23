package com.meitu.niqihang.surfaceandtextureviewproject.ui.adapter;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.meitu.niqihang.surfaceandtextureviewproject.R;
import com.meitu.niqihang.surfaceandtextureviewproject.entity.MediaProgress;
import com.meitu.niqihang.surfaceandtextureviewproject.entity.MediaStateEvent;
import com.meitu.niqihang.surfaceandtextureviewproject.entity.VideoBean;
import com.meitu.niqihang.surfaceandtextureviewproject.utils.Contracts;
import com.meitu.niqihang.surfaceandtextureviewproject.utils.MediaPlayerManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * @author nqh 2018/10/23.
 */
public class NewTexturePageAdapter extends RecyclerView.Adapter<NewTexturePageAdapter.NewTextureViewHolder> {
    private static final String TAG = "NewTexturePageAdapter";
    private Context mContext;
    private List<VideoBean> mDatas;
    private boolean isPlaying = false;
    private int currentPosition = -1;
    private NewTextureViewHolder mHolder;

    public NewTexturePageAdapter(Context context, List<VideoBean> datas) {
        this.mContext = context;
        this.mDatas = datas;
        EventBus.getDefault().register(this);
    }

    @NonNull
    @Override
    public NewTextureViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.view_new_texture, viewGroup, false);
        NewTextureViewHolder newTextureViewHolder = null;
        try {
            newTextureViewHolder = new NewTextureViewHolder(itemView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newTextureViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final NewTextureViewHolder holder, int i) {
        final VideoBean bean = mDatas.get(i);
        this.mHolder = holder;
        holder.mTvTitle.setText(bean.getVideoName());
        holder.mRlControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.mRlControl.getVisibility() != View.INVISIBLE) {
                    holder.mRlControl.setVisibility(View.INVISIBLE);
                }
            }
        });
        holder.mIvCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.mRlControl.getVisibility() != View.INVISIBLE) {
                    holder.mRlControl.setVisibility(View.INVISIBLE);
                }
                if (holder.mRlControl.getVisibility() != View.VISIBLE) {
                    holder.mRlControl.setVisibility(View.VISIBLE);
                }
            }
        });
        holder.mTtvVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.mRlControl.getVisibility() != View.VISIBLE) {
                    holder.mRlControl.setVisibility(View.VISIBLE);
                }
            }
        });
        holder.mIvStart.setTag(i);
        holder.mIvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer pos = (Integer) v.getTag();
                currentPosition = pos;
                notifyDataSetChanged();
            }
        });
        if (currentPosition == i) {
            holder.mTtvVideo.setVisibility(View.VISIBLE);
            holder.mIvCover.setVisibility(View.INVISIBLE);
            holder.mIvStart.setImageResource(R.drawable.icon_stop);
            holder.mTtvVideo.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
                @Override
                public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                    MediaPlayerManager.getInstance().initMediaPlayer(surface);
                    MediaPlayerManager.getInstance().setUrlAndPlay(bean.getVideoPath());
                }

                @Override
                public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

                }

                @Override
                public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                    MediaPlayerManager.getInstance().releaseMediaPlayer();
                    return true;
                }

                @Override
                public void onSurfaceTextureUpdated(SurfaceTexture surface) {

                }
            });
            holder.mSbProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    MediaPlayerManager.getInstance().seekTo(seekBar.getProgress());
                }
            });
        } else {
//            MediaPlayerManager.getInstance().pause();
            holder.mSbProgress.setOnSeekBarChangeListener(null);
            holder.mIvCover.setVisibility(View.VISIBLE);
            holder.mTtvVideo.setVisibility(View.INVISIBLE);
            holder.mIvStart.setImageResource(R.drawable.icon_start);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMediaStateChange(MediaStateEvent mediaStateEvent) {
        switch (mediaStateEvent.getState()) {
            case Contracts.MEDIA_PLAYING:
                Log.i(TAG, "media playing");
                break;
            case Contracts.MEDIA_PAUSE:
                isPlaying = false;
                Log.i(TAG, "media pause");
                break;
            case Contracts.MEDIA_STOP:
                isPlaying = false;
                Log.i(TAG, "media stop");
                break;
            case Contracts.MEDIA_ERROR:
                isPlaying = false;
                Log.i(TAG, "media error");
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProgressChanged(MediaProgress mediaProgress) {
        mHolder.mSbProgress.setProgress(mediaProgress.getProgress());
        Log.i(TAG, "progress: " + mediaProgress.getProgress());
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        EventBus.getDefault().unregister(this);
    }

    class NewTextureViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvTitle;
        private TextView mTvDuration;
        private ImageView mIvCover;
        private SeekBar mSbProgress;
        private TextureView mTtvVideo;
        private ImageView mIvStart;
        private RelativeLayout mRlControl;

        public NewTextureViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvDuration = itemView.findViewById(R.id.tv_duration);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTtvVideo = itemView.findViewById(R.id.ttv_video);
            mSbProgress = itemView.findViewById(R.id.sb_progress);
            mIvCover = itemView.findViewById(R.id.iv_cover);
            mIvStart = itemView.findViewById(R.id.iv_start);
            mRlControl = itemView.findViewById(R.id.rl_control);
        }
    }
}
