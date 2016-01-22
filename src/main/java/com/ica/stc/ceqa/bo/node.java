package com.ica.stc.ceqa.bo;

public class node {
	// node id
	private int id;
	// node value 1-50
	private float value;
	// 如：iphone5s,实体节点名称。
	private String label;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
