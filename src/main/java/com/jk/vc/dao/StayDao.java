package com.jk.vc.dao;

import static com.jk.vc.dao.ConnectionUtils.*;

import java.sql.*;

import com.jk.vc.exception.*;
import com.jk.vc.model.*;

public class StayDao {
	
	private static final String SQL_INSERT_STAY = ""
			+ " INSERT INTO EMPLOYEESSTAYMASTER "
			+ " VALUES(?, ?, ?, ?)";
	private static final String SQL_UPDATE_WORK_STATUS = ""
			+ "UPDATE WORKS SET WORK_STATUS=? WHERE WORK_ID=?";

	public boolean saveStay(Stay stay) {
		Connection con = null;
		boolean status = false;

		try {
			con = getConnection();
			con.setAutoCommit(false);

			PreparedStatement pstmt = con.prepareStatement(SQL_INSERT_STAY);
			int col = 1;
			pstmt.setInt(col++, stay.getTravelId());
			pstmt.setInt(col++, stay.getHotelId());
			pstmt.setString(col++, stay.getRoomNo());
			pstmt.setString(col++, stay.getVehicleNo());

			boolean status1 = pstmt.executeUpdate() > 0;

			PreparedStatement pwork = con.prepareStatement(SQL_UPDATE_WORK_STATUS);
			int workId = new TravelMasterDao().findTravelById(stay.getTravelId()).getWorkId();
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

		} catch (SQLException e) {
			DaoUtils.rollback(con);
			throw new VcDataAccessException(e);
		} finally {
			DaoUtils.closeCon(con);
		}

		return status;
	}


	private static final String SQL_UPDATE_STAY = ""
			+ " UPDATE EMPLOYEESSTAYMASTER "
			+ " SET TRAVELID=?, HOTELID=?, ROOMNO=?, PICKUPVEHICLENO=? "
			+ " WHERE STAYID=?";

	public boolean updateStay(Stay stay) {
		Connection con = null;
		boolean status = false;

		try {
			con = getConnection();

			PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_STAY);
			pstmt.setInt(1, stay.getTravelId());
			pstmt.setInt(2, stay.getHotelId());
			pstmt.setString(3, stay.getRoomNo());
			pstmt.setString(4, stay.getVehicleNo());
			pstmt.setInt(5, stay.getStayId());

			int count = pstmt.executeUpdate();

			status = count > 0;
		} catch (SQLException e) {
			DaoUtils.rollback(con);
			throw new VcDataAccessException(e);
		} finally {
			DaoUtils.closeCon(con);
		}

		return status;
	}


	private static final String SQL_FIND_STATE_BY_ID = ""
			+ "SELECT * FROM EMPLOYEESSTAYMASTER WHERE STAYID=?";

	public Stay findStayById(int stayId) {
		Stay stay = null;
		Connection con = null;

		try {
			con = getConnection();

			PreparedStatement pstmt = con.prepareStatement(SQL_FIND_STATE_BY_ID);
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
		} catch (SQLException e) {
			DaoUtils.rollback(con);
			throw new VcDataAccessException(e);
		} finally {
			DaoUtils.closeCon(con);
		}

		return stay;
	}

	private static final String SQL_FIND_STAY_ID_BY_TRAVEL_ID = ""
			+ "SELECT STAYID FROM EMPLOYEESSTAYMASTER WHERE TRAVELID=?";

	public int getStayIdFromTravel(String travelId) {
		Connection con = null;

		try {
			con = getConnection();

			PreparedStatement pstmt = con.prepareStatement(SQL_FIND_STAY_ID_BY_TRAVEL_ID);
			pstmt.setString(1, travelId);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			DaoUtils.rollback(con);
			throw new VcDataAccessException(e);
		} finally {
			DaoUtils.closeCon(con);
		}

		return 0;
	}
}
