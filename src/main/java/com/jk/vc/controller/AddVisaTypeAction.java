package com.jk.vc.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jk.vc.dao.*;
import com.jk.vc.model.*;

@WebServlet("/AddVisaTypeAction")
public class AddVisaTypeAction extends HttpServlet {
	private static final long serialVersionUID = 1L;


	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("/index.jsp");
	}


	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String target = "/admin/AddVisaType.jsp";
		NotiMsg noti = new NotiMsg(NotiMsg.FAIL, "Visa Type addtion failed.");

		VisaType visaType = new VisaType();
		visaType.setCntVtFk(Integer.parseInt(request.getParameter("CountryID")));
		visaType.setVisaTypeName(request.getParameter("VisaTypeName"));
		visaType.setVisaTypeAbbr(request.getParameter("VisaTypeAbbr"));
		visaType.setVisaTypeDesc(request.getParameter("VisaTypeDesc"));
		visaType.setVisaEleg(request.getParameter("VisaTypeEleg"));
		visaType.setMaxDuration(Integer.parseInt(request.getParameter("MaxDur")));
		visaType.setStampGuide(request.getParameter("StampGuide"));
		visaType.setReqAdv(Integer.parseInt(request.getParameter("ReqAdv")));

		boolean flag = new VisaTypeDao().insertVisaType(visaType);

		if (flag) {
			target = "/common/ViewVisaTypes.jsp";
			noti.setOk("Visa Type added successfully");
		}

		request.getSession().setAttribute("status", noti);
		response.sendRedirect(target);
	}

}
