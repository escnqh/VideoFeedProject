package com.meitu.niqihang.surfaceandtextureviewproject.ui.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.meitu.niqihang.surfaceandtextureviewproject.R;
import com.meitu.niqihang.surfaceandtextureviewproject.entity.VideoBean;
import com.meitu.niqihang.surfaceandtextureviewproject.ui.view.TextureVideoView;

import java.util.List;

/**
 * @author nqh 2018/10/11.
 */
public class TexturePageAdapter extends RecyclerView.Adapter<TexturePageAdapter.TexturePageViewHolder> {
    private static final String TAG = "TexturePageAdapter";
    private Context mContext;
    private List<VideoBean> mDatas;
    private boolean isPlaying = false;
    private int currentPosition = -1;

    public TexturePageAdapter(Context context, List<VideoBean> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }


    @NonNull
    @Override
    public TexturePageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.view_texture, viewGroup, false);
        TexturePageViewHolder texturePageViewHolder = null;
        try {
            texturePageViewHolder = new TexturePageViewHolder(itemView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return texturePageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final TexturePageViewHolder holder, int i) {
        final VideoBean bean = mDatas.get(i);
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
        holder.mTvvVideo.setOnClickListener(new View.OnClickListener() {
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
        holder.mTvvVideo.setOnMediaInitFinishedListener(new TextureVideoView.OnMediaInitFinishedListener() {
            @Override
            public void onMediaInitFinished() {
                holder.mTvvVideo.setUrl(bean.getVideoPath());
            }
        });

        holder.mTvvVideo.setPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                holder.mTvDuration.setText("总时长： " + mp.getDuration());
                holder.mSbProgress.setMax(mp.getDuration());
                holder.mTvvVideo.startPlay();
                isPlaying = true;
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
                holder.mTvvVideo.seekTo(seekBar.getProgress());
            }
        });
        holder.mTvvVideo.setSeekBarProgressListener(new TextureVideoView.OnSeekBarProgressListener() {
            @Override
            public void onSeekBarProgress(int progress) {
                holder.mSbProgress.setProgress(progress);
            }
        });
        holder.mTvvVideo.setCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.seekTo(0);
                holder.mTvvVideo.pausePlay();
                holder.mSbProgress.setProgress(0);
                holder.mIvStart.setImageResource(R.drawable.icon_start);
                holder.mIvCover.setVisibility(View.VISIBLE);
                holder.mTvvVideo.setVisibility(View.INVISIBLE);
                isPlaying = false;
            }
        });
        if (currentPosition == i) {
            if (!isPlaying) {
                isPlaying = true;
                holder.mIvCover.setVisibility(View.INVISIBLE);
                holder.mTvvVideo.setVisibility(View.VISIBLE);
                holder.mIvStart.setImageResource(R.drawable.icon_stop);
            } else {
                holder.mTvvVideo.pausePlay();
                holder.mIvStart.setImageResource(R.drawable.icon_start);
                holder.mIvCover.setVisibility(View.VISIBLE);
                holder.mTvvVideo.setVisibility(View.INVISIBLE);
                isPlaying = false;
            }
        } else {
            holder.mTvvVideo.pausePlay();
            holder.mIvCover.setVisibility(View.VISIBLE);
            holder.mTvvVideo.setVisibility(View.INVISIBLE);
            holder.mIvStart.setImageResource(R.drawable.icon_start);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class TexturePageViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvTitle;
        private TextView mTvDuration;
        private ImageView mIvCover;
        private SeekBar mSbProgress;
        private TextureVideoView mTvvVideo;
        private ImageView mIvStart;
        private RelativeLayout mRlControl;

        public TexturePageViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvDuration = itemView.findViewById(R.id.tv_duration);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvvVideo = itemView.findViewById(R.id.tvv_video);
            mSbProgress = itemView.findViewById(R.id.sb_progress);
            mIvCover = itemView.findViewById(R.id.iv_cover);
            mIvStart = itemView.findViewById(R.id.iv_start);
            mRlControl = itemView.findViewById(R.id.rl_control);
        }
    }
}
