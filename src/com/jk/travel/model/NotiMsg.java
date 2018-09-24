package com.jk.travel.model;

public class NotiMsg {
	public static final int OK = 1;
	public static final int FAIL = 2;

	private int status;
	private String msg;

	public NotiMsg() {
	}

	public NotiMsg(int status, String msg) {
		this.status = status;
		this.msg = msg;
	}

	public boolean isOk() {
		return status == OK;
	}

	public boolean isFail() {
		return status == FAIL;
	}

	public String getHtmlMsg() {
		if (status == OK) {
			return "<div class=\"noti-ok\">" + msg + "</div>";
		} else {
			return "<div class=\"noti-fail\">" + msg + "</div>";
		}
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setOk(String msg) {
		this.status = OK;
		this.msg = msg;
	}

	public void setFail(String msg) {
		this.status = FAIL;
		this.msg = msg;
	}
}
