package com.ynhenc.gis.ui.viewer_03.dbf_viewer;

public abstract class Cell {
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public boolean isUpdated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}

	public Cell( Object value ) {
		this.value = value;
	}

	private boolean updated = false;
	private Object value;
}
