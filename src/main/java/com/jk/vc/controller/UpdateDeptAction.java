package com.jk.vc.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jk.vc.dao.*;
import com.jk.vc.model.*;

@WebServlet("/UpdateDeptAction")
public class UpdateDeptAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("/index.jsp");
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String target = "/admin/UpdateDepartment.jsp";
		NotiMsg noti = new NotiMsg(NotiMsg.FAIL, "Department updation failed.");

		Department dept = new Department();
		dept.setDepartmentID(Integer.parseInt(request.getParameter("deptno")));
		dept.setDepartmentName(request.getParameter("DeptName"));
		dept.setDepartmentAbbr(request.getParameter("DeptAbbr"));
		dept.setDepartmentInChgID(Integer.parseInt(request.getParameter("EmpID")));

		boolean flag = new DepartmentDAO().updateDept(dept);

		if (flag) {
			target = "/common/ViewDepartments.jsp";
			noti.setOk("Deparment updated successfully");
		}

		request.getSession().setAttribute("status", noti);
		response.sendRedirect(target);
	}

}
