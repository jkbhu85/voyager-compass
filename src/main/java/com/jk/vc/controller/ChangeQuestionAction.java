package com.jk.vc.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jk.vc.dao.*;
import com.jk.vc.model.*;

@WebServlet("/ChangeQuestionAction")
public class ChangeQuestionAction extends HttpServlet {
	private static final long serialVersionUID = 1L;


	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("/index.jsp");
	}


	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		NotiMsg noti = new NotiMsg(NotiMsg.FAIL, "Changing security question failed.");

		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute("user");
		String password = request.getParameter("password");
		String secAns = request.getParameter("sanswer");

		String secQues = request.getParameter("squest");

		boolean flag = new SecurityDao().updateSecretQuestion(loginId, password, secQues, secAns);

		if (flag) {
			noti.setOk("Security Question changed successfully.");
		}

		session.setAttribute("status", noti);
		response.sendRedirect("/common/ChangeQuestion.jsp");
	}

}
