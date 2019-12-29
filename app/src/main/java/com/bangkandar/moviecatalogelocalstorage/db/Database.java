package com.bangkandar.moviecatalogelocalstorage.db;

import android.provider.BaseColumns;

public class Database {
    static String TABLE_MOVIE = "movie";

    static final class MovieColumns implements BaseColumns {
        static String JUDUL = "judul";
        static String GAMBAR = "gambar";
        static String DESKRIPSI = "deskripsi";
        static String BAHASA = "bahasa";
        static String RILIS = "rilis";
        static String RATING = "rating";
    }

    static String TABLE_TVSHOW = "tvshow";

    static final class TvshowColumns implements BaseColumns {
        static String JUDUL = "judul";
        static String GAMBAR = "gambar";
        static String DESKRIPSI = "deskripsi";
        static String POPULAR = "popular";
        static String RILIS = "rilis";
        static String RATING = "rating";
        static String BAHASA = "bahasa";
    }
}
