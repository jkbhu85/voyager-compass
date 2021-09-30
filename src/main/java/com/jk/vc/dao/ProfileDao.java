package com.jk.vc.dao;

import static com.jk.vc.dao.ConnectionUtils.*;

import java.sql.*;
import java.util.*;

import javax.servlet.http.*;

import com.jk.vc.exception.*;
import com.jk.vc.model.*;
import com.jk.vc.util.*;

public class ProfileDao {

	private static final String SQL_INSERT_USER = "INSERT INTO EMPLOYEEMASTER VALUES ( "
			+
			" ?, ?, ?, " + // FN, LN, DOB
			" CURRENT_TIMESTAMP, " + // DOJ
			" ?, ?, ?, ?, " + // LOGINID, PWD, SQUES, SANS
			" ?, ?, " + // EMAIL, PHONE
			" ?, " + // PHOTO
			" ?, ?, ?, " + // DEPTID, DSGN, EXT
			" ? " + // GENDER
			" )";

	private static final String SQL_INSERT_ADDRESS = "INSERT INTO ADDRESSES VALUES ( "
			+ " ?, " // EMP ID
			+ " ?, ?, ?, " // TYPE, LINE1, LINE2
			+ " ?, ?, ?, ? " // CITY, STATE, COUNTRY, PIN
			+ " )";

	public boolean saveProfile(Profile p)
	{
		boolean status = false;
		Connection con = null;

		try {
			con = getConnection();
			con.setAutoCommit(false);
			Part imgPart = p.getProfileImgPart();

			PreparedStatement prfStmt = con.prepareStatement(SQL_INSERT_USER,
					new String[] { "EMPLOYEEID" });
			// preparing profile statement
			int col = 1;
			prfStmt.setString(col++, p.getFirstName());
			prfStmt.setString(col++, p.getLastName());
			prfStmt.setString(col++, DateUtils.convertDate(p.getBdate()));

			prfStmt.setString(col++, p.getLoginID());
			prfStmt.setString(col++, p.getPassword());
			prfStmt.setString(col++, p.getSecretqid());
			prfStmt.setString(col++, p.getSecretAnswer());

			prfStmt.setString(col++, p.getEmail());
			prfStmt.setString(col++, p.getPhone());

			prfStmt.setBinaryStream(col++, null);

			prfStmt.setInt(col++, p.getDeptID());
			prfStmt.setString(col++, p.getLogintype());

			String mimeType = imgPart.getContentType();
			String photoExt = mimeType.substring(mimeType.indexOf('/') + 1);
			prfStmt.setString(col++, photoExt);
			prfStmt.setString(col++, p.getGender());

			boolean prfStatus = prfStmt.executeUpdate() > 0;

			if (!prfStatus) {
				throw new VcException("Employee registration failed.");
			}

			ResultSet genKeys = prfStmt.getGeneratedKeys();
			int empId = 1;
			if (genKeys.next()) {
				empId = genKeys.getInt(1);
			}

			System.out.println("Auto gen profile id: " + empId);

			// preparing permanent addr
			PreparedStatement prmAddrStmt = con
					.prepareStatement(SQL_INSERT_ADDRESS);
			col = 1;
			prmAddrStmt.setInt(col++, empId);

			prmAddrStmt.setString(col++, p.getHome());
			prmAddrStmt.setString(col++, p.getHno());
			prmAddrStmt.setString(col++, p.getStreet());
			prmAddrStmt.setString(col++, p.getCity());
			prmAddrStmt.setString(col++, p.getState());
			prmAddrStmt.setString(col++, p.getCountry());
			prmAddrStmt.setString(col++, p.getPin());

			// preparing current addr
			PreparedStatement curAddrStmt = con
					.prepareStatement(SQL_INSERT_ADDRESS);
			col = 1;
			curAddrStmt.setInt(col++, empId);

			curAddrStmt.setString(col++, p.getContact());
			curAddrStmt.setString(col++, p.getChno());
			curAddrStmt.setString(col++, p.getCstreet());
			curAddrStmt.setString(col++, p.getCcity());
			curAddrStmt.setString(col++, p.getCstate());
			curAddrStmt.setString(col++, p.getCcountry());
			curAddrStmt.setString(col++, p.getCpin());

			boolean prmAddrStatus = prmAddrStmt.executeUpdate() > 0;
			boolean curAddrStatus = curAddrStmt.executeUpdate() > 0;

			if (prmAddrStatus && curAddrStatus) {
				con.commit();
				status = true;
			}
			else {
				con.rollback();
			}
		}
		catch (SQLException e) {
			DaoUtils.rollback(con);
			throw new VcDataAccessException(e);
		}
		finally {
			DaoUtils.closeCon(con);
		}

		return status;
	}

