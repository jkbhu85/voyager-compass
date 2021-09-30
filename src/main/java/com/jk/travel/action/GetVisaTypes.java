package com.jk.travel.action;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jk.travel.dao.VisaTypeDAO;
import com.jk.travel.model.VisaType;

@WebServlet("/GetVisaType")
public class GetVisaTypes extends HttpServlet {
	private static final long serialVersionUID = 1L;


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int countryId = Integer.parseInt(request.getParameter("countryId"));

		/*
		 * {staus:"ok"|"fail",data:[...]}
		 * {
		 * id:'',
		 * name:'',
		 * abbr:'',
		 * }
		 */
		StringBuilder text = new StringBuilder();
		String status = "ok";

		// start of the data array
		text.append("\"data\":[");
		List<VisaType> list = null;

		try {
			// System.out.println("Countryid: " + countryId);
			list = new VisaTypeDAO().findAllVisaTypesByCountryId(countryId);
		} catch (Exception e) {
			e.printStackTrace();
			status = "fail";
		}

		// filling data array with objects if list is not empty
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				if (i != 0)
					text.append(",");
				text.append(list.get(i).jsonValue());
			}
		}

		// end of the data array
		text.append("]");
		// System.out.println(text);

		response.setContentType("application/json");
		Writer writer = response.getWriter();
		writer.write("{\"status\":\"" + status + "\","); // {"status":"<status>",
		writer.write(text.toString());
		writer.write("}"); // end of response text
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
