package com.bangkandar.moviecatalogelocalstorage.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.bangkandar.moviecatalogelocalstorage.model.Movie;

import java.util.ArrayList;

import static com.bangkandar.moviecatalogelocalstorage.db.Database.TABLE_MOVIE;

public class MoviesHelper {
    private static final String DATABASE_TABLE = TABLE_MOVIE;
    private static DatabaseHelper dataBaseHelper;
    private static MoviesHelper INSTANCE;
    private static SQLiteDatabase database;

    private MoviesHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
    }

    public static MoviesHelper getINSTANCE(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MoviesHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    public ArrayList<Movie> getAllMovie() {
        ArrayList<Movie> movies = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null, null, null, null, null, Database.MovieColumns._ID, null);
        cursor.moveToFirst();
        Movie mItem;
        if (cursor.getCount() > 0) {
            do {
                mItem = new Movie();
                mItem.setId(cursor.getInt(0));
                mItem.setJudul(String.valueOf(cursor.getString(1)));
                mItem.setGambar(String.valueOf(cursor.getString(2)));
                mItem.setDeskripsi(String.valueOf(cursor.getString(3)));
                mItem.setBahasa(String.valueOf(cursor.getString(4)));
                mItem.setRilis(String.valueOf(cursor.getString(5)));
                mItem.setRating(Double.valueOf(cursor.getDouble(6)));
                movies.add(mItem);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return movies;
    }

    public Boolean getOne(String name) {
        String querySingleRecord = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + Database.MovieColumns.JUDUL + " " + " LIKE " + "'" + name + "'";
        Cursor cursor = database.rawQuery(querySingleRecord, null);
        cursor.moveToFirst();
        Log.d("cursor", String.valueOf(cursor.getCount()));
        if (cursor.getCount() > 0) {

            return true;
        } else if (cursor.getCount() == 0) {
            return false;
        }
        return false;
    }

    public long insertMovie(Movie mItem) {
        ContentValues args = new ContentValues();
        args.put(Database.MovieColumns.JUDUL, mItem.getJudul());
        args.put(Database.MovieColumns.GAMBAR, mItem.getGambar());
        args.put(Database.MovieColumns.DESKRIPSI, mItem.getDeskripsi());
        args.put(Database.MovieColumns.RILIS, mItem.getRilis());
        args.put(Database.MovieColumns.RATING, mItem.getRating());
        args.put(Database.MovieColumns.BAHASA, mItem.getBahasa());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteMovie(String title) {
        return database.delete(DATABASE_TABLE, Database.MovieColumns.JUDUL + " = " + "'" + title + "'", null);
    }
}
