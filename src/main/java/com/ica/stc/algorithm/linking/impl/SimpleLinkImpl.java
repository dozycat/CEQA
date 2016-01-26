package com.ica.stc.algorithm.linking.impl;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;

import com.ica.stc.algorithm.linking.Link;
import com.ica.stc.ceqa.bo.Edges;
import com.ica.stc.ceqa.bo.EntityLink;
import com.ica.stc.ceqa.bo.Node;

public class SimpleLinkImpl implements Link {

	private HashMap<String, String> linkCacheRes = new HashMap<String, String>();
	private HashMap<String, String> linkCachePro = new HashMap<String, String>();

	@Override
	public String[] getUrl(String[] words, String[] segs) {
		// TODO Auto-generated method stub
		return null;
	}

	public void initCacha(Model model) {
		String queryString = " SELECT ?x ?y ?z where {?x ?y ?z }";
		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		while (results.hasNext()) {
			QuerySolution qs = results.next();
			String x = qs.get("?x").toString();
			linkCacheRes.put(x.split("#")[1], x);
			//System.out.println(x.split("#")[1]);
			String y = qs.get("?y").toString();
			linkCachePro.put(y.split("#")[1], y);
		}

	}

	public EntityLink getLink(String word) {
		EntityLink el = null;
		// System.out.println("check for "+word);
		if (linkCacheRes.containsKey(word)) {
			el = new EntityLink(word, linkCacheRes.get(word), "?x");
		} else if (linkCachePro.containsKey(word)) {
			el = new EntityLink(word, linkCachePro.get(word), "?y");
		}
		return el;
	}

}
