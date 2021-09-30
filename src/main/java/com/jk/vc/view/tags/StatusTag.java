package com.jk.vc.view.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.jk.vc.model.*;

public class StatusTag extends SimpleTagSupport {
	private String var;

	@Override
	public void doTag()
			throws JspException, IOException {
		PageContext pageContext = (PageContext) getJspContext();
		NotiMsg noti = (NotiMsg) pageContext.getAttribute(var, PageContext.SESSION_SCOPE);

		if (noti != null) {
			pageContext.getOut().print(noti.getHtmlMsg());
			pageContext.removeAttribute(var, PageContext.SESSION_SCOPE);
		}
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}
}
