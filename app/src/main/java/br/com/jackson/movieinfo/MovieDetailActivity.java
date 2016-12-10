package br.com.jackson.movieinfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import br.com.jackson.movieinfo.model.Result;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            Result result = extras.getParcelable(Constants.EXTRA_MOVIE_RESULT);

            ImageView movieImage = (ImageView) findViewById(R.id.movie_detail_image);
            String urlImage = String.format(Constants.URL_BASE_IMAGE, result.getPosterPath());
            Picasso.with(MovieDetailActivity.this).load(urlImage).error(R.drawable.image_not_found).into(movieImage);

            TextView title = (TextView) findViewById(R.id.movie_detail_title);
            title.setText(result.getTitle());

            RatingBar rating = (RatingBar) findViewById(R.id.movie_detail_rating);
            double voteAverage = result.getVoteAverage();
            voteAverage = voteAverage <= 0L ? 0 : voteAverage / 2;
            rating.setRating((float) voteAverage);

            TextView sinopse = (TextView) findViewById(R.id.movie_detail_sinopse);
            sinopse.setText(result.getOverview());
        }
    }
}
