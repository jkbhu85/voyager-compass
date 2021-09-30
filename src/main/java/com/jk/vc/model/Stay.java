package com.jk.vc.model;

public class Stay {
	private int stayId;
	private int travelId;
	private int hotelId;
	private String roomNo;

	/**
	 * Pickup vehicle number.
	 */
	private String vehicleNo;

	public int getStayId() {
		return stayId;
	}

	public void setStayId(int stayId) {
		this.stayId = stayId;
	}

	public int getTravelId() {
		return travelId;
	}

	public void setTravelId(int travelId) {
		this.travelId = travelId;
	}

	public int getHotelId() {
		return hotelId;
	}

	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	@Override
	public String toString() {
		return "Stay [stayId=" + stayId + ", travelId=" + travelId + ", hotelId=" + hotelId + ", roomNo=" + roomNo
				+ ", vehicleNo=" + vehicleNo + "]";
	}

}
