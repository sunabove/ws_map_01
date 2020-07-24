package com.ynhenc.gis.model.shape;

public class MinMaxTemp<Type> {

	public Type getMin() {
		return min;
	}

	public void setMin(Type min) {
		this.min = min;
	}

	public Type getMax() {
		return max;
	}

	public void setMax(Type max) {
		this.max = max;
	}

	public MinMaxTemp(Type min, Type max) {
		super();
		this.min = min;
		this.max = max;
	}

	private Type min ;
	private Type max ;

}
