
package com.jk.travel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.jk.core.util.LoggerManager;
import com.jk.travel.model.Hotel;

public class HotelDAO extends AbstractDAO {
	public boolean insertHotel(Hotel hotel) {
		boolean flag = false;
		Connection con = null;

		// int hotelID = getSequenceID("HotelMaster", "HotelID");
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(
					"insert into HotelMaster values(HOTEL_ID_SEQ.nextval,?,?,?,?,?,?,?)");

			// pstmt.setInt(1, hotelID);
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
			e.printStackTrace();
			LoggerManager.writeLogWarning(e);
		} finally {

			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
				}
			}
		}
		return flag;
	}


	// updateDepartmentt
	public boolean updateHotel(Hotel hotel) {
		boolean flag = false;
		Connection con = null;

		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			String sql = "update HotelMaster set HotelName=?,HotelAddr=?,HotelPhoneNo=?, " +
					" hotelcontactpersonname=?,hotelminrentalcharges=?, " +
					" hotelmaxcharges=?,countryid=? where HotelID=? ";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, hotel.getHotelName());
			pstmt.setString(2, hotel.getHotelAddr());
			pstmt.setString(3, hotel.getHotelPhno());
			pstmt.setString(4, hotel.getHotelContactPerson());
			pstmt.setDouble(5, hotel.getMinCharge());
			pstmt.setDouble(6, hotel.getMaxCharge());
			pstmt.setInt(7, hotel.getCountryID());
			pstmt.setInt(8, hotel.getHotelID());

			flag = pstmt.executeUpdate() > 0;
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


	public List<Hotel> getHotelList() {
		List<Hotel> list = new ArrayList<>();
		boolean flag = false;
		Connection con = null;

		try {
			con = getConnection();

			String sql = "SELECT h.*,cm.cnt_name from HotelMaster h  "
					+ " INNER JOIN Countries_Master cm ON cm.cnt_id=h.countryid "
					+ " ORDER BY cm.cnt_name asc,h.hotelname asc ";

			PreparedStatement st = con.prepareStatement(sql);

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


	public List<Hotel> getHotelList(int countryFilter) {
		List<Hotel> list = new ArrayList<>();
		boolean flag = false;
		Connection con = null;

		try {
			con = getConnection();

			String sql = "SELECT h.*,cm.cnt_name from HotelMaster h  "
					+ " INNER JOIN Countries_Master cm ON cm.cnt_id=h.countryid "
					+ " where h.countryid=? "
					+ " ORDER BY h.hotelname asc ";

			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, countryFilter);

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


	// select Particular Hotel
	public Hotel getHotel(int hotelId) {
		Hotel hotel = null;
		boolean flag = false;
		Connection con = null;

		try {
			con = getConnection();
			hotel = new Hotel();

			String sql = "SELECT h.*,cm.cnt_name from HotelMaster h "
					+ " INNER JOIN Countries_Master cm ON cm.cnt_id=h.countryid "
					+ " where h.hotelid=? ";
			;

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, hotelId);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
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
			e.printStackTrace();
			LoggerManager.writeLogWarning(e);
		} finally {
			try {
				if (con != null) con.close();

			} catch (Exception e) {
			}
		}
		return hotel;
	}

}
