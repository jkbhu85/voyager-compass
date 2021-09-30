package com.jk.travel.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jk.travel.dao.PassportDAO;
import com.jk.travel.model.NotiMsg;
import com.jk.travel.model.Passport;

@WebServlet("/AddPassportAction")
public class PassportAction extends HttpServlet {
	private static final long serialVersionUID = 1L;


	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("/index.jsp");
	}


	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		NotiMsg noti = new NotiMsg(NotiMsg.FAIL, "Passport addition failed.");
		Passport ppt = new Passport();

		ppt.setEmpId(Integer.parseInt(request.getParameter("empId")));
		ppt.setCntId(Integer.parseInt(request.getParameter("countryId")));
		ppt.setPptType(request.getParameter("passportType"));
		ppt.setPptNo(request.getParameter("passportNo"));
		ppt.setBirthPlace(request.getParameter("birthPlace"));
		ppt.setIssueDate(request.getParameter("issueDate"));
		ppt.setExpiryDate(request.getParameter("expireDate"));
		ppt.setPlaceIssued(request.getParameter("placeIssued"));
		ppt.setAddress(request.getParameter("pptAddr"));
		ppt.setComments(request.getParameter("pptComments"));

		boolean flag = new PassportDAO().insertPassport(ppt);

		String target = "/admin/AddPassport.jsp?empId=" + ppt.getEmpId();

		if (flag) {
			target = "/common/ViewEmployees.jsp";
			noti.setOk("Passport added successfully.");
		}

		request.getSession().setAttribute("status", noti);
		response.sendRedirect(target);
	}

}
