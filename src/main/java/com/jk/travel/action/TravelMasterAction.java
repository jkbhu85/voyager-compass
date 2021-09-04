package com.jk.travel.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jk.travel.dao.TravelMasterDAO;
import com.jk.travel.model.NotiMsg;
import com.jk.travel.model.Travel;

@WebServlet("/TravelMasterAction")
public class TravelMasterAction extends HttpServlet {
	private static final long serialVersionUID = 1L;


	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}


	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		NotiMsg noti = new NotiMsg(NotiMsg.FAIL, "Travel addition failed.");

		Travel travel = new Travel();
		travel.setEmpId(Integer.parseInt(request.getParameter("empId")));
		travel.setStartDate(request.getParameter("StartDate"));
		travel.setEndDate(request.getParameter("EndDate"));
		travel.setInst(request.getParameter("Instruction"));
		travel.setWorkId(Integer.parseInt(request.getParameter("WorkId")));
		travel.setVisaIssueDate(request.getParameter("VisaIssueDate"));
		travel.setVisaType(request.getParameter("visaTypeId"));

		TravelMasterDAO t = new TravelMasterDAO();
		System.out.println(travel);

		boolean flag = t.insert(travel);
		String target = "/admin/TravelMaster.jsp?WorkId=" + travel.getWorkId();

		if (flag) {
			target = "/ViewTravelAction?workId=" + travel.getWorkId();
			noti.setOk("Travel added successfully.");
		}

		request.getSession().setAttribute("status", noti);
		response.sendRedirect(target);
	}

}
