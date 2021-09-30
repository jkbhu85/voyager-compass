
package com.jk.vc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.jk.core.util.LoggerManager;
import com.jk.vc.model.*;

public class HotelDAO extends AbstractDAO {
	
	private static final String SQL_INSERT_HOTEL = ""
			+ "INSERT INTO HOTELMASTER VALUES(?,?,?,?,?,?,?)";
	
	public boolean insertHotel(Hotel hotel) {
		boolean flag = false;
		Connection con = null;
		try {
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(SQL_INSERT_HOTEL);

			int col = 1;
			pstmt.setString(col++, hotel.getHotelName());
			pstmt.setString(col++, hotel.getHotelAddr());
			pstmt.setString(col++, hotel.getHotelPhno());
			pstmt.setString(col++, hotel.getHotelContactPerson());
			pstmt.setDouble(col++, hotel.getMinCharge());
			pstmt.setDouble(col++, hotel.getMaxCharge());
			pstmt.setInt(col++, hotel.getCountryID());

			flag = pstmt.executeUpdate() > 0;
		} catch (Exception e) {
			LoggerManager.writeLogWarning(e);
		} finally {
			DaoUtils.closeCon(con);
		}
		return flag;
	}

	private static final String SQL_UPDATE_HOTEL = ""
			+ " UPDATE HOTELMASTER SET HOTELNAME=?,HOTELADDR=?,HOTELPHONENO=?, "
			+ " HOTELCONTACTPERSONNAME=?,HOTELMINRENTALCHARGES=?, "
			+ " HOTELMAXCHARGES=?,COUNTRYID=? WHERE HOTELID=? ";

	public boolean updateHotel(Hotel hotel) {
		boolean flag = false;
		Connection con = null;

		try {
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_HOTEL);
			
			int col = 1;
			pstmt.setString(col++, hotel.getHotelName());
			pstmt.setString(col++, hotel.getHotelAddr());
			pstmt.setString(col++, hotel.getHotelPhno());
			pstmt.setString(col++, hotel.getHotelContactPerson());
			pstmt.setDouble(col++, hotel.getMinCharge());
			pstmt.setDouble(col++, hotel.getMaxCharge());
			pstmt.setInt(col++, hotel.getCountryID());
			pstmt.setInt(col++, hotel.getHotelID());

			flag = pstmt.executeUpdate() > 0;
		} catch (Exception e) {
			LoggerManager.writeLogWarning(e);
		} finally {
			DaoUtils.closeCon(con);
		}
		return flag;
	}

	
	private static final String SQL_FIND_ALL_HOTELS = ""
			+ " SELECT H.*,CM.CNT_NAME FROM HOTELMASTER H  "
			+ " INNER JOIN COUNTRIES_MASTER CM ON CM.CNT_ID=H.COUNTRYID "
			+ " ORDER BY CM.CNT_NAME ASC,H.HOTELNAME ASC ";

	public List<Hotel> findAllHotels() {
		List<Hotel> list = new ArrayList<>();
		Connection con = null;

		try {
			con = getConnection();
			PreparedStatement st = con.prepareStatement(SQL_FIND_ALL_HOTELS);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Hotel hotel = new Hotel();

				hotel.setHotelID(rs.getInt(1));
				hotel.setHotelName(rs.getString(2));
				hotel.setHotelAddr(rs.getString(3));
				hotel.setHotelPhno(rs.getString(4));
				hotel.setHotelContactPerson(rs.getString(5));
				hotel.setMinCharge(rs.getDouble(6));
				hotel.setMaxCharge(rs.getDouble(7));
				hotel.setCountryID(rs.getInt(8));
				hotel.setCountryName(rs.getString(9));

				list.add(hotel);
			}
		} catch (Exception e) {
			LoggerManager.writeLogWarning(e);
		} finally {
			DaoUtils.closeCon(con);
		}
		return list;
	}

	private static final String SQL_FIND_HOTEL_BY_COUNTRY_ID = ""
			+ " SELECT H.*,CM.CNT_NAME FROM HOTELMASTER H  "
			+ " INNER JOIN COUNTRIES_MASTER CM ON CM.CNT_ID=H.COUNTRYID "
			+ " WHERE H.COUNTRYID=? "
			+ " ORDER BY H.HOTELNAME ASC ";

	public List<Hotel> findHotelsByCountryId(int countryId) {
		List<Hotel> list = new ArrayList<>();
		Connection con = null;

		try {
			con = getConnection();
			PreparedStatement st = con.prepareStatement(SQL_FIND_HOTEL_BY_COUNTRY_ID);
			st.setInt(1, countryId);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Hotel hotel = new Hotel();

				hotel.setHotelID(rs.getInt(1));
				hotel.setHotelName(rs.getString(2));
				hotel.setHotelAddr(rs.getString(3));
				hotel.setHotelPhno(rs.getString(4));
				hotel.setHotelContactPerson(rs.getString(5));
				hotel.setMinCharge(rs.getDouble(6));
				hotel.setMaxCharge(rs.getDouble(7));
				hotel.setCountryID(rs.getInt(8));
				hotel.setCountryName(rs.getString(9));

				list.add(hotel);
			}
		} catch (Exception e) {
			LoggerManager.writeLogWarning(e);
		} finally {
			DaoUtils.closeCon(con);
		}
		return list;
	}

	private static final String SQL_FIND_HOTEL_BY_ID = ""
			+ " SELECT H.*,CM.CNT_NAME FROM HOTELMASTER H "
			+ " INNER JOIN COUNTRIES_MASTER CM ON CM.CNT_ID=H.COUNTRYID "
			+ " WHERE H.HOTELID=? ";

	public Hotel findHotelById(int hotelId) {
		Connection con = null;

		try {
			con = getConnection();

			PreparedStatement pstmt = con.prepareStatement(SQL_FIND_HOTEL_BY_ID);
			pstmt.setInt(1, hotelId);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				Hotel hotel = new Hotel();
				hotel.setHotelID(rs.getInt(1));
				hotel.setHotelName(rs.getString(2));
				hotel.setHotelAddr(rs.getString(3));
				hotel.setHotelPhno(rs.getString(4));
				hotel.setHotelContactPerson(rs.getString(5));
				hotel.setMinCharge(rs.getDouble(6));
				hotel.setMaxCharge(rs.getDouble(7));
				hotel.setCountryID(rs.getInt(8));
				hotel.setCountryName(rs.getString(9));
			}
		} catch (Exception e) {
			LoggerManager.writeLogWarning(e);
		} finally {
			DaoUtils.closeCon(con);
		}
		return null;
	}

}
