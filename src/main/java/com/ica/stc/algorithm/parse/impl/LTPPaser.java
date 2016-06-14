package com.ica.stc.algorithm.parse.impl;

import java.util.ArrayList;

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
		for (String x : argvs) {
			System.out.println(x);
		}

	}

	@Override
	public String[] entityReg(String[] seqs) {
		ArrayList<String> res = new ArrayList<String>();
		for (int i = 0; i < seqs.length; i++) {
			String temp = "";
			if (i == 0) {
				if (seqs.length > 1) {
					if (isNumber(seqs[i + 1]) || isAZ(seqs[i + 1]) || isProduct(seqs[i + 1])) {
						temp = "BRAND";
					}

				}
			} else {
				if (i + 1 < seqs.length) {
					if (isProduct(seqs[i + 1])) {
						temp = "BRAND";
					}
				}
			}
			res.add(temp);
		}
		String[] out = new String[res.size()];
		res.toArray(out);
		return out;
	}

	public static boolean isNumber(String s) {
		for (int i = 0; i < s.length(); i++) {
			if ((s.charAt(i) >= '0') && (s.charAt(i) <= '9')) {
				return true;
			}
		}
		return false;
	}

	public static boolean isAZ(String s) {
		for (int i = 0; i < s.length(); i++) {
			if ((s.charAt(i) >= 'a') && (s.charAt(i) <= 'z')) {
				return true;
			}
			if ((s.charAt(i) >= 'A') && (s.charAt(i) <= 'Z')) {
				return true;
			}
		}
		return false;
	}

	public static boolean isProduct(String s) {
		if (s.equals("手机")) {
			return true;
		}
		return false;
	}

}
