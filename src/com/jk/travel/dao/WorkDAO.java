package com.jk.travel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.jk.core.util.LoggerManager;
import com.jk.travel.model.Work;

public class WorkDAO extends AbstractDAO {

	public boolean insert(Work work) {
		boolean status = false;
		Connection con = null;

		try {
			con = getConnection();
			String sql = "insert into works values(WORK_ID_SEQ.nextval, ?, ?, ?, ?, ?, ?)";

			PreparedStatement ps = con.prepareStatement(sql);
			int col = 1;
			ps.setInt(col++, work.getCntId());
			ps.setInt(col++, work.getInchId());
			ps.setString(col++, work.getTitle());
			ps.setString(col++, work.getRespb());
			ps.setString(col++, work.getDescription());
			ps.setInt(col++, work.getStatusId());

			status = ps.executeUpdate() > 0;

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


	public boolean update(int workId, int status, String desc) {
		boolean result = false;
		Connection con = null;

		try {
			con = getConnection();
			String sql = "update works set work_status=?, work_desc=? where work_id=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, status);
			ps.setString(2, desc);
			ps.setInt(3, workId);

			result = ps.executeUpdate() > 0;

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


	public Work get(int workId) {
		Work work = null;
		Connection con = null;

		try {
			con = getConnection();
			String sql = "select w.*, e.firstname || ' ' || e.lastname \"name\", c.cnt_name from "
					+ " works w inner join countries_master c on w.cnt_work_fk = c.cnt_id "
					+ " inner join employeemaster e on w.emp_work_inch_fk = e.employeeid "
					+ " where w.work_id = ?";

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, workId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				work = new Work();

				work.setWorkId(rs.getInt("work_id"));
				work.setCntId(rs.getInt("cnt_work_fk"));
				work.setInchId(rs.getInt("emp_work_inch_fk"));
				work.setTitle(rs.getString("work_title"));
				work.setRespb(rs.getString("work_rspb"));
				work.setDescription(rs.getString("work_desc"));
				work.setStatusId(rs.getInt("work_status"));

				work.setInchName(rs.getString("name"));
				work.setCntName(rs.getString("cnt_name"));
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

		return work;

	}


	public List<Work> getAll() {
		List<Work> list = new ArrayList<>();
		Connection con = null;

		try {
			con = getConnection();
			String sql = "select w.*, e.firstname || ' ' || e.lastname \"name\", c.cnt_name from "
					+ " works w inner join countries_master c on w.cnt_work_fk = c.cnt_id "
					+ " inner join employeemaster e on w.emp_work_inch_fk = e.employeeid "
					+ " order by w.work_status";

			PreparedStatement ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Work work = new Work();

				work.setWorkId(rs.getInt("work_id"));
				work.setCntId(rs.getInt("cnt_work_fk"));
				work.setInchId(rs.getInt("emp_work_inch_fk"));
				work.setTitle(rs.getString("work_title"));
				work.setRespb(rs.getString("work_rspb"));
				work.setDescription(rs.getString("work_desc"));
				work.setStatusId(rs.getInt("work_status"));

				work.setInchName(rs.getString("name"));
				work.setCntName(rs.getString("cnt_name"));

				list.add(work);
			}

		} catch (Exception e) {
			e.printStackTrace();
			LoggerManager.writeLogWarning(e);
		} finally {
			try {
				if (con != null) con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return list;
	}


	public List<Work> getCountryWise(int countryId) {
		List<Work> list = new ArrayList<>();
		Connection con = null;

		try {
			con = getConnection();
			String sql = "select w.*, e.firstname || ' ' || e.lastname \"name\", c.cnt_name from "
					+ " works w inner join countries_master c on w.cnt_work_fk = c.cnt_id "
					+ " inner join employeemaster e on w.emp_work_inch_fk = e.employeeid "
					+ " where w.cnt_work_fk=? order by w.work_status";

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, countryId);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Work work = new Work();

				work.setWorkId(rs.getInt("work_id"));
				work.setCntId(rs.getInt("cnt_work_fk"));
				work.setInchId(rs.getInt("emp_work_inch_fk"));
				work.setTitle(rs.getString("work_title"));
				work.setRespb(rs.getString("work_rspb"));
				work.setDescription(rs.getString("work_desc"));
				work.setStatusId(rs.getInt("work_status"));

				work.setInchName(rs.getString("name"));
				work.setCntName(rs.getString("cnt_name"));

				list.add(work);
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


	public List<Work> getInchargeWise(int inchId, int statusFilter) {
		List<Work> list = new ArrayList<>();
		Connection con = null;

		try {
			con = getConnection();
			String sql = "" +
					" select w.*, e.firstname || ' ' || e.lastname \"name\", c.cnt_name from " +
					" works w " +
					" inner join countries_master c on w.cnt_work_fk = c.cnt_id " +
					" inner join employeemaster e on w.emp_work_inch_fk = e.employeeid " +
					" where w.emp_work_inch_fk=? " +
					"    and w.work_status <= ? " +
					" order by w.work_status";

			PreparedStatement ps = con.prepareStatement(sql);
			int col = 1;
			ps.setInt(col++, inchId);
			ps.setInt(col++, statusFilter);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Work work = new Work();

				work.setWorkId(rs.getInt("work_id"));
				work.setCntId(rs.getInt("cnt_work_fk"));
				work.setInchId(rs.getInt("emp_work_inch_fk"));
				work.setTitle(rs.getString("work_title"));
				work.setRespb(rs.getString("work_rspb"));
				work.setDescription(rs.getString("work_desc"));
				work.setStatusId(rs.getInt("work_status"));

				work.setInchName(rs.getString("name"));
				work.setCntName(rs.getString("cnt_name"));

				list.add(work);
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


	/**
	 * Returns all works whoose status is less than or equal to
	 * {@code statusFilter}.
	 * 
	 * @param empId
	 *            ID of the employee
	 * @param statusFilter
	 *            maximum value for status
	 * @return all works whoose status is less than or equal to
	 *         {@code statusFilter}.
	 */
	public List<Work> getEmpWise(int empId, int statusFilter) {
		List<Work> list = new ArrayList<>();
		Connection con = null;

		try {
			con = getConnection();
			String sql = "" +
					" select w.*, c.CNT_NAME, i.FIRSTNAME || ' ' || i.LASTNAME \"inch\" from  " +
					"    WORKS w " +
					"    inner join TRAVELMASTER t on t.WORKID = w.WORK_ID " +
					"    inner join EMPLOYEEMASTER e on e.EMPLOYEEID = t.EMPLOYEEID " +
					"    inner join COUNTRIES_MASTER c on c.CNT_ID = w.CNT_WORK_FK " +
					"    inner join EMPLOYEEMASTER i on i.EMPLOYEEID = w.EMP_WORK_INCH_FK " +
					" where " +
					"    w.WORK_STATUS > 1 " +
					"    and e.employeeid = ? " +
					"    and w.work_status >= ? " +
					"    and w.work_status <= ? " +
					" order by w.WORK_STATUS asc, c.CNT_NAME asc ";

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, empId);
			ps.setInt(2, Work.STATUS_PREPARED);
			ps.setInt(3, statusFilter);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Work work = new Work();

				work.setWorkId(rs.getInt("work_id"));
				work.setCntId(rs.getInt("cnt_work_fk"));
				work.setInchId(rs.getInt("emp_work_inch_fk"));
				work.setTitle(rs.getString("work_title"));
				work.setRespb(rs.getString("work_rspb"));
				work.setDescription(rs.getString("work_desc"));
				work.setStatusId(rs.getInt("work_status"));

				work.setInchName(rs.getString("inch"));
				work.setCntName(rs.getString("cnt_name"));

				list.add(work);
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


	public boolean exists(int workId) {
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


	public int getInch(int workId) {
		int inchId = 0;
		Connection con = null;

		try {
			con = getConnection();
			String sql = "select emp_work_inch_fk from works where work_id=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, workId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				inchId = rs.getInt(1);
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

		return inchId;
	}
}
