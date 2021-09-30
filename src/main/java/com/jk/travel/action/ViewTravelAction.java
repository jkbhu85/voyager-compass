package com.jk.travel.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jk.travel.dao.DepartmentDAO;
import com.jk.travel.dao.HotelDAO;
import com.jk.travel.dao.ProfileDAO;
import com.jk.travel.dao.StayDAO;
import com.jk.travel.dao.TravelMasterDAO;
import com.jk.travel.dao.TravelTicketDAO;
import com.jk.travel.dao.WorkDAO;
import com.jk.travel.model.Profile;
import com.jk.travel.model.Stay;
import com.jk.travel.model.Travel;
import com.jk.travel.model.TravelTicket;
import com.jk.travel.model.Work;

@WebServlet("/ViewTravelAction")
public class ViewTravelAction extends HttpServlet {
	private static final long serialVersionUID = 1L;


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String target = "/common/ViewTravelPlan.jsp";

		int workId = Integer.parseInt(request.getParameter("workId"));
		Work work = new WorkDAO().get(workId);

		if (work == null) {
			response.sendRedirect("/NotFound.jsp");
			return;
		}

		request.setAttribute("work", work);

		TravelMasterDAO tm = new TravelMasterDAO();
		Travel travel = tm.findTravelById(tm.findTravelIdByWorkId(work.getWorkId()));

		if (travel != null) {
			TravelTicketDAO ttd = new TravelTicketDAO();
			StayDAO sdao = new StayDAO();

			String ticketId = ttd.getTicketIdFromTravel(travel.getTravelId()) + "";
			TravelTicket ticket = ttd.findTicketById(ticketId);

			int stayId = sdao.getStayIdFromTravel(travel.getTravelId() + "");
			Stay stay = sdao.findStayById(stayId);

			ProfileDAO profileDb = new ProfileDAO();
			@SuppressWarnings("deprecation")
			Profile emp = profileDb.findProfile(profileDb.findLoginIdByEmployeeId(travel.getEmpId()),
					request.getRealPath("/userimages"));

			request.setAttribute("emp", emp);
			request.setAttribute("travel", travel);
			request.setAttribute("ticket", ticket);
			request.setAttribute("stay", stay);

			if (stay != null) {
				request.setAttribute("hotel", new HotelDAO().findHotelById(stay.getHotelId()));
			}

			if (emp != null) {
				request.setAttribute("dept", new DepartmentDAO().findDeptatmentById(emp.getDeptID()));
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
