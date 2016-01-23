package com.ica.stc.ceqa.bo;

public class Edges {
	private int id;
	private int source;
	private int target;
	private String name;

	public Edges(int id, int source, int target, String name) {
		super();
		this.id = id;
		this.source = source;
		this.target = target;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
