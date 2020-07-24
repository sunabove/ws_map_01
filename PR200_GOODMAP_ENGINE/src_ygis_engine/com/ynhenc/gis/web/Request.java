package com.ynhenc.gis.web;

import java.util.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.comm.util.*;
import com.ynhenc.gis.*;
import com.ynhenc.gis.model.map.*;
import com.ynhenc.gis.model.shape.*;
import com.ynhenc.gis.projection.*;
import com.ynhenc.gis.web.*;

public class Request extends GisComLib implements HttpContentHeaderNameInterface {

	public String getContextPath() {
		return this.getHttpServletRequest().getContextPath();
	}

	public MapSession getMapSeesion() {
		int mapNo = -1;

		try {
			mapNo = this.getInt("mapNo", -1);
			if (mapNo < 0) {
				mapNo = 0;
				this.setAttribute("mapNo", mapNo);
			}
		} catch (Exception e) {
			return MapSession.nullMapSesion;
		}

		MapSession mapSession = this.mapSession;

		if (mapSession == null) {
			Session session = this.getSession();
			double mapSessionNo = -1;
			if (session != null) {
				mapSessionNo = session.getSessionNo() + ((mapNo + 0.0) / 100000.0);
			}

			mapSession = MapSession.getMapSession(mapSessionNo, this);

			this.mapSession = mapSession;
		}

		return mapSession;
	}

	public void setAttributeToSession(String name, Object o) {

		if (!this.getSession().setAttribute(name, o)) {
			this.setAttribute(name, o);
		}
	}

	public void setAttribute(String name, Object o) {
		this.getHttpServletRequest().setAttribute(name, o);
	}

	public Object getAttribute(String name) {
		return this.getHttpServletRequest().getAttribute(name);
	}

	public PntShort getPointConverted(String mapX, String mapY, GisProject gisProject) {

		Request request = this;
		Double x = request.getDouble(mapX, null);
		Double y = request.getDouble(mapY, null);

		if (x == null || y == null) {
			return null;
		} else {
			PntShort point = PntShort.pntShort(x, y);
			CoordinateConversion coordinateConversion = gisProject.getCoordinateConversion();

			if (coordinateConversion != null) {
				return coordinateConversion.getConvertValue(point);
			} else {
				return point;
			}
		}
	}

	public GeoPoint getGeoPointConverted(String mapX, String mapY, GisProject gisProject) {
		PntShort point = this.getPointConverted(mapX, mapY, gisProject);
		if (point != null) {
			return new GeoPoint(point.getX(), point.getY());
		} else {
			return null;
		}
	}

	public String getParameter(String name) {
		String val = this.request.getParameter(name);
		if (val == null) {
			return "";
		} else if (true) {
			try {
				return new String(val.getBytes("UTF-8"), "UTF-8").trim();
			} catch (UnsupportedEncodingException e) {
				this.debug(e);
				return val.trim();
			}
		} else if (true) {
			try {
				return new String(val.getBytes("8859_1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				this.debug(e);
				return val.trim();
			}
		} else if (true) {
			return val.trim();
		} else {
			try {

				boolean localDebug = false;

				String convVal;

				String charset = this.request.getParameter("charset");
				charset = charset != null ? charset.toUpperCase() : charset;

				if (charset == null || charset.trim().length() < 1) {
					java.net.URLDecoder decoder = new URLDecoder();
					convVal = decoder.decode(val, "UTF-8");
					if (localDebug) {
						this.debug("DECDING URL - UTF .... ORG = " + val + ", CONV = " + convVal);
					}
				} else {
					convVal = new String(val.getBytes("8859_1"), "UTF-8");
					if (localDebug) {
						this.debug("DECDING UTF .... ORG = " + val + ", CONV = " + convVal);
					}
				}

				return convVal.trim();

			} catch (UnsupportedEncodingException ex) {
				debug.println(this, ex);
				return val.trim();
			}
		}
	}

	public String getParameter(String name, String def) {
		String val = this.getParameter(name);
		if (val == null || val.length() < 1) {
			return def;
		} else {
			return val;
		}
	}

	public Long getLong(String name) {

		try {
			return Long.valueOf(this.getParameter(name));
		} catch (NumberFormatException ex) {
			return null;
		}

	}

	public Enumeration getParameterNames() {
		return this.request.getParameterNames();
	}

	public String getString(String name) {
		return this.getParameter(name);
	}

	public String getString(String name, String def) {
		return this.getParameter(name, def);
	}

	public String getRealPath(String path) {
		return this.context.getRealPath(path);
	}

	public Integer getInteger(String name) {

		try {
			return (int) (Double.parseDouble(this.getParameter(name).trim()));
		} catch (NumberFormatException ex) {
			return null;
		}

	}

	public Integer getInteger(String name, Integer def) {

		Integer i = this.getInteger(name);
		if (i == null) {
			return def;
		} else {
			return i;
		}

	}

	public Integer getInteger(String name, int def) {

		return this.getInteger(name, new Integer(def));

	}

	public int getInt(String name, int def) {
		Integer i = this.getInteger(name);
		if (i == null) {
			return def;
		} else {
			return i.intValue();
		}
	}

	public Double getDouble(String name) {
		return this.getDouble(name, null);
	}

	public Double getDouble(String name, Double def) {

		try {
			return Double.valueOf(this.getParameter(name));
		} catch (NumberFormatException ex) {
			return def;
		}

	}

	@Override
	public Color getColor(String name) {
		return ColorUtil.fromHtmlColor(this.getString(name));
	}

	@Override
	public Color getColor(String name, Color def) {
		Color color = this.getColor(name);
		return color != null ? color : def;
	}

	public Boolean getBoolean(String name) {

		try {
			return new Boolean(this.getParameter(name));
		} catch (Exception e) {
			return false;
		}

	}

	public Session getSession() {
		if (this.session == null) {
			HttpSession httpSession = null;
			if (this.request != null) {
				httpSession = this.request.getSession(true);
			}
			this.session = SessionManager.getSession(httpSession);
			return this.session;
		} else {
			return this.session;
		}
	}

	public int getParamentersLength() {
		return this.request.getParameterMap().size();
	}

	public void showAllInfo() {
		HttpServletRequest request = this.request;

		this.debug("REQ URI = " + request.getRequestURI());

		java.util.Enumeration it = request.getParameterNames();

		String name;

		while (it.hasMoreElements()) {
			name = (String) (it.nextElement());
			this.debug("NAME = " + name + ", VALUE = " + request.getParameter(name));
		}
	}

	public HttpServletRequest getHttpServletRequest() {
		return this.request;
	}

	public String getMethod() {
		return this.request.getMethod();
	}

	private Request() {
	}

	protected Request(HttpServletRequest request) {
		this.request = request;
		this.context = request.getSession().getServletContext();
	}

	// member

	private Session session;
	private MapSession mapSession;
	private HttpServletRequest request;
	private ServletContext context;

	// end of member

	// static member

	public static Request createRequest(HttpServletRequest req) {
		String contentType = req.getHeader(CONTENT_TYPE);

		if (contentType == null) {
			return new Request(req);
		} else if (contentType.startsWith(MULTIPART)) {
			return new MultiPartRequest(req);
		} else {
			return new Request(req);
		}
	}

	public static Request getNulRequest() {
		return nullRequest;
	}

	private static final Request nullRequest = new Request();
	// end of static member

}
