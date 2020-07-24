package com.ynhenc.gis.web;

import javax.servlet.*;
import javax.servlet.http.*;

import com.ynhenc.comm.file.FileManager;
import com.ynhenc.comm.Text;
import com.ynhenc.gis.*;

public class Server {

	private Server(HttpServlet servlet) {
		super();
		this.context = servlet.getServletContext();
		if (SERVER == null) {
			SERVER = this;
		}
	}

	public void initServer() {

		Server.inited = true;

	}

	// member
	private ServletContext context;

	// end of member

	public static Server getServer(HttpServlet servlet) {
		if (SERVER == null) {
			SERVER = new Server(servlet);
		}
		return SERVER;
	}

	// static member
	private static FileManager FILE_MANAGER;
	private static Server SERVER;
	private static boolean inited = false;

	// end of static member

	// static method
	public static final FileManager getFileManager() {
		if (FILE_MANAGER == null && SERVER != null && SERVER.context != null) {
			FILE_MANAGER = new FileManager(SERVER.context) {
			};
			return FILE_MANAGER;
		} else if (FILE_MANAGER != null) {
			return FILE_MANAGER;
		} else {
			// debug.debug( "", "FILE MANAGER CREATED WITH NULL CONTEXT!" );
			return new FileManager(null) {
			};
		}
	}

	public static boolean isInited() {
		return inited;
	}

	public static String getApplicationTitle() {
		return Text.APPLICATION_TITLE + Text.APPLICATION_VER;
	}

}
