package com.jk.vc.model;

public class TravelTicket {

	private int empTicketID;
	private int travelID;
	private String ticketNo;
	private String ticketBookDate;
	private String ticketAvailableDate;
	private String ticketFrom;
	private String ticketTo;
	private String seatNo;
	private String flightName;
	private String InchargeContactNo;

	private String journeytime;
	private String vehicleNo;

	private int empID;
	private int trainbit;
	private String travelAgetName;
	private String personImage;
	private String ticketAvailableTo;


	public int getEmpTicketID() {
		return empTicketID;
	}


	public void setEmpTicketID(int empTicketID) {
		this.empTicketID = empTicketID;
	}


	public int getTravelID() {
		return travelID;
	}


	public void setTravelID(int travelID) {
		this.travelID = travelID;
	}


	public String getTicketNo() {
		return ticketNo;
	}


	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}


	public String getTicketBookDate() {
		return ticketBookDate;
	}


	public void setTicketBookDate(String ticketBookDate) {
		this.ticketBookDate = ticketBookDate;
	}


	public String getTicketAvailableDate() {
		return ticketAvailableDate;
	}


	public void setTicketAvailableDate(String ticketAvailableDate) {
		this.ticketAvailableDate = ticketAvailableDate;
	}


	public String getTicketFrom() {
		return ticketFrom;
	}


	public void setTicketFrom(String ticketFrom) {
		this.ticketFrom = ticketFrom;
	}


	public String getTicketTo() {
		return ticketTo;
	}


	public void setTicketTo(String ticketTo) {
		this.ticketTo = ticketTo;
	}


	public String getSeatNo() {
		return seatNo;
	}


	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}


	public String getFlightName() {
		return flightName;
	}


	public void setFlightName(String flightName) {
		this.flightName = flightName;
	}


	public String getInchargeContactNo() {
		return InchargeContactNo;
	}


	public void setInchargeContactNo(String inchargeContactNo) {
		InchargeContactNo = inchargeContactNo;
	}


	public String getJourneytime() {
		return journeytime;
	}


	public void setJourneytime(String journeytime) {
		this.journeytime = journeytime;
	}


	public String getVehicleNo() {
		return vehicleNo;
	}


	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}


	@Deprecated
	public int getEmpID() {
		return empID;
	}


	@Deprecated
	public void setEmpID(int empID) {
		this.empID = empID;
	}


	@Deprecated
	public int getTrainbit() {
		return trainbit;
	}


	@Deprecated
	public void setTrainbit(int trainbit) {
		this.trainbit = trainbit;
	}


	public String getTravelAgetName() {
		return travelAgetName;
	}


	public void setTravelAgetName(String travelAgetName) {
		this.travelAgetName = travelAgetName;
	}


	@Deprecated
	public String getPersonImage() {
		return personImage;
	}


	@Deprecated
	public void setPersonImage(String personImage) {
		this.personImage = personImage;
	}


	@Deprecated
	public String getTicketAvailableTo() {
		return ticketAvailableTo;
	}


	@Deprecated
	public void setTicketAvailableTo(String ticketAvailableTo) {
		this.ticketAvailableTo = ticketAvailableTo;
	}

}