	private static final String SQL_FIND_USER_PROFILE = ""
			+ "SELECT * FROM EMPLOYEEMASTER WHERE LOGINID=?";

	private static final String SQL_FIND_ALL_USER_ADDRESSES = ""
			+ "SELECT * FROM ADDRESSES WHERE EMPLOYEEID=?";

	public Profile findProfile(String loginId, String imageBasePath)
	{
		Profile profile = null;
		Connection con = null;

		try {
			con = getConnection();
			PreparedStatement empSt = con
					.prepareStatement(SQL_FIND_USER_PROFILE);
			empSt.setString(1, loginId);
			ResultSet empRs = empSt.executeQuery();

			if (empRs.next()) {
				profile = new Profile();
				profile.setEmpid(empRs.getInt("EMPLOYEEID"));
				profile.setFirstname(empRs.getString("FIRSTNAME"));
				profile.setLastName(empRs.getString("LASTNAME"));
				profile.setBdate(DateUtils.getDateString(empRs.getDate("DOB")));
				profile.setJoinDate(
						DateUtils.getDateString(empRs.getDate("DOJ")));
				profile.setLoginID(empRs.getString("LOGINID"));
				profile.setEmail(empRs.getString("EMAIL"));
				profile.setPhone(empRs.getString("PHONE"));
				profile.setDeptID(empRs.getInt("DEPARTMENTID"));

				String ext = empRs.getString("PHOTOEXT");
				String photoName = profile.getLoginID() + "." + ext;

				profile.setPhoto(photoName);
				profile.setGender(empRs.getString("GENDER"));
			}

			if (profile == null)
				return null;

			PreparedStatement addrSt = con
					.prepareStatement(SQL_FIND_ALL_USER_ADDRESSES);
			addrSt.setInt(1, profile.getEmpid());
			ResultSet rs = addrSt.executeQuery();

			while (rs.next()) {
				if (rs.getString("ADDRESSTYPE").equals("HOME")) {
					profile.setHome("HOME");
					profile.setHno(rs.getString("LINE1"));
					profile.setStreet(rs.getString("LINE2"));
					profile.setCity(rs.getString("CITY"));
					profile.setState(rs.getString("STATE"));
					profile.setCountry(rs.getString("COUNTRY"));
					profile.setPin(rs.getString("PINCODE"));
				}
				else {
					profile.setContact("PERSONAL");
					profile.setChno(rs.getString("LINE1"));
					profile.setCstreet(rs.getString("LINE2"));
					profile.setCcity(rs.getString("CITY"));
					profile.setCstate(rs.getString("STATE"));
					profile.setCcountry(rs.getString("COUNTRY"));
					profile.setCpin(rs.getString("PINCODE"));
				}
			}

		}
		catch (SQLException e) {
			DaoUtils.rollback(con);
			throw new VcDataAccessException(e);
		}
		finally {
			DaoUtils.closeCon(con);
		}

		return profile;
	}

	private static String[] getAddrSql(ProfileData.Address[] list)
	{
		String sqlh = "UPDATE ADDRESSES SET ";
		sqlh += "LINE1='" + list[0].getLine1() + "'";
		sqlh += ",LINE2='" + list[0].getLine2() + "'";
		sqlh += ",CITY='" + list[0].getCity() + "'";
		sqlh += ",STATE='" + list[0].getState() + "'";
		sqlh += ",COUNTRY='" + list[0].getCountry() + "'";
		sqlh += ",PINCODE='" + list[0].getPin() + "'";
		sqlh += " WHERE ADDRESSTYPE='HOME' AND EMPLOYEEID=";

		String sqlp = "";
		if (list[1].getLine1() != null && list[1].getLine1().length() > 0) {
			sqlp = "UPDATE ADDRESSES SET ";
			sqlp += "LINE1='" + list[1].getLine1() + "'";
			sqlp += ",LINE2='" + list[1].getLine2() + "'";
			sqlp += ",CITY='" + list[1].getCity() + "'";
			sqlp += ",STATE='" + list[1].getState() + "'";
			sqlp += ",COUNTRY='" + list[1].getCountry() + "'";
			sqlp += ",PINCODE='" + list[1].getPin() + "'";
			sqlp += " WHERE ADDRESSTYPE='PERSONAL' AND EMPLOYEEID=";
		}

		return new String[] { sqlh, sqlp };
	}

