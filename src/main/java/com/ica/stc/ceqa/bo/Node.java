package com.ica.stc.ceqa.bo;

public class Node {

	// node id
	private int id;
	// node value 1-50
	private float symbolSize;
	// 如：iphone5s,实体节点名称。
	private String name;
	// 加入class
	private int modularityClass;

	public Node(int id, float symbolSize, String name, int modularityClass) {
		super();
		this.id = id;
		this.symbolSize = symbolSize;
		this.name = name;
		this.modularityClass = modularityClass;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getSymbolSize() {
		return symbolSize;
	}

	public void setSymbolSize(float symbolSize) {
		this.symbolSize = symbolSize;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getModularityClass() {
		return modularityClass;
	}

	public void setModularityClass(int modularityClass) {
		this.modularityClass = modularityClass;
	}

}
