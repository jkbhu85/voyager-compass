package com.jk.travel.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jk.travel.dao.ProfileDAO;
import com.jk.travel.model.NotiMsg;
import com.jk.travel.model.Profile;

@MultipartConfig
@WebServlet("/Register")
public class RegisterAction extends HttpServlet {
	private static final long serialVersionUID = 1L;


	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("/index.jsp");
	}


	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String target = "/admin/Registerform.jsp";
		NotiMsg noti = new NotiMsg(NotiMsg.FAIL, "Employee registration failed.");
		HttpSession session = request.getSession();

		Profile rb = new Profile();
		rb.setPhoto(request.getParameter("photo"));
		rb.setProfileImgPart(request.getPart("photo"));

		rb.setFirstName(request.getParameter("firstName"));
		rb.setLastName(request.getParameter("lastName"));

		rb.setBdate(request.getParameter("dob"));

		rb.setEmail(request.getParameter("email"));
		rb.setPhone(request.getParameter("PhoneNo"));
		rb.setGender(request.getParameter("gender"));

		rb.setDeptID(Integer.parseInt(request.getParameter("DepartmentID")));

		// home
		String home = request.getParameter("homeaddresstype");
		if (home != null) {
			rb.setHome(home);
			rb.setHno(request.getParameter("homehouseno"));
			rb.setStreet(request.getParameter("homestreet"));
			rb.setCity(request.getParameter("homecity"));
			rb.setState(request.getParameter("homestate"));
			rb.setCountry(request.getParameter("homecountry"));
			rb.setPin(request.getParameter("homepin"));
		}

		// contact
		String contact = request.getParameter("personaladdresstype");
		rb.setContact(contact);

		if ("1".equals(request.getParameter("sameAsPrm"))) {
			rb.setChno(request.getParameter("homehouseno"));
			rb.setCstreet(request.getParameter("homestreet"));
			rb.setCcity(request.getParameter("homecity"));
			rb.setCstate(request.getParameter("homestate"));
			rb.setCcountry(request.getParameter("homecountry"));
			rb.setCpin(request.getParameter("homepin"));
		}
		else {
			rb.setChno(request.getParameter("personalhouseno"));
			rb.setCstreet(request.getParameter("personalstreet"));
			rb.setCcity(request.getParameter("personalcity"));
			rb.setCstate(request.getParameter("personalstate"));
			rb.setCcountry(request.getParameter("personalcountry"));
			rb.setCpin(request.getParameter("personalpin"));
		}

		rb.setLoginID(request.getParameter("userName"));
		rb.setLoginType(request.getParameter("Desingation"));
		rb.setPassword(request.getParameter("password"));

		rb.setSecretQuestionID(request.getParameter("squest"));
		rb.setSecretAnswer(request.getParameter("secrete"));

		boolean flag = false;

		flag = new ProfileDAO().registration(rb);

		if (flag) {
			target = "/common/ViewEmployees.jsp";
			noti.setOk("Employee Registration successful.");
		}

		// adjust below line if you remove return statement from above
		session.setAttribute("status", noti);
		response.sendRedirect(target);
	}

}
