package com.ynhenc.droute;

import java.awt.Color;

import com.ynhenc.droute.render.*;

public class SrchOption {

	public double getHeuristicFactor() {
		return this.heuristicFactor;
	}

	public void setHeuristicFactor(double heuristicFactor) {
		this.heuristicFactor = heuristicFactor;
	}

	public StyleLink getDefStyle() {
		int srchType = this.getSrchType();
		if( srchType == 0 ) {
			return new StyleLink(Color.yellow, Color.yellow, 6, 255, true );
		} else if( srchType == 1 ){
			return new StyleLink(Color.green, Color.green, 4, 255, true);
		} else if( srchType == 2 ) {
			return new StyleLink(Color.red, Color.red, 2, 255,true);
		} else {
		    return defStyleList[this.getSrchType()];
		}
	}

	public int getSrchType() {
		return srchType;
	}

	public void setSrchType(int srchType) {
		this.srchType = srchType;
	}

	@Override
	public String toString() {
		return String.format( "type(%d), heur(%f)", this.getSrchType(), this.getHeuristicFactor() );
	}

	public SrchOption() {
		this(0);
	}

	public SrchOption(int srchType) {
		this.srchType = srchType;
		this.setHeuristicFactor( HeuristicFactor.getHeuristicFactor().getFactor(this))	;
	}

	private int srchType = 0;
	private double heuristicFactor = 1 ;

	private final static StyleLink[] defStyleList = { new StyleLink(Color.yellow, Color.yellow, 1, 255, true ), new StyleLink(Color.green, Color.green, 1, 255, true),
			new StyleLink(Color.red, Color.red, 1, 255,true) };

}
