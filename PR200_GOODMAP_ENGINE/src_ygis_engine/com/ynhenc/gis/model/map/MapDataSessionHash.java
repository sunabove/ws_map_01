package com.ynhenc.gis.model.map;

import java.util.*;

import com.ynhenc.gis.web.*;

public class MapDataSessionHash extends Hashtable<MapSession, MapDataSession> {

	public long getLastCheckTime() {
		return lastCheckTime;
	}

	public void setLastCheckTime(long lastCheckTime) {
		this.lastCheckTime = lastCheckTime;
	}

	public void getClearOutOldSession() {

		final long currTime = System.currentTimeMillis();

		final long lastCheckTime = this.getLastCheckTime();

		final long diffTime = currTime - lastCheckTime;

		long minTime = 10 * 60*1000;

		if (diffTime > minTime) {
			this.setLastCheckTime(currTime);
			try {
				this.removeOldSessionImpl();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void removeOldSessionImpl() {

		long minTime = 24*60*60*1000;

		final long currTime = System.currentTimeMillis();
		long diffTime;

		Enumeration<MapSession> it = this.keys();

		MapSession mapSession;
		while (it.hasMoreElements()) {
			mapSession = it.nextElement();
			if (mapSession != null) {
				long lat = mapSession.getLastAccessedTime();
				diffTime = currTime - lat;
				if (diffTime > minTime) {
					mapSession.debug( "remove mapSession " + mapSession.getNo() );
					this.remove(mapSession);
				}
			}
		}

	}

	private long lastCheckTime = -1;

}
