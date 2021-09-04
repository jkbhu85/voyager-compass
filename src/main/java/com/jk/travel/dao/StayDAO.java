package com.jk.travel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jk.core.util.LoggerManager;
import com.jk.travel.model.Stay;
import com.jk.travel.model.Work;

public class StayDAO extends AbstractDAO {

	public boolean addStay(Stay stay) {
		Connection con = null;
		boolean status = false;

		try {
			con = getConnection();
			con.setAutoCommit(false);

			String sql = "INSERT INTO EmployeesStayMaster "
					+ "VALUES(STAY_ID_SEQ.nextval, ?, ?, ?, ?)";
			String workSql = "update works set work_status=? where work_id=?";

			PreparedStatement pstmt = con.prepareStatement(sql);
			int col = 1;
			pstmt.setInt(col++, stay.getTravelId());
			pstmt.setInt(col++, stay.getHotelId());
			pstmt.setString(col++, stay.getRoomNo());
			pstmt.setString(col++, stay.getVehicleNo());

			boolean status1 = pstmt.executeUpdate() > 0;

			PreparedStatement pwork = con.prepareStatement(workSql);
			int workId = new TravelMasterDAO().get(stay.getTravelId()).getWorkId();
			pwork.setInt(1, Work.STATUS_PREPARED);
			pwork.setInt(2, workId);

			boolean status2 = pwork.executeUpdate() > 0;

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
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}

			e.printStackTrace();
			LoggerManager.writeLogSevere(e);
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return status;
	}


	public boolean updateStay(Stay stay) {
		Connection con = null;
		boolean status = false;

		try {
			con = getConnection();
			String sql = "UPDATE EmployeesStayMaster "
					+ "SET travelId=?, hotelId=?, roomno=?, pickupvehicleno=? "
					+ "WHERE stayId=?";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, stay.getTravelId());
			pstmt.setInt(2, stay.getHotelId());
			pstmt.setString(3, stay.getRoomNo());
			pstmt.setString(4, stay.getVehicleNo());
			pstmt.setInt(5, stay.getStayId());

			int count = pstmt.executeUpdate();

			status = count > 0;
		} catch (Exception e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}

			e.printStackTrace();
			LoggerManager.writeLogSevere(e);
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return status;
	}


	public Stay getStay(int stayId) {
		Stay stay = null;
		Connection con = null;

		try {
			con = getConnection();
			String sql = "SELECT * FROM EmployeesStayMaster WHERE stayid=?";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, stayId);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				stay = new Stay();
				stay.setStayId(rs.getInt(1));
				stay.setTravelId(rs.getInt(2));
				stay.setHotelId(rs.getInt(3));
				stay.setRoomNo(rs.getString(4));
				stay.setVehicleNo(rs.getString(5));
			}
		} catch (Exception e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}

			e.printStackTrace();
			LoggerManager.writeLogSevere(e);
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return stay;
	}


	public int getStayIdFromTravel(String travelId) {
		int stayId = 0;
		Connection con = null;

		try {
			con = getConnection();
			String sql = "SELECT stayid FROM EmployeesStayMaster WHERE travelid=?";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, travelId);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				stayId = rs.getInt(1);
			}
		} catch (Exception e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}

			e.printStackTrace();
			LoggerManager.writeLogSevere(e);
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return stayId;
	}
}
