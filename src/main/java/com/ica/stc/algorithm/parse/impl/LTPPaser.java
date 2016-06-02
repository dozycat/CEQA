package com.ica.stc.algorithm.parse.impl;

import com.ica.stc.algorithm.parse.Parser;
import com.ica.stc.common.util.HttpRequest;

/**
 * 哈工大LTP的 Parser
 * 
 * @version 1.0 2016.1.18
 * @author zeyu
 */
public class LTPPaser implements Parser {

	// 申請的key
	private final static String LTP_KEY = "L431y7O7ydiWqW8bzbyMOFqHGDCQnBeiKaUJkKJK";

	private final static String BASE_URL = "http://ltpapi.voicecloud.cn/analysis/";

	@Override
	public String[] ParseSetence(String s) {
		return HttpRequest.sendGet(BASE_URL, "api_key=" + LTP_KEY + "&text=" + s + "&pattern=pos&format=plain")
				.split(" ");
	}

	/**
	 * 获得分词后的序列
	 * 
	 * 
	 * @param s
	 *            待分词串
	 * 
	 * @return 分词后的数组 [word1,word2,word3.....]
	 */
	public String[] getWords(String s) {
		String res = HttpRequest.sendGet(BASE_URL, "api_key=" + LTP_KEY + "&text=" + s + "&pattern=ws&format=plain");
		return res.split(" ");
	}

	public static void main(String[] args) {
		LTPPaser parse = new LTPPaser();
		String[] argvs = parse.ParseSetence("123123 我爱北京天安门");
		for (String x: argvs){
			System.out.println(x);
		}
		
	}

}
