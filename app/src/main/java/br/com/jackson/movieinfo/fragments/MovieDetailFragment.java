package br.com.jackson.movieinfo.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.jackson.movieinfo.Constants;
import br.com.jackson.movieinfo.R;
import br.com.jackson.movieinfo.dao.MovieDAO;
import br.com.jackson.movieinfo.model.Movie;

public class MovieDetailFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_movie_detail, null);
        initializeForm(view);
        return view;
    }

    private void initializeForm(View view) {
        Bundle extras = getArguments();

        if (extras != null) {

            Movie movie = extras.getParcelable(Constants.EXTRA_MOVIE_RESULT);

            ImageView movieImage = (ImageView) view.findViewById(R.id.movie_detail_image);
            String urlImage = String.format(Constants.URL_BASE_IMAGE, movie.getPosterPath());
            Picasso.with(getActivity()).load(urlImage).error(R.drawable.image_not_found).into(movieImage);

            TextView title = (TextView) view.findViewById(R.id.movie_detail_title);
            title.setText(movie.getTitle());

            RatingBar rating = (RatingBar) view.findViewById(R.id.movie_detail_rating);
            double voteAverage = movie.getVoteAverage();
            voteAverage = voteAverage <= 0L ? 0L : voteAverage / 2;
            rating.setRating((float) voteAverage);

            TextView sinopse = (TextView) view.findViewById(R.id.movie_detail_sinopse);
            sinopse.setText(movie.getOverview());

            boolean saveWatchedMovie = extras.getBoolean(Constants.EXTRA_SAVE_WATCHED_MOVIE);

            if (saveWatchedMovie) {
                saveWatchedMovieAndClean(movie);
            }
        }
    }

    private void saveWatchedMovieAndClean(Movie movie) {
        MovieDAO movieDAO = new MovieDAO(getActivity());

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
