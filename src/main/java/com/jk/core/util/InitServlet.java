package com.jk.core.util;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import com.jk.vc.dao.*;

@WebServlet(urlPatterns = { "/InitServlet" }, loadOnStartup = 0)
@WebInitParam(name = "config", value = "/WEB-INF/config/system.properties")
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ConnectionUtils dobject;


	@Override
	public void init(ServletConfig sc) {

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
