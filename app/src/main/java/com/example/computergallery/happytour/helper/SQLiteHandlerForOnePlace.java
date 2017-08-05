package com.example.computergallery.happytour.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;



public class SQLiteHandlerForOnePlace extends SQLiteOpenHelper {
    private static final String TAG = SQLiteHandlerForOnePlace.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "one_place";

    // Login table name
    private static final String TABLE_USER = "fixed_place";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_PLACE_ID = "place_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DES = "des";
    private static final String KEY_RATING = "rating";
    private static final String KEY_IMG1 = "img1";
    private static final String KEY_IMG2 = "img2";
    private static final String KEY_IMG3 = "img3";
    private static final String KEY_LOC = "loc";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LON = "lon";
    private static final String KEY_DIVISION = "division";
    private static final String KEY_DISTRICT = "district";

    public SQLiteHandlerForOnePlace(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," +  KEY_PLACE_ID + " TEXT UNIQUE," + KEY_NAME + " TEXT,"
                + KEY_DES + " TEXT," + KEY_RATING + " TEXT,"  + KEY_IMG1 + " TEXT," +
                KEY_IMG2 + " TEXT," + KEY_IMG3 + " TEXT,"+ KEY_LOC + " TEXT," +
                KEY_LAT + " TEXT,"+ KEY_LON + " TEXT,"+ KEY_DIVISION + " TEXT,"
                + KEY_DISTRICT + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String place_id, String name, String des, String rating, String img1, String img2,
                        String img3, String loc, String lat, String lon, String division, String district) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PLACE_ID, place_id); // Email
        values.put(KEY_NAME, name); // Name
        values.put(KEY_DES, des); // Email
        values.put(KEY_RATING, rating); // Created At
        values.put(KEY_IMG1, img1);
        values.put(KEY_IMG2, img2); // Created At
        values.put(KEY_IMG3, img3); // Name
        values.put(KEY_LOC, loc); // Email
        values.put(KEY_LAT, lat); // Email
        values.put(KEY_LON, lon); // Created At
        values.put(KEY_DIVISION, division); // Email
        values.put(KEY_DISTRICT, district); // Created At

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("place_id", cursor.getString(1));
            user.put("name", cursor.getString(2));
            user.put("des", cursor.getString(3));
            user.put("rating", cursor.getString(4));
            user.put("img1", cursor.getString(5));
            user.put("img2", cursor.getString(6));
            user.put("img3", cursor.getString(7));
            user.put("loc", cursor.getString(8));
            user.put("lat", cursor.getString(9));
            user.put("lon", cursor.getString(10));
            user.put("division", cursor.getString(11));
            user.put("district", cursor.getString(12));

        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }


    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

}

