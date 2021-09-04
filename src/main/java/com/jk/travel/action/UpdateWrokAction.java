package com.jk.travel.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jk.travel.dao.WorkDAO;
import com.jk.travel.model.NotiMsg;

@WebServlet("/UpdateWorkAction")
public class UpdateWrokAction extends HttpServlet {
	private static final long serialVersionUID = 1L;


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("/index.jsp");
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String desc = request.getParameter("desc");
		int status = Integer.parseInt(request.getParameter("status"));
		int workId = Integer.parseInt(request.getParameter("workId"));

		boolean result = false;
		NotiMsg noti = new NotiMsg(NotiMsg.FAIL, "Work updation failed.");

		if (status == 0 || workId == 0) {
			response.sendRedirect("/Error.jsp");
			return;
		}

		result = new WorkDAO().update(workId, status, desc);
		String target = "/admin/UpdateWork?workId=?" + workId;

		if (result) {
			target = "/common/ViewTravelPlan.jsp?workId=" + workId;
			noti.setOk("Work updated successfully.");
		}

		request.getSession().setAttribute("status", noti);
		response.sendRedirect(target);
	}
}
