package com.asyn.wasalnytaskfsq.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_VENUES = "venues";
	public static final String COLUMN_ID = "_id";

	public static final String VENUE_ID = "venueID";
	public static final String VENUE_NAME = "venueName";
	public static final String VENUE_CATEGORY = "venueCategory";
	public static final String VENUE_ADDRESS = "venueAddress";
	public static final String VENUE_LATITUDE = "venueLat";
	public static final String VENUE_LONGITUDE = "venueLng";
	public static final String VENUE_PHOTO_URL = "photoURL";

	private static final String DATABASE_NAME = "venues.db";
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE = "create table "
			+ TABLE_VENUES + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + VENUE_ID + " text, "
			+ VENUE_NAME + " text, " + VENUE_CATEGORY + " text, "
			+ VENUE_ADDRESS + " text, " + VENUE_LATITUDE + " real, "
			+ VENUE_LONGITUDE + " real, " + VENUE_PHOTO_URL + " text);";

	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_VENUES);
		onCreate(db);
	}

}
