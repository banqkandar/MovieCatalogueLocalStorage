package com.bangkandar.moviecatalogelocalstorage.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bangkandar.moviecatalogelocalstorage.R;
import com.bangkandar.moviecatalogelocalstorage.activity.MovieDetailActivity;
import com.bangkandar.moviecatalogelocalstorage.model.Movie;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterMovies extends RecyclerView.Adapter<AdapterMovies.GridViewHolder> {
    private ArrayList<Movie> movieData = new ArrayList<>();

    public AdapterMovies() {
    }

    public void setData(ArrayList<Movie> items) {
        movieData.clear();
        movieData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterMovies.GridViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list, viewGroup, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {
        holder.bind(movieData.get(position));
    }

    @Override
    public int getItemCount() {
        return movieData.size();
    }

    class GridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle, tvRilis, tvRating;
        ImageView imgMovie;

        GridViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.txt_title);
            imgMovie = itemView.findViewById(R.id.img_poster);
            tvRating = itemView.findViewById(R.id.user_score);
            tvRilis = itemView.findViewById(R.id.txt_bahasa);
            itemView.setOnClickListener(this);
        }

        void bind(Movie movie) {
            String rating = Double.toString(movie.getRating());
            String url_img = "http://image.tmdb.org/t/p/original/" + movie.getGambar();
            tvTitle.setText(movie.getJudul());
            tvRating.setText(rating);
            tvRilis.setText(movie.getRilis());
            Glide.with(itemView.getContext())
                    .load(url_img)
                    .placeholder(R.color.abu_terang)
                    .dontAnimate()
                    .into(imgMovie);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Movie movie = movieData.get(position);

            movie.setJudul(movie.getJudul());
            movie.setRating(movie.getRating());
            movie.setRilis(movie.getRilis());

            Intent intent = new Intent(itemView.getContext(), MovieDetailActivity.class);
            intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
            itemView.getContext().startActivity(intent);
        }
    }
}
