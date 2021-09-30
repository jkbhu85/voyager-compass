package com.jk.vc.model;

public class Hotel {

	private int hotelID;
	private String hotelName;
	private String hotelAddr;
	private String hotelPhno;
	private String hotelContactPerson;
	private double minCharge;
	private double maxCharge;
	private int countryID;
	private String countryName;

	public int getHotelID() {
		return hotelID;
	}

	public void setHotelID(int hotelID) {
		this.hotelID = hotelID;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getHotelAddr() {
		return hotelAddr;
	}

	public void setHotelAddr(String hotelAddr) {
		this.hotelAddr = hotelAddr;
	}

	public String getHotelPhno() {
		return hotelPhno;
	}

	public void setHotelPhno(String hotelPhno) {
		this.hotelPhno = hotelPhno;
	}

	public String getHotelContactPerson() {
		return hotelContactPerson;
	}

	public void setHotelContactPerson(String hotelContactPerson) {
		this.hotelContactPerson = hotelContactPerson;
	}

	public double getMinCharge() {
		return minCharge;
	}

	public void setMinCharge(double minCharge) {
		this.minCharge = minCharge;
	}

	public double getMaxCharge() {
		return maxCharge;
	}

	public void setMaxCharge(double maxCharge) {
		this.maxCharge = maxCharge;
	}

	public int getCountryID() {
		return countryID;
	}

	public void setCountryID(int countryID) {
		this.countryID = countryID;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
}
