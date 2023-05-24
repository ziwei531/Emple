package com.example.emple;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String POST_TABLE = "POSTS";
    public static final String COLUMN_POST_ID = "post_id";
    public static final String COLUMN_POST_TITLE = "post_title";
    public static final String COLUMN_POST_CONTENT = "post_content";
    public static final String COLUMN_POST_DEGREE_COURSE = "post_degree_course"; //for the tags. e.g. Bachelor's of Computer Science, Bachelor's of Architecture
    public static final String COLUMN_POST_DATETIME = "post_datetime";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "emple.db", null, 1);
    }

    // this is called the first time a database is accessed. There should be code in here to create a new database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE "+ POST_TABLE + " (" + COLUMN_POST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_POST_TITLE + " TEXT, " + COLUMN_POST_CONTENT + " TEXT, " + COLUMN_POST_DEGREE_COURSE + " TEXT, " + COLUMN_POST_DATETIME + " DATETIME)";
        db.execSQL(createTableStatement);
    }

    //ensures version is up-to-date
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(String title, String content, String degreeCourse, String dateTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_POST_TITLE, title);
        values.put(COLUMN_POST_CONTENT, content);
        values.put(COLUMN_POST_DEGREE_COURSE, degreeCourse);
        values.put(COLUMN_POST_DATETIME, dateTime);

        long result = db.insert(POST_TABLE, null, values);
        db.close();

        return result != -1;
    }

}
