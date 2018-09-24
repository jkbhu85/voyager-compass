package com.jk.travel.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jk.travel.dao.SecurityDAO;

public class ChekUserAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("userName");
		String target = "Registerform.jsp?status1=Invalid username and password";
		try {

			String user = new SecurityDAO().checkUser(username);

			if (user.equals(null)) {
				target = "Registerform.jsp?status1=<font color=green>Available</font>";
			}

			else
				target = "Registerform.jsp?status1=<font color=red>Already exists</font>&userName=" + username;
		} catch (Exception e) {
			target = "Registerform.jsp?status1=<font color=green><b>Available</b></font>&userName=" + username;
		}
		RequestDispatcher rd = request.getRequestDispatcher(target);
		rd.forward(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
