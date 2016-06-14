package com.ica.stc.ceqa.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ica.stc.algorithm.ceqa.Main;
import com.ica.stc.ceqa.bo.Graph;
import com.ica.stc.ceqa.service.CeqaService;
import com.ica.stc.common.util.Config;

/**
 * ceqa service
 * 
 * @author zeyu this service is to init ceqa system,and give two implements. qa
 *         and graph.
 * 
 */
@Service("ceqaService")
public class CeqaServiceImpl implements CeqaService {

	@Autowired
	private Config config;

	private Main ceqaAlgorithm = new Main();
	private HashMap<String, Graph> cacheGraphMap = new HashMap<String, Graph>();

	@Override
	public String getResult(String q) {
		return ceqaAlgorithm.answerQuestion(q);
	}

	/**
	 * Service初始化
	 */
	@PostConstruct
	public void init() {
		System.out.println(config.getRdfpath());
		ceqaAlgorithm.initModel(config.getRdfpath());
		String path = config.getRdfpath();
	}

	@Override
	public Graph getGraph(String url, String type) {
		if (cacheGraphMap.containsKey(url)) {
			return cacheGraphMap.get(url);
		} else {
			return ceqaAlgorithm.searchGraph(url,type);
		}

	}

	@Override
	public Graph getEntityGraph(String nl) {
		Graph temp = ceqaAlgorithm.serchSingelEntity(nl);
		if (temp == null) {
			String[] links = ceqaAlgorithm.getLinks(nl);
			String longKey = "";
			for (String x: links) {
				if (x != null) {
					String key = x.split("#")[1];
					if (key.length() > longKey.length()) {
						longKey = key;
					}
				}
			}
			temp = ceqaAlgorithm.serchSingelEntity(longKey);
			if ((temp != null)) {
				return temp;
			}
		}
		return temp;
	}
}
