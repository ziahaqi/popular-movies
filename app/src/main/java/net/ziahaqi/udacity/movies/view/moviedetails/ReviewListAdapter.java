package net.ziahaqi.udacity.movies.view.moviedetails;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.ziahaqi.udacity.movies.R;
import net.ziahaqi.udacity.movies.model.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ziahaqi on 9/23/17.
 */

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ReviewViewHolder> {

    private List<Review> reviews = new ArrayList<>();

    public ReviewListAdapter() {
        this.reviews = new ArrayList<>();
    }

    public void setData(List<Review> data) {
        if (data != null) {
            reviews.clear();
            reviews.addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public ReviewListAdapter.ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewListAdapter.ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.textAuthor.setText(review.author);
        holder.textReview.setText(review.content);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView textAuthor;
        TextView textReview;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            textAuthor = (TextView) itemView.findViewById(R.id.text_author);
            textReview = (TextView) itemView.findViewById(R.id.text_review);
        }

    }
}
