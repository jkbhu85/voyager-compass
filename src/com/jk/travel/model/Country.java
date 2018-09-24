package com.jk.travel.model;

public class Country {

	private int countryID;
	private String countryName;
	private String countryAbbr;
	private String countryFullName;
	private String countryDesc;
	private String nationality;

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

	public String getCountryFullName() {
		return countryFullName;
	}

	public void setCountryFullName(String countryFullName) {
		this.countryFullName = countryFullName;
	}

	@Deprecated
	public String getCountryAbbr() {
		return countryAbbr;
	}

	@Deprecated
	public void setCountryAbbr(String countryAbbr) {
		this.countryAbbr = countryAbbr;
	}

	public String getCountryDesc() {
		return countryDesc;
	}

	public void setCountryDesc(String countryDesc) {
		this.countryDesc = countryDesc;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	@Override
	public String toString() {
		return "Country [countryID=" + countryID + ", countryName=" + countryName + ", countryAbbr=" + countryAbbr
				+ ", countryDesc=" + countryDesc + ", nationality=" + nationality + "]";
	}

}
