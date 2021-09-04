package com.jk.travel.action;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class PagePrivledge
 */
@WebFilter(
		dispatcherTypes = { DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ERROR
		}, urlPatterns = { "/admin/*" })
public class PagePrivledge implements Filter {

	public PagePrivledge() {
	}


	@Override
	public void destroy() {
	}


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String priv = (String) req.getSession().getAttribute("role");

		if (priv == null) {
			res.sendRedirect("/index.jsp");
			return;
		}

		if ("admin".equals(priv)) {
			chain.doFilter(request, response);
		}
		else {
			request.getRequestDispatcher("/NotFound.jsp").forward(request, response);
		}
	}


	@Override
	public void init(FilterConfig fConfig)
			throws ServletException {
	}

}
