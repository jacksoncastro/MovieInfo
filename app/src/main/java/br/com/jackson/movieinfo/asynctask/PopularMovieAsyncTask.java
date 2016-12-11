package br.com.jackson.movieinfo.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.jackson.movieinfo.Constants;
import br.com.jackson.movieinfo.R;
import br.com.jackson.movieinfo.adapter.MovieAdapter;
import br.com.jackson.movieinfo.helper.HttpHelper;
import br.com.jackson.movieinfo.model.Page;
import br.com.jackson.movieinfo.model.Movie;

/**
 * Created by jackson on 10/12/16.
 */

public class PopularMovieAsyncTask extends AsyncTask<Integer, Void, List<Movie>> {

    private final Context context;
    private final View view;

    public PopularMovieAsyncTask(Context context, View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    protected List<Movie> doInBackground(Integer... parameters) {
        try {
            String json = HttpHelper.doGet(Constants.URL_POPULAR_MOVIES);
            Gson gson = new Gson();
            Page page = gson.fromJson(json, Page.class);
            return page.getMovies();
        } catch (IOException e) {
            Log.e(PopularMovieAsyncTask.class.getSimpleName(), "Erro to call doInBackground", e);
        }
        return new ArrayList<>();
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {

        RecyclerView recyclerView = (RecyclerView) this.view.findViewById(R.id.recycler_view_popular_movies);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.context);
        recyclerView.setLayoutManager(layoutManager);

        // save clicked movie
        MovieAdapter myRecyclerViewAdapter = new MovieAdapter(context, movies, true);
        recyclerView.setAdapter(myRecyclerViewAdapter);
    }
}
