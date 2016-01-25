package com.ica.stc.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config {

	@Value("#{remoteSettings['rdf.path']}")
	private String rdfpath;

	public String getRdfpath() {
		return rdfpath;
	}

	public void setRdfpath(String rdfpath) {
		this.rdfpath = rdfpath;
	}

}
