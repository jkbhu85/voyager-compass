package com.jk.vc.model;

import javax.servlet.http.Part;

public class ProfileData {
	private String email;
	private String mobile;
	private Part imgPart;
	private String loginId;
	private Address[] list;


	public ProfileData() {
	}

	public static class Address {
		private String line1;
		private String line2;
		private String city;
		private String state;
		private String country;
		private String pin;


		public String getLine1() {
			return line1;
		}


		public void setLine1(String line1) {
			this.line1 = line1;
		}


		public String getLine2() {
			return line2;
		}


		public void setLine2(String line2) {
			this.line2 = line2;
		}


		public String getCity() {
			return city;
		}


		public void setCity(String city) {
			this.city = city;
		}


		public String getState() {
			return state;
		}


		public void setState(String state) {
			this.state = state;
		}


		public String getCountry() {
			return country;
		}


		public void setCountry(String country) {
			this.country = country;
		}


		public String getPin() {
			return pin;
		}


		public void setPin(String pin) {
			this.pin = pin;
		}


		@Override
		public String toString() {
			return "Address [line1=" + line1 + ", line2=" + line2 + ", city=" + city + ", state=" + state
					+ ", country=" + country + ", pin=" + pin + "]";
		}

	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public Part getImgPart() {
		return imgPart;
	}


	public void setImgPart(Part imgPart) {
		this.imgPart = imgPart;
	}


	public String getLoginId() {
		return loginId;
	}


	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}


	public Address[] getList() {
		return list;
	}


	public void setList(Address[] list) {
		this.list = list;
	}

}
