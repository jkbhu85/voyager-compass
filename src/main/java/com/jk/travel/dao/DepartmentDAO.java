
package com.jk.travel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.jk.core.util.LoggerManager;
import com.jk.travel.model.Department;

public class DepartmentDAO extends AbstractDAO {

	public Connection con;
	private boolean flag = false;


	// insert department
	public boolean insertDept(Department dept) {
		// int deptID = getSequenceID("DepartmentMaster", "DepartmentID");
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			pstmt = con
					.prepareStatement("insert into DepartmentMaster values(DEPT_ID_SEQ.nextval,?,?,?)");

			// pstmt.setInt(1, deptID);
			int col = 1;
			pstmt.setString(col++, dept.getDepartmentName());
			pstmt.setString(col++, dept.getDepartmentAbbr());

			if (dept.getDepartmentInChgID() == 0) {
				pstmt.setNull(col++, Types.INTEGER);
			}
			else {
				pstmt.setInt(col++, dept.getDepartmentInChgID());
			}

			int i = pstmt.executeUpdate();

			if (i > 0) {
				flag = true;
				con.commit();
			}
			else {
				con.rollback();
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
		return flag;
	}


	// updateDepartmentt
	public boolean updateDept(Department dept) {
		try {
			con = getConnection();
			String sql = "update DepartmentMaster set DepartmentName=?,DepartmentAbbr=?,DepartmentInChgID=? where DepartmentID=?";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, dept.getDepartmentName());
			pstmt.setString(2, dept.getDepartmentAbbr());
			pstmt.setInt(4, dept.getDepartmentID());

			if (dept.getDepartmentInChgID() == 0) {
				pstmt.setNull(3, Types.INTEGER);
			}
			else {
				pstmt.setInt(3, dept.getDepartmentInChgID());
			}

			int i = pstmt.executeUpdate();

			if (i > 0) {
				flag = true;
				con.commit();
			}
			else {
				con.commit();
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
		return flag;
	}


	public List<Department> getDepartmentList() {
		List<Department> list = new ArrayList<>();

		try {
			con = getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * from DepartmentMaster order by DepartmentId asc");
			while (rs.next()) {
				Department dept = new Department();
				dept.setDepartmentID(rs.getInt(1));
				dept.setDepartmentName(rs.getString(2));
				dept.setDepartmentAbbr(rs.getString(3));
				dept.setDepartmentInChgID(rs.getInt(4));

				list.add(dept);
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


	public List<Department> getDeptatmentList() {
		List<Department> list = new ArrayList<>();

		try {
			con = getConnection();

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * from DepartmentMaster order by DepartmentId desc");

			while (rs.next()) {
				Department dept = new Department();
				dept.setDepartmentID(rs.getInt(1));
				dept.setDepartmentName(rs.getString(2));
				dept.setDepartmentAbbr(rs.getString(3));
				dept.setDepartmentInChgID(rs.getInt(4));

				list.add(dept);
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


	// select Particular Department
	public Department getDeptatment(int deptno) {
		Statement st;
		Department dept = null;
		try {
			con = getConnection();
			dept = new Department();
			st = con.createStatement();
			ResultSet rs = st
					.executeQuery("SELECT * from DepartmentMaster where departmentid=" + deptno);
			if (rs.next()) {

				dept.setDepartmentID(rs.getInt(1));
				dept.setDepartmentName(rs.getString(2));
				dept.setDepartmentAbbr(rs.getString(3));
				dept.setDepartmentInChgID(rs.getInt(4));

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
		return dept;
	}

}
