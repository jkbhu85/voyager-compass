package com.jk.travel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jk.core.util.DateWrapper;
import com.jk.core.util.LoggerManager;
import com.jk.travel.model.Travel;

public class TravelMasterDAO extends AbstractDAO {

	public boolean insert(Travel travel) {
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

			String sql = "insert into travelmaster values(travel_id_seq.nextval,?,?,?,?,?)";
			String visaSql = "update Emp_Visas set ev_visit_count = ev_visit_count+1 "
					+ "where ppt_ev_fk=? and vt_ev_fk=? and ev_issue_date=?";

			PreparedStatement pstmt = con.prepareStatement(sql);
			int col = 1;
			pstmt.setInt(col++, travel.getEmpId());
			pstmt.setString(col++, DateWrapper.parseDate(travel.getStartDate()));
			pstmt.setString(col++, DateWrapper.parseDate(travel.getEndDate()));
			pstmt.setString(col++, travel.getInst());
			pstmt.setInt(col++, travel.getWorkId());

			boolean status1 = pstmt.executeUpdate() > 0;
			boolean status2 = false;

			if (visaTypeId > 0) {
				PreparedStatement psVisa = con.prepareStatement(visaSql);
				int pptId = new PassportDAO().getPptIdFromEmp(travel.getEmpId());
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
			if (con != null) {
				try {
					con.rollback();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			e.printStackTrace();
			LoggerManager.writeLogWarning(e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return status;
	}


	public int getTravelFromWork(int workId) {
		int travelId = 0;
		Connection con = null;

		try {
			con = getConnection();
			String sql = "select travelid from travelmaster where workid=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, workId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				travelId = rs.getInt(1);
			}
		} catch (Exception e) {
			// e.printStackTrace();
			LoggerManager.writeLogWarning(e);
		}

		return travelId;
	}


	private boolean update(Travel travel) {
		Connection con = null;
		boolean status = false;

		try {
			con = getConnection();

			String sql = "update travelmaster set employeeid=?,travelstartdate=?, "
					+ " travelenddate=?,instructions=? where travelid=?";

			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, travel.getEmpId());
			pstmt.setString(2, travel.getStartDate());
			pstmt.setString(3, travel.getEndDate());
			pstmt.setString(5, travel.getInst());
			pstmt.setInt(6, travel.getTravelId());

			status = pstmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			LoggerManager.writeLogWarning(e);
		} finally {
			try {
				if (con != null) con.close();

			} catch (Exception e) {
			}
		}

		return status;
	}


	public List<Travel> getAll() {
		List<Travel> list = new ArrayList<>();
		Connection con = null;

		try {
			con = getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(
					"SELECT * from travelmaster order by TRAVELSTARTDATE desc");

			while (rs.next()) {
				Travel travel = new Travel();

				travel = new Travel();
				travel.setTravelId(rs.getInt("travelid"));
				travel.setEmpId(rs.getInt("employeeId"));
				travel.setStartDate(DateWrapper.getDateString(rs.getDate("travelstartdate")));
				travel.setEndDate(DateWrapper.getDateString(rs.getDate("travelenddate")));
				travel.setInst(rs.getString("instructions"));
				travel.setWorkId(rs.getInt("workid"));

				list.add(travel);
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


	public Travel get(int travelId) {
		Travel travel = null;
		Connection con = null;

		try {
			con = getConnection();
			String sql = "SELECT * from travelmaster where travelid=?";

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, travelId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				travel = new Travel();
				travel.setTravelId(rs.getInt("travelid"));
				travel.setEmpId(rs.getInt("employeeId"));
				travel.setStartDate(DateWrapper.getDateString(rs.getDate("travelstartdate")));
				travel.setEndDate(DateWrapper.getDateString(rs.getDate("travelenddate")));
				travel.setInst(rs.getString("instructions"));
				travel.setWorkId(rs.getInt("workid"));
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
		return travel;
	}


	public boolean exists(int travelId) {
		boolean result = false;
		Connection con = null;

		try {
			con = getConnection();
			String sql = "SELECT travelid from travelmaster where travelid=?";

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, travelId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				result = true;
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
		return result;
	}
}