	public boolean updateProfile(ProfileData data)
	{
		boolean status = false;

		String profsql = "";
		boolean photoExist = false;

		if (data.getEmail() != null)
			profsql += ", EMAIL='" + data.getEmail() + "'";
		if (data.getMobile() != null)
			profsql += ", PHONE='" + data.getMobile() + "'";
		if (data.getImgPart() != null && data.getImgPart().getSize() > 0) {
			photoExist = true;
			Part part = data.getImgPart();
			String ext = part.getContentType().split("/")[1];
			String name = part.getName()
					.substring(part.getName().lastIndexOf("/") + 1);
			profsql += ", PHOTOEXT='" + ext + "'";
			profsql += ", PHOTO=?";
		}

		int empId = findEmpIdByLoginId(data.getLoginId());
		String[] addrSql = getAddrSql(data.getList());

		profsql = "UPDATE EMPLOYEEMASTER SET DOJ=DOJ " + profsql +
				" WHERE EMPLOYEEID=" + empId;

		// System.out.println("ProfSql: " + profsql);
		// System.out.println("Home: " + addrSql[0]);
		// System.out.println("Personal: " + addrSql[1]);
		Connection con = null;

		try {
			con = getConnection();
			con.setAutoCommit(false);

			PreparedStatement profst = con.prepareStatement(profsql);
			if (photoExist)
				profst.setBinaryStream(1, null);

			boolean status1 = profst.executeUpdate() > 0;

			Statement sth = con.createStatement();
			boolean status2 = sth.executeUpdate(addrSql[0] + empId) > 0;

			Statement stp;
			boolean status3 = false;

			if (addrSql[1].length() > 0) {
				stp = con.createStatement();
				status3 = stp.executeUpdate(addrSql[1] + empId) > 0;
			}
			else {
				status3 = true;
			}

			status = status1 && status2 && status3;

			if (status) {
				con.commit();
			}
			else {
				con.rollback();
			}
		}
		catch (SQLException e) {
			DaoUtils.rollback(con);
			throw new VcDataAccessException(e);
		}
		finally {
			DaoUtils.closeCon(con);
		}

		return status;
	}

	private static final String SQL_FIND_LOGIN_ID_BY_EMPLOYEE_ID = ""
			+ "SELECT LOGINID FROM EMPLOYEEMASTER WHERE EMPLOYEEID=?";

	public String findLoginIdByEmployeeId(int empid)
	{
		Connection con = null;

		try {
			con = getConnection();

			PreparedStatement ps = con
					.prepareStatement(SQL_FIND_LOGIN_ID_BY_EMPLOYEE_ID);
			ps.setInt(1, empid);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
		}
		catch (SQLException e) {
			DaoUtils.rollback(con);
			throw new VcDataAccessException(e);
		}
		finally {
			DaoUtils.closeCon(con);
		}
		return null;
	}

	private static final String SQL_FIND_ALL_EMPLOYEES = ""
			+ "SELECT EMPLOYEEID,LOGINID FROM EMPLOYEEMASTER";

	public List<Profile> findAllEmployees()
	{
		List<Profile> list = new ArrayList<>();
		Connection con = null;

		try {
			con = getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(SQL_FIND_ALL_EMPLOYEES);

			while (rs.next()) {
				Profile profile = new Profile();
				profile.setEmpid(rs.getInt(1));
				profile.setLoginID(rs.getString(2));

				list.add(profile);
			}
		}
		catch (SQLException e) {
			throw new VcDataAccessException(e);
		}
		finally {
			DaoUtils.closeCon(con);
		}
		return list;
	}

	private static final String SQL_FIND_ALL_EMPLOYEES_BY_DEPARTMENT_ID = ""
			+ " SELECT EMPLOYEEID,LOGINID,FIRSTNAME,LASTNAME "
			+ " FROM EMPLOYEEMASTER WHERE DEPARTMENTID=?";

