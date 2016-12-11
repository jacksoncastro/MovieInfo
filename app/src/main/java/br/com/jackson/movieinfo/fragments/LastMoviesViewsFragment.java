package br.com.jackson.movieinfo.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.jackson.movieinfo.Constants;
import br.com.jackson.movieinfo.R;
import br.com.jackson.movieinfo.adapter.MovieAdapter;
import br.com.jackson.movieinfo.dao.MovieDAO;
import br.com.jackson.movieinfo.model.Movie;

public class LastMoviesViewsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_last_movies_views, null);
        setContent(view);
        return view;
    }

    private void setContent(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_last_movies_views);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        List<Movie> movies = getLastMovies();

        // not save clicked movie
        MovieAdapter myRecyclerViewAdapter = new MovieAdapter(getActivity(), movies, false);
        recyclerView.setAdapter(myRecyclerViewAdapter);
    }

    private List<Movie> getLastMovies() {
        MovieDAO movieDAO = new MovieDAO(getContext());
        try {
            return movieDAO.findLastViewMovies(Constants.MAX_NUMBER_MOVIES);
        } finally {
            if (movieDAO != null) {
                movieDAO.close();
            }
        }
    }
}
