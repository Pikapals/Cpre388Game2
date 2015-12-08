package com.example.tommy.cpre388game2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This class helps with database setup. None of its methods (except for the constructor)
 * should be called directly.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    // Table name
    public static final String TABLE_EVENTS = "highScore";

    // Table columns
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SCORE = "score";

    // Database name
    private static final String DATABASE_NAME = "game.db";

    // Increment this number to clear everything in database
    private static final int DATABASE_VERSION = 1;

    /**
     * Returns an instance of this helper object given the activity
     * @param context
     */
    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*
     * (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
		/*
		 * TODO: Create database table "events"
		 * COLUMN_ID should be of type "integer primary key autoincrement"
		 * All other columns should be of type "text not null"
		 * Columns names have been created as constants at top of this class
		 */
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + TABLE_EVENTS + " ( ");
        sb.append(COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sb.append(COLUMN_SCORE + " INTEGER); ");
        db.execSQL(sb.toString());
    }

    /*
     * (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(db);
    }

}