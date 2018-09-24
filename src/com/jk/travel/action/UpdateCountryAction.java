package com.jk.travel.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jk.travel.dao.CountryDAO;
import com.jk.travel.model.Country;
import com.jk.travel.model.NotiMsg;

@WebServlet("/UpdateCountryAction")
public class UpdateCountryAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("/index.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		NotiMsg noti = new NotiMsg(NotiMsg.FAIL, "Country updation failed.");

		Country country = new Country();
		country.setCountryID(Integer.parseInt(request.getParameter("countryId")));
		country.setCountryName(request.getParameter("countryName"));
		country.setCountryFullName(request.getParameter("countryFullName"));
		country.setCountryDesc(request.getParameter("countryDesc"));
		country.setNationality(request.getParameter("nationality"));

		String target = "/admin/UpdateCountry.jsp?countryId=" + country.getCountryID();
		boolean flag = new CountryDAO().updateCountry(country);

		if (flag) {
			target = "/common/ViewCountries.jsp";
			noti.setOk("Country updated successfully.");
		}

		request.getSession().setAttribute("status", noti);
		response.sendRedirect(target);
	}

}
