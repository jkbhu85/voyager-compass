
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

	private static final String SQL_INSERT_TICKET = ""
			+ "INSERT INTO TICKETSMASTER VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

	public boolean insertTicket(TravelTicket ticket) {
		Connection con = null;
		try {
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(SQL_INSERT_TICKET);
			int col = 1;
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

			int rowsUpdated = pstmt.executeUpdate();
			return (rowsUpdated > 0);
		} catch (Exception e) {
			e.printStackTrace();
			LoggerManager.writeLogWarning(e);
		} finally {
			DaoUtils.closeCon(con);
		}
		return false;
	}

	private static final String SQL_FIND_ALL_TICKETS = ""
			+ "SELECT * FROM TICKETSMASTER ORDER BY AVAILABLEDATE DESC";

	public List<TravelTicket> findAllTickets() {
		List<TravelTicket> list = new ArrayList<>();
		Connection con = null;

		try {
			con = getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(SQL_FIND_ALL_TICKETS);

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
			LoggerManager.writeLogWarning(e);
		} finally {
			DaoUtils.closeCon(con);
		}
		return list;
	}

	private static final String SQL_FIND_TICKET_BY_ID = ""
			+ "SELECT * FROM TICKETSMASTER WHERE TICKETID=?";

	public TravelTicket findTicketById(String ticketId) {
		Connection con = null;

		try {
			con = getConnection();
			PreparedStatement ps = con.prepareStatement(SQL_FIND_TICKET_BY_ID);
			ps.setString(1, ticketId);
			ResultSet rs = ps.executeQuery();

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
			}
		} catch (Exception e) {
			LoggerManager.writeLogWarning(e);
		} finally {
			DaoUtils.closeCon(con);
		}
		return null;
	}

	private static final String SQL_FIND_TICKET_ID_BY_TRAVEL_ID = ""
			+ "SELECT TICKETID FROM TICKETSMASTER WHERE TRAVELID=?";

	public int getTicketIdFromTravel(int travelId) {
		Connection con = null;

		try {
			con = getConnection();
			PreparedStatement ps = con.prepareStatement(SQL_FIND_TICKET_ID_BY_TRAVEL_ID);
			ps.setInt(1, travelId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			LoggerManager.writeLogWarning(e);
		} finally {
			DaoUtils.closeCon(con);
		}

		return 0;
	}

}
