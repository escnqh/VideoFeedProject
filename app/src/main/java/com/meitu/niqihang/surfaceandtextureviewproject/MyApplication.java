package com.meitu.niqihang.surfaceandtextureviewproject;

import android.app.Application;
import android.media.MediaPlayer;

/**
 * @author nqh 2018/10/16.
 */
public class MyApplication extends Application {
    private static MyApplication instance = null;
    private MediaPlayer mMediaPlayer;

    /**
     * 获取实例
     *
     * @return Application实例
     */
    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    /**
     * @return MediaPlayer 唯一对象
     */
    public MediaPlayer getMediaPlayer() {
        if (mMediaPlayer == null) {
            synchronized (MyApplication.class) {
                if (mMediaPlayer == null) {
                    mMediaPlayer = new MediaPlayer();
                }
            }
        }
        return mMediaPlayer;
    }
}
