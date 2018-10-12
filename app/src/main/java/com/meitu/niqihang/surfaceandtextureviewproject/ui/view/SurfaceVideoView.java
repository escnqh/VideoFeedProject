package com.meitu.niqihang.surfaceandtextureviewproject.ui.view;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @author nqh 2018/10/10.
 */
public class SurfaceVideoView extends SurfaceView implements SurfaceHolder.Callback,Runnable {

    public SurfaceVideoView(Context context) {
        super(context);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void run() {

    }
}
