package com.jk.vc.model;

public class Travel {
	private int travelId;
	private int empId;
	private int workId;

	private String startDate;
	private String endDate;
	private String inst;

	private String empName;
	private String visaType;
	private String visaIssueDate;


	public int getTravelId() {
		return travelId;
	}


	public void setTravelId(int travelId) {
		this.travelId = travelId;
	}


	public int getEmpId() {
		return empId;
	}


	public void setEmpId(int empId) {
		this.empId = empId;
	}


	public int getWorkId() {
		return workId;
	}


	public void setWorkId(int workId) {
		this.workId = workId;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public String getInst() {
		return inst;
	}


	public void setInst(String inst) {
		this.inst = inst;
	}


	public String getEmpName() {
		return empName;
	}


	public void setEmpName(String empName) {
		this.empName = empName;
	}


	public String getVisaType() {
		return visaType;
	}


	public void setVisaType(String visaType) {
		this.visaType = visaType;
	}


	public String getVisaIssueDate() {
		return visaIssueDate;
	}


	public void setVisaIssueDate(String visaIssueDate) {
		this.visaIssueDate = visaIssueDate;
	}


	@Override
	public String toString() {
		return "Travel [travelId=" + travelId + ", empId=" + empId + ", workId=" + workId
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", inst=" + inst + ", empName="
				+ empName + ", visaType=" + visaType + ", visaIssueDate=" + visaIssueDate + "]";
	}

}
