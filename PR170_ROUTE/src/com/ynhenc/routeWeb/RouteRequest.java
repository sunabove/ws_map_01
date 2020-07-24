package com.ynhenc.routeWeb;

import java.awt.Color;

import com.ynhenc.droute.*;

public class RouteRequest {

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

	public int getSrchType() {
		return srchType;
	}

	public void setSrchType(int srchType) {
		this.srchType = srchType;
	}

	public Double getSx() {
		return sx;
	}

	public void setSx(Double sx) {
		this.sx = sx;
	}

	public Double getSy() {
		return sy;
	}

	public void setSy(Double sy) {
		this.sy = sy;
	}

	public Double getMx() {
		return mx;
	}

	public void setMx(Double mx) {
		this.mx = mx;
	}

	public Double getMy() {
		return my;
	}

	public void setMy(Double my) {
		this.my = my;
	}

	public Double getEx() {
		return ex;
	}

	public void setEx(Double ex) {
		this.ex = ex;
	}

	public Double getEy() {
		return ey;
	}

	public void setEy(Double ey) {
		this.ey = ey;
	}

	public SrchOption getSrchOption() {
		return new SrchOption( this.getSrchType() );
	}

	private Double sx;
	private Double sy;
	private Double mx;
	private Double my;
	private Double ex;
	private Double ey;
	private String lineColor ;
	private int lineWidth = 1 ;

	private int srchType = 0;

}
