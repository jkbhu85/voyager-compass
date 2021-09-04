package com.jk.core.db;

import com.jk.travel.dao.AbstractDAO;

public class DBFactory
{
	public DBFactory()
	{
		new AbstractDAO().getConnection();
	}
}
