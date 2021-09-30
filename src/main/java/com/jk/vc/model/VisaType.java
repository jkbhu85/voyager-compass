package com.jk.vc.model;

public class VisaType {
	private int visaTypeID;
	private String visaTypeName;
	private String visaTypeDesc;
	private String visaTypeAbbr;
	private String visaEleg; // eligibility
	private int cntVtFk; // foreignkey
	private int maxDuration;
	private String stampGuide;
	private int reqAdv;


	public int getReqAdv() {
		return reqAdv;
	}


	public void setReqAdv(int reqAdv) {
		this.reqAdv = reqAdv;
	}


	public String getVisaEleg() {
		return visaEleg;
	}


	public void setVisaEleg(String visaEleg) {
		this.visaEleg = visaEleg;
	}


	public int getCntVtFk() {
		return cntVtFk;
	}


	public void setCntVtFk(int cntVtFk) {
		this.cntVtFk = cntVtFk;
	}


	public int getMaxDuration() {
		return maxDuration;
	}


	public void setMaxDuration(int maxDuration) {
		this.maxDuration = maxDuration;
	}


	public String getStampGuide() {
		return stampGuide;
	}


	public void setStampGuide(String stampGuide) {
		this.stampGuide = stampGuide;
	}


	public int getVisaTypeID() {
		return visaTypeID;
	}


	public void setVisaTypeID(int visaTypeID) {
		this.visaTypeID = visaTypeID;
	}


	public String getVisaTypeName() {
		return visaTypeName;
	}


	public void setVisaTypeName(String visaTypeName) {
		this.visaTypeName = visaTypeName;
	}


	public String getVisaTypeDesc() {
		return visaTypeDesc;
	}


	public void setVisaTypeDesc(String visaTypeDesc) {
		this.visaTypeDesc = visaTypeDesc;
	}


	public String getVisaTypeAbbr() {
		return visaTypeAbbr;
	}


	public void setVisaTypeAbbr(String visaTypeAbbr) {
		this.visaTypeAbbr = visaTypeAbbr;
	}


	/*
	 * {
	 * id:'',
	 * name:'',
	 * abbr:'',
	 * }
	 */

	public StringBuilder jsonValue() {
		final StringBuilder sb = new StringBuilder();

		sb.append("{");
		sb.append("\"id\":").append("\"").append(visaTypeID).append("\",");
		sb.append("\"name\":").append("\"").append(visaTypeName).append("\",");
		sb.append("\"abbr\":").append("\"").append(visaTypeAbbr).append("\"");
		sb.append("}");

		return sb;
	}
}
