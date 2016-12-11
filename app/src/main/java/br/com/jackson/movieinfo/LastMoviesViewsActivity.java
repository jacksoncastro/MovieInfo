package br.com.jackson.movieinfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import br.com.jackson.movieinfo.adapter.MovieAdapter;
import br.com.jackson.movieinfo.dao.MovieDAO;
import br.com.jackson.movieinfo.model.Movie;

public class LastMoviesViewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_movies_views);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_last_movies_views);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(LastMoviesViewsActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        List<Movie> movies = getLastMovies();

        // not save clicked movie
        MovieAdapter myRecyclerViewAdapter = new MovieAdapter(movies, false);
        recyclerView.setAdapter(myRecyclerViewAdapter);
    }

    private List<Movie> getLastMovies() {
        MovieDAO movieDAO = new MovieDAO(LastMoviesViewsActivity.this);
        try {
            return movieDAO.findLastViewMovies(Constants.MAX_NUMBER_MOVIES);
        } finally {
            if (movieDAO != null) {
                movieDAO.close();
            }
        }
    }
}
