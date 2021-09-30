package com.jk.travel.action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jk.core.util.DateWrapper;
import com.jk.travel.dao.AbstractDAO;
import com.jk.travel.model.Work;

@WebServlet("/SearchEmpAction")
public class SearchEmpAction extends HttpServlet {
	private static final long serialVersionUID = 1L;


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean status = false;
		response.setContentType("application/json");
		StringBuilder data = null;

		try {
			// if visa type is -2 then no visa required no passport required
			// if visa type is -1 then no visa required but passport required
			// if visa type is +ve then visa required
			// 0 means error

			String visaTypeStr = request.getParameter("visaTypeId");
			String startDateStr = request.getParameter("startDate");
			String endDateStr = request.getParameter("endDate");

			int visaType = 0;

			// System.out.println("visa type str: " + visaTypeStr + " sd: " +
			// startDateStr + " ed: " + endDateStr);

			if ("N".equals(visaTypeStr)) {
				visaType = -2; // not even passport
				data = getData(startDateStr, endDateStr);
			}
			else if ("P".equals(visaTypeStr)) {
				visaType = -1; // passport required
				data = getDataWithPassport(startDateStr, endDateStr);
			}
			else if (Integer.parseInt(visaTypeStr) > 0) {
				visaType = Integer.parseInt(visaTypeStr);
				data = getDataWithVisa(visaType, startDateStr, endDateStr);
			}

			if (visaType == 0) throw new RuntimeException("Invalid visaType");

			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * Response strucutre
		 * {"status":"ok"|"fail", "data":[{"name":"", "dept":""}, ...]}
		 */
		StringBuilder responseText = new StringBuilder();
		if (data == null) {
			data = new StringBuilder();
			data.append("[]");
		}

		// System.out.println(data);
		responseText.append("{").append("\"status\":").append(status ? "\"ok\"" : "\"fail\"")
				.append(",").append("\"data\":").append(data).append("}");

		response.getWriter().write(responseText.toString());
		// System.out.println(responseText.toString());
	}


	private StringBuilder getData(String startDateStr, String endDateStr)
			throws SQLException {
		Connection con = new AbstractDAO().getConnection();

		String sql = "" +
				" select e.employeeid, e.FIRSTNAME|| ' '|| e.LASTNAME \"name\",  " +
				" d.DEPARTMENTNAME from  " +
				"    EMPLOYEEMASTER e  " +
				"    inner join DEPARTMENTMASTER d on d.DEPARTMENTID = e.DEPARTMENTID " +
				" where  " +
				"    e.EMPLOYEEID not in " +
				getCommonSql(); // 8 positional parameters

		/*
		 * start date, end date, work_status
		 * start date, end date, work_status
		 */
		PreparedStatement ps = con.prepareStatement(sql);
		int col = 1;
		ps.setString(col++, startDateStr);
		ps.setString(col++, endDateStr);
		ps.setInt(col++, Work.STATUS_CANCELLED);

		ps.setString(col++, startDateStr);
		ps.setString(col++, endDateStr);
		ps.setInt(col++, Work.STATUS_CANCELLED);

		ResultSet rs = ps.executeQuery();
		List<String> values = new ArrayList<>();
		String[] props = { "Emp ID", "Name", "Department", "Date of Issue", "Date of Expire" };

		while (rs.next()) {
			values.add(rs.getString(1));
			values.add(rs.getString(2));
			values.add(rs.getString(3));
			values.add("");
			values.add("");
		}

		return getJsonString(props, values);
	}


	private StringBuilder getDataWithPassport(String startDateStr, String endDateStr)
			throws SQLException {
		Connection con = new AbstractDAO().getConnection();

		String sql = "" +
				" select e.employeeid, e.FIRSTNAME|| ' '|| e.LASTNAME \"name\",  " +
				" d.DEPARTMENTNAME, p.PPT_ISSUE_DATE, p.PPT_EXPIRY_DATE from  " +
				"    EMPLOYEEMASTER e  " +
				"    inner join PASSPORTS p on p.EMP_PPT_FK = e.EMPLOYEEID " +
				"    inner join DEPARTMENTMASTER d on d.DEPARTMENTID = e.DEPARTMENTID " +
				" where " +
				"    p.PPT_ISSUE_DATE <= ? " + // passport issue date
				"    and p.PPT_EXPIRY_DATE >= ? " + // passport expire date
				"    and e.EMPLOYEEID not in " +
				getCommonSql();
		/*
		 * start date, end date
		 * start date, end date, work_status
		 * start date, end date, work_status
		 */
		PreparedStatement ps = con.prepareStatement(sql);
		int col = 1;
		ps.setString(col++, startDateStr);
		ps.setString(col++, endDateStr);

		ps.setString(col++, startDateStr);
		ps.setString(col++, endDateStr);
		ps.setInt(col++, Work.STATUS_CANCELLED);

		ps.setString(col++, startDateStr);
		ps.setString(col++, endDateStr);
		ps.setInt(col++, Work.STATUS_CANCELLED);

		ResultSet rs = ps.executeQuery();
		List<String> values = new ArrayList<>();
		String[] props = { "Emp ID", "Name", "Department", "Date of Issue", "Date of Expire" };

		while (rs.next()) {
			values.add(rs.getString(1));
			values.add(rs.getString(2));
			values.add(rs.getString(3));
			values.add(DateWrapper.getDateString(rs.getDate(4)));
			values.add(DateWrapper.getDateString(rs.getDate(5)));
		}

		return getJsonString(props, values);
	}


