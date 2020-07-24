package com.ynhenc.gis.model.map;

import com.ynhenc.gis.projection.CoordinateConversion;
import com.ynhenc.gis.projection.DirectConversion;

public class GisProjectOption {

	public boolean isDisplayShapeAndIconLabelTogether() {
		return displayShapeAndIconLabelTogether > 0 ;
	}

	public void setDisplayShapeAndIconLabelTogether(boolean displayShapeAndIconLabelTogether) {
		this.displayShapeAndIconLabelTogether = displayShapeAndIconLabelTogether ? 1 : 0 ;
	}

	public boolean isRenderingTime_Show() {
		return renderingTime_Show;
	}

	public void setRenderingTime_Show(boolean renderingTime_Show) {
		this.renderingTime_Show = renderingTime_Show;
	}

	public CoordinateConversion getCoordinateConversion() {
		if( this.coordinateConversion == null ) {
			this.coordinateConversion = new DirectConversion();
		}
		return this.coordinateConversion;
	}

	public void setCoordinateConversion(CoordinateConversion coordinateConversion) {
		this.coordinateConversion = coordinateConversion;
	}


	public boolean isGraphics_HighQuality() {
		return this.graphics_HighQuality;
	}

	public void setGraphics_HighQuality(boolean graphics_HighQuality) {
		this.graphics_HighQuality = graphics_HighQuality;
	}

	public boolean isCrossLine_Show() {
		return this.crossLine_Show;
	}

	public void setCrossLine_Show(boolean crossLine_Show) {
		this.crossLine_Show = crossLine_Show;
	}

	public boolean isGlobalMbr_Show() {
		return this.globalMbr_Show;
	}

	public void setGlobalMbr_Show(boolean globalMbr_Show) {
		this.globalMbr_Show = globalMbr_Show;
	}

	public boolean isLogo_Show() {
		return this.logo_Show;
	}

	public void setLogo_Show(boolean logo_Show) {
		this.logo_Show = logo_Show;
	}

	private CoordinateConversion coordinateConversion;

	private boolean crossLine_Show = false;
	private boolean globalMbr_Show = false ;
	private boolean logo_Show = true;
	private boolean graphics_HighQuality = true;
	private boolean renderingTime_Show = true;
	private int displayShapeAndIconLabelTogether = 1 ;

}
