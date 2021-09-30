package com.jk.travel.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jk.core.util.DateWrapper;
import com.jk.travel.dao.TravelMasterDAO;
import com.jk.travel.dao.TravelTicketDAO;
import com.jk.travel.model.NotiMsg;
import com.jk.travel.model.TravelTicket;

@WebServlet("/AddTicket")
public class TravelTicketAction extends HttpServlet {
	private static final long serialVersionUID = 1L;


	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("/index.jsp");
	}


	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String target = "/admin/AddTravelTicket.jsp";
		NotiMsg noti = new NotiMsg(NotiMsg.FAIL, "Travel ticket addition failed.");

		TravelTicket ticket = new TravelTicket();
		ticket.setTravelID(Integer.parseInt(request.getParameter("TravelID")));
		ticket.setTicketNo(request.getParameter("TicketNumber"));
		ticket.setTicketBookDate(DateWrapper.parseDate(request.getParameter("bookingDate")));
		ticket.setTicketAvailableDate(DateWrapper.parseDate(request.getParameter("availabledate")));
		ticket.setTicketFrom(request.getParameter("TicketFrom"));
		ticket.setTicketTo(request.getParameter("TicketTo"));
		ticket.setSeatNo(request.getParameter("SeatNo"));
		ticket.setFlightName(request.getParameter("FTName"));
		ticket.setTravelAgetName(request.getParameter("AgentName"));
		ticket.setInchargeContactNo(request.getParameter("ContactNo"));
		ticket.setJourneytime(request.getParameter("journeyTime"));
		ticket.setVehicleNo(request.getParameter("vehicleNo"));

		boolean flag = new TravelTicketDAO().insertTicket(ticket);

		if (flag) {
			int workId = new TravelMasterDAO().findTravelById(ticket.getTravelID()).getWorkId();
			target = "/ViewTravelAction?workId=" + workId;
			noti.setOk("Ticket addedd successfully.");
		}

		request.getSession().setAttribute("status", noti);
		response.sendRedirect(target);

	}
}
