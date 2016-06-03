package com.ica.stc.algorithm.linking.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;

import com.ica.stc.algorithm.linking.Link;
import com.ica.stc.ceqa.bo.EntityLink;

public class SimpleLinkImpl implements Link {

	private HashMap<String, String> linkCacheRes = new HashMap<String, String>();
	private HashMap<String, String> linkCachePro = new HashMap<String, String>();
	private static float THREADHOLD = 0.5f;

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
			// System.out.println(x.split("#")[1]);
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

	public static int ld(String s, String t) {
		int d[][];
		int sLen = s.length();
		int tLen = t.length();
		int si;
		int ti;
		char ch1;
		char ch2;
		int cost;
		if (sLen == 0) {
			return tLen;
		}
		if (tLen == 0) {
			return sLen;
		}
		d = new int[sLen + 1][tLen + 1];
		for (si = 0; si <= sLen; si++) {
			d[si][0] = si;
		}
		for (ti = 0; ti <= tLen; ti++) {
			d[0][ti] = ti;
		}
		for (si = 1; si <= sLen; si++) {
			ch1 = s.charAt(si - 1);
			for (ti = 1; ti <= tLen; ti++) {
				ch2 = t.charAt(ti - 1);
				if (ch1 == ch2) {
					cost = 0;
				} else {
					cost = 1;
				}
				d[si][ti] = Math.min(Math.min(d[si - 1][ti] + 1, d[si][ti - 1] + 1), d[si - 1][ti - 1] + cost);
			}
		}
		return d[sLen][tLen];
	}

	public static double similarity(String src, String tar) {
		int ld = ld(src, tar);
		return 1 - (double) ld / Math.max(src.length(), tar.length());
	}

	// 词stemming
	public static String stemWord(String word) {
		if (word.equals("价格")) {
			return "市场价格";
		}
		if (word.equals("功能")) {
			return "附加功能";
		}
		if (word.equals("上市")) {
			return "上市时间";
		}
		if (word.equals("网络类型")) {
			return "上市时间";
		}
		if (word.equals("摄像")) {
			return "后置";
		}
		return word;
	}

	// 候选项计算,每个词保留5个候选实体
	public String[] getCandidates(String word) {
		String word2 = SimpleLinkImpl.stemWord(word);
		ArrayList<String> res = new ArrayList<String>();
		for (String x : linkCacheRes.keySet()) {
			if ((SimpleLinkImpl.similarity(x, word2) > SimpleLinkImpl.THREADHOLD)) {
				res.add(x);
			}
		}
		for (String x : linkCachePro.keySet()) {
			if ((SimpleLinkImpl.similarity(x, word2) > SimpleLinkImpl.THREADHOLD)) {
				res.add(x);
			}
		}
		Collections.sort(res, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				double a = SimpleLinkImpl.similarity(word2, o1);
				double b = SimpleLinkImpl.similarity(word2, o2);
				if (Math.abs((a - b)) < 0.01f) {
					return 0;
				}
				if (a > b) {
					return (-1);
				} else {
					return (1);
				}
			}
		});
		ArrayList<String> top5 = new ArrayList<String>();
		for (int i = 0; i < Math.min(res.size(), 5); i++) {
			top5.add(res.get(i));
		}
		String[] ret = new String[5];
		top5.toArray(ret);
		return ret;
	}

	// Link Test Result
	public String linkTest(String word) {

		return "";
	}
}
