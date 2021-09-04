package com.jk.travel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jk.core.util.LoggerManager;
import com.jk.travel.model.Country;

public class CountryDAO extends AbstractDAO {
	private Connection con;


	public boolean insertCountry(Country country) {
		boolean flag = false;

		// int countryID = getSequenceID("countries_master", "cnt_id");

		try {
			con = getConnection();
			String sql = "insert into countries_master values(CNT_ID_SEQ.nextval,?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);

			// pstmt.setInt(1, countryID);
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
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
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


	public boolean updateCountry(Country country) {
		boolean flag = false;

		try {
			con = getConnection();
			String sql = "UPDATE countries_master SET cnt_name=?,cnt_full_name=?,cnt_desc=?,cnt_nlty=? WHERE cnt_id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);

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
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
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


	public List<Country> getCountryList() {
		List<Country> list = new ArrayList<>();

		try {
			con = getConnection();
			String sql = "select * from countries_master order by cnt_name";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

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
			e.printStackTrace();
			LoggerManager.writeLogWarning(e);
		} finally {
			try {
				if (con != null) {
					con.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return list;
	}


	public Country getCountry(int countryId) {
		Country country = null;

		try {
			con = getConnection();
			String sql = "select * from countries_master where cnt_id = '" + countryId + "'";

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				country = new Country();
				country.setCountryID(rs.getInt(1));
				country.setCountryName(rs.getString(2));
				country.setCountryFullName(rs.getString(3));
				country.setCountryDesc(rs.getString(4));
				country.setNationality(rs.getString(5));
			}
		} catch (Exception e) {
			e.printStackTrace();
			LoggerManager.writeLogWarning(e);
		} finally {
			try {
				if (con != null) {
					con.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return country;
	}


	// get country name
	public String getCountryName(int countryId) {
		String countryName = null;

		try {
			con = getConnection();

			String sql = "SELECT cnt_name from countries_master where cnt_id='" + countryId + "'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);

			if (rs.next()) {
				countryName = rs.getString(1);
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
		return countryName;
	}
}
