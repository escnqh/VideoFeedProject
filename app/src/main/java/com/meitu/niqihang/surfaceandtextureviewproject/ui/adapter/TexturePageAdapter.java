package com.meitu.niqihang.surfaceandtextureviewproject.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.meitu.niqihang.surfaceandtextureviewproject.entity.VideoBean;

import java.util.List;

/**
 * @author nqh 2018/10/11.
 */
public class TexturePageAdapter extends RecyclerView.Adapter<TexturePageAdapter.TexturePageViewHolder> {

    public TexturePageAdapter(Context context, List<VideoBean> datas) {


    }


    @NonNull
    @Override
    public TexturePageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TexturePageViewHolder texturePageViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class TexturePageViewHolder extends RecyclerView.ViewHolder {

        public TexturePageViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
