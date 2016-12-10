package br.com.jackson.movieinfo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.jackson.movieinfo.helper.MySQLiteHelper;
import br.com.jackson.movieinfo.model.Movie;

/**
 * Created by jackson on 10/12/16.
 */

public class MovieDAO extends MySQLiteHelper {

    private final String TABLE_NAME = "movie";

    private final String COLUMN_ID = "id";
    private final String COLUMN_TITLE = "title";
    private final String COLUMN_OVERVIEW = "overview";
    private final String COLUMN_POSTER_PATH = "poster_path";
    private final String COLUMN_VOTE_AVERAGE = "vote_average";
    private final String COLUMN_DATA_VIEW = "date_view";

    private final String DATA_VIEW_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public MovieDAO(Context context) {
        super(context);
    }

    public void insert (Movie movie) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, movie.getTitle());
        contentValues.put(COLUMN_OVERVIEW, movie.getOverview());
        contentValues.put(COLUMN_POSTER_PATH, movie.getPosterPath());
        contentValues.put(COLUMN_VOTE_AVERAGE, movie.getVoteAverage());

        getWritableDatabase().insert(TABLE_NAME, null, contentValues);
    }

    public void cleanOldMovies(int maxNumberMovies) {
        String sql = "DELETE FROM %s WHERE %s IN (SELECT %s FROM %s ORDER BY %s DESC, %s DESC LIMIT %d)";
        sql = String.format(sql, TABLE_NAME, COLUMN_ID, COLUMN_ID, TABLE_NAME, COLUMN_DATA_VIEW, COLUMN_ID, maxNumberMovies);

        getWritableDatabase().execSQL(sql);
    }

    public List<Movie> findLastViewMovies(int maxNumberMovies) {
        String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_OVERVIEW, COLUMN_POSTER_PATH, COLUMN_VOTE_AVERAGE, COLUMN_DATA_VIEW};

        String orderBy = COLUMN_DATA_VIEW + " DESC";
        String limit = String.valueOf(maxNumberMovies);

        Cursor cursor = getReadableDatabase().query(TABLE_NAME, columns, null, null, null, null, orderBy, limit);

        try {
            List<Movie> movies = new ArrayList<>();
            while (cursor.moveToNext()) {
                Movie movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(COLUMN_OVERVIEW)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndex(COLUMN_POSTER_PATH)));
                movie.setVoteAverage(cursor.getDouble(cursor.getColumnIndex(COLUMN_VOTE_AVERAGE)));

                try {
                    String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATA_VIEW));
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATA_VIEW_FORMAT);
                    Date dateView = simpleDateFormat.parse(date);
                    movie.setDateView(dateView);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                movies.add(movie);
            }
            return movies;
        } finally {
            closeQuietly(cursor);
        }
    }
}