package com.jk.vc.model;

/**
 * This class represents details of visa obtained by an employee from a country.
 * It is a POJO class.
 * 
 * @author Jitendra
 *
 */
public class Visa {
	private int visaId;
	private int pptId;
	private int visaTypeId;

	private String visaTypeName;
	private String visaCountry;

	private int empId;

	private int maxVisits;
	private int visitCount;

	private String visaNo;
	private String issueDate;
	private String expireDate;
	private String placeIssued;
	private String comments;
	private String visaCost;

	private int cancelStatus;
	private String cancelDate;


	public int getVisaId() {
		return visaId;
	}


	public void setVisaId(int visaId) {
		this.visaId = visaId;
	}


	public int getPptId() {
		return pptId;
	}


	public void setPptId(int pptId) {
		this.pptId = pptId;
	}


	public int getEmpId() {
		return pptId;
	}


	public void setEmpId(int pptId) {
		this.pptId = pptId;
	}


	public int getVisaTypeId() {
		return visaTypeId;
	}


	public void setVisaTypeId(int visaTypeId) {
		this.visaTypeId = visaTypeId;
	}


	public int getMaxVisits() {
		return maxVisits;
	}


	public void setMaxVisits(int maxVisits) {
		this.maxVisits = maxVisits;
	}


	public int getVisitCount() {
		return visitCount;
	}


	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}


	public String getVisaNo() {
		return visaNo;
	}


	public void setVisaNo(String visaNo) {
		this.visaNo = visaNo;
	}


	public String getIssueDate() {
		return issueDate;
	}


	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}


	public String getExpireDate() {
		return expireDate;
	}


	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
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


	public String getVisaCost() {
		return visaCost;
	}


	public void setVisaCost(String visaCost) {
		this.visaCost = visaCost;
	}


	public int getCancelStatus() {
		return cancelStatus;
	}


	public void setCancelStatus(int cancelStatus) {
		this.cancelStatus = cancelStatus;
	}


	public String getCancelDate() {
		return cancelDate;
	}


	public void setCancelDate(String cancelDate) {
		this.cancelDate = cancelDate;
	}


	public boolean isCancelled() {
		return cancelStatus != 0;
	}


	public String getVisaTypeName() {
		return visaTypeName;
	}


	public void setVisaTypeName(String visaTypeName) {
		this.visaTypeName = visaTypeName;
	}


	public String getVisaCountry() {
		return visaCountry;
	}


	public void setVisaCountry(String visaCountry) {
		this.visaCountry = visaCountry;
	}


	@Override
	public String toString() {
		return "Visa [visaId=" + visaId + ", pptId=" + pptId + ", visaTypeId=" + visaTypeId
				+ ", empId=" + empId + ", maxVisits=" + maxVisits + ", visitCount=" + visitCount
				+ ", visaNo=" + visaNo + ", issueDate=" + issueDate + ", expireDate=" + expireDate
				+ ", placeIssued=" + placeIssued + ", comments=" + comments + ", visaCost="
				+ visaCost + ", cancelStatus=" + cancelStatus + ", cancelDate=" + cancelDate + "]";
	}

}
