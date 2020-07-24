package com.ynhenc.gis.mapsvr;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.gis.web.Request;

public abstract class JspService extends GisComLib {

	public ServletContext getApplication() {
		if( this.getSession() != null ) {
			return this.getSession().getServletContext();
		} else {
			return null;
		}
	}

	public HttpSession getSession() {
		if (this.getHttpRequest() != null) {
			return this.getHttpRequest().getSession();
		} else {
			return null;
		}
	}

	private HttpServletRequest getHttpRequest() {
		return this.httpRequest;
	}

	public Request getRequest() {

		if (this.request == null) {
			this.request = Request.createRequest(this.httpRequest );
		}

		return this.request;
	}

	public JspService( HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;

	}

	public String getRealPath(String path) {
		return this.getApplication().getRealPath(path);
	}

	public String getContextPath() {
		return this.getHttpRequest().getContextPath();
	}

	public abstract void getService();

	private HttpServletRequest httpRequest;

	private Request request;

}
