package com.ynhenc.gis.model.shape;

public class Node extends Vertex {

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Node(double x, double y, int id, String name) {
		super(x, y);
		this.id = id;
		this.name = name;
	}

	private int id;
	private String name;

}
