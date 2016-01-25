package com.ica.stc.ceqa.service;

import javax.annotation.Resource;

import com.ica.stc.ceqa.bo.Graph;

@Resource
public interface CeqaService {
	/**
	 * 问答服务
	 * @param q 问题
	 * @return 答案
	 */
	public String getResult(String q);
	
	/**
	 * 画图接口
	 * @param url 实体,类别
	 * @return graph 对象
	 */
	public Graph getGraph(String url,String type);
	
	/**
	 * 画图接口
	 * @param nl 自然語言實體
	 * @return graph 对象
	 */
	public Graph getEntityGraph(String nl);
}
