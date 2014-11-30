package com.asyn.wasalnytaskfsq.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.asyn.wasalnytaskfsq.models.Venue;

public class VenuesDataSource {

	private SQLiteDatabase database;
	private SQLiteHelper dbHelper;
	private String[] allColumns = { SQLiteHelper.VENUE_ID,
			SQLiteHelper.VENUE_NAME, SQLiteHelper.VENUE_CATEGORY,
			SQLiteHelper.VENUE_ADDRESS, SQLiteHelper.VENUE_LATITUDE,
			SQLiteHelper.VENUE_LONGITUDE, SQLiteHelper.VENUE_PHOTO_URL };
	
	private List<Venue> venues;

	public VenuesDataSource(Context context) {
		dbHelper = new SQLiteHelper(context);
	}

	public void open() {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		database.close();
	}

	public Venue storeVenue(Venue venue) {
		ContentValues values = new ContentValues();

		values.put(SQLiteHelper.VENUE_ID, venue.getId());
		values.put(SQLiteHelper.VENUE_NAME, venue.getName());
		values.put(SQLiteHelper.VENUE_CATEGORY, venue.getCategory());
		values.put(SQLiteHelper.VENUE_ADDRESS, venue.getAddress());
		values.put(SQLiteHelper.VENUE_LATITUDE, venue.getLatitude());
		values.put(SQLiteHelper.VENUE_LONGITUDE, venue.getLongtitude());
		values.put(SQLiteHelper.VENUE_PHOTO_URL, venue.getPhotoURL());

		long insertId = database
				.insert(SQLiteHelper.TABLE_VENUES, null, values);

		Cursor cursor = database.query(SQLiteHelper.TABLE_VENUES, allColumns,
				SQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null,
				null);

		Venue newVenue = cursorToVenue(cursor);
		cursor.close();
		return newVenue;
	}

	public List<Venue> getAllVenues() {
		venues = new ArrayList<Venue>();
		Cursor cursor = database.query(SQLiteHelper.TABLE_VENUES, allColumns,
				null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Venue venue = cursorToVenue(cursor);
			venues.add(venue);
			cursor.moveToNext();
		}

		cursor.close();
		return venues;
	}

	private Venue cursorToVenue(Cursor cursor) {
		Venue venue = new Venue();
		venue.setId(cursor.getString(1));
		venue.setName(cursor.getString(2));
		venue.setCategory(cursor.getString(3));
		venue.setAddress(cursor.getString(4));
		venue.setLatitude(cursor.getDouble(5));
		venue.setLongtitude(cursor.getDouble(6));
		venue.setPhotoURL(cursor.getString(7));
		return venue;
	}
	
	public boolean isItEmpty() {
		if(venues.size() == 0)
			return true;
		else
			return false;
	}
}
