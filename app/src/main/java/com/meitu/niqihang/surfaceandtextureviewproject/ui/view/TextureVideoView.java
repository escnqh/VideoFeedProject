package com.meitu.niqihang.surfaceandtextureviewproject.ui.view;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;

import com.meitu.niqihang.surfaceandtextureviewproject.utils.MediaPlayerManager;

import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * @author nqh 2018/10/10.
 */
public class TextureVideoView extends TextureView implements TextureView.SurfaceTextureListener {
    private static final String TAG = "TextureVideoView";
    private MediaPlayer mMediaPlayer;
    private Surface mSurface;
    private String mPlayingUrl = "";
    private MediaPlayer.OnPreparedListener mPreparedListener;
    private MediaPlayer.OnErrorListener mErrorListener;
    private MediaPlayer.OnCompletionListener mCompletionListener;
    private MediaPlayer.OnSeekCompleteListener mSeekCompleteListener;
    private MediaPlayer.OnInfoListener mInfoListener;
    private OnSeekBarProgressListener mSeekBarProgressListener;
    private OnMediaInitFinishedListener mMediaInitFinishedListener;
    private Handler mHandler = new Handler();
    private int mProgress;

    public TextureVideoView(Context context) {
        super(context);
        initView();
    }

    public TextureVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public TextureVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setSurfaceTextureListener(this);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        if (initMediaPlayer(surfaceTexture) == 1) {
            if (mMediaInitFinishedListener != null) {
                mMediaInitFinishedListener.onMediaInitFinished();
            }
            if (mPreparedListener != null) {
                mMediaPlayer.setOnPreparedListener(mPreparedListener);
            }
            if (mErrorListener != null) {
                mMediaPlayer.setOnErrorListener(mErrorListener);
            }
            if (mInfoListener != null) {
                mMediaPlayer.setOnInfoListener(mInfoListener);
            }
            if (mCompletionListener != null) {
                mMediaPlayer.setOnCompletionListener(mCompletionListener);
            }
        }
    }

    /**
     * 初始化MediaPlayer
     *
     * @param surfaceTexture
     * @return -1 初始化失败，1 初始化成功
     */
    private int initMediaPlayer(SurfaceTexture surfaceTexture) {
        if (surfaceTexture == null) {
            return -1;
        }
        try {
            if (mMediaPlayer == null) {
                mMediaPlayer = MediaPlayerManager.getInstance().getMediaPlayer();
            }
            mSurface = new Surface(surfaceTexture);
            mMediaPlayer.setSurface(mSurface);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        destory();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    /**
     * @param path 设置播放路径
     */
    public void setUrl(String path) {
        if (!mPlayingUrl.equals(path)) {
            mMediaPlayer.reset();
            try {
                mPlayingUrl = path;
                mMediaPlayer.setDataSource(path);
                mMediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            startPlay();
        }
    }

    /**
     * 开始播放
     */
    public void startPlay() {
        if (null != mMediaPlayer) {
            mMediaPlayer.start();
            mHandler.post(mSeekProgress);
            Log.e(TAG, "start play");
        } else {
            Log.e(TAG, "start error media is null");
        }
    }

    /**
     * 暂停播放
     */
    public void pausePlay() {
        if (null != mMediaPlayer) {
            mMediaPlayer.pause();
            mHandler.removeCallbacksAndMessages(null);
            Log.e(TAG, "pause play");
        } else {
            Log.e(TAG, "pause error media is null");
        }
    }

    /**
     * 重置播放
     */
    public void resetPlay() {
        if (null != mMediaPlayer) {
            mMediaPlayer.reset();
            Log.e(TAG, "reset play");
        } else {
            Log.e(TAG, "reset error media is null");
        }
    }

    /**
     * 销毁播放器
     */
    public void destory() {
        if (null != mMediaPlayer) {
            mMediaPlayer.pause();
        }
        mHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 从某处开始播放
     *
     * @param position 播放位置
     */
    public void seekTo(int position) {
        if (null != mMediaPlayer) {
            mMediaPlayer.seekTo(position);
            mMediaPlayer.setOnSeekCompleteListener(mSeekCompleteListener);
        } else {
            Log.e(TAG, "reset error media is null");
        }
    }

    public void setPreparedListener(MediaPlayer.OnPreparedListener preparedListener) {
        this.mPreparedListener = preparedListener;
    }

    public void setErrorListener(MediaPlayer.OnErrorListener errorListener) {
        this.mErrorListener = errorListener;
    }

    public void setCompletionListener(MediaPlayer.OnCompletionListener completionListener) {
        this.mCompletionListener = completionListener;
    }

    public void setInfoListener(MediaPlayer.OnInfoListener infoListener) {
        this.mInfoListener = infoListener;
    }

    public void setSeekCompleteListener(MediaPlayer.OnSeekCompleteListener seekCompleteListener) {
        this.mSeekCompleteListener = seekCompleteListener;
    }

    public interface OnSeekBarProgressListener {
        /**
         * 回调媒体播放进度
         *
         * @param progress 媒体播放进度
         */
        void onSeekBarProgress(int progress);
    }

    public interface OnMediaInitFinishedListener {
        /**
         * 当MediaPlayer初始化成功时回调
         */
        void onMediaInitFinished();
    }

    public void setSeekBarProgressListener(OnSeekBarProgressListener seekBarProgressListener) {
        this.mSeekBarProgressListener = seekBarProgressListener;
    }

    public void setOnMediaInitFinishedListener(OnMediaInitFinishedListener mediaInitFinishedListener) {
        this.mMediaInitFinishedListener = mediaInitFinishedListener;
    }

    private Runnable mSeekProgress = new Runnable() {
        @Override
        public void run() {
            if (null != mMediaPlayer) {
                if (mSeekBarProgressListener != null) {
                    mProgress = mMediaPlayer.getCurrentPosition();
                    mSeekBarProgressListener.onSeekBarProgress(mProgress);
                    mHandler.postDelayed(this, 800);
                }

            }
        }
    };

}
