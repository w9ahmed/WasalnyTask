package com.asyn.wasalnytaskfsq.database;

import java.io.ByteArrayOutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class ImageLiteHelper extends SQLiteOpenHelper {

	private Context context;
	private static final int databaseVersion = 1;
	private static final String databaseName = "imagedb";
	private static final String TABLE_IMAGE = "ImageTable";

	private static final String COL_ID = "col_id";
	private static final String IMAGE_ID = "image_id";
	private static final String IMAGE_BITMAP = "image_bitmap";

	private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_IMAGE
			+ "(" + COL_ID + " integer primary key autoincrement, " + IMAGE_ID
			+ " text, " + IMAGE_BITMAP + " text);";
	
	private String[] allColumns = {COL_ID, IMAGE_ID, IMAGE_BITMAP};

	public ImageLiteHelper(Context context) {
		super(context, databaseName, null, databaseVersion);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	public void insertImage(Drawable dbDrawable, String imageId) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(IMAGE_ID, imageId);
		Bitmap bitmap = ((BitmapDrawable) dbDrawable).getBitmap();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
		values.put(IMAGE_BITMAP, stream.toByteArray());
		db.insert(TABLE_IMAGE, null, values);
		db.close();
		Log.v(ImageLiteHelper.class.getSimpleName(), "Image Inserted");
	}

	public ImageHelper getImage(String imageId) {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.query(TABLE_IMAGE, allColumns, IMAGE_ID + " LIKE '" + imageId + "%'", null,
				null, null, null);
		ImageHelper imageHelper = new ImageHelper();
		if(cursor.moveToFirst()) {
			do {
				imageHelper.setImageId(cursor.getString(1));
				imageHelper.setImageByteArray(cursor.getBlob(2));
			} while(cursor.moveToNext());
		}
		
		cursor.close();
		db.close();
		return imageHelper;
	}

}
