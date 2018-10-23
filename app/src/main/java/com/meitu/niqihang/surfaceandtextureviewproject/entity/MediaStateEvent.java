package com.meitu.niqihang.surfaceandtextureviewproject.entity;

/**
 * @author nqh 2018/10/23.
 */
public class MediaStateEvent {
    private int mState;

    public MediaStateEvent(int state) {
        this.mState = state;
    }

    public void setState(int state) {
        mState = state;
    }

    public int getState() {
        return mState;
    }
}
