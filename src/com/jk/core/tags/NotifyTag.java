package com.jk.core.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class NotifyTag extends SimpleTagSupport {
	private String attr;
	private String successMsg;
	private String failureMsg;
	private String scope;
	private boolean useOnce;

	@Override
	public void doTag() throws JspException, IOException {
		PageContext pageContext = (PageContext) getJspContext();
		int attrScope = getScopeValue();
		String attrValue = (String) pageContext.getAttribute(attr, attrScope);

		// if attrValue is null then do nothing
		if (attrValue == null)
			return;

		String msg;
		if ("ok".equals(attrValue)) {
			msg = "<div class=\"notif success\" id=\"notification\">" + successMsg;
		} else {
			msg = "<div class=\"notif failure\" id=\"notification\">" + failureMsg;
		}
		msg += "</div>";

		getJspContext().getOut().println(msg);

		if (useOnce) {
			pageContext.removeAttribute(attr, attrScope);
		}
	}

	private int getScopeValue() {
		int attrScope;

		switch (scope) {
		case "request":
			attrScope = PageContext.REQUEST_SCOPE;
			break;
		case "page":
			attrScope = PageContext.PAGE_SCOPE;
			break;
		case "session":
			attrScope = PageContext.SESSION_SCOPE;
			break;
		case "application":
			attrScope = PageContext.APPLICATION_SCOPE;
			break;
		default:
			throw new RuntimeException("Value of scope attribute is invalid.");
		}

		return attrScope;
	}

	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

	public String getSuccessMsg() {
		return successMsg;
	}

	public void setSuccessMsg(String successMsg) {
		this.successMsg = successMsg;
	}

	public String getFailureMsg() {
		return failureMsg;
	}

	public void setFailureMsg(String failureMsg) {
		this.failureMsg = failureMsg;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public boolean isUseOnce() {
		return useOnce;
	}

	public void setUseOnce(boolean useOnce) {
		this.useOnce = useOnce;
	}
}
