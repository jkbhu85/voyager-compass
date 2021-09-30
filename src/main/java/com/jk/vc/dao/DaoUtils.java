package com.jk.vc.dao;

import java.sql.*;

public final class DaoUtils {

	// no instantiation
	private DaoUtils() {
	}

	public static void rollback(Connection con)
	{
		if (con != null) {
			try {
				con.rollback();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void closeCon(Connection con)
	{
		if (con != null) {
			try {
				con.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
