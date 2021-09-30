package com.jk.vc.dao;

import static com.jk.vc.dao.ConnectionUtils.*;

import java.sql.*;

import com.jk.vc.exception.*;
import com.jk.vc.model.*;
import com.jk.vc.util.*;

public class PassportDao {
	
	private static final String SQL_INSERT_PASSPORT = ""
			+ "INSERT INTO PASSPORTS VALUES(?,?,?,?,?,?,?,?,?,?)";

	public boolean insertPassport(Passport ppt) {
		Connection con = null;
		boolean status = false;

		try {
			con = getConnection();
			PreparedStatement insertPpt = con.prepareStatement(SQL_INSERT_PASSPORT);

			int col = 1;
			insertPpt.setInt(col++, ppt.getEmpId());
			insertPpt.setInt(col++, ppt.getCntId());
			insertPpt.setString(col++, ppt.getPptType());
			insertPpt.setString(col++, ppt.getPptNo());
			insertPpt.setString(col++, ppt.getBirthPlace());
			insertPpt.setString(col++, DateUtils.convertDate(ppt.getIssueDate()));
			insertPpt.setString(col++, DateUtils.convertDate(ppt.getExpiryDate()));
			insertPpt.setString(col++, ppt.getPlaceIssued());
			insertPpt.setString(col++, ppt.getAddress());
			insertPpt.setString(col++, ppt.getComments());

			status = insertPpt.executeUpdate() > 0;
		} catch (SQLException e) {
			DaoUtils.rollback(con);
			throw new VcDataAccessException(e);
		} finally {
			DaoUtils.closeCon(con);
		}

		return status;
	}

	private static final String SQL_UPDATE_PASSPORT = ""
			+ " UPDATE PASSPORTS "
			+ " SET CNT_PPT_FK=?, PPT_TYPE=?, PPT_NUMBER=?, PPT_BIRTH_PLACE=?, "
			+ " PPT_ISSUE_DATE=?, PPT_EXPIRY_DATE=?, PPT_ISSUE_PLACE=?, "
			+ " PPT_ADDRESS=?, PPT_COMMENTS=? WHERE PPT_ID=?";

	public boolean updatePassport(Passport ppt) {
		Connection con = null;
		boolean status = false;

		try {
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_PASSPORT);

			pstmt.setInt(10, ppt.getPptId());
			pstmt.setInt(1, ppt.getCntId());
			pstmt.setString(2, ppt.getPptType());
			pstmt.setString(3, ppt.getPptNo());
			pstmt.setString(4, ppt.getBirthPlace());
			pstmt.setString(5, DateUtils.convertDate(ppt.getIssueDate()));
			pstmt.setString(6, DateUtils.convertDate(ppt.getExpiryDate()));
			pstmt.setString(7, ppt.getPlaceIssued());
			pstmt.setString(8, ppt.getAddress());
			pstmt.setString(9, ppt.getComments());
			status = true;
		} catch (SQLException e) {
			throw new VcDataAccessException(e);
		} finally {
			DaoUtils.closeCon(con);
		}

		return status;
	}


	private static final String SQL_FIND_PASSPORT_BY_ID = ""
			+ "SELECT * FROM PASSPORTS WHERE PPT_ID=?";

	public Passport findPassportById(int pptId) {
		Connection con = null;

		try {
			con = getConnection();
			PreparedStatement ps = con.prepareStatement(SQL_FIND_PASSPORT_BY_ID);
			ps.setInt(1, pptId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				Passport ppt = new Passport();

				ppt.setPptId(rs.getInt(1));
				ppt.setEmpId(rs.getInt(2));
				ppt.setCntId(rs.getInt(3));
				ppt.setPptType(rs.getString(4));
				ppt.setPptNo(rs.getString(5));
				ppt.setBirthPlace(rs.getString(6));
				ppt.setIssueDate(DateUtils.getDateString(rs.getDate(7)));
				ppt.setExpiryDate(DateUtils.getDateString(rs.getDate(8)));
				ppt.setPlaceIssued(rs.getString(9));
				ppt.setAddress(rs.getString(10));
				ppt.setComments(rs.getString(11));
			}
		} catch (SQLException e) {
			throw new VcDataAccessException(e);
		} finally {
			DaoUtils.closeCon(con);
		}

		return null;
	}


	private static final String SQL_DOES_PASSPORT_EXIST_BY_ID = ""
			+ "SELECT PPT_ID FROM PASSPORTS WHERE PPT_ID=?";

	public boolean doesPassportExists(int pptId) {
		Connection con = null;
		boolean status = false;

		try {
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(SQL_DOES_PASSPORT_EXIST_BY_ID);
			pstmt.setInt(1, pptId);

			ResultSet rs = pstmt.executeQuery();
			status = rs.next();
		} catch (SQLException e) {
			throw new VcDataAccessException(e);
		} finally {
			DaoUtils.closeCon(con);
		}

		return status;
	}


	private static final String SQL_FIND_PASSPORT_ID_BY_EMP_ID = ""
			+ "SELECT PPT_ID FROM PASSPORTS WHERE EMP_PPT_FK=?";

	public int findPassportIdByEmployeeId(int empId) {
		Connection con = null;

		try {
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(SQL_FIND_PASSPORT_ID_BY_EMP_ID);
			pstmt.setInt(1, empId);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new VcDataAccessException(e);
		} finally {
			DaoUtils.closeCon(con);
		}

		return 0;
	}
}
