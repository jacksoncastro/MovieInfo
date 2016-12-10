package br.com.jackson.movieinfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.jackson.movieinfo.dao.MovieDAO;
import br.com.jackson.movieinfo.model.Movie;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        initializeForm();
    }

    private void initializeForm() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            Movie movie = extras.getParcelable(Constants.EXTRA_MOVIE_RESULT);

            ImageView movieImage = (ImageView) findViewById(R.id.movie_detail_image);
            String urlImage = String.format(Constants.URL_BASE_IMAGE, movie.getPosterPath());
            Picasso.with(MovieDetailActivity.this).load(urlImage).error(R.drawable.image_not_found).into(movieImage);

            TextView title = (TextView) findViewById(R.id.movie_detail_title);
            title.setText(movie.getTitle());

            RatingBar rating = (RatingBar) findViewById(R.id.movie_detail_rating);
            double voteAverage = movie.getVoteAverage();
            voteAverage = voteAverage <= 0L ? 0L : voteAverage / 2;
            rating.setRating((float) voteAverage);

            TextView sinopse = (TextView) findViewById(R.id.movie_detail_sinopse);
            sinopse.setText(movie.getOverview());

            boolean saveWatchedMovie = extras.getBoolean(Constants.EXTRA_SAVE_WATCHED_MOVIE);

            if (saveWatchedMovie) {
                saveWatchedMovieAndClean(movie);
            }
        }
    }

    private void saveWatchedMovieAndClean(Movie movie) {
        MovieDAO movieDAO = new MovieDAO(MovieDetailActivity.this);

        try {
            movieDAO.insert(movie);
            movieDAO.cleanOldMovies(Constants.MAX_NUMBER_MOVIES);
        } finally {
            if (movieDAO != null) {
                movieDAO.close();
            }
        }
    }
}
