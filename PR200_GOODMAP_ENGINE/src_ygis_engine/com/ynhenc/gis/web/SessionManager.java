package com.ynhenc.gis.web;

import java.util.*;
import javax.servlet.http.*;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.gis.*;


import java.sql.*;

public class SessionManager extends GisComLib {

    private SessionManager() {
    }

    public static Session getSession( HttpSession httpSession ) {

    	if( httpSession == null ) {
    		return defaultSession;
    	}

    	Hashtable<HttpSession, Session> sessionList = SessionManager.sessionList;
        Session session = sessionList.get( httpSession ) ;

        if( session != null ) {
            session.updateSessionInfo();

            return session;
        } else {
            session = new Session( httpSession );
            sessionList.put( httpSession, session );
            return session;
        }
    }

    // member

    private static Session defaultSession = new Session();

    private static Hashtable<HttpSession, Session> sessionList = new Hashtable<HttpSession, Session>();

}
