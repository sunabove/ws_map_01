package com.ynhenc.gis.mapsvr;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

import com.ynhenc.gis.web.*;

public class JspService_001_MapRegistry extends JspService_000_MapObject {

	public JspService_001_MapRegistry( HttpServletRequest request) {
		super( request);
	}

	@Override
	public void getService() {
		Request req = this.getRequest();

		MapRegistry mapRegistry = this.getMapRegistry();

		String filePath = req.getParameter("filePath");

		if (filePath != null && filePath.length() > 0) {
			boolean b = mapRegistry.setGisProjectFilePath(filePath);

			req.setAttribute("result", "������Ʈ ���� ��� " + b + " !");

			// ���ο� gis project�� ����Ѵ�.
			this.getMapRegistry();
		}

		int load = req.getInt("load", -1);

		if (load > 0) {
			boolean loadResult = mapRegistry.getLoadGisProject();

			super.initService();

			req.setAttribute("load", loadResult);
		}
	}

}
