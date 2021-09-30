package com.jk.vc.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jk.vc.dao.*;
import com.jk.vc.model.*;

@WebServlet("/AddDeptAction")
public class AddDeptAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("/index.jsp");
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String target = "/admin/AddDept.jsp";
		NotiMsg noti = new NotiMsg(NotiMsg.FAIL, "Department addition failed.");

		Department dept = new Department();
		dept.setDepartmentName(request.getParameter("DeptName"));
		dept.setDepartmentAbbr(request.getParameter("DeptAbbr"));
		dept.setDepartmentInChgID(0);

		boolean flag = new DepartmentDAO().insertDept(dept);

		if (flag) {
			target = "/common/ViewDepartments.jsp";
			noti.setOk("Deparment added successfully.");
		}

		request.getSession().setAttribute("status", noti);
		response.sendRedirect(target);
	}

}
