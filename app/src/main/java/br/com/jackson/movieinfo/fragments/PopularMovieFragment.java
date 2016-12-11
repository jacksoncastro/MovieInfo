package br.com.jackson.movieinfo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import br.com.jackson.movieinfo.R;
import br.com.jackson.movieinfo.asynctask.PopularMovieAsyncTask;

public class PopularMovieFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_popular_movie, null);
        callAsyncTask(view);
        return view;
    }

    private void callAsyncTask(View view) {
        PopularMovieAsyncTask httpAsyncTask = new PopularMovieAsyncTask(getContext(), view);
        httpAsyncTask.execute();
    }
}