package com.jk.travel.dao;

import java.io.*;
import java.sql.*;
import java.util.*;

import com.jk.core.util.*;

public class AbstractDAO {

	private static Properties mProps;

	public static Properties getProperties()
	{
		if (mProps == null) {
			mProps = new Properties();
			try {
				mProps.load(findResourceStream());
			}
			catch (IOException e) {
				LoggerManager.writeLogInfo(e);
			}
		}
		return mProps;
	}
	
	static InputStream findResourceStream() {
		InputStream in = AbstractDAO.class.getResourceAsStream("application.properties");
		if (in == null) {
			return AbstractDAO.class.getResourceAsStream("/application.properties");
		}
		return in;
	}

	public Connection getConnection()
	{
		try {
			Properties aProps = getProperties();
			String driverClassName = aProps.getProperty("vc.ds.driver-class-name");
			String jdbcUrl = aProps.getProperty("vc.ds.jdbc-url");
			String username = aProps.getProperty("vc.ds.username");
			String password = aProps.getProperty("vc.ds.password");
			
			Class.forName(driverClassName);
			return DriverManager.getConnection(jdbcUrl, username, password);
		}
		catch (Exception se) {
			LoggerManager.writeLogWarning(se);
		}

		return null;
	}

}
