package com.jk.travel.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jk.travel.dao.SecurityDAO;
import com.jk.travel.model.NotiMsg;
import com.jk.travel.model.Profile;

@WebServlet("/LoginAction")
public class LoginAction extends HttpServlet {
	private static final long serialVersionUID = 1L;


	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String target = "/index.jsp";
		NotiMsg noti = new NotiMsg(NotiMsg.FAIL, "Invalid username or password.");

		try {
			HttpSession session = request.getSession();
			String username = request.getParameter("username");
			String pass = request.getParameter("password");

			Profile rb = new Profile();
			rb.setLoginID(username);
			rb.setPassword(pass);

			String role = new SecurityDAO().loginCheck(rb);

			if (role != null && role.length() > 0) {
				session.setAttribute("user", username);
				session.setAttribute("role", role);
				session.setAttribute("pass", pass);

				if (role.equals("admin")) {
					target = "/admin/AdminHome.jsp";
				}
				else if (role.equals("employee")) {
					target = "/emp/EmpHome.jsp";
				}

				noti = null;
			}
		} catch (Exception e) {

		}

		request.getSession().setAttribute("status", noti);
		response.sendRedirect(target);
	}

}
