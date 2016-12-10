package br.com.jackson.movieinfo.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.jackson.movieinfo.Constants;
import br.com.jackson.movieinfo.MovieDetailActivity;
import br.com.jackson.movieinfo.R;
import br.com.jackson.movieinfo.model.Result;

/**
 * Created by jackson on 10/12/16.
 */

public class PopularMovieAdapter extends RecyclerView.Adapter<PopularMovieAdapter.ViewHolder>  {

    private List<Result> items;

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

    public PopularMovieAdapter(List<Result> items) {
        this.items = items;
    }

    @Override
    public PopularMovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Result result = items.get(position);

        String urlImage = String.format(Constants.URL_BASE_IMAGE, result.getPosterPath());

        ImageView imageView = (ImageView) holder.view.findViewById(R.id.image_movie);

        Picasso.with(holder.getView().getContext()).load(urlImage).error(R.drawable.image_not_found).into(imageView);

        TextView infoText = (TextView) holder.getView().findViewById(R.id.info_text);

        infoText.setText(result.getTitle());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.view.getContext(), MovieDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.EXTRA_MOVIE_RESULT, result);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}