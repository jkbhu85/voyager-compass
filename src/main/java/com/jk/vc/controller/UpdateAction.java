package com.jk.vc.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jk.vc.dao.*;
import com.jk.vc.model.*;

@MultipartConfig
@WebServlet("/UpdateAction")
public class UpdateAction extends HttpServlet {

	private static final long serialVersionUID = 1L;


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("index.jsp");
	}


	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String target = "/common/UpdateProfile.jsp";
		NotiMsg noti = new NotiMsg(NotiMsg.FAIL, "Updation Failed");

		try {
			String username = (String) request.getSession().getAttribute("user");
			ProfileData prfData = new ProfileData();

			prfData.setImgPart(request.getPart("photo"));
			prfData.setEmail(request.getParameter("email"));
			prfData.setMobile(request.getParameter("PhoneNo"));

			ProfileData.Address home = new ProfileData.Address();
			home.setLine1(request.getParameter("homehouseno"));
			home.setLine2(request.getParameter("homestreet"));
			home.setCity(request.getParameter("homecity"));
			home.setState(request.getParameter("homestate"));
			home.setCountry(request.getParameter("homecountry"));
			home.setPin(request.getParameter("homepin"));

			ProfileData.Address prs = null;
			if ("1".equals(request.getParameter("sameAsPrm"))) {
				prs = home;
			}
			else {
				prs = new ProfileData.Address();
				prs.setLine1(request.getParameter("personalhouseno"));
				prs.setLine2(request.getParameter("personalstreet"));
				prs.setCity(request.getParameter("personalcity"));
				prs.setState(request.getParameter("personalstate"));
				prs.setCountry(request.getParameter("personalcountry"));
				prs.setPin(request.getParameter("personalpin"));
			}

			// System.out.println("Prs house: " +
			// request.getParameter("personalhouseno"));
			// System.out.println("Same as prm: " +
			// request.getParameter("sameAsPrm"));
			// System.out.println("Current addr: " + prs);

			prfData.setList(new ProfileData.Address[] { home, prs });
			prfData.setLoginId((String) request.getSession().getAttribute("user"));

			boolean flag = new ProfileDao().updateProfile(prfData);

			if (flag) {
				noti.setOk("Profile updated successfully.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		request.getSession().setAttribute("status", noti);
		response.sendRedirect(target);
	}
}
