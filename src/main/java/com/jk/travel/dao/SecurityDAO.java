package com.jk.travel.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jk.core.util.LoggerManager;
import com.jk.travel.model.Profile;

public class SecurityDAO extends AbstractDAO {
	private Connection con;
	private String desc;
	private boolean flag;


	public SecurityDAO() {
	}


	// Login Check
	public String loginCheck(Profile regbean) {
		String loginid = regbean.getLoginID();
		String password = regbean.getPassword();
		String role = "";

		try {
			con = getConnection();
			String sql = "select type from employeemaster where loginid=? and password=?";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, loginid);
			pstmt.setString(2, password);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				role = rs.getString(1);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			LoggerManager.writeLogSevere(ex);
			desc = "Database Connection problem";
			flag = false;
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
					LoggerManager.writeLogSevere(e);
				}
			}
		}

		return role;
	}


	// Method for login audit
	@Deprecated
	public void loginaudit(String loginid) {
		try {
			con = getConnection();
			CallableStatement cstmt = con.prepareCall("{call signoutprocedure(?)}");
			cstmt.setString(1, loginid);

			// System.out.println("in loginaudit");
			cstmt.execute();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				LoggerManager.writeLogSevere(e);
			}
		}
	}


	// Change Password
	public boolean changePassword(String loginId, String curPwd, String newPwd) {
		try {
			con = getConnection();
			con.setAutoCommit(false);

			PreparedStatement pstmt = con
					.prepareStatement(
							"update EmployeeMaster set password=? where loginid=? and password=?");

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
			e.printStackTrace();
			flag = false;

			try {
				con.rollback();
			} catch (SQLException sex) {
				sex.printStackTrace();
				LoggerManager.writeLogSevere(sex);
			}
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				LoggerManager.writeLogSevere(e);
			}
		}
		return flag;
	}


	// Change Secret Question
	public boolean changeQuestion(String loginId, String password, String secQues, String secAns) {
		try {
			con = getConnection();
			con.setAutoCommit(false);
			PreparedStatement pstmt = con.prepareStatement(
					"update EmployeeMaster set s_ques=?,s_ans=? where loginid=? and password=?");

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

		} catch (SQLException ex) {
			ex.printStackTrace();
			LoggerManager.writeLogSevere(ex);
			flag = false;
			try {
				con.rollback();
			} catch (SQLException sex) {
				LoggerManager.writeLogSevere(sex);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LoggerManager.writeLogSevere(e);
			flag = false;
			try {
				con.rollback();
			} catch (SQLException sex) {
				LoggerManager.writeLogSevere(sex);
			}
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				LoggerManager.writeLogSevere(e);
			}

		}

		return flag;
	}


	// Recover Password using Existed Question
	public String recoverPasswordByQuestion(String loginId, String secQues, String secAns) {
		String password = null;

		try {
			con = getConnection();
			con.setAutoCommit(true);
			PreparedStatement pstmt = con.prepareStatement(
					"select password,s_ques,s_ans from EmployeeMaster where loginid=?");

			pstmt.setString(1, loginId);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				if (rs.getString(2).equals(secQues) && rs.getString(3).equals(secAns)) {
					password = rs.getString(1);
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			LoggerManager.writeLogSevere(ex);
		} catch (Exception e) {
			LoggerManager.writeLogSevere(e);
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				LoggerManager.writeLogSevere(e);
			}
		}

		return password;
	}


	// check useravailability
	public String checkUser(String userName) {
		String user = null;
		// System.out.println("username" + userName);
		try {
			con = getConnection();

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(
					"select loginid from EmployeeMaster where loginid=+'" + userName + "'");

			if (rs.next()) {
				user = rs.getString(1);
				System.out.println("loginid alredy exist" + userName);
			}
			else
				user = null;

		} catch (SQLException ex) {
			ex.printStackTrace();
			LoggerManager.writeLogSevere(ex);
			user = null;
		} catch (Exception e) {
			LoggerManager.writeLogSevere(e);
			user = null;
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				LoggerManager.writeLogSevere(e);
			}
		}

		return user;
	}

}
