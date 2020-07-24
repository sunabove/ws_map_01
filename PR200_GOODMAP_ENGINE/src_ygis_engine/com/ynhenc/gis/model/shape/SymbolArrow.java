package com.ynhenc.gis.model.shape;

public class SymbolArrow {

	public Type getType() {
		return this.type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public int getWidth() {
		return this.width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return this.height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public SymbolArrow create() {
		return new SymbolArrow( this.getWidth(), this.getHeight(), this.getType() );
	}

	public SymbolArrow() {
		this( 16, 12 , Type.ARROW_ZERO );
	}

	public SymbolArrow(int width, int height, SymbolArrow.Type type) {
		this.width = width;
		this.height = height;
		this.type = type;
	}

	private int width;
	private int height;
	private Type type;

	public enum Type {
		ARROW_ZERO, ARROW_RIGHT, ARROW_LEFT, ARROW_BOTH
	}

}
