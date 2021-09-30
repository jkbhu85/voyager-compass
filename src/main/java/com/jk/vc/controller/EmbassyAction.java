package com.jk.vc.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jk.vc.dao.*;
import com.jk.vc.model.*;

@WebServlet("/EmbassyAction")
public class EmbassyAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("/index.jsp");
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String target = "/admin/AddEmbassy.jsp";
		NotiMsg noti = new NotiMsg(NotiMsg.FAIL, "Embassy addition failed.");

		Embassy embassy = new Embassy();
		embassy.setEmbassyName(request.getParameter("EmbassyName"));
		embassy.setEmbassyAddr(request.getParameter("Address"));
		embassy.setEmbassyContactPerson("");
		embassy.setPhoneNo(request.getParameter("PhoneNo"));
		embassy.setCountryID(Integer.parseInt(request.getParameter("CountryID")));

		boolean flag = new EmbassyDao().insertEmbassy(embassy);

		if (flag) {
			target = "/common/ViewEmbassies.jsp";
			noti.setOk("Embassy added successfully.");
		}

		request.getSession().setAttribute("status", noti);
		response.sendRedirect(target);
	}

}
