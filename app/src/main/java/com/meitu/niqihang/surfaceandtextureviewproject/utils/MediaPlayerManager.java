package com.meitu.niqihang.surfaceandtextureviewproject.utils;

import android.media.MediaPlayer;

/**
 * 播放器管理类
 *
 * @author nqh 2018/10/17.
 */
public class MediaPlayerManager {

    private MediaPlayer mMediaPlayer;

    private MediaPlayerManager() {
    }

    private static MediaPlayerManager getInstance() {
        return GetInstance.INSTANCE;
    }

    private static class GetInstance {
        private static final MediaPlayerManager INSTANCE = new MediaPlayerManager();
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        if (mMediaPlayer != mediaPlayer) {

            mMediaPlayer = mediaPlayer;
        }
    }

    public void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public void resumeMediaPlayer() {
        if (mMediaPlayer != null) {

        }
    }
}
