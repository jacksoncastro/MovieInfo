package br.com.jackson.movieinfo.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.List;

import br.com.jackson.movieinfo.Constants;
import br.com.jackson.movieinfo.MainActivity;
import br.com.jackson.movieinfo.fragments.MovieDetailFragment;
import br.com.jackson.movieinfo.R;
import br.com.jackson.movieinfo.model.Movie;

/**
 * Created by jackson on 10/12/16.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context context;
    private List<Movie> items;
    private boolean saveWatchedMovie;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
        }

        public View getView() {
            return view;
        }

        public void setView(View view) {
            this.view = view;
        }
    }

    public MovieAdapter(Context context, List<Movie> items, boolean saveWatchedMovie) {
        this.context = context;
        this.items = items;
        this.saveWatchedMovie = saveWatchedMovie;
    }

    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Movie movie = items.get(position);

        String urlImage = String.format(Constants.URL_BASE_IMAGE, movie.getPosterPath());

        ImageView imageView = (ImageView) holder.view.findViewById(R.id.image_movie);

        Picasso.with(holder.getView().getContext()).load(urlImage).error(R.drawable.image_not_found).into(imageView);

        TextView infoText = (TextView) holder.getView().findViewById(R.id.info_text);

        infoText.setText(movie.getTitle());

        if (movie.getDateView() != null) {
            TextView dateView = (TextView) holder.getView().findViewById(R.id.date_view);

            DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);
            String date = dateFormat.format(movie.getDateView());

            String seenOn = holder.getView().getContext().getString(R.string.seen_on, date);

            dateView.setText(seenOn);
        }


        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context != null && context instanceof MainActivity) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Constants.EXTRA_MOVIE_RESULT, movie);
                    bundle.putBoolean(Constants.EXTRA_SAVE_WATCHED_MOVIE, isSaveWatchedMovie());

                    MainActivity mainActivity = (MainActivity) context;
                    mainActivity.switchContent(MovieDetailFragment.class, bundle);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<Movie> getItems() {
        return items;
    }

    public void setItems(List<Movie> items) {
        this.items = items;
    }

    public boolean isSaveWatchedMovie() {
        return saveWatchedMovie;
    }

    public void setSaveWatchedMovie(boolean saveWatchedMovie) {
        this.saveWatchedMovie = saveWatchedMovie;
    }
}