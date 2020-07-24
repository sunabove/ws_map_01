package com.ynhenc.gis.web;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.JspWriter;

import com.ynhenc.comm.KernelInterface;
import com.ynhenc.comm.util.*;
import com.ynhenc.gis.*;

import java.awt.Color;
import java.awt.image.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class MapServlet extends HttpServlet implements KernelInterface {

	public int getServiceNo() {
		return serviceNo;
	}

	public void setServiceNo(int serviceNo) {
		this.serviceNo = serviceNo;
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doService(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doService(request, response);
	}

	public final void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Request req = Request.createRequest(request );

		MapService mapService = this.getMapService();

		try {
			this.sendToClient( mapService, req, response );
		} catch (Exception e) {
			this.debug.println(this, e);
		}

	}

	public void sendToClient(MapService mapService, Request req, HttpServletResponse response ) throws Exception {

		boolean localDebug = true;

		int serviceNo = this.getServiceNo();

		if (localDebug) {
			debug.println(this, "Starting a MapService[ " + serviceNo + "] ......");
		}

		this.serviceNo ++ ;

		try {

			BufferedImage image = mapService.getImage(req);

			OutputStream os = response.getOutputStream();

			ImageUtil imageUtil = new ImageUtil();

			String format = req.getString( "format" , "JPG" ).trim().toUpperCase() ;

			if( false ) {
				format = "GIF";
			}

			if( true ) { // 포맷 체크
				String formatList [] = { "PNG" , "JPG" , "JPEG" , "GIF" };

				boolean goodFormat = false;

				for( String formatOne : formatList ) {
					if( format.equals( formatOne ) ) {
						goodFormat = true;
					}
				}

				if( ! goodFormat ) {
					format = "JPEG" ;
				}
			} // 포맷 체크.

			this.debug.println( this, "format = " + format );

			if( "GIF".equalsIgnoreCase(format)) {
				GIFOutputStream.writeGIF(os, image, GIFOutputStream.STANDARD_256_COLORS, Color.white);
			} else {
				ImageUtil.write( image, format, os);
			}

			os.flush();
			os.close();

		} catch (Exception e) {

			String msg = e.getMessage();

			if ( e instanceof SocketException || ( msg != null && msg.startsWith( "ClientAbortException") ) ) {
				if (localDebug) {
					//debug.println(this, msg);
				}
			} else {
				//this.debug.println( e );
			}

		}

		if (localDebug) {
			debug.println(this, "A MapService[" + this.getServiceNo() + "] has done successfully.");
		}

	}

	public MapService getMapService() {
		return this.mapService;
	}

	private MapService mapService = new MapService();;

	private int serviceNo ;

}
