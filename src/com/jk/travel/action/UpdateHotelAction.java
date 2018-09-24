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

@WebServlet("/UpdateHotelAction")
public class UpdateHotelAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("/index.jsp");
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		NotiMsg noti = new NotiMsg(NotiMsg.FAIL, "Hotel updation failed");

		Hotel hotel = new Hotel();
		hotel.setHotelID(Integer.parseInt(request.getParameter("HotelID")));

		hotel.setHotelName(request.getParameter("HotelName"));
		hotel.setHotelAddr(request.getParameter("HotelAddress"));
		hotel.setHotelContactPerson(request.getParameter("ContactPersonName"));
		hotel.setHotelPhno(request.getParameter("PhoneNo"));
		hotel.setMinCharge(Double.parseDouble(request.getParameter("MinCharge")));
		hotel.setMaxCharge(Double.parseDouble(request.getParameter("MaxCharge")));
		hotel.setCountryID(Integer.parseInt(request.getParameter("CountryID")));

		String target = "/admin/UpdateHotel.jsp?HotelID=" + hotel.getHotelID();

		boolean flag = new HotelDAO().updateHotel(hotel);

		if (flag) {
			target = "/common/ViewHotels.jsp";
			noti.setOk("Hotel updated successfully");
		}

		request.getSession().setAttribute("status", noti);
		response.sendRedirect(target);
	}

}
