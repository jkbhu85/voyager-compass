package com.jk.vc.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jk.vc.dao.*;
import com.jk.vc.model.*;

@WebServlet("/ChangePasswordAction")
public class ChangePasswordAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("/index.jsp");
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String target = "/common/ChangePassword.jsp";
		NotiMsg noti = new NotiMsg(NotiMsg.FAIL, "Password change unsuccessful.");

		String loginId = (String) request.getSession().getAttribute("user");
		String curPwd = request.getParameter("oldpassword");
		String newPwd = request.getParameter("newpassword");

		boolean flag = new SecurityDAO().updatePassword(loginId, curPwd, newPwd);

		if (flag) {
			noti.setOk("Password changed successfully.");
		}

		request.getSession().setAttribute("status", noti);
		response.sendRedirect(target);
	}
}
