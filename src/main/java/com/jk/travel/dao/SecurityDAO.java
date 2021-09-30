package com.jk.travel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jk.core.util.LoggerManager;
import com.jk.travel.model.Profile;

public class SecurityDAO extends AbstractDAO {

	private static final String SQL_FIND_USER_ROLE_BY_LOGIN_ID_AND_PASSWORD = ""
			+ "SELECT TYPE FROM EMPLOYEEMASTER WHERE LOGINID=? AND PASSWORD=?";
	
	public String loginCheck(Profile regbean) {
		Connection con = null;

		try {
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(SQL_FIND_USER_ROLE_BY_LOGIN_ID_AND_PASSWORD);
			pstmt.setString(1, regbean.getLoginID());
			pstmt.setString(2, regbean.getPassword());

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}

		} catch (SQLException ex) {
			LoggerManager.writeLogSevere(ex);
		} finally {
			DaoUtils.closeCon(con);
		}

		return "";
	}

	
	private static final String SQL_CHANGE_PASSWORD = ""
			+ "UPDATE EMPLOYEEMASTER SET PASSWORD=? WHERE LOGINID=? AND PASSWORD=?";

	public boolean updatePassword(String loginId, String curPwd, String newPwd) {
		Connection con = null;
		boolean flag = false;
		try {
			con = getConnection();
			con.setAutoCommit(false);

			PreparedStatement pstmt = con.prepareStatement(SQL_CHANGE_PASSWORD);

			pstmt.setString(1, newPwd);
			pstmt.setString(2, loginId);
			pstmt.setString(3, curPwd);

			flag = pstmt.executeUpdate() > 0;

			if (flag) {
				con.commit();
			}
			else {
				con.rollback();
			}
		} catch (Exception e) {
			DaoUtils.rollback(con);
			LoggerManager.writeLogSevere(e);
		} finally {
			DaoUtils.closeCon(con);
		}
		return flag;
	}

	private static final String SQL_UPDATE_SECRET_QUESTION = ""
			+ "UPDATE EMPLOYEEMASTER SET S_QUES=?,S_ANS=? WHERE LOGINID=? AND PASSWORD=?";

	public boolean updateSecretQuestion(String loginId, String password, String secQues, String secAns) {
		Connection con = null;
		boolean flag = false;
		try {
			con = getConnection();
			con.setAutoCommit(false);
			PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_SECRET_QUESTION);

			pstmt.setString(1, secQues);
			pstmt.setString(2, secAns);
			pstmt.setString(3, loginId);
			pstmt.setString(4, password);

			flag = pstmt.executeUpdate() > 0;

			if (flag) {
				con.commit();
			}
			else {
				con.rollback();
			}

		} catch (Exception e) {
			DaoUtils.rollback(con);
			LoggerManager.writeLogSevere(e);
		} finally {
			DaoUtils.closeCon(con);
		}

		return flag;
	}

	private static final String SQL_FIND_USER_SECRETS_BY_LOGIN_ID = ""
			+ "SELECT PASSWORD,S_QUES,S_ANS FROM EMPLOYEEMASTER WHERE LOGINID=?";

	public String recoverPasswordByQuestion(String loginId, String secQues, String secAns) {
		Connection con = null;

		try {
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(SQL_FIND_USER_SECRETS_BY_LOGIN_ID);

			pstmt.setString(1, loginId);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				if (rs.getString(2).equals(secQues) && rs.getString(3).equals(secAns)) {
					return rs.getString(1);
				}
			}
		} catch (Exception e) {
			LoggerManager.writeLogSevere(e);
		} finally {
			DaoUtils.closeCon(con);
		}

		return null;
	}

	private static final String SQL_DOES_LOGIN_ID_EXISTS = ""
			+ "SELECT LOGINID FROM EMPLOYEEMASTER WHERE LOGINID=?";

	public String doesLoginIdExist(String userName) {
		Connection con = null;
		try {
			con = getConnection();

			PreparedStatement ps = con.prepareStatement(SQL_DOES_LOGIN_ID_EXISTS);
			ps.setString(1, userName);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				System.out.println("loginid alredy exist" + userName);
				return rs.getString(1);
			}
		} catch (Exception e) {
			LoggerManager.writeLogSevere(e);
		} finally {
			DaoUtils.closeCon(con);
		}

		return null;
	}

}
