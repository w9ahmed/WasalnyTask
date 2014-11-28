package com.asyn.wasalnytaskfsq.helpers;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;


public class Venue {
	
	protected static List<Venue> venues = new ArrayList<Venue>();
	
	private String id;
	private String name;

	private String address;
	private double latitude;
	private double longitude;
	
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
		if(address != null)
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
	
	/**
	 * Return Location
	 * @return
	 */
	public LatLng getLocation() {
		return new LatLng(latitude, longitude);
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Name: " + name + "\n");
		if(address != null)
			builder.append("Address: " + address + "\n");
		builder.append("Location: " + getLocation());
		return builder.toString();
	}
	
	public static List<Venue> getVenues() {
		return venues;
	}
	
}
