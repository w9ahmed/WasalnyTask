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
	private String[] allColumns = { SQLiteHelper.COLUMN_ID,
			SQLiteHelper.VENUE_ID, SQLiteHelper.VENUE_NAME,
			SQLiteHelper.VENUE_CATEGORY, SQLiteHelper.VENUE_ADDRESS,
			SQLiteHelper.VENUE_LATITUDE, SQLiteHelper.VENUE_LONGITUDE,
			SQLiteHelper.VENUE_PHOTO_URL };

	private List<Venue> venues;

	public VenuesDataSource(Context context) {
		dbHelper = new SQLiteHelper(context);
		venues = new ArrayList<Venue>();
	}

	public void open() {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		database.close();
	}

	public void storeVenue(Venue venue) {
		ContentValues values = new ContentValues();

		values.put(SQLiteHelper.VENUE_ID, venue.getId());
		values.put(SQLiteHelper.VENUE_NAME, venue.getName());
		values.put(SQLiteHelper.VENUE_CATEGORY, venue.getCategory());
		values.put(SQLiteHelper.VENUE_ADDRESS, venue.getAddress());
		values.put(SQLiteHelper.VENUE_LATITUDE, venue.getLatitude());
		values.put(SQLiteHelper.VENUE_LONGITUDE, venue.getLongtitude());
		values.put(SQLiteHelper.VENUE_PHOTO_URL, venue.getPhotoURL());

		database.insert(SQLiteHelper.TABLE_VENUES, null, values);
	}

	public List<Venue> getAllVenues() {
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
		int i = 1;
		venue.setId(cursor.getString(i));
		venue.setName(cursor.getString(++i));
		venue.setCategory(cursor.getString(++i));
		venue.setAddress(cursor.getString(++i));
		venue.setLatitude(cursor.getDouble(++i));
		venue.setLongtitude(cursor.getDouble(++i));
		venue.setPhotoURL(cursor.getString(++i));
		return venue;
	}
}
