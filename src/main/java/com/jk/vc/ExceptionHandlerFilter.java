package com.jk.vc;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import org.slf4j.*;

@WebFilter(urlPatterns = "/*")
public class ExceptionHandlerFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExceptionHandlerFilter.class);

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException
	{
		try {
			chain.doFilter(req, res);
		}
		catch (Exception e) {
			LOGGER.error("Unhandled exception.", e);
			HttpServletRequest httpReq = (HttpServletRequest) req;
			httpReq.getRequestDispatcher("/Error.jsp").forward(req, res);
		}
	}

}
