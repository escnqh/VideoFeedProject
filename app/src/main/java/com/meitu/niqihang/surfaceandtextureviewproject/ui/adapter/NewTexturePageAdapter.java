package com.meitu.niqihang.surfaceandtextureviewproject.ui.adapter;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
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
    private RecyclerView mRecyclerView;
    private boolean isPlaying = false;
    private int currentPosition = -1;
    private SparseArray<SurfaceTexture> mSurfaceTextureSparseArray = new SparseArray<>();


    public NewTexturePageAdapter(Context context, List<VideoBean> datas, RecyclerView recyclerView) {
        this.mContext = context;
        this.mDatas = datas;
        this.mRecyclerView = recyclerView;
        EventBus.getDefault().register(this);
    }

    @NonNull
    @Override
    public NewTextureViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
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
    public void onBindViewHolder(@NonNull final NewTextureViewHolder holder, final int i) {
        final VideoBean bean = mDatas.get(i);
        holder.mTvTitle.setText(bean.getVideoName());
        holder.mIvStart.setTag(i);
        holder.mIvCover.setVisibility(View.VISIBLE);
        holder.mIvStart.setImageResource(R.drawable.icon_start);
        holder.mSbProgress.setProgress(0);
        holder.mIvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer pos = (Integer) v.getTag();
                if (currentPosition != pos) {
                    MediaPlayerManager.getInstance().stop();
                    //这里处理的是首次将焦点移到这个item
                    Log.i(TAG, "start first play");
                    MediaPlayerManager.getInstance().setSurface(mSurfaceTextureSparseArray.get(pos));
                    MediaPlayerManager.getInstance().setUrlAndPlay(mDatas.get(pos).getVideoPath());
                    holder.mTtvVideo.setVisibility(View.VISIBLE);
                    holder.mIvCover.setVisibility(View.INVISIBLE);
                    holder.mIvStart.setImageResource(R.drawable.icon_stop);
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
                    currentPosition = pos;
                } else {
                    //这里处理的是已经处于激活状态的
                    if (MediaPlayerManager.getInstance().getPlayingState()) {
                        MediaPlayerManager.getInstance().pause();
                        holder.mIvCover.setVisibility(View.VISIBLE);
                        holder.mTtvVideo.setVisibility(View.INVISIBLE);
                        holder.mIvStart.setImageResource(R.drawable.icon_start);
                    } else {
                        MediaPlayerManager.getInstance().resume();
                        holder.mTtvVideo.setVisibility(View.VISIBLE);
                        holder.mIvCover.setVisibility(View.INVISIBLE);
                        holder.mIvStart.setImageResource(R.drawable.icon_stop);
                    }
                }
            }
        });
        holder.mTtvVideo.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                mSurfaceTextureSparseArray.put(i, surface);
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                mSurfaceTextureSparseArray.remove(i);
                return true;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMediaStateChange(MediaStateEvent mediaStateEvent) {
        switch (mediaStateEvent.getState()) {
            case Contracts.MEDIA_PLAYING:
                Log.i(TAG, "media playing");
                if (getTargetHolder(currentPosition) != null) {
                    getTargetHolder(currentPosition).mSbProgress.setMax(MediaPlayerManager.getInstance().getMediaPlayer().getDuration());
                }
                break;
            case Contracts.MEDIA_PAUSE:
                Log.i(TAG, "media pause");
                break;
            case Contracts.MEDIA_STOP:
                if (currentPosition != -1) {
                    Log.i(TAG, "fresh view item : " + currentPosition);
                    RecyclerView.ViewHolder viewHolder = mRecyclerView.findViewHolderForAdapterPosition(currentPosition);
                    if (viewHolder != null && viewHolder instanceof NewTextureViewHolder) {
                        NewTextureViewHolder holder = (NewTextureViewHolder) viewHolder;
                        holder.mIvCover.setVisibility(View.VISIBLE);
                        holder.mTtvVideo.setVisibility(View.INVISIBLE);
                        holder.mIvStart.setImageResource(R.drawable.icon_start);
                        holder.mSbProgress.setOnSeekBarChangeListener(null);
                    }
                }
                Log.i(TAG, "media stop");
                break;
            case Contracts.MEDIA_ERROR:
                Log.i(TAG, "media error");
                if (currentPosition != -1) {
                    notifyItemChanged(currentPosition);
                }
                break;
            default:
                break;
        }
    }

    private NewTextureViewHolder getTargetHolder(int position) {
        RecyclerView.ViewHolder viewHolder = mRecyclerView.findViewHolderForAdapterPosition(position);
        if (viewHolder != null && viewHolder instanceof NewTextureViewHolder) {
            return (NewTextureViewHolder) viewHolder;
        }
        return null;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProgressChanged(MediaProgress mediaProgress) {
        RecyclerView.ViewHolder viewHolder = mRecyclerView.findViewHolderForAdapterPosition(currentPosition);
        if (viewHolder != null && viewHolder instanceof NewTextureViewHolder) {
            NewTextureViewHolder holder = (NewTextureViewHolder) viewHolder;
            holder.mSbProgress.setProgress(mediaProgress.getProgress());
        }
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
        private SurfaceTexture mSurfaceTexture;

        public NewTextureViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvDuration = itemView.findViewById(R.id.tv_duration);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTtvVideo = itemView.findViewById(R.id.ttv_video);
            mSbProgress = itemView.findViewById(R.id.sb_progress);
            mIvCover = itemView.findViewById(R.id.iv_cover);
            mIvStart = itemView.findViewById(R.id.iv_start);
            mRlControl = itemView.findViewById(R.id.rl_control);
            mRlControl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRlControl.getVisibility() != View.INVISIBLE) {
                        mRlControl.setVisibility(View.INVISIBLE);
                    }
                }
            });
            mIvCover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRlControl.getVisibility() != View.INVISIBLE) {
                        mRlControl.setVisibility(View.INVISIBLE);
                    }
                    if (mRlControl.getVisibility() != View.VISIBLE) {
                        mRlControl.setVisibility(View.VISIBLE);
                    }
                }
            });
            mTtvVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRlControl.getVisibility() != View.VISIBLE) {
                        mRlControl.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        public void setSurfaceTexture(SurfaceTexture surfaceTexture) {
            this.mSurfaceTexture = surfaceTexture;
        }

        public SurfaceTexture getSurfaceTexture() {
            return mSurfaceTexture;
        }
    }
}
