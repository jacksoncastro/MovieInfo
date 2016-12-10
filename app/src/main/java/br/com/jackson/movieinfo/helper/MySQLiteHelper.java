package br.com.jackson.movieinfo.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

import br.com.jackson.movieinfo.R;


public class MySQLiteHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "movie_info";
    private Context context;

    public MySQLiteHelper(Context context) {
        super(context, MySQLiteHelper.DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        InputStream inputStream = context.getResources().openRawResource(R.raw.movie_info);
        try {
            String sql = IOUtils.toString(inputStream);

            String[] split = sql.split(";");

            for (String create : split) {
                sqLiteDatabase.execSQL(create);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int version, int lastVersion) {
        String sql = "DROP TABLE IF EXISTS movie;";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public void closeQuietly(Cursor cursor) {
        try {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        } catch (Exception e) {
        }
    }

}