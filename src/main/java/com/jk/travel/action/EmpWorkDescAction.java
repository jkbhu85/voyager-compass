package com.jk.travel.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jk.travel.dao.WorkDAO;
import com.jk.travel.model.NotiMsg;
import com.jk.travel.model.Work;

@WebServlet("/AddWork")
public class EmpWorkDescAction extends HttpServlet {
	private static final long serialVersionUID = 1L;


	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("/index.jsp");
	}


	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		NotiMsg noti = new NotiMsg(NotiMsg.FAIL, "Work addition failed.");
		String target = "/admin/AddWork.jsp";

		Work work = new Work();

		work.setTitle(request.getParameter("Title"));
		work.setDescription(request.getParameter("Desc"));
		work.setRespb(request.getParameter("Response"));
		work.setInchId(Integer.parseInt(request.getParameter("InchargeID")));
		work.setCntId(Integer.parseInt(request.getParameter("CountryID")));
		work.setStatusId(Work.STATUS_UNPREPARED);

		boolean flag = new WorkDAO().insertWork(work);

		if (flag) {
			noti.setOk("Work description added successfully.");
			target = "/admin/ViewAllWorks.jsp";
		}

		request.getSession().setAttribute("status", noti);
		response.sendRedirect(target);
	}

}
