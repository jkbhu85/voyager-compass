package com.jk.vc.controller;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import com.jk.vc.dao.*;
import com.jk.vc.mail.*;
import com.jk.vc.model.*;

@WebServlet("/RecoverPasswordAction")
public class RecoverPasswordAction extends HttpServlet {
	private static final long serialVersionUID = 1L;


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("index.jsp");
	}


	@SuppressWarnings("deprecation")
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String target = "RecoverPassword.jsp";
		NotiMsg noti = new NotiMsg(NotiMsg.FAIL, "Invalid recovery data.");

		String loginId = request.getParameter("username");
		String secAns = request.getParameter("sanswer");
		String secQues = null;

		if (request.getParameter("ch").equals("1")) {
			secQues = request.getParameter("ownquest");
		}
		else {
			secQues = request.getParameter("squest");
		}

		String password = new SecurityDao().recoverPasswordByQuestion(loginId, secQues, secAns);
		Profile profile = new ProfileDao().findProfile(loginId, request.getRealPath("/userimages"));
		String email = profile.getEmail();

		if (password != null && password.length() > 0) {
			if (email == null || email.length() == 0) {
				noti.setFail(
						"Password recovery failed. A valid email is not associated with this account.");
			}
			else {
				if (sendMail(email, loginId, password)) {
					noti.setOk("Password recovery email has been sent to your email account.");
				}
				else {
					noti.setFail("Error while recovering password. Try again later.");
				}
			}

		}

		request.getSession().setAttribute("status", noti);
		response.sendRedirect(target);
	}


	private boolean sendMail(String to, String loginId, String password) {
		boolean status = false;
		String subject = "Voyager Compass account password recovery";
		String text = "Hello,\n" +
				"You have requested password recovery for your Voyager Compass account."
				+ "The following are your login details:\n"
				+ "Login ID: " + loginId + "\n"
				+ "Login password: " + password + "\n\n\n\n"
				+ "This is an automatic generated email. Do not reply to this email.";

		Mailer mailer = Mailer.getInstance();
		status = mailer.sendMail(to, subject, text);

		return status;
	}

}
