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

@WebServlet("/AddCountryAction")
public class CountryAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("/index.jsp");
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String target = "/admin/AddCountry.jsp";
		NotiMsg noti = new NotiMsg(NotiMsg.FAIL, "Country addition failed.");

		Country country = new Country();
		country.setCountryName(request.getParameter("countryName"));
		country.setCountryFullName(request.getParameter("countryFullName"));
		country.setCountryDesc(request.getParameter("countryDesc"));
		country.setNationality(request.getParameter("nationality"));

		boolean flag = new CountryDAO().insertCountry(country);

		if (flag) {
			target = "/common/ViewCountries.jsp";
			noti.setOk("Country added successfully.");
		}

		request.getSession().setAttribute("status", noti);
		response.sendRedirect(target);
	}

}
