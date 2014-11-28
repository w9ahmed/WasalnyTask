package com.asyn.wasalnytaskfsq.models;

import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.LatLng;

public class Venue {

	private String id;
	private String name;

	private String address;
	private double latitude;
	private double longitude;

	private String photoURL;
	private Drawable photo;

	public Venue() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		if (address != null)
			return address;
		return "Address Not Available";
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongtitude() {
		return longitude;
	}

	public void setLongtitude(double longtitude) {
		this.longitude = longtitude;
	}

	public String getPhotoURL() {
		return photoURL;
	}

	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}

	public Drawable getPhoto() {
		return photo;
	}

	public void setPhoto(Drawable photo) {
		this.photo = photo;
	}

	/**
	 * Return Location
	 * 
	 * @return
	 */
	public LatLng getLocation() {
		return new LatLng(latitude, longitude);
	}
		

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Name: " + name + "\n");
		if (address != null)
			builder.append("Address: " + address + "\n");
		builder.append("Location: " + getLocation());
		return builder.toString();
	}
}