	private StringBuilder getDataWithVisa(int visaType, String startDateStr, String endDateStr)
			throws SQLException {
		Connection con = new AbstractDAO().getConnection();

		String sql = "" +
				" select e.employeeid, e.FIRSTNAME|| ' '|| e.LASTNAME \"name\",  " +
				" d.DEPARTMENTNAME, v.EV_ISSUE_DATE, v.EV_EXPIRE_DATE from " +
				"    EMP_VISAS v  " +
				"    inner join PASSPORTS p on v.PPT_EV_FK = p.PPT_ID " +
				"    inner join EMPLOYEEMASTER e on e.EMPLOYEEID = p.EMP_PPT_FK " +
				"    inner join DEPARTMENTMASTER d on d.DEPARTMENTID = e.DEPARTMENTID " +
				" where " +
				"    v.EV_CANCEL_STATUS = 0 " +
				"    and v.VT_EV_FK = ? " +
				"    and v.EV_ISSUE_DATE <= ? " +
				"    and v.EV_EXPIRE_DATE >= ? " +
				"    and (v.EV_MAX_VISITS = -1 or v.EV_MAX_VISITS > v.EV_VISIT_COUNT) " +
				"    and e.EMPLOYEEID not in " +
				getCommonSql();

		/*
		 * visatype
		 * start date, end date
		 * start date, end date, work_status
		 * start date, end date, work_status
		 */
		PreparedStatement ps = con.prepareStatement(sql);
		int col = 1;
		ps.setInt(col++, visaType);

		ps.setString(col++, startDateStr);
		ps.setString(col++, endDateStr);

		ps.setString(col++, startDateStr);
		ps.setString(col++, endDateStr);
		ps.setInt(col++, Work.STATUS_CANCELLED);

		ps.setString(col++, startDateStr);
		ps.setString(col++, endDateStr);
		ps.setInt(col++, Work.STATUS_CANCELLED);

		ResultSet rs = ps.executeQuery();
		List<String> values = new ArrayList<>();
		String[] props = { "Emp ID", "Name", "Department", "Date of Issue", "Date of Expire" };

		while (rs.next()) {
			values.add(rs.getString(1));
			values.add(rs.getString(2));
			values.add(rs.getString(3));
			values.add(DateWrapper.getDateString(rs.getDate(4)));
			values.add(DateWrapper.getDateString(rs.getDate(5)));
		}

		return getJsonString(props, values);
	}


	private String getCommonSql() {
		return "( " +
				" select t.EMPLOYEEID from  " +
				"     TRAVELMASTER t " +
				"     inner join WORKS w on w.WORK_ID = t.workid " +
				" where " +
				" t.employeeid IS NOT NULL AND (" +
				"     ( ? <= t.TRAVELSTARTDATE  " + // start date
				"       and ? >= t.TRAVELSTARTDATE  " + // end date
				"       and w.WORK_STATUS != ? " +
				"     ) " +
				"     or  " +
				"     ( ? <= t.TRAVELENDDATE  " + // start date
				"       and ? >= t.TRAVELENDDATE " + // end date
				"       and w.WORK_STATUS != ? " +
				"     )) " +
				" ) "; // has eight positional parameters
	}


	private StringBuilder getJsonString(String[] props, List<String> values) {
		StringBuilder data = new StringBuilder();

		int num = values.size() / props.length;
		int count = 0;
		int i = 0;

		data.append("[");

		for (count = 0; count < num; count++) {
			if (count > 0) data.append(",\n");

			data.append("{");

			// appending property value pairs
			for (int j = 0; j < props.length; j++) {
				if (j > 0) data.append(",");

				data.append("\"" + props[j] + "\":\"").append(values.get(i++)).append("\"");
			}

			data.append("}");
		}

		data.append("]");

		// System.out.println("SearchEmpAction: Wrote " + num + " records.");

		return data;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
