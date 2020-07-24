package com.ynhenc.gis.web;

import java.util.*;
import javax.servlet.http.*;

import com.ynhenc.comm.GisComLib;


import java.sql.SQLException;

public class Session extends GisComLib {

	public boolean setAttribute( String name, Object o ) {
		if( this.getHttpSession() != null ) {
			this.getHttpSession().setAttribute(name, o);
			return true;
		} else {
			this.debug( "HttpSession is null. Skipped setAttribute(" + name + "," + o + ")" );
		}
		return false;
	}

	public HttpSession getHttpSession() {
		return httpSession;
	}

	public String getSessionId() {
		return sessionId;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public boolean isNew() {
		return isNew;
	}

    @Override
	public boolean equals( Object obj ) {
        if( obj instanceof Session ) {
        	return super.equalsStringTrimIgnoreCase( this.getSessionId(), ((Session) obj).getSessionId() ) ;
        } else {
            return false;
        }
    }

    public long getLastAccessedTime() {
    	if( this.httpSession != null ) {
    		return this.httpSession.getLastAccessedTime();
    	} else {
    		return -1;
    	}
    }

    public void updateSessionInfo() {
        HttpSession httpSession = this.getHttpSession() ;
        this.isNew = httpSession.isNew();
    }

    public int getSessionNo() {
		return sessionNo;
	}

    protected Session() {
    }


    protected Session( HttpSession httpSession ) {
        super();

        this.httpSession = httpSession;
        this.sessionId = httpSession.getId();
        this.creationTime = new Date( httpSession.getCreationTime() );
        this.sessionNo = ( sessionNoLast ++ );

        this.updateSessionInfo();
    }

    private transient HttpSession httpSession;

    public String sessionId;
    private int sessionNo;
    public Date creationTime;
    public boolean isNew;

    public static int sessionNoLast = 0 ;

}
