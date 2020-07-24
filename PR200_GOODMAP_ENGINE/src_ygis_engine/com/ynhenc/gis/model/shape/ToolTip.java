package com.ynhenc.gis.model.shape;

public class ToolTip {

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ToolTip(int type, String content) {
		super();
		this.type = type;
		this.content = content;
	}

	private int type;
	private String content;

}
