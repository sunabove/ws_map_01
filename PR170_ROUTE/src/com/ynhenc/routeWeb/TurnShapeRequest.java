package com.ynhenc.routeWeb;

public class TurnShapeRequest {

	public Long getS_link_id() {
		return s_link_id;
	}
	public void setS_link_id(Long s_link_id) {
		this.s_link_id = s_link_id;
	}
	public Long getE_link_id() {
		return e_link_id;
	}
	public void setE_link_id(Long e_link_id) {
		this.e_link_id = e_link_id;
	}
	public String getLineColor() {
		return lineColor;
	}
	public void setLineColor(String lineColor) {
		this.lineColor = lineColor;
	}
	public int getLineWidth() {
		return lineWidth;
	}
	public void setLineWidth(int lineWidth) {
		this.lineWidth = lineWidth;
	}

	private Long s_link_id;
	private Long e_link_id;

	private String lineColor ;
	private int lineWidth = 1 ;

}
