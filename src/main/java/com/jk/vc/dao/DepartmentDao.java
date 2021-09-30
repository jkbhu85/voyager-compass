
package com.jk.vc.dao;

import static com.jk.vc.dao.ConnectionUtils.*;

import java.sql.*;
import java.util.*;

import com.jk.vc.exception.*;
import com.jk.vc.model.*;

public class DepartmentDao {
	
	private static final String SQL_INSERT_DEPARTMENT = ""
			+ " INSERT INTO DEPARTMENTMASTER ( "
			+ "   DEPARTMENTNAME, DEPARTMENTABBR, DEPARTMENTINCHGID "
			+ " ) VALUES(?,?,?)";

	public boolean insertDept(Department dept) {
		Connection con = null;
		try {
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(SQL_INSERT_DEPARTMENT);

			Integer inchargeId = dept.getDepartmentInChgID() == 0
					? null
					: dept.getDepartmentInChgID();
			
			int col = 1;
			pstmt.setString(col++, dept.getDepartmentName());
			pstmt.setString(col++, dept.getDepartmentAbbr());
			pstmt.setObject(col++, inchargeId);

			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new VcDataAccessException(e);
		} finally {
			DaoUtils.closeCon(con);
		}
	}


	private static final String SQL_UPDATE_DEPARTMENT = ""
			+ " UPDATE DEPARTMENTMASTER "
			+ " SET DEPARTMENTNAME=?,DEPARTMENTABBR=?,DEPARTMENTINCHGID=? "
			+ " WHERE DEPARTMENTID=?";
	
	public boolean updateDept(Department dept) {
		Connection con = null;
		try {
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_DEPARTMENT);

			Integer inchargeId = dept.getDepartmentInChgID() == 0
					? null
					: dept.getDepartmentInChgID();

			pstmt.setString(1, dept.getDepartmentName());
			pstmt.setString(2, dept.getDepartmentAbbr());
			pstmt.setObject(3, inchargeId);
			pstmt.setInt(4, dept.getDepartmentID());

			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new VcDataAccessException(e);
		} finally {
			DaoUtils.closeCon(con);
		}
	}

	private static final String SQL_FIND_ALL_DEPARTMENTS = ""
			+ "SELECT * FROM DEPARTMENTMASTER ORDER BY DEPARTMENTID ASC";

	public List<Department> findAllDepartments() {
		List<Department> list = new ArrayList<>();
		Connection con = null;

		try {
			con = getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(SQL_FIND_ALL_DEPARTMENTS);
			while (rs.next()) {
				Department dept = new Department();
				dept.setDepartmentID(rs.getInt(1));
				dept.setDepartmentName(rs.getString(2));
				dept.setDepartmentAbbr(rs.getString(3));
				dept.setDepartmentInChgID(rs.getInt(4));

				list.add(dept);
			}
		} catch (SQLException e) {
			throw new VcDataAccessException(e);
		} finally {
			DaoUtils.closeCon(con);
		}
		return list;
	}

	private static final String SQL_FIND_DEPARTMENT_BY_ID = ""
			+ "SELECT * FROM DEPARTMENTMASTER WHERE DEPARTMENTID=?";

	public Department findDeptatmentById(int deptId) {
		Connection con = null;
		try {
			con = getConnection();
			PreparedStatement ps = con.prepareStatement(SQL_FIND_DEPARTMENT_BY_ID);
			ps.setInt(1, deptId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Department dept = new Department();
				dept.setDepartmentID(rs.getInt(1));
				dept.setDepartmentName(rs.getString(2));
				dept.setDepartmentAbbr(rs.getString(3));
				dept.setDepartmentInChgID(rs.getInt(4));
				return dept;
			}
		} catch (SQLException e) {
			throw new VcDataAccessException(e);
		} finally {
			DaoUtils.closeCon(con);
		}
		return null;
	}

}