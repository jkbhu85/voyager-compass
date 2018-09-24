
package com.jk.travel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jk.core.util.DateWrapper;
import com.jk.core.util.LoggerManager;
import com.jk.travel.model.TravelTicket;

public class TravelTicketDAO extends AbstractDAO {

	public Connection con;
	private boolean flag = false;


	// insert visatype
	public boolean insertTicket(TravelTicket ticket) {

		PreparedStatement pstmt = null;
		try {
			// int travelTicketID = getSequenceID("ticketsmaster", "ticketid");

			con = getConnection();
			pstmt = con.prepareStatement(
					"insert into ticketsmaster values(TICKET_ID_SEQ.nextval,?,?,?,?,?,?,?,?,?,?,?,?)");
			int col = 1;
			// pstmt.setInt(col++, travelTicketID);
			pstmt.setInt(col++, ticket.getTravelID());
			pstmt.setString(col++, ticket.getTicketNo());
			pstmt.setString(col++, ticket.getTicketBookDate());
			pstmt.setString(col++, ticket.getTicketAvailableDate());
			pstmt.setString(col++, ticket.getTicketFrom());
			pstmt.setString(col++, ticket.getTicketTo());
			pstmt.setString(col++, ticket.getSeatNo());
			pstmt.setString(col++, ticket.getFlightName());
			pstmt.setString(col++, ticket.getTravelAgetName());
			pstmt.setString(col++, ticket.getInchargeContactNo());
			pstmt.setString(col++, ticket.getJourneytime());
			pstmt.setString(col++, ticket.getVehicleNo());

			int i = pstmt.executeUpdate();
			if (i > 0) flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			LoggerManager.writeLogWarning(e);
		} finally {
			try {
				if (con != null) con.close();

			} catch (Exception e) {
			}
		}
		return flag;
	}


	public List<TravelTicket> getTicketList() {
		List<TravelTicket> list = new ArrayList<>();

		try {
			con = getConnection();

			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("SELECT * from ticketsmaster order by AVAILABLEDATE desc");

			while (rs.next()) {
				TravelTicket ticket = new TravelTicket();
				ticket.setEmpTicketID(rs.getInt(1));
				ticket.setTravelID(rs.getInt(2));
				ticket.setTicketNo(rs.getString(3));
				ticket.setTicketBookDate(DateWrapper.parseDate(rs.getDate(4)));
				ticket.setTicketAvailableDate(DateWrapper.parseDate(rs.getDate(5)));
				ticket.setTicketFrom(rs.getString(6));
				ticket.setTicketTo(rs.getString(7));
				ticket.setSeatNo(rs.getString(8));
				ticket.setFlightName(rs.getString(9));
				ticket.setTravelAgetName(rs.getString(10));
				ticket.setInchargeContactNo(rs.getString(11));
				ticket.setJourneytime(rs.getString(12));
				ticket.setVehicleNo(rs.getString(13));

				list.add(ticket);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LoggerManager.writeLogWarning(e);
		} finally {
			try {
				if (con != null) con.close();

			} catch (Exception e) {
			}
		}
		return list;
	}


	public TravelTicket getTicket(String ticketId) {
		TravelTicket ticket = null;

		try {

			con = getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(
					"SELECT * from ticketsmaster where TICKETID='" + ticketId + "'");

			while (rs.next()) {
				ticket = new TravelTicket();
				ticket.setEmpTicketID(rs.getInt(1));
				ticket.setTravelID(rs.getInt(2));
				ticket.setTicketNo(rs.getString(3));
				ticket.setTicketBookDate(DateWrapper.parseDate(rs.getDate(4)));
				ticket.setTicketAvailableDate(DateWrapper.parseDate(rs.getDate(5)));
				ticket.setTicketFrom(rs.getString(6));
				ticket.setTicketTo(rs.getString(7));
				ticket.setSeatNo(rs.getString(8));
				ticket.setFlightName(rs.getString(9));
				ticket.setTravelAgetName(rs.getString(10));
				ticket.setInchargeContactNo(rs.getString(11));
				ticket.setJourneytime(rs.getString(12));
				ticket.setVehicleNo(rs.getString(13));
			}
		} catch (Exception e) {
			e.printStackTrace();
			LoggerManager.writeLogWarning(e);
		} finally {
			try {
				if (con != null) con.close();

			} catch (Exception e) {
			}
		}
		return ticket;
	}


	public int getTicketIdFromTravel(int travelId) {
		int ticketId = 0;

		try {
			con = getConnection();
			String sql = "select TICKETID from ticketsmaster where travelid='" + travelId + "'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);

			if (rs.next()) {
				ticketId = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LoggerManager.writeLogWarning(e);
		}

		return ticketId;
	}

}
