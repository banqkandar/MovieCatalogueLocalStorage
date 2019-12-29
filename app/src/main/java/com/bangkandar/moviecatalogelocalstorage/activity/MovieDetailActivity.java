package com.bangkandar.moviecatalogelocalstorage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bangkandar.moviecatalogelocalstorage.R;
import com.bangkandar.moviecatalogelocalstorage.db.MoviesHelper;
import com.bangkandar.moviecatalogelocalstorage.model.Movie;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MovieDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "information";
    private TextView tv_judul, tv_deskripsi, tv_rating, tv_bahasa, tv_rilis;
    private ImageView img_movie;
    private ProgressBar progressBar;

    FloatingActionButton fab;

    MoviesHelper moviesHelper;
    Boolean act = true;
    Boolean insert = true;
    Boolean delete = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        img_movie = findViewById(R.id.img_poster);
        tv_judul = findViewById(R.id.txt_title);
        tv_rating = findViewById(R.id.txt_user_score);
        tv_bahasa = findViewById(R.id.txt_runtime);
        tv_rilis = findViewById(R.id.txt_rilis);
        tv_deskripsi = findViewById(R.id.txt_overview);

        progressBar = findViewById(R.id.progressDetailMovie);

        fab = findViewById(R.id.fab_movie);

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        moviesHelper = MoviesHelper.getINSTANCE(this);
        String movieTitle = movie.getJudul();
        Log.d("test", "onCreate: " + movieTitle + moviesHelper.getOne(movieTitle));
        if (moviesHelper.getOne(movieTitle)) {
            //delete
            act = false;
            delete = false;
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite));
        } else if (!moviesHelper.getOne(movieTitle)) {
            //insert
            act = true;
            insert = true;
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border));
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabClick();
            }
        });

        data();

        progressBar.setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {

                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
                        String rating = Double.toString(movie.getRating());
                        String url_image = "http://image.tmdb.org/t/p/original/" + movie.getGambar();

                        tv_judul.setText(movie.getJudul());
                        tv_deskripsi.setText(movie.getDeskripsi());
                        tv_bahasa.setText(movie.getBahasa());
                        tv_rating.setText(rating);
                        tv_rilis.setText(movie.getRilis());
                        Glide.with(getApplicationContext())
                                .load(url_image)
                                .placeholder(R.color.abu_terang)
                                .dontAnimate()
                                .into(img_movie);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).start();
    }

    private void data() {
        progressBar.setVisibility(View.GONE);
        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        String rating = Double.toString(movie.getRating());
        String url_image = "http://image.tmdb.org/t/p/original/" + movie.getGambar();

        tv_judul.setText(movie.getJudul());
        tv_deskripsi.setText(movie.getDeskripsi());
        tv_bahasa.setText(movie.getBahasa());
        tv_rating.setText(rating);
        tv_rilis.setText(movie.getRilis());
        Glide.with(MovieDetailActivity.this)
                .load(url_image)
                .placeholder(R.color.abu_terang)
                .dontAnimate()
                .into(img_movie);
    }

    private void fabClick() {
        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        if (insert && act) {
            movie.setJudul(movie.getJudul());
            movie.setGambar(movie.getGambar());
            movie.setBahasa(movie.getBahasa());
            movie.setRating(movie.getRating());
            movie.setRilis(movie.getRilis());
            movie.setDeskripsi(movie.getDeskripsi());
            long result = moviesHelper.insertMovie(movie);
            if (result > 0) {
                Toast.makeText(MovieDetailActivity.this, R.string.success, Toast.LENGTH_SHORT).show();
                fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite));
                Intent intent = new Intent(MovieDetailActivity.this, HomeActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(MovieDetailActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
            }
        } else if (!delete && !act) {
            long result = moviesHelper.deleteMovie(movie.getJudul());
            if (result > 0) {
                Toast.makeText(MovieDetailActivity.this, R.string.success_hapus, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MovieDetailActivity.this, HomeActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(MovieDetailActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
