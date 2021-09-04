package com.jk.travel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jk.core.util.DateWrapper;
import com.jk.core.util.LoggerManager;
import com.jk.travel.model.Passport;

public class PassportDAO extends AbstractDAO {

	public boolean addPassport(Passport ppt) {
		Connection con = null;
		Boolean status = false;

		try {
			// int pptId = getSequenceID("Passports", "ppt_id");
			// if (pptId == 0) pptId = 1;

			con = getConnection();

			String sql = "INSERT INTO Passports VALUES(PPT_ID_SEQ.nextval,?,?,?,?,?,?,?,?,?,?)";

			PreparedStatement insertPpt = con.prepareStatement(sql);

			int col = 1;
			// insertPpt.setInt(col++, ppt.getPptId());
			insertPpt.setInt(col++, ppt.getEmpId());
			insertPpt.setInt(col++, ppt.getCntId());
			insertPpt.setString(col++, ppt.getPptType());
			insertPpt.setString(col++, ppt.getPptNo());
			insertPpt.setString(col++, ppt.getBirthPlace());
			insertPpt.setString(col++, DateWrapper.parseDate(ppt.getIssueDate()));
			insertPpt.setString(col++, DateWrapper.parseDate(ppt.getExpiryDate()));
			insertPpt.setString(col++, ppt.getPlaceIssued());
			insertPpt.setString(col++, ppt.getAddress());
			insertPpt.setString(col++, ppt.getComments());

			status = insertPpt.executeUpdate() > 0;
		} catch (Exception e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}

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


	public boolean updatePassport(Passport ppt) {
		Connection con = null;
		Boolean status = false;

		try {
			con = getConnection();

			String sql = "UPDATE Passports SET cnt_ppt_fk=?, ppt_type=?, ppt_number=?, ppt_birth_place=?, "
					+ " ppt_issue_date=?, ppt_expiry_date=?, ppt_issue_place=?, "
					+ " ppt_address=?, ppt_comments=? WHERE ppt_id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setInt(10, ppt.getPptId());
			pstmt.setInt(1, ppt.getCntId());
			pstmt.setString(2, ppt.getPptType());
			pstmt.setString(3, ppt.getPptNo());
			pstmt.setString(4, ppt.getBirthPlace());
			pstmt.setString(5, DateWrapper.parseDate(ppt.getIssueDate()));
			pstmt.setString(6, DateWrapper.parseDate(ppt.getExpiryDate()));
			pstmt.setString(7, ppt.getPlaceIssued());
			pstmt.setString(8, ppt.getAddress());
			pstmt.setString(9, ppt.getComments());
			status = true;

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


	public Passport getPassport(int pptId) {
		Connection con = null;
		Passport ppt = null;

		try {
			con = getConnection();
			String sql = "SELECT * FROM Passports where ppt_id=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, pptId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				ppt = new Passport();

				ppt.setPptId(rs.getInt(1));
				ppt.setEmpId(rs.getInt(2));
				ppt.setCntId(rs.getInt(3));
				ppt.setPptType(rs.getString(4));
				ppt.setPptNo(rs.getString(5));
				ppt.setBirthPlace(rs.getString(6));
				ppt.setIssueDate(DateWrapper.getDateString(rs.getDate(7)));
				ppt.setExpiryDate(DateWrapper.getDateString(rs.getDate(8)));
				ppt.setPlaceIssued(rs.getString(9));
				ppt.setAddress(rs.getString(10));
				ppt.setComments(rs.getString(11));
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

		return ppt;
	}


	public boolean passportExists(int pptId) {
		Connection con = null;
		Boolean status = false;

		try {
			con = getConnection();
			String sql = "SELECT ppt_id FROM Passports where ppt_id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, pptId);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				status = true;
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

		return status;
	}


	public int getPptIdFromEmp(int empId) {
		Connection con = null;
		int pptId = 0;

		try {
			con = getConnection();
			String sql = "SELECT ppt_id FROM Passports where emp_ppt_fk=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, empId);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				pptId = rs.getInt(1);
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

		return pptId;

	}
}
