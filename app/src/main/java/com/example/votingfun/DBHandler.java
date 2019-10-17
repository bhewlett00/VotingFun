package com.example.votingfun;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "voter.db";

    public static final String TABLE_SONG_VOTE = "song_vote";
    public static final String COLUMN_LIST_ID = "_id";
    public static final String COLUMN_LIST_SONG = "song";
    public static final String COLUMN_LIST_VOTER = "voter";
    public static final String COLUMN_LIST_VOTE = "vote";


    public DBHandler (Context context, SQLiteDatabase.CursorFactory factory){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_SONG_VOTE + "(" +
                COLUMN_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LIST_SONG + " TEXT, " +
                COLUMN_LIST_VOTER + " TEXT, " +
                COLUMN_LIST_VOTE + " INTEGER" +
                ");";

        // execute create statement
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SONG_VOTE);

        // call method that creates tables
        onCreate(sqLiteDatabase);
    }

    public void addSongVote (String song, String voter, int vote) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        // put key-value pairs in ContentValues.  The key must be the name
        // of a column and the value is the value to be inserted in the column.
        values.put(COLUMN_LIST_SONG, song);
        values.put(COLUMN_LIST_VOTER, voter);
        values.put(COLUMN_LIST_VOTE, vote);

        // insert values into shopping list table
        db.insert(TABLE_SONG_VOTE, null, values);

        // close reference to shopper database
        db.close();
    }
}
