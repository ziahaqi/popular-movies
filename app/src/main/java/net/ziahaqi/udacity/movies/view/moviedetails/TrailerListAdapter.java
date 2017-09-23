package net.ziahaqi.udacity.movies.view.moviedetails;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.ziahaqi.udacity.movies.R;
import net.ziahaqi.udacity.movies.model.Trailer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ziahaqi on 9/23/17.
 */

public class TrailerListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    List<Trailer> trailers;
    private TrailerListener listener;

    public interface TrailerListener {
        void onTrailerClick(Trailer position);
    }

    public TrailerListAdapter(TrailerListener listener) {
        trailers = new ArrayList<>();
        this.listener = listener;
    }

    public void setData(List<Trailer> list) {
        if (list != null) {
            trailers.clear();
            trailers.addAll(list);
            this.notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_trailer, parent, false);
        return new TrailerViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TrailerViewHolder viewHolder = (TrailerViewHolder) holder;
        Trailer trailer = trailers.get(position);
        viewHolder.textDesc.setText(trailer.name);

        String imageVideoUrl = "https://i1.ytimg.com/vi/" + trailer.key + "/0.jpg";
        Glide.with(holder.itemView.getContext())
                .load(imageVideoUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.imageTrailer);
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageTrailer;
        TextView textDesc;

        public TrailerViewHolder(View itemView, final TrailerListener listener) {
            super(itemView);
            textDesc = (TextView) itemView.findViewById(R.id.text_desc);
            imageTrailer = (ImageView) itemView.findViewById(R.id.image_trailer);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onTrailerClick(trailers.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
}
