package com.jk.vc.model;

public class Work {
	public static final int STATUS_UNPREPARED = 1;
	public static final int STATUS_PREPARED = 2;
	public static final int STATUS_CANCELLED = 3;
	public static final int STATUS_PARTIALLY_SUCCESSFUL = 4;
	public static final int STATUS_SUCCESSFUL = 5;
	public static final int STATUS_FAILED = 6;

	private static final String[] STATUS_MSG = { "Unprepared", "Prepared", "Canecelled", "Partially Successful", "Successful", "Failed" };

	private int workId;
	private int cntId;
	private int inchId;
	private String title;

	private String respb; // responsibilities
	private String description;
	private int statusId;
	private String status;

	private String inchName;
	private String cntName; // country name


	public int getWorkId() {
		return workId;
	}


	public void setWorkId(int workId) {
		this.workId = workId;
	}


	public int getCntId() {
		return cntId;
	}


	public void setCntId(int cntId) {
		this.cntId = cntId;
	}


	public int getInchId() {
		return inchId;
	}


	public void setInchId(int inchId) {
		this.inchId = inchId;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getRespb() {
		return respb;
	}


	public void setRespb(String respb) {
		this.respb = respb;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getStatusId() {
		return statusId;
	}


	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}


	public String getStatus() {
		return STATUS_MSG[statusId - 1];
	}


	public String getInchName() {
		return inchName;
	}


	public void setInchName(String inchName) {
		this.inchName = inchName;
	}


	public String getCntName() {
		return cntName;
	}


	public void setCntName(String cntName) {
		this.cntName = cntName;
	}


	@Override
	public String toString() {
		return "Work [workId=" + workId + ", title=" + title + ", respb=" + respb + ", description="
				+ description + ", status=" + status + ", inchName=" + inchName + ", cntName="
				+ cntName + "]";
	}

}
