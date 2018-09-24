package com.jk.travel.model;

public class Embassy {

	private int embassyID;
	private int countryID;
	private String embassyName;
	private String embassyAddr;
	private String embassyContactPerson;
	private String phoneNo;

	public int getEmbassyID() {
		return embassyID;
	}

	public void setEmbassyID(int embassyID) {
		this.embassyID = embassyID;
	}

	public int getCountryID() {
		return countryID;
	}

	public void setCountryID(int countryID) {
		this.countryID = countryID;
	}

	public String getEmbassyName() {
		return embassyName;
	}

	public void setEmbassyName(String embassyName) {
		this.embassyName = embassyName;
	}

	public String getEmbassyAddr() {
		return embassyAddr;
	}

	public void setEmbassyAddr(String embassyAddr) {
		this.embassyAddr = embassyAddr;
	}

	public String getEmbassyContactPerson() {
		return embassyContactPerson;
	}

	public void setEmbassyContactPerson(String embassyContactPerson) {
		this.embassyContactPerson = embassyContactPerson;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
}
