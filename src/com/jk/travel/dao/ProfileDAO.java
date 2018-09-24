/*
 * ProfileDAO.java
 *
 * 
 * 
 */

package com.jk.travel.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Part;

import com.jk.core.util.DateWrapper;
import com.jk.core.util.LoggerManager;
import com.jk.travel.model.Profile;
import com.jk.travel.model.ProfileData;

public class ProfileDAO extends AbstractDAO {

	public boolean registration(Profile p) {
		boolean status = false;
		Connection con = null;

		try {
			con = getConnection();
			con.setAutoCommit(false);
			Part imgPart = p.getProfileImgPart();

			String prfSql = "insert into employeemaster values ( " +
					" emp_id_seq.nextval, " +
					" ?, ?, ?, " +     // fn, ln, dob
					" sysdate, " +     // doj
					" ?, ?, ?, ?, " +  // loginId, pwd, sQues, sAns
					" ?, ?, " +        // email, phone
					" ?, " +           // photo
					" ?, ?, ?, " +      // deptId, dsgn, ext
					" ? " +            // gender
					" )";

			String addrSql = "insert into addresses values ( "
					+ " ?, "          // emp id
					+ " addr_id_seq.nextval, "
					+ " ?, ?, ?, "    // type, line1, line2
					+ " ?, ?, ?, ? " // city, state, country, pin
					+ " )";

			PreparedStatement prfStmt = con.prepareStatement(prfSql, new String[] { "employeeid" });
			// preparing profile statement
			int col = 1;
			prfStmt.setString(col++, p.getFirstName());
			prfStmt.setString(col++, p.getLastName());
			prfStmt.setString(col++, DateWrapper.parseDate(p.getBdate()));

			prfStmt.setString(col++, p.getLoginID());
			prfStmt.setString(col++, p.getPassword());
			prfStmt.setString(col++, p.getSecretqid());
			prfStmt.setString(col++, p.getSecretAnswer());

			prfStmt.setString(col++, p.getEmail());
			prfStmt.setString(col++, p.getPhone());

			prfStmt.setBinaryStream(col++, imgPart.getInputStream()); // photo

			prfStmt.setInt(col++, p.getDeptID());
			prfStmt.setString(col++, p.getLogintype());

			String mimeType = imgPart.getContentType();
			String photoExt = mimeType.substring(mimeType.indexOf('/') + 1);
			prfStmt.setString(col++, photoExt);
			prfStmt.setString(col++, p.getGender());

			boolean prfStatus = prfStmt.executeUpdate() > 0;

			if (!prfStatus) {
				throw new Exception("Employee registration failed.");
			}

			ResultSet genKeys = prfStmt.getGeneratedKeys();
			int empId = 1;
			if (genKeys.next()) {
				empId = genKeys.getInt(1);
			}

			System.out.println("Auto gen profile id: " + empId);

			// preparing permanent addr
			PreparedStatement prmAddrStmt = con.prepareStatement(addrSql);
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
			PreparedStatement curAddrStmt = con.prepareStatement(addrSql);
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
		} catch (Exception e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			e.printStackTrace();
			LoggerManager.writeLogWarning(e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

		return status;
	}


	public Profile getProfile(String loginId, String path) {
		Profile profile = null;
		Connection con = null;
		boolean flag = false;

		try {
			con = getConnection();
			String empSql = "select * from employeemaster where loginid=?";
			String addrSql = "select * from addresses where employeeid=?";

			PreparedStatement empSt = con.prepareStatement(empSql);
			PreparedStatement addrSt = con.prepareStatement(addrSql);

			empSt.setString(1, loginId);
			ResultSet empRs = empSt.executeQuery();

			if (empRs.next()) {
				profile = new Profile();
				profile.setEmpid(empRs.getInt("employeeid"));
				profile.setFirstname(empRs.getString("firstname"));
				profile.setLastName(empRs.getString("lastname"));
				profile.setBdate(DateWrapper.getDateString(empRs.getDate("dob")));
				profile.setJoinDate(DateWrapper.getDateString(empRs.getDate("doj")));
				profile.setLoginID(empRs.getString("loginid"));
				profile.setEmail(empRs.getString("email"));
				profile.setPhone(empRs.getString("phone"));
				profile.setDeptID(empRs.getInt("departmentid"));

				String ext = empRs.getString("photoext");
				Blob photo = empRs.getBlob("photo");

				String photoName = profile.getLoginID() + "." + ext;
				FileOutputStream photoOut = new FileOutputStream(path + File.separator + photoName);

				photoOut.write(photo.getBytes(1, (int) photo.length()));
				photoOut.close();

				profile.setPhoto(photoName);
				profile.setGender(empRs.getString("gender"));
			}

			if (profile == null) return null;

			addrSt.setInt(1, profile.getEmpid());
			ResultSet rs = addrSt.executeQuery();

			while (rs.next()) {
				if (rs.getString("addresstype").equals("home")) {
					profile.setHome("home");
					profile.setHno(rs.getString("line1"));
					profile.setStreet(rs.getString("line2"));
					profile.setCity(rs.getString("city"));
					profile.setState(rs.getString("state"));
					profile.setCountry(rs.getString("country"));
					profile.setPin(rs.getString("pincode"));
				}
				else {
					profile.setContact("personal");
					profile.setChno(rs.getString("line1"));
					profile.setCstreet(rs.getString("line2"));
					profile.setCcity(rs.getString("city"));
					profile.setCstate(rs.getString("state"));
					profile.setCcountry(rs.getString("country"));
					profile.setCpin(rs.getString("pincode"));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			flag = false;

			try {
				con.rollback();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				LoggerManager.writeLogSevere(e);
			}
		}

		return profile;
	}


	// Getting profile
	@Deprecated
	public Profile getProfile1(String loginname, String path) {
		Connection con = null;
		boolean flag = false;

		Profile rb = new Profile();
		try {
			con = getConnection();
			CallableStatement cs = con.prepareCall(
					"{call showprofile(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			/*
			 * 
			 * 1 logid EmployeeMaster.loginid%type, 2 pass out
			 * EmployeeMaster.PASSWORD%type, 3 fname OUT
			 * EmployeeMaster.FIRSTNAME%type, 4 lname OUT
			 * EmployeeMaster.LASTNAME%type, 5 db OUT varchar2, 6 emailid OUT
			 * EmployeeMaster.EMAIL%type, 7 phno OUT EmployeeMaster.phone%type,
			 * 8 addresshome OUT addresses.ADDRESSTYPE%type, 9 housenohome OUT
			 * addresses.DoorNO%type, 10 streethome OUT addresses.STREET%type,
			 * 11 cityhome OUT addresses.CITY%type, 12 statehome OUT
			 * addresses.STATE%type, 13 countryhome OUT addresses.COUNTRY%type,
			 * 14 pincodehome OUT addresses.PINCODE%type, 15 addressoffice OUT
			 * addresses.ADDRESSTYPE%type, 16 housenooffice OUT
			 * addresses.doorNO%type, 17 streetoffice OUT addresses.STREET%type,
			 * 18 cityoffice OUT addresses.CITY%type, 19 stateoffice OUT
			 * addresses.STATE%type, 20 countryoffice OUT
			 * addresses.COUNTRY%type, 21 pincodeoffice OUT
			 * addresses.PINCODE%type, 22 addresspersonal OUT
			 * addresses.ADDRESSTYPE%type, 23 housenopersonal OUT
			 * addresses.doorNO%type, 24 streetpersonal OUT
			 * addresses.STREET%type, 25 citypersonal OUT addresses.CITY%type,
			 * 26 statepersonal OUT addresses.STATE%type, 27 countrypersonal OUT
			 * addresses.COUNTRY%type, 28 pincodepersonal OUT
			 * addresses.PINCODE%type, 29 photograph OUT
			 * EmployeeMaster.PHOTO%type
			 * 
			 */

			cs.setString(1, loginname);
			cs.registerOutParameter(2, Types.VARCHAR);
			cs.registerOutParameter(3, Types.VARCHAR);
			cs.registerOutParameter(4, Types.VARCHAR);
			cs.registerOutParameter(5, Types.VARCHAR);
			cs.registerOutParameter(6, Types.VARCHAR);
			cs.registerOutParameter(7, Types.VARCHAR);
			cs.registerOutParameter(8, Types.VARCHAR);
			cs.registerOutParameter(9, Types.VARCHAR);
			cs.registerOutParameter(10, Types.VARCHAR);
			cs.registerOutParameter(11, Types.VARCHAR);
			cs.registerOutParameter(12, Types.VARCHAR);
			cs.registerOutParameter(13, Types.VARCHAR);
			cs.registerOutParameter(14, Types.VARCHAR);
			cs.registerOutParameter(15, Types.VARCHAR);
			cs.registerOutParameter(16, Types.VARCHAR);
			cs.registerOutParameter(17, Types.VARCHAR);
			cs.registerOutParameter(18, Types.VARCHAR);
			cs.registerOutParameter(19, Types.VARCHAR);
			cs.registerOutParameter(20, Types.VARCHAR);
			cs.registerOutParameter(21, Types.VARCHAR);
			cs.registerOutParameter(22, Types.VARCHAR);
			cs.registerOutParameter(23, Types.VARCHAR);
			cs.registerOutParameter(24, Types.VARCHAR);
			cs.registerOutParameter(25, Types.VARCHAR);
			cs.registerOutParameter(26, Types.VARCHAR);
			cs.registerOutParameter(27, Types.VARCHAR);
			cs.registerOutParameter(28, Types.VARCHAR);
			cs.registerOutParameter(29, Types.BLOB);
			cs.registerOutParameter(30, Types.VARCHAR); // photo ext
			cs.registerOutParameter(31, Types.BIGINT); // deptid
			cs.registerOutParameter(32, Types.INTEGER); // empid
			cs.execute();
			/*
			 * 
			 * 1 logid EmployeeMaster.loginid%type, 2 pass out
			 * EmployeeMaster.PASSWORD%type, 3 fname OUT
			 * EmployeeMaster.FIRSTNAME%type, 4 lname OUT
			 * EmployeeMaster.LASTNAME%type, 5 db OUT varchar2, 6 emailid OUT
			 * EmployeeMaster.EMAIL%type, 7 phno OUT EmployeeMaster.phone%type,
			 * 8 addresshome OUT addresses.ADDRESSTYPE%type, 9 housenohome OUT
			 * addresses.DoorNO%type, 10 streethome OUT addresses.STREET%type,
			 * 11 cityhome OUT addresses.CITY%type, 12 statehome OUT
			 * addresses.STATE%type, 13 countryhome OUT addresses.COUNTRY%type,
			 * 14 pincodehome OUT addresses.PINCODE%type,
			 */
			rb.setLoginID(loginname);
			rb.setPassword(cs.getString(2));
			rb.setFirstname(cs.getString(3));
			rb.setLastname(cs.getString(4));
			rb.setBdate(cs.getString(5));
			rb.setEmail(cs.getString(6));
			rb.setPhone(cs.getString(7));

			rb.setHome(cs.getString(8));
			rb.setHno(cs.getString(9));
			rb.setStreet(cs.getString(10));
			rb.setCity(cs.getString(11));
			rb.setState(cs.getString(12));
			rb.setCountry(cs.getString(13));
			rb.setPin(cs.getString(14));
			/*
			 * 
			 * 15 addressoffice OUT addresses.ADDRESSTYPE%type, 16 housenooffice
			 * OUT addresses.doorNO%type, 17 streetoffice OUT
			 * addresses.STREET%type, 18 cityoffice OUT addresses.CITY%type, 19
			 * stateoffice OUT addresses.STATE%type, 20 countryoffice OUT
			 * addresses.COUNTRY%type, 21 pincodeoffice OUT
			 * addresses.PINCODE%type, 22 addresspersonal OUT
			 * addresses.ADDRESSTYPE%type, 23 housenopersonal OUT
			 * addresses.doorNO%type, 24 streetpersonal OUT
			 * addresses.STREET%type, 25 citypersonal OUT addresses.CITY%type,
			 * 26 statepersonal OUT addresses.STATE%type, 27 countrypersonal OUT
			 * addresses.COUNTRY%type, 28 pincodepersonal OUT
			 * addresses.PINCODE%type, 29 photograph OUT
			 * EmployeeMaster.PHOTO%type
			 * 
			 */
			rb.setContact(cs.getString(15));
			rb.setChno(cs.getString(16));
			rb.setCstreet(cs.getString(17));
			rb.setCcity(cs.getString(18));
			rb.setCstate(cs.getString(19));
			rb.setCcountry(cs.getString(20));
			rb.setCpin(cs.getString(21));

			rb.setOffice(cs.getString(22));
			rb.setOhno(cs.getString(23));
			rb.setOstreet(cs.getString(24));
			rb.setOcity(cs.getString(25));
			rb.setOstate(cs.getString(26));
			rb.setOcountry(cs.getString(27));
			rb.setOpin(cs.getString(28));
			System.out.println(rb.getHome() + ":" + rb.getContact() + ":" + rb.getOffice());

			Blob b = cs.getBlob(29);
			String photoName = loginname + "." + cs.getString(30);

			byte b1[] = b.getBytes(1, (int) b.length());
			OutputStream fout = new FileOutputStream(path + File.separator + photoName);
			fout.write(b1);

			rb.setPhoto(photoName);
			rb.setDeptID((int) cs.getLong(31));
			rb.setEmpid(cs.getInt(32));

			fout.close();
		}

		catch (Exception e) {
			e.printStackTrace();
			LoggerManager.writeLogSevere(e);
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				LoggerManager.writeLogSevere(e);
			}

		}
		return rb;
	} // Modify Profile


	private String[] getAddrSql(ProfileData.Address[] list) {

		String sqlh = "update addresses set ";
		sqlh += "line1='" + list[0].getLine1() + "'";
		sqlh += ",line2='" + list[0].getLine2() + "'";
		sqlh += ",city='" + list[0].getCity() + "'";
		sqlh += ",state='" + list[0].getState() + "'";
		sqlh += ",country='" + list[0].getCountry() + "'";
		sqlh += ",pincode='" + list[0].getPin() + "'";
		sqlh += " where addresstype='home' and employeeid=";

		String sqlp = "";
		if (list[1].getLine1() != null && list[1].getLine1().length() > 0) {
			sqlp = "update addresses set ";
			sqlp += "line1='" + list[1].getLine1() + "'";
			sqlp += ",line2='" + list[1].getLine2() + "'";
			sqlp += ",city='" + list[1].getCity() + "'";
			sqlp += ",state='" + list[1].getState() + "'";
			sqlp += ",country='" + list[1].getCountry() + "'";
			sqlp += ",pincode='" + list[1].getPin() + "'";
			sqlp += " where addresstype='personal' and employeeid=";
		}

		return new String[] { sqlh, sqlp };
	}


	public boolean updateProfile(ProfileData data) {
		boolean status = false;

		String profsql = "";
		boolean photoExist = false;

		if (data.getEmail() != null) profsql += ", email='" + data.getEmail() + "'";
		if (data.getMobile() != null) profsql += ", phone='" + data.getMobile() + "'";
		if (data.getImgPart() != null && data.getImgPart().getSize() > 0) {
			photoExist = true;
			Part part = data.getImgPart();
			String ext = part.getContentType().split("/")[1];
			String name = part.getName().substring(part.getName().lastIndexOf("/") + 1);
			profsql += ", photoext='" + ext + "'";
			profsql += ", photo=?";
		}

		int empId = getEmpIdFromLogin(data.getLoginId());
		String[] addrSql = getAddrSql(data.getList());

		profsql = "update employeemaster set doj=doj " + profsql +
				" where employeeid=" + empId;

		// System.out.println("ProfSql: " + profsql);
		// System.out.println("Home: " + addrSql[0]);
		// System.out.println("Personal: " + addrSql[1]);
		Connection con = null;

		try {
			con = getConnection();
			con.setAutoCommit(false);

			PreparedStatement profst = con.prepareStatement(profsql);
			if (photoExist) profst.setBinaryStream(1, data.getImgPart().getInputStream());

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
		} catch (Exception e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return status;
	}


	// getting inchargeUserName
	public String getUserID(int empid) {
		Connection con = null;
		boolean flag = false;
		String loginid = null;

		try {
			con = getConnection();

			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("SELECT loginid from EmployeeMaster where EmployeeID=" + empid);
			if (rs.next()) {
				loginid = rs.getString(1);

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
		return loginid;
	}


	@Deprecated
	public boolean modifyProfile(Profile profile) {
		Connection con = null;
		boolean flag = false;

		String loginid = profile.getLoginID();
		String firstname = profile.getFirstName();
		String lastname = profile.getLastName();
		String phone = profile.getPhone();
		// String bdate=DateWrapper.parseDate(regbean.getBirthDate());
		// home
		String hno = profile.getHno();
		String home = profile.getHome();
		String street = profile.getStreet();
		String city = profile.getCity();
		String state = profile.getState();
		String country = profile.getCountry();
		String pin = profile.getPin();

		// office
		String ohno = profile.getOhno();
		String office = profile.getOffice();
		String ostreet = profile.getOstreet();
		String ocity = profile.getOcity();
		String ostate = profile.getOstate();
		String ocountry = profile.getOcountry();
		String opin = profile.getOpin();
		// personal
		String phno = profile.getChno();
		String contact = profile.getContact();
		String pstreet = profile.getCstreet();
		String pcity = profile.getCcity();
		String pstate = profile.getCstate();
		String pcountry = profile.getCcountry();
		String ppin = profile.getCpin();

		String email = profile.getEmail();
		String photo = profile.getPhoto();
		String newdate = DateWrapper.parseDate(new Date());
		Part imgPart = profile.getProfileImgPart();
		String mimeType = imgPart.getContentType();
		String photoExt = mimeType.substring(mimeType.indexOf('/') + 1);

		try {
			// System.out.println("photo=" + photo);
			// File f = new File(photo);
			// FileInputStream fis = new FileInputStream(f);
			// System.out.println("fole=" + f.length());

			con = getConnection();
			// con.setAutoCommit(false);
			CallableStatement cs = con.prepareCall(
					"{call changeprofile(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			/*
			 * 1 fname userdetails.FIRSTNAME%type, 2 lname
			 * userdetails.LASTNAME%type, 3 logid userdetails.LOGINID%type, 4
			 * photo userdetails.photograph%type, 5 email
			 * userdetails.EMAILID%type,
			 * 
			 */
			cs.setString(1, firstname);
			cs.setString(2, lastname);
			cs.setString(3, loginid);
			// cs.setBinaryStream(4, fis, (int) f.length());
			cs.setBinaryStream(4, imgPart.getInputStream());
			cs.setString(5, email);
			cs.setString(6, phone);
			/*
			 * 7 addresshome addresses.ADDRESSTYPE%type, 8 housenohome
			 * addresses.HOUSENO%type, 9 streethome addresses.STREET%type, 10
			 * cityhome addresses.CITY%type, 11 statehome addresses.STATE%type,
			 * 12 countryhome addresses.COUNTRY%type, 13 pincodehome
			 * addresses.PINCODE%type,
			 */
			cs.setString(7, home);
			cs.setString(8, hno);
			cs.setString(9, street);
			cs.setString(10, city);
			cs.setString(11, state);
			cs.setString(12, country);
			cs.setString(13, pin);
			/*
			 * 14 addressoffice addresses.ADDRESSTYPE%type, 15 housenooffice
			 * addresses.HOUSENO%type, 16 streetoffice addresses.STREET%type, 17
			 * cityoffice addresses.CITY%type, 18 stateoffice
			 * addresses.STATE%type, 19 countryoffice addresses.COUNTRY%type, 20
			 * pincodeoffice addresses.PINCODE%type,
			 */
			cs.setString(14, office);
			cs.setString(15, ohno);
			cs.setString(16, ostreet);
			cs.setString(17, ocity);
			cs.setString(18, ostate);
			cs.setString(19, ocountry);
			cs.setString(20, opin);
			/*
			 * 21 addresspersonal addresses.ADDRESSTYPE%type, 22 housenopersonal
			 * addresses.HOUSENO%type, 23 streetpersonal addresses.STREET%type,
			 * 24 citypersonal addresses.CITY%type, 25 statepersonal
			 * addresses.STATE%type, 26 countrypersonal addresses.COUNTRY%type,
			 * 27 pincodepersonal addresses.PINCODE%type,
			 * 
			 */
			cs.setString(21, contact);
			cs.setString(22, phno);
			cs.setString(23, pstreet);
			cs.setString(24, pcity);
			cs.setString(25, pstate);
			cs.setString(26, pcountry);
			cs.setString(27, ppin);
			cs.setString(28, photoExt);

			/*
			 * 28 phonehome phones.PHONETYPE%type, 29 phonenohome
			 * phones.PHONENO%type, 30 phoneoffice phones.PHONETYPE%type, 31
			 * phonenooffice phones.PHONENO%type, 32 phonepersonal
			 * phones.PHONETYPE%type, 33 phonenopersonal phones.PHONENO%type, 34
			 * flag out number
			 * 
			 */
			cs.registerOutParameter(29, Types.INTEGER);

			cs.execute();
			int n = cs.getInt(29);
			if (n > 0) {
				flag = true;
			}

			else {
				flag = false;
				con.rollback();
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			flag = false;
			try {
				con.rollback();
			} catch (SQLException sex) {
				sex.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			try {
				con.rollback();
			} catch (SQLException se) {
				se.printStackTrace();
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


	public List<Profile> getEmpList() {
		List<Profile> list = new ArrayList<>();
		Connection con = null;

		try {
			con = getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT EmployeeID,loginid from EmployeeMaster");

			while (rs.next()) {
				Profile profile = new Profile();
				profile.setEmpid(rs.getInt(1));
				profile.setLoginID(rs.getString(2));

				list.add(profile);
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


	public List<Profile> getEmpList(String deptFilter) {
		List<Profile> list = new ArrayList<>();
		Connection con = null;
		boolean flag = false;

		try {
			con = getConnection();
			final String sql = "SELECT employeeid,loginid,firstname,lastname from EmployeeMaster where departmentid=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, deptFilter);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Profile profile = new Profile();
				profile.setEmpid(rs.getInt(1));
				profile.setLoginID(rs.getString(2));
				profile.setFirstName(rs.getString(3));
				profile.setLastName(rs.getString(4));

				list.add(profile);
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


	public List<Profile> getAdminList() {
		List<Profile> list = new ArrayList<>();
		Connection con = null;

		try {
			con = getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(
					"SELECT EmployeeID,loginid from EmployeeMaster where type='admin'");
			while (rs.next()) {
				Profile profile = new Profile();
				profile.setEmpid(rs.getInt(1));
				profile.setLoginID(rs.getString(2));

				list.add(profile);
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


	public boolean profileIdExists(int empId) {
		Connection con = null;
		boolean status = false;

		try {
			con = getConnection();
			String sql = "select employeeid from employeemaster where employeeid=?";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, empId);

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


	public int getEmpIdFromLogin(String loginId) {
		Connection con = null;
		int empId = 0;

		try {
			con = getConnection();
			String sql = "select employeeid from employeemaster where loginid=?";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, loginId);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				empId = rs.getInt(1);
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

		return empId;
	}


	public boolean profileLoginExists(String loginId) {
		Connection con = null;
		boolean status = false;

		try {
			con = getConnection();
			String sql = "select employeeid from employeemaster where loginid=?";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, loginId);

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


	public List<Profile> getAllEmpProfiles(String path) {
		List<Profile> list = new ArrayList<>();
		Connection con = null;

		try {
			con = getConnection();

			Statement st = con.createStatement();
			String sql = "select d.departmentabbr,e.* from " +
					" employeemaster e " +
					" 	inner join departmentmaster d on d.departmentid=e.departmentid " +
					" order by d.departmentname asc, e.firstname asc, e.lastname asc ";

			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {
				Profile profile = new Profile();
				int c = 1;
				profile.setDeptName(rs.getString("departmentabbr"));
				profile.setEmpid(rs.getInt("employeeid"));
				profile.setFirstname(rs.getString("firstname"));
				profile.setLastName(rs.getString("lastname"));
				String date = DateWrapper.parseDate(rs.getDate("dob")).trim();
				profile.setLoginID(rs.getString("loginid"));
				profile.setEmail(rs.getString("email"));
				profile.setPhone(rs.getString("phone"));
				profile.setDeptID(rs.getInt("departmentid"));
				profile.setGender(rs.getString("gender"));

				profile.setBirthDate(date);

				Blob b = rs.getBlob("photo");
				String photoName = rs.getString("loginid") + "." + rs.getString("photoext");

				byte b1[] = b.getBytes(1, (int) b.length());
				OutputStream fout = new FileOutputStream(path + "/" + photoName);
				fout.write(b1);
				fout.close();

				profile.setPhoto(photoName);

				list.add(profile);
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
}
