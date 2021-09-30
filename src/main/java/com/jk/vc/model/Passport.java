package com.jk.vc.model;

public class Passport {
	private int pptId;
	private int empId;
	private int cntId;
	private String pptNo;
	private String pptType;
	private String birthPlace;
	private String issueDate;
	private String expiryDate;
	private String address;
	private String placeIssued;
	private String comments;


	public int getPptId() {
		return pptId;
	}


	public void setPptId(int pptId) {
		this.pptId = pptId;
	}


	public int getEmpId() {
		return empId;
	}


	public void setEmpId(int empId) {
		this.empId = empId;
	}


	public int getCntId() {
		return cntId;
	}


	public void setCntId(int cntId) {
		this.cntId = cntId;
	}


	public String getPptNo() {
		return pptNo;
	}


	public void setPptNo(String pptNo) {
		this.pptNo = pptNo;
	}


	public String getPptType() {
		return pptType;
	}


	public void setPptType(String pptType) {
		this.pptType = pptType;
	}


	public String getBirthPlace() {
		return birthPlace;
	}


	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}


	public String getIssueDate() {
		return issueDate;
	}


	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}


	public String getExpiryDate() {
		return expiryDate;
	}


	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getPlaceIssued() {
		return placeIssued;
	}


	public void setPlaceIssued(String placeIssued) {
		this.placeIssued = placeIssued;
	}


	public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
	}
}
