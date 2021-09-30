package com.jk.travel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.jk.core.util.LoggerManager;
import com.jk.travel.model.Work;

public class WorkDAO extends AbstractDAO {	

	private static final String SQL_INSERT_INTO_WORK = "INSERT INTO WORKS VALUES(?, ?, ?, ?, ?, ?)";

	public boolean insertWork(Work work) {
		Connection con = null;

		try {
			con = getConnection();

			PreparedStatement ps = con.prepareStatement(SQL_INSERT_INTO_WORK);
			int col = 1;
			ps.setInt(col++, work.getCntId());
			ps.setInt(col++, work.getInchId());
			ps.setString(col++, work.getTitle());
			ps.setString(col++, work.getRespb());
			ps.setString(col++, work.getDescription());
			ps.setInt(col++, work.getStatusId());

			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			LoggerManager.writeLogWarning(e);
		} finally {
			DaoUtils.closeCon(con);
		}

		return false;
	}

	private static final String SQL_UPDATE_WORK = ""
			+ "UPDATE WORKS SET WORK_STATUS=?, WORK_DESC=? WHERE WORK_ID=?";

	public boolean updateWork(int workId, int status, String desc) {
		Connection con = null;

		try {
			con = getConnection();
			PreparedStatement ps = con.prepareStatement(SQL_UPDATE_WORK);
			ps.setInt(1, status);
			ps.setString(2, desc);
			ps.setInt(3, workId);

			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			LoggerManager.writeLogWarning(e);
		} finally {
			DaoUtils.closeCon(con);
		}

		return false;
	}

	private static final String SQL_FIND_WORK_BY_ID = ""
			+ " SELECT "
			+ "   W.*, E.FIRSTNAME || ' ' || E.LASTNAME NAME, C.CNT_NAME "
			+ " FROM "
			+ "   WORKS W "
			+ " INNER JOIN COUNTRIES_MASTER C "
			+ "   ON W.CNT_WORK_FK = C.CNT_ID "
			+ " INNER JOIN EMPLOYEEMASTER E "
			+ "   ON W.EMP_WORK_INCH_FK = E.EMPLOYEEID "
			+ " WHERE "
			+ "   W.WORK_ID = ? ";

	public Work get(int workId) {
		Connection con = null;

		try {
			con = getConnection();
			PreparedStatement ps = con.prepareStatement(SQL_FIND_WORK_BY_ID);
			ps.setInt(1, workId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				Work work = new Work();

				work.setWorkId(rs.getInt("WORK_ID"));
				work.setCntId(rs.getInt("CNT_WORK_FK"));
				work.setInchId(rs.getInt("EMP_WORK_INCH_FK"));
				work.setTitle(rs.getString("WORK_TITLE"));
				work.setRespb(rs.getString("WORK_RSPB"));
				work.setDescription(rs.getString("WORK_DESC"));
				work.setStatusId(rs.getInt("WORK_STATUS"));

				work.setInchName(rs.getString("NAME"));
				work.setCntName(rs.getString("CNT_NAME"));
				return work;
			}
		} catch (Exception e) {
			LoggerManager.writeLogWarning(e);
		} finally {
			DaoUtils.closeCon(con);
		}

		return null;
	}

	private static final String SQL_FIND_ALL_WORKS = ""
			+ " SELECT "
			+ "   W.*, E.FIRSTNAME || ' ' || E.LASTNAME NAME, C.CNT_NAME "
			+ " FROM "
			+ " WORKS W "
			+ "   INNER JOIN COUNTRIES_MASTER C "
			+ "     ON W.CNT_WORK_FK = C.CNT_ID "
			+ "   INNER JOIN EMPLOYEEMASTER E "
			+ "     ON W.EMP_WORK_INCH_FK = E.EMPLOYEEID "
			+ " ORDER BY "
			+ "   W.WORK_STATUS ";


