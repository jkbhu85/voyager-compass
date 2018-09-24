package com.jk.travel.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jk.travel.dao.VisaDAO;
import com.jk.travel.model.NotiMsg;
import com.jk.travel.model.Visa;

@WebServlet("/AddVisaAction")
public class AddVisaAction extends HttpServlet {
	private static final long serialVersionUID = 1L;


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("/index.jsp");
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		NotiMsg noti = new NotiMsg(NotiMsg.FAIL, "Visa addition failed.");

		Visa visa = new Visa();

		visa.setPptId(Integer.parseInt(request.getParameter("passportId")));
		visa.setVisaTypeId(Integer.parseInt(request.getParameter("visaTypeId")));

		visa.setVisaNo(request.getParameter("visaNo"));

		visa.setIssueDate(request.getParameter("issueDate"));
		visa.setExpireDate(request.getParameter("expireDate"));

		String maxVisits = request.getParameter("maxVisits");

		visa.setMaxVisits(maxVisits.equals("M") ? -1 : 0);
		visa.setVisitCount(Integer.parseInt(request.getParameter("visitCount")));

		visa.setPlaceIssued(request.getParameter("placeIssued"));
		visa.setComments(request.getParameter("comments"));

		visa.setVisaCost(request.getParameter("cost"));

		System.out.println(visa);

		boolean status = new VisaDAO().addVisa(visa);
		String target = "/admin/AddVisa.jsp?pptId=" + visa.getPptId();

		if (status) {
			target = "/index.jsp";
			noti.setOk("Visa added successfully.");
		}

		request.getSession().setAttribute("status", noti);
		response.sendRedirect(target);
	}

}
