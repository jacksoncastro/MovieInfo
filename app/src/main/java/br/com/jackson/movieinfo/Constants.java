package br.com.jackson.movieinfo;

/**
 * Created by jackson on 10/12/16.
 */

public final class Constants {

    private Constants() {
    }

    public static final String SECRET_KEY_API = "YOUR_SECRET_KEY_API";

    public static final String URL_POPULAR_MOVIES = "https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=" + SECRET_KEY_API;
    // 1st parameter: code image
    public static final String URL_BASE_IMAGE = "http://image.tmdb.org/t/p/w500/%s";

    public static final String EXTRA_MOVIE_RESULT = "EXTRA_MOVIE_RESULT";

    public static final String EXTRA_SAVE_WATCHED_MOVIE = "EXTRA_SAVE_WATCHED_MOVIE";

    public static final int MAX_NUMBER_MOVIES = 15;
}