	public List<Work> findAllWorks() {
		List<Work> list = new ArrayList<>();
		Connection con = null;

		try {
			con = getConnection();
			PreparedStatement ps = con.prepareStatement(SQL_FIND_ALL_WORKS);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Work work = new Work();

				work.setWorkId(rs.getInt("WORK_ID"));
				work.setCntId(rs.getInt("CNT_WORK_FK"));
				work.setInchId(rs.getInt("EMP_WORK_INCH_FK"));
				work.setTitle(rs.getString("WORK_TITLE"));
				work.setRespb(rs.getString("WORK_RSPB"));
				work.setDescription(rs.getString("WORK_DESC"));
				work.setStatusId(rs.getInt("WORK_STATUS"));

				work.setInchName(rs.getString("NAME"));
				work.setCntName(rs.getString("CNT_NAME"));

				list.add(work);
			}

		} catch (Exception e) {
			LoggerManager.writeLogWarning(e);
		} finally {
			DaoUtils.closeCon(con);
		}

		return list;
	}

	private static final String SQL_FIND_ALL_WORKS_BY_COUNTRY_ID = ""
			+ " SELECT "
			+ "   W.*, E.FIRSTNAME || ' ' || E.LASTNAME NAME, C.CNT_NAME "
			+ " FROM "
			+ "   WORKS W "
			+ "   INNER JOIN COUNTRIES_MASTER C "
			+ "     ON W.CNT_WORK_FK = C.CNT_ID "
			+ "   INNER JOIN EMPLOYEEMASTER E "
			+ "     ON W.EMP_WORK_INCH_FK = E.EMPLOYEEID "
			+ " WHERE "
			+ "   W.CNT_WORK_FK=? "
			+ " ORDER BY "
			+ "   W.WORK_STATUS ";

	public List<Work> getCountryWise(int countryId) {
		List<Work> list = new ArrayList<>();
		Connection con = null;

		try {
			con = getConnection();

			PreparedStatement ps = con.prepareStatement(SQL_FIND_ALL_WORKS_BY_COUNTRY_ID);
			ps.setInt(1, countryId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Work work = new Work();

				work.setWorkId(rs.getInt("WORK_ID"));
				work.setCntId(rs.getInt("CNT_WORK_FK"));
				work.setInchId(rs.getInt("EMP_WORK_INCH_FK"));
				work.setTitle(rs.getString("WORK_TITLE"));
				work.setRespb(rs.getString("WORK_RSPB"));
				work.setDescription(rs.getString("WORK_DESC"));
				work.setStatusId(rs.getInt("WORK_STATUS"));

				work.setInchName(rs.getString("NAME"));
				work.setCntName(rs.getString("CNT_NAME"));

				list.add(work);
			}
		} catch (Exception e) {
			LoggerManager.writeLogWarning(e);
		} finally {
			DaoUtils.closeCon(con);
		}

		return list;
	}

	private static final String SQL_FIND_ALL_WORKS_BY_INCHARGE_AND_STATUS = ""
			+ " SELECT "
			+ "   W.*, E.FIRSTNAME || ' ' || E.LASTNAME NAME, C.CNT_NAME "
			+ " FROM "
			+ "   WORKS W "
			+ "   INNER JOIN COUNTRIES_MASTER C "
			+ "     ON W.CNT_WORK_FK = C.CNT_ID "
			+ "   INNER JOIN EMPLOYEEMASTER E "
			+ "     ON W.EMP_WORK_INCH_FK = E.EMPLOYEEID "
			+ " WHERE "
			+ "   W.EMP_WORK_INCH_FK=? "
			+ "   AND W.WORK_STATUS <= ? "
			+ " ORDER BY W.WORK_STATUS ";

	public List<Work> findAllWorksByInchargeAndStatus(int inchargeId, int workStatus) {
		List<Work> list = new ArrayList<>();
		Connection con = null;

		try {
			con = getConnection();

			PreparedStatement ps = con.prepareStatement(SQL_FIND_ALL_WORKS_BY_INCHARGE_AND_STATUS);
			int col = 1;
			ps.setInt(col++, inchargeId);
			ps.setInt(col++, workStatus);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Work work = new Work();

				work.setWorkId(rs.getInt("WORK_ID"));
				work.setCntId(rs.getInt("CNT_WORK_FK"));
				work.setInchId(rs.getInt("EMP_WORK_INCH_FK"));
				work.setTitle(rs.getString("WORK_TITLE"));
				work.setRespb(rs.getString("WORK_RSPB"));
				work.setDescription(rs.getString("WORK_DESC"));
				work.setStatusId(rs.getInt("WORK_STATUS"));

				work.setInchName(rs.getString("NAME"));
				work.setCntName(rs.getString("CNT_NAME"));

				list.add(work);
			}
		} catch (Exception e) {
			LoggerManager.writeLogWarning(e);
		} finally {
			DaoUtils.closeCon(con);
		}

		return list;
	}

	private static final String SQL_FIND_ALL_WORKS_BY_EMPLOYEE_ID_AND_STATUS = ""
			+ " SELECT "
			+ "   W.*, C.CNT_NAME, I.FIRSTNAME || ' ' || I.LASTNAME INCH "
			+ " FROM "
			+ "   WORKS W "
			+ "   INNER JOIN TRAVELMASTER T "
			+ "     ON T.WORKID = W.WORK_ID "
			+ "   INNER JOIN EMPLOYEEMASTER E "
			+ "     ON E.EMPLOYEEID = T.EMPLOYEEID "
			+ "   INNER JOIN COUNTRIES_MASTER C "
			+ "     ON C.CNT_ID = W.CNT_WORK_FK "
			+ "   INNER JOIN EMPLOYEEMASTER I "
			+ "     ON I.EMPLOYEEID = W.EMP_WORK_INCH_FK "
			+ " WHERE "
			+ "   W.WORK_STATUS > 1 "
			+ "   AND E.EMPLOYEEID = ? "
			+ "   AND W.WORK_STATUS >= ? "
			+ "   AND W.WORK_STATUS <= ? "
			+ " ORDER BY "
			+ "   W.WORK_STATUS ASC, "
			+ "   C.CNT_NAME ASC ";

	/**
	 * Returns all works whose status is less than or equal to
	 * {@code statusFilter}.
	 * 
	 * @param employeeId
	 *            ID of the employee
	 * @param workStatus
	 *            maximum value for status
	 * @return all works whose status is less than or equal to
	 *         {@code statusFilter}.
	 */
	public List<Work> findAllWorksByEmployeeIdAndStatus(int employeeId, int workStatus) {
		List<Work> list = new ArrayList<>();
		Connection con = null;

		try {
			con = getConnection();

			PreparedStatement ps = con.prepareStatement(SQL_FIND_ALL_WORKS_BY_EMPLOYEE_ID_AND_STATUS);
			ps.setInt(1, employeeId);
			ps.setInt(2, Work.STATUS_PREPARED);
			ps.setInt(3, workStatus);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Work work = new Work();

				work.setWorkId(rs.getInt("WORK_ID"));
				work.setCntId(rs.getInt("CNT_WORK_FK"));
				work.setInchId(rs.getInt("EMP_WORK_INCH_FK"));
				work.setTitle(rs.getString("WORK_TITLE"));
				work.setRespb(rs.getString("WORK_RSPB"));
				work.setDescription(rs.getString("WORK_DESC"));
				work.setStatusId(rs.getInt("WORK_STATUS"));

				work.setInchName(rs.getString("INCH"));
				work.setCntName(rs.getString("CNT_NAME"));

				list.add(work);
			}

		} catch (Exception e) {
			LoggerManager.writeLogWarning(e);
		} finally {
			DaoUtils.closeCon(con);
		}

		return list;
	}


	public boolean doesWorkExistById(int workId) {
		boolean status = false;
		Connection con = null;

		try {
			con = getConnection();
			String sql = "select work_id from works where work_id=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, workId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				status = true;
			}

		} catch (Exception e) {
			LoggerManager.writeLogWarning(e);
		} finally {
			DaoUtils.closeCon(con);
		}

		return status;
	}

	private static final String sql = ""
			+ "SELECT EMP_WORK_INCH_FK FROM WORKS WHERE WORK_ID=?";

	public int findInchargeOfWork(int workId) {
		Connection con = null;

		try {
			con = getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, workId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			LoggerManager.writeLogWarning(e);
		} finally {
			DaoUtils.closeCon(con);
		}

		return 0;
	}
}