	public List<Profile> findAllEmployeesByDepartmentId(String departmentId)
	{
		List<Profile> list = new ArrayList<>();
		Connection con = null;

		try {
			con = getConnection();
			PreparedStatement pstmt = con
					.prepareStatement(SQL_FIND_ALL_EMPLOYEES_BY_DEPARTMENT_ID);
			pstmt.setString(1, departmentId);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Profile profile = new Profile();
				profile.setEmpid(rs.getInt(1));
				profile.setLoginID(rs.getString(2));
				profile.setFirstName(rs.getString(3));
				profile.setLastName(rs.getString(4));

				list.add(profile);
			}
		}
		catch (SQLException e) {
			throw new VcDataAccessException(e);
		}
		finally {
			DaoUtils.closeCon(con);
		}
		return list;
	}

	private static final String SQL_FIND_ALL_ADMINS = ""
			+ "SELECT EMPLOYEEID,LOGINID FROM EMPLOYEEMASTER WHERE TYPE='ADMIN'";

	public List<Profile> findAllAdmins()
	{
		List<Profile> list = new ArrayList<>();
		Connection con = null;

		try {
			con = getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(SQL_FIND_ALL_ADMINS);
			while (rs.next()) {
				Profile profile = new Profile();
				profile.setEmpid(rs.getInt(1));
				profile.setLoginID(rs.getString(2));

				list.add(profile);
			}
		}
		catch (SQLException e) {
			throw new VcDataAccessException(e);
		}
		finally {
			DaoUtils.closeCon(con);
		}
		return list;
	}

	private static final String SQL_DOES_PROFILE_EXIST_BY_EMPLOYEE_ID = ""
			+ "SELECT EMPLOYEEID FROM EMPLOYEEMASTER WHERE EMPLOYEEID=?";

	public boolean doesProfileExists(int empId)
	{
		Connection con = null;
		boolean status = false;

		try {
			con = getConnection();
			PreparedStatement pstmt = con
					.prepareStatement(SQL_DOES_PROFILE_EXIST_BY_EMPLOYEE_ID);
			pstmt.setInt(1, empId);

			ResultSet rs = pstmt.executeQuery();
			status = rs.next();
		}
		catch (SQLException e) {
			throw new VcDataAccessException(e);
		}
		finally {
			DaoUtils.closeCon(con);
		}

		return status;
	}

	private static final String SQL_FIND_EMPLOYEE_ID_BY_LOGIN_ID = ""
			+ "SELECT EMPLOYEEID FROM EMPLOYEEMASTER WHERE LOGINID=?";

	public int findEmpIdByLoginId(String loginId)
	{
		Connection con = null;

		try {
			con = getConnection();
			PreparedStatement pstmt = con
					.prepareStatement(SQL_FIND_EMPLOYEE_ID_BY_LOGIN_ID);
			pstmt.setString(1, loginId);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			}
		}
		catch (SQLException e) {
			throw new VcDataAccessException(e);
		}
		finally {
			DaoUtils.closeCon(con);
		}

		return 0;
	}

	private static final String SQL_DOES_PROFILE_EXISTS_BY_LOGIN_ID = ""
			+ "SELECT EMPLOYEEID FROM EMPLOYEEMASTER WHERE LOGINID=?";

	public boolean doesProfileExistByLoginId(String loginId)
	{
		Connection con = null;

		try {
			con = getConnection();
			PreparedStatement pstmt = con
					.prepareStatement(SQL_DOES_PROFILE_EXISTS_BY_LOGIN_ID);
			pstmt.setString(1, loginId);

			ResultSet rs = pstmt.executeQuery();
			return rs.next();
		}
		catch (SQLException e) {
			throw new VcDataAccessException(e);
		}
		finally {
			DaoUtils.closeCon(con);
		}
	}

	private static final String SQL_FIND_ALL_PROFILES = ""
			+ " SELECT D.DEPARTMENTABBR,E.* FROM "
			+ " EMPLOYEEMASTER E "
			+ " 	INNER JOIN DEPARTMENTMASTER D ON D.DEPARTMENTID=E.DEPARTMENTID "
			+ " ORDER BY D.DEPARTMENTNAME ASC, E.FIRSTNAME ASC, E.LASTNAME ASC ";

	public List<Profile> getAllEmpProfiles(String imageBasePath)
	{
		List<Profile> list = new ArrayList<>();
		Connection con = null;

		try {
			con = getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(SQL_FIND_ALL_PROFILES);

			while (rs.next()) {
				Profile profile = new Profile();
				profile.setDeptName(rs.getString("DEPARTMENTABBR"));
				profile.setEmpid(rs.getInt("EMPLOYEEID"));
				profile.setFirstname(rs.getString("FIRSTNAME"));
				profile.setLastName(rs.getString("LASTNAME"));
				String date = DateUtils.parseDate(rs.getDate("DOB")).trim();
				profile.setLoginID(rs.getString("LOGINID"));
				profile.setEmail(rs.getString("EMAIL"));
				profile.setPhone(rs.getString("PHONE"));
				profile.setDeptID(rs.getInt("DEPARTMENTID"));
				profile.setGender(rs.getString("GENDER"));
				profile.setBirthDate(date);

				String photoName = rs.getString("LOGINID") + "."
						+ rs.getString("PHOTOEXT");
				profile.setPhoto(photoName);

				list.add(profile);
			}
		}
		catch (SQLException e) {
			throw new VcDataAccessException(e);
		}
		finally {
			DaoUtils.closeCon(con);
		}
		return list;
	}

}
