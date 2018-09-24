package com.jk.travel.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jk.travel.dao.HotelDAO;
import com.jk.travel.model.Hotel;
import com.jk.travel.model.NotiMsg;

@WebServlet("/HotelAction")
public class HotelAction extends HttpServlet {
	private static final long serialVersionUID = 1L;


	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}


	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		NotiMsg noti = new NotiMsg(NotiMsg.FAIL, "Hotel addition failed.");
		String target = "/admin/AddHotel.jsp";

		Hotel hotel = new Hotel();
		hotel.setHotelName(request.getParameter("HotelName"));
		hotel.setHotelAddr(request.getParameter("HotelAddress"));
		hotel.setHotelContactPerson("");
		hotel.setHotelPhno(request.getParameter("PhoneNo"));
		hotel.setMinCharge(Integer.parseInt(request.getParameter("MinCharge")));
		hotel.setMaxCharge(Integer.parseInt(request.getParameter("MaxCharge")));
		hotel.setCountryID(Integer.parseInt(request.getParameter("CountryID")));

		boolean flag = new HotelDAO().insertHotel(hotel);

		if (flag) {
			noti.setOk("Hotel added successfully.");
			target = "/common/ViewHotels.jsp";
		}

		request.getSession().setAttribute("status", noti);
		response.sendRedirect(target);
	}
}
