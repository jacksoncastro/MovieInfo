package br.com.jackson.movieinfo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import br.com.jackson.movieinfo.asynctask.PopularMovieAsyncTask;

public class PopularMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movie);

        callAsyncTask();
    }

    private void callAsyncTask() {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_popular_movie);

        PopularMovieAsyncTask httpAsyncTask = new PopularMovieAsyncTask(PopularMovieActivity.this, relativeLayout);
        httpAsyncTask.execute();
    }
}