package br.com.jackson.movieinfo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.jackson.movieinfo.R;
import br.com.jackson.movieinfo.asynctask.PopularMovieAsyncTask;

public class PopularMovieFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular_movie, null);
        callAsyncTask(view);
        return view;
    }

    private void callAsyncTask(View view) {
        PopularMovieAsyncTask httpAsyncTask = new PopularMovieAsyncTask(getContext(), view);
        httpAsyncTask.execute();
    }
}