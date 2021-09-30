package com.jk.vc.dao;

import java.io.*;
import java.sql.*;
import java.util.*;

import javax.sql.*;

import com.jk.core.util.*;
import com.zaxxer.hikari.*;

public class AbstractDAO {

	private static final int DEFAULT_MAX_POOL_SIZE = 3;
	private static final int DEFAULT_MIN_IDLE_CONNECTIONS = 1;

	private static final Object LOCK = new Object();
	private static Properties mProps;
	private static DataSource dataSource;

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

	static InputStream findResourceStream()
	{
		InputStream in = AbstractDAO.class
				.getResourceAsStream("application.properties");
		if (in == null) {
			return AbstractDAO.class
					.getResourceAsStream("/application.properties");
		}
		return in;
	}

	public Connection getConnection()
	{
		if (dataSource == null) {
			dataSource = createDataSource();
		}

		try {
			return dataSource.getConnection();
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private static DataSource createDataSource()
	{
		synchronized (LOCK) {
			Properties props = getProperties();

			HikariConfig hc = new HikariConfig();
			hc.setDriverClassName(props.getProperty("vc.ds.driver-class-name"));
			hc.setJdbcUrl(props.getProperty("vc.ds.jdbc-url"));
			hc.setUsername(props.getProperty("vc.ds.username"));
			hc.setPassword(props.getProperty("vc.ds.password"));
			hc.setMaximumPoolSize(maxPoolSize());
			hc.setMinimumIdle(minIdleConnections());
			return new HikariDataSource(hc);
		}
	}

	private static int maxPoolSize()
	{
		Properties props = getProperties();
		if (props.getProperty("vc.ds.max-pool-size") != null)
			return Integer.parseInt(props.getProperty("vc.ds.max-pool-size"));
		return DEFAULT_MAX_POOL_SIZE;
	}
	
	private static int minIdleConnections() {
		Properties props = getProperties();
		if (props.getProperty("vc.ds.min-idle") != null)
			return Integer.parseInt(props.getProperty("vc.ds.min-idle"));
		return DEFAULT_MIN_IDLE_CONNECTIONS;
	}

}
