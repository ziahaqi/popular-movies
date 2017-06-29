package net.ziahaqi.udacity.movies.view.movielist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.ziahaqi.udacity.movies.R;
import net.ziahaqi.udacity.movies.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ziahaqi on 6/28/17.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieHolder> {
    private EventListener mListener;
    private List<Movie> movies;

    public Movie getItem(int position) {
        return movies.get(position);
    }

    public interface EventListener {
        void onClick(int position);
    }

    public MovieListAdapter(EventListener listener) {
        this.mListener = listener;
        this.movies = new ArrayList<>();
    }

    public void setData(List<Movie> movies) {
        if (movies != null) {
            this.movies.clear();
            this.movies.addAll(movies);
            this.notifyDataSetChanged();
        }
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_movie_list, parent, false);
        MovieHolder holder = new MovieHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        Movie movie = getItem(position);
        final String posterUrl = "http://image.tmdb.org/t/p/w342/" + movie.posterPath;
        Glide.with(holder.itemView.getContext())
                .load(posterUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imagePoster);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder {
        private ImageView imagePoster;

        public MovieHolder(View itemView) {
            super(itemView);
            imagePoster = (ImageView) itemView.findViewById(R.id.image_movie_poster);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(getAdapterPosition());
                }
            });
        }
    }
}
