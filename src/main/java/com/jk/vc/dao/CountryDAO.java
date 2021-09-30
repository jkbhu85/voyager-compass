package com.jk.vc.dao;

import java.sql.*;
import java.util.*;

import com.jk.core.util.*;
import com.jk.vc.model.*;

public class CountryDAO extends AbstractDAO {

	private static final String SQL_INSERT_COUNTRY = ""
			+ "INSERT INTO COUNTRIES_MASTER VALUES(?,?,?,?)";

	public boolean insertCountry(Country country) {
		boolean flag = false;
		Connection con = null;
		try {
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(SQL_INSERT_COUNTRY);

			int col = 1;
			pstmt.setString(col++, country.getCountryName());
			pstmt.setString(col++, country.getCountryFullName());
			pstmt.setString(col++, country.getCountryDesc());
			pstmt.setString(col++, country.getNationality());

			int i = pstmt.executeUpdate();

			if (i > 0) {
				flag = true;
			}
		} catch (Exception e) {
			DaoUtils.rollback(con);
			LoggerManager.writeLogWarning(e);
		} finally {
			DaoUtils.closeCon(con);
		}
		return flag;
	}

	
	private static final String SQL_UPDATE_COUNTRY = ""
			+ " UPDATE COUNTRIES_MASTER "
			+ " SET CNT_NAME=?,CNT_FULL_NAME=?,CNT_DESC=?,CNT_NLTY=? "
			+ " WHERE CNT_ID=?";

	public boolean updateCountry(Country country) {
		boolean flag = false;
		Connection con = null;

		try {
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_COUNTRY);

			pstmt.setString(1, country.getCountryName());
			pstmt.setString(2, country.getCountryFullName());
			pstmt.setString(3, country.getCountryDesc());
			pstmt.setString(4, country.getNationality());
			pstmt.setInt(5, country.getCountryID());

			int i = pstmt.executeUpdate();

			if (i > 0) {
				flag = true;
			}
		} catch (Exception e) {
			DaoUtils.rollback(con);
			LoggerManager.writeLogWarning(e);
		} finally {
			DaoUtils.closeCon(con);
		}
		return flag;
	}

	private static final String SQL_FIND_ALL_COUNTRIES = ""
			+ "SELECT * FROM COUNTRIES_MASTER ORDER BY CNT_NAME";

	public List<Country> findAllCountries() {
		List<Country> list = new ArrayList<>();
		Connection con = null;

		try {
			con = getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(SQL_FIND_ALL_COUNTRIES);

			while (rs.next()) {
				Country country = new Country();
				country.setCountryID(rs.getInt(1));
				country.setCountryName(rs.getString(2));
				country.setCountryFullName(rs.getString(3));
				country.setCountryDesc(rs.getString(4));
				country.setNationality(rs.getString(5));

				list.add(country);
			}
		} catch (Exception e) {
			LoggerManager.writeLogWarning(e);
		} finally {
			DaoUtils.closeCon(con);
		}

		return list;
	}
	
	private static final String SQL_FIND_COUNTRY_BY_ID = ""
			+ "SELECT * FROM COUNTRIES_MASTER WHERE CNT_ID = ?";

	public Country findCountryById(int countryId) {
		Connection con = null;

		try {
			con = getConnection();
			PreparedStatement ps = con.prepareStatement(SQL_FIND_COUNTRY_BY_ID);
			ps.setInt(1, countryId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Country country = new Country();
				country.setCountryID(rs.getInt(1));
				country.setCountryName(rs.getString(2));
				country.setCountryFullName(rs.getString(3));
				country.setCountryDesc(rs.getString(4));
				country.setNationality(rs.getString(5));
				return country;
			}
		} catch (Exception e) {
			LoggerManager.writeLogWarning(e);
		} finally {
			DaoUtils.closeCon(con);
		}

		return null;
	}

}
