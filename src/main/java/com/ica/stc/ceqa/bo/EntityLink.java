package com.ica.stc.ceqa.bo;

public class EntityLink {
	public EntityLink(String nl, String url, String type) {
		super();
		this.nl = nl;
		this.url = url;
		this.type = type;
	}

	private String nl;
	private String url;
	private String type;

	public String getNl() {
		return nl;
	}

	public void setNl(String nl) {
		this.nl = nl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
