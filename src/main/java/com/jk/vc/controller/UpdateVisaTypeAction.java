package com.jk.vc.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jk.vc.dao.*;
import com.jk.vc.model.*;

@WebServlet("/UpdateVisaTypeAction")
public class UpdateVisaTypeAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("/index.jsp");
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		NotiMsg noti = new NotiMsg(NotiMsg.FAIL, "VisaType updation failed.");

		VisaType visaType = new VisaType();
		visaType.setVisaTypeID(Integer.parseInt(request.getParameter("VisaTypeID")));
		visaType.setCntVtFk(Integer.parseInt(request.getParameter("CountryID")));
		visaType.setVisaTypeName(request.getParameter("VisaTypeName"));
		visaType.setVisaTypeAbbr(request.getParameter("VisaTypeAbbr"));
		visaType.setVisaTypeDesc(request.getParameter("VisaTypeDesc"));
		visaType.setVisaEleg(request.getParameter("VisaTypeEleg"));
		visaType.setMaxDuration(Integer.parseInt(request.getParameter("MaxDur")));
		visaType.setStampGuide(request.getParameter("StampGuide"));
		visaType.setReqAdv(Integer.parseInt(request.getParameter("ReqAdv")));

		boolean flag = new VisaTypeDAO().updateVisaType(visaType);
		String target = "/admin/UpdateVisaType.jsp?VisaTypeID=" + visaType.getVisaTypeID();

		if (flag) {
			target = "/common/ViewVisaTypes.jsp";
			noti.setOk("VisaType updated successfully");
		}

		request.getSession().setAttribute("status", noti);
		response.sendRedirect(target);
	}

}
