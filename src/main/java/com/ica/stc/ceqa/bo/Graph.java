package com.ica.stc.ceqa.bo;

public class Graph {

	private Node[] nodes;
	private Edges[] links;

	public Graph(Node[] nodes, Edges[] links) {
		super();
		this.nodes = nodes;
		this.links = links;
	}

	public Node[] getNodes() {
		return nodes;
	}

	public void setNodes(Node[] nodes) {
		this.nodes = nodes;
	}

	public Edges[] getLinks() {
		return links;
	}

	public void setLinks(Edges[] links) {
		this.links = links;
	}
}
