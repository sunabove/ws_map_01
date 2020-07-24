package com.ynhenc.gis.ui.comp;

import java.util.*;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.gis.*;

public class StateEventSource extends GisComLib implements StateEventSourceInterface {

	public StateEventSource() {
		this.listeners = new Vector<StateListener>();
	}

	public void addWorkStateListener(StateListener listener) {
		listeners.addElement(listener);
	}

	public void removeWorkStateListener(StateListener listener) {
		listeners.remove(listener);
	}

	public void fireWorkStateEvent(StateEvent e) {

		for (StateListener listener : listeners) {
			try {
				this.fireWorkStateEventRunnable(listener, e);
			} catch (Exception ex) {
				this.debug( ex );
			}
		}

	}

	public void fireWorkStateEventRunnable(final StateListener listener, final StateEvent e) throws Exception {
		if (true) {
			// 직접 이벤트 발생.
			listener.workStateChanged(e);
		} else {
			// 스윙 이벤트큐 이용하여 이벤트 전달.
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					listener.workStateChanged(e);
				}
			});
		}

	}

	public StateEvent createWorkStateEvent(Object obj) {
		return null;
	}

	private Vector<StateListener> listeners;

}
