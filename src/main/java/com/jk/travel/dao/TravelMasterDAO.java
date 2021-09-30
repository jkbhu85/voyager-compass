package com.jk.travel.dao;

import java.sql.*;
import java.util.*;

import com.jk.core.util.*;
import com.jk.travel.model.*;

public class TravelMasterDAO extends AbstractDAO {

	private static final String SQL_INSERT_TRAVEL = ""
			+ "INSERT INTO TRAVELMASTER VALUES(TRAVEL_ID_SEQ.NEXTVAL,?,?,?,?,?)";
	private static final String SQL_UPDATE_VISA = ""
			+ "UPDATE EMP_VISAS SET EV_VISIT_COUNT = EV_VISIT_COUNT+1 "
			+ "WHERE PPT_EV_FK=? AND VT_EV_FK=? AND EV_ISSUE_DATE=?";
	
	public boolean saveTravel(Travel travel) {
		boolean status = false;
		Connection con = null;

		try {
			con = getConnection();
			con.setAutoCommit(false);

			int visaTypeId = 0;

			try {
				visaTypeId = Integer.parseInt(travel.getVisaType());
			} catch (NumberFormatException ignore) {

			}


			PreparedStatement pstmt = con.prepareStatement(SQL_INSERT_TRAVEL);
			int col = 1;
			pstmt.setInt(col++, travel.getEmpId());
			pstmt.setString(col++, DateWrapper.parseDate(travel.getStartDate()));
			pstmt.setString(col++, DateWrapper.parseDate(travel.getEndDate()));
			pstmt.setString(col++, travel.getInst());
			pstmt.setInt(col++, travel.getWorkId());

			boolean status1 = pstmt.executeUpdate() > 0;
			boolean status2 = false;

			if (visaTypeId > 0) {
				PreparedStatement psVisa = con.prepareStatement(SQL_UPDATE_VISA);
				int pptId = new PassportDAO().findPassportIdByEmployeeId(travel.getEmpId());
				psVisa.setInt(1, pptId);
				psVisa.setInt(2, visaTypeId);
				psVisa.setString(3, travel.getVisaIssueDate());

				status2 = psVisa.executeUpdate() > 0;
			}
			else {
				status2 = true;
			}

			status = status1 && status2;

			if (status) {
				con.commit();
			}
			else {
				con.rollback();
			}

		} catch (Exception e) {
			DaoUtils.rollback(con);
			LoggerManager.writeLogWarning(e);
		} finally {
			DaoUtils.closeCon(con);
		}
		return status;
	}

	
	private static final String SQL_FIND_TRAVEL_ID_BY_WORK_ID = ""
			+ "SELECT TRAVELID FROM TRAVELMASTER WHERE WORKID=?";

	public int findTravelIdByWorkId(int workId) {
		Connection con = null;

		try {
			con = getConnection();
			PreparedStatement ps = con.prepareStatement(SQL_FIND_TRAVEL_ID_BY_WORK_ID);
			ps.setInt(1, workId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			LoggerManager.writeLogWarning(e);
		}

		return 0;
	}

	private static final String SQL_FIND_ALL_TRAVELS = ""
			+ "SELECT * FROM TRAVELMASTER ORDER BY TRAVELSTARTDATE DESC";
	
	public List<Travel> findAllTravels() {
		List<Travel> list = new ArrayList<>();
		Connection con = null;

		try {
			con = getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(SQL_FIND_ALL_TRAVELS);

			while (rs.next()) {
				Travel travel = new Travel();

				travel = new Travel();
				travel.setTravelId(rs.getInt("TRAVELID"));
				travel.setEmpId(rs.getInt("EMPLOYEEID"));
				travel.setStartDate(DateWrapper.getDateString(rs.getDate("TRAVELSTARTDATE")));
				travel.setEndDate(DateWrapper.getDateString(rs.getDate("TRAVELENDDATE")));
				travel.setInst(rs.getString("INSTRUCTIONS"));
				travel.setWorkId(rs.getInt("WORKID"));

				list.add(travel);
			}
		} catch (Exception e) {
			LoggerManager.writeLogWarning(e);
		} finally {
			DaoUtils.closeCon(con);
		}
		return list;
	}

	private static final String SQL_FIND_TRAVEL_BY_ID = ""
			+ "SELECT * FROM TRAVELMASTER WHERE TRAVELID=?";

	public Travel findTravelById(int travelId) {
		Travel travel = null;
		Connection con = null;

		try {
			con = getConnection();

			PreparedStatement ps = con.prepareStatement(SQL_FIND_TRAVEL_BY_ID);
			ps.setInt(1, travelId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				travel = new Travel();
				travel.setTravelId(rs.getInt("TRAVELID"));
				travel.setEmpId(rs.getInt("EMPLOYEEID"));
				travel.setStartDate(DateWrapper.getDateString(rs.getDate("TRAVELSTARTDATE")));
				travel.setEndDate(DateWrapper.getDateString(rs.getDate("TRAVELENDDATE")));
				travel.setInst(rs.getString("INSTRUCTIONS"));
				travel.setWorkId(rs.getInt("WORKID"));
			}
		} catch (Exception e) {
			LoggerManager.writeLogWarning(e);
		} finally {
			DaoUtils.closeCon(con);
		}
		return travel;
	}


	private static final String sql = ""
			+ "SELECT TRAVELID FROM TRAVELMASTER WHERE TRAVELID=?";

	public boolean doesTravelExistById(int travelId) {
		Connection con = null;

		try {
			con = getConnection();

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, travelId);

			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (Exception e) {
			LoggerManager.writeLogWarning(e);
		} finally {
			DaoUtils.closeCon(con);
		}
		return false;
	}
}
