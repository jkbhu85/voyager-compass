package com.jk.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.jk.core.db.DBFactory;
import com.jk.travel.dao.AbstractDAO;

@WebServlet(urlPatterns = { "/InitServlet" }, loadOnStartup = 0)
@WebInitParam(name = "config", value = "/WEB-INF/config/system.properties")
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	AbstractDAO dobject;


	@Override
	public void init(ServletConfig sc) {
		ServletContext ctx = sc.getServletContext();
		InputStream fis = ctx.getResourceAsStream("/WEB-INF/config/system.properties");
		Properties props = new Properties();

		try {
			props.load(fis);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		dobject = new AbstractDAO();
		dobject.setProperties(props);

		String logFileName = props.getProperty("logFileName");

		if (logFileName == null) {
			logFileName = "vc.log";
		}

		LoggerManager.logger = new LoggerManager().getLogger(logFileName);
		LoggerManager.writeLogInfo("Logger Instantiated");

		try {
			new DBFactory();
		} catch (NullPointerException npe) {
			LoggerManager.writeLogWarning("Connection to database FAILED");
		}

		/*  
		String vcImgDirPath = System.getenv("VCIMGPATH");
		
		if (vcImgDirPath == null) {
			LoggerManager.writeLogInfo(
				new RuntimeException("Fatal Error: Environment variable \"VCIMGPATH\" is not set."));
		}
		
		File vcImgDir = new File(vcImgDirPath);
		
		if (!vcImgDir.exists() || !vcImgDir.isDirectory()) {
			LoggerManager.writeLogInfo(new RuntimeException(
					"Fatal Error: Environment variable \"VCIMGPATH\" is not a directory.")
					);
		}
		
		if (!vcImgDir.canWrite()) {
			LoggerManager.writeLogInfo(new RuntimeException("Fatal Error: Environment variable"
					+ " \"VCIMGPATH\" points to a directory that can not be written."));
		}*/
	}
}
