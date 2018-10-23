package com.meitu.niqihang.surfaceandtextureviewproject.utils;

import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.view.Surface;

import com.meitu.niqihang.surfaceandtextureviewproject.entity.MediaProgress;
import com.meitu.niqihang.surfaceandtextureviewproject.entity.MediaStateEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

/**
 * 播放器管理类
 *
 * @author nqh 2018/10/17.
 */
public class MediaPlayerManager {

    private MediaPlayer mMediaPlayer;
    private EventBus mEventBus;
    private final MediaStateEvent MEDIA_PLAYING = new MediaStateEvent(Contracts.MEDIA_PLAYING);
    private final MediaStateEvent MEDIA_PAUSE = new MediaStateEvent(Contracts.MEDIA_PAUSE);
    private final MediaStateEvent MEDIA_STOP = new MediaStateEvent(Contracts.MEDIA_STOP);
    private final MediaStateEvent MEDIA_ERROR = new MediaStateEvent(Contracts.MEDIA_ERROR);
    private final MediaProgress mMediaProgress = new MediaProgress();
    private final Handler mHandler = new Handler();
    private final Runnable mProgressTask = new Runnable() {
        @Override
        public void run() {
            if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                if (mEventBus != null) {
                    mMediaProgress.setProgress(mMediaPlayer.getCurrentPosition());
                    mEventBus.post(mMediaProgress);
                    mHandler.postDelayed(mProgressTask, 600);
                }
            }
        }
    };

    private MediaPlayerManager() {
        mEventBus = EventBus.getDefault();
    }

    public static MediaPlayerManager getInstance() {
        return GetInstance.INSTANCE;
    }

    private static class GetInstance {
        private static final MediaPlayerManager INSTANCE = new MediaPlayerManager();
    }

    /**
     * 通过Surface初始化MediaPlayer
     */
    public boolean initMediaPlayer(SurfaceTexture surfaceTexture) {
        if (null == surfaceTexture) {
            return false;
        }
        try {
            if (null == mMediaPlayer) {
                mMediaPlayer = new MediaPlayer();
            }
            mMediaPlayer.setSurface(new Surface(surfaceTexture));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        if (mMediaPlayer != mediaPlayer) {
            mMediaPlayer = mediaPlayer;
        }
    }

    public void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public void setUrlAndPlay(String url) {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            try {
                mMediaPlayer.setDataSource(url);
                mMediaPlayer.prepareAsync();
                mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        start();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void resume() {
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
            mEventBus.post(MEDIA_PLAYING);
        }
    }

    public void start() {
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
            mEventBus.post(MEDIA_PLAYING);
            mHandler.post(mProgressTask);
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stop();
                }
            });
            mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    mEventBus.post(MEDIA_ERROR);
                    if (null != mMediaPlayer) {
                        mMediaPlayer.reset();
                    }
                    return true;
                }
            });
        }
    }

    public void pause() {
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
            mEventBus.post(MEDIA_PAUSE);
        }
    }

    public void seekTo(int position) {
        if (null != mMediaPlayer) {
            mMediaPlayer.seekTo(position);
        }
    }

    public void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mEventBus.post(MEDIA_STOP);
            mHandler.removeCallbacksAndMessages(null);
        }
    }
}
