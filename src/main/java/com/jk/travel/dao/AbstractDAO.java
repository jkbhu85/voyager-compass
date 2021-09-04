package com.jk.travel.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.jk.core.util.LoggerManager;

public class AbstractDAO {
	private Connection mCon;
	private static Properties mProps;


	public Properties getProperties() {
		return mProps;
	}


	public void setProperties(Properties aProps) {
		mProps = aProps;
	}


	public Connection getConnection() {
		try {
			Properties aProps = getProperties();
			Class.forName(aProps.getProperty("driver"));

			mCon = DriverManager.getConnection(aProps.getProperty("url"),
					aProps.getProperty("duser"), aProps.getProperty("dpass"));

		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
			LoggerManager.writeLogWarning(cnfe);
		} catch (SQLException se) {
			se.printStackTrace();
			LoggerManager.writeLogWarning(se);
		}

		return mCon;
	}


	public int getSequenceID(String tableName, String pkid) {
		int num = 0;
		Connection con = null;
		boolean exStatus = false; // whether exception occurred

		try {
			con = getConnection();
			String sql = "select (nvl(max(" + pkid + "),0)+1) from " + tableName;

			PreparedStatement getMaxNum = con.prepareStatement(sql);

			ResultSet rs = getMaxNum.executeQuery();

			if (rs.next()) {
				num = rs.getInt(1);
			}
		} catch (Exception e) {
			LoggerManager.writeLogWarning(e);
			exStatus = true;
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					LoggerManager.writeLogWarning(e);
				}
			}
		}

		if (exStatus) throw new RuntimeException("Exception occurred during sequence number retrieval.");

		return num + 1;
	}
}
