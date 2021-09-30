package com.jk.vc.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jk.vc.dao.*;
import com.jk.vc.model.*;

@WebServlet("/ViewTravelAction")
public class ViewTravelAction extends HttpServlet {
	private static final long serialVersionUID = 1L;


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String target = "/common/ViewTravelPlan.jsp";

		int workId = Integer.parseInt(request.getParameter("workId"));
		Work work = new WorkDao().get(workId);

		if (work == null) {
			response.sendRedirect("/NotFound.jsp");
			return;
		}

		request.setAttribute("work", work);

		TravelMasterDao tm = new TravelMasterDao();
		Travel travel = tm.findTravelById(tm.findTravelIdByWorkId(work.getWorkId()));

		if (travel != null) {
			TravelTicketDao ttd = new TravelTicketDao();
			StayDao sdao = new StayDao();

			String ticketId = ttd.getTicketIdFromTravel(travel.getTravelId()) + "";
			TravelTicket ticket = ttd.findTicketById(ticketId);

			int stayId = sdao.getStayIdFromTravel(travel.getTravelId() + "");
			Stay stay = sdao.findStayById(stayId);

			ProfileDao profileDb = new ProfileDao();
			@SuppressWarnings("deprecation")
			Profile emp = profileDb.findProfile(profileDb.findLoginIdByEmployeeId(travel.getEmpId()),
					request.getRealPath("/userimages"));

			request.setAttribute("emp", emp);
			request.setAttribute("travel", travel);
			request.setAttribute("ticket", ticket);
			request.setAttribute("stay", stay);

			if (stay != null) {
				request.setAttribute("hotel", new HotelDao().findHotelById(stay.getHotelId()));
			}

			if (emp != null) {
				request.setAttribute("dept", new DepartmentDao().findDeptatmentById(emp.getDeptID()));
			}
		}

		request.getRequestDispatcher(target).forward(request, response);
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
