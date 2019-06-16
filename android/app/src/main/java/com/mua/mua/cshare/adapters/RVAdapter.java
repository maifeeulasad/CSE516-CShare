package com.mua.mua.cshare.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mua.mua.cshare.R;
import com.mua.mua.cshare.model.Content;
import com.mua.mua.cshare.observer.CustomItemClickListener;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ContentViewHolder> {

    CustomItemClickListener listener;

    public static class ContentViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView fileName;
        TextView fileLocation;
        ImageView fileThumbnail;

        ContentViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            fileName = (TextView)itemView.findViewById(R.id.file_name);
            fileLocation = (TextView)itemView.findViewById(R.id.file_location);
            fileThumbnail = (ImageView)itemView.findViewById(R.id.file_thumbnail);
        }
    }

    List<Content> contents;

    public RVAdapter(List<Content> contents, CustomItemClickListener listener){
        this.contents = contents;
        this.listener = listener;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        final ContentViewHolder pvh = new ContentViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, pvh.getPosition());
            }
        });
        return pvh;
    }

    @Override
    public void onBindViewHolder(ContentViewHolder ContentViewHolder, int i) {
        ContentViewHolder.fileName.setText(contents.get(i).name);
        ContentViewHolder.fileLocation.setText(contents.get(i).location);
        ContentViewHolder.fileThumbnail.setImageResource(contents.get(i).thumbnail);
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }
}
