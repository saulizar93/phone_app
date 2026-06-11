package com.example.rottenbanana;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import java.util.List;
import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "rottenbanana.db";
    private static final int DATABASE_VERSION = 1;

    // Table and Column names
    public static final String TABLE_MOVIES = "movies";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_GENRE = "genre";
    public static final String COLUMN_DESCRIPTION = "description";

    // SQL statement to create the table
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_MOVIES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_YEAR + " TEXT, " +
                    COLUMN_GENRE + " TEXT, " +
                    COLUMN_DESCRIPTION + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        onCreate(db);
    }

    // Method to insert a movie into the database
    public boolean addMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_TITLE, movie.getTitle());
        values.put(COLUMN_YEAR, movie.getYear());
        values.put(COLUMN_GENRE, movie.getGenre());
        values.put(COLUMN_DESCRIPTION, movie.getDescription());

        long result = db.insert(TABLE_MOVIES, null, values);
        // db.close(); // Close database connection

        // If insert fails, it returns -1
        return result != -1;
    }

    public List<Movie> getAllMovies() {
        List<Movie> movieList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_MOVIES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
                String year = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_YEAR));
                String genre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENRE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));

                Movie movie = new Movie(id, title, year, genre, description);
                movieList.add(movie);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return movieList;
    }

}
