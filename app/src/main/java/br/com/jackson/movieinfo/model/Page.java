package br.com.jackson.movieinfo.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Page implements Serializable {

	private static final long serialVersionUID = -1792825525754175290L;

	private int page;

	@SerializedName("results")
	private List<Movie> movies;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List<Movie> getMovies() {
		return movies;
	}

	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}
}