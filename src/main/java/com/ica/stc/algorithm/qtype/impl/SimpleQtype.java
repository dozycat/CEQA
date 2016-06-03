package com.ica.stc.algorithm.qtype.impl;

import com.ica.stc.algorithm.qtype.QType;

/**
 * 最简单的问题分类
 * @author zeyu
 *
 */
public class SimpleQtype implements QType{
	
	
    public static final int TYPE_UNKNOWN = 0; // 未知类别
    public static final int TYPE_COUNT = 1; // 计数型问题
    public static final int TYPE_MAX = 2; // 最大值型问题
    public static final int TYPE_MIN = 3; // 最小值型问题
    public static final int TYPE_NUMERIC = 4; // 数值类问题
    public static final int TYPE_BOOL = 5; // 是否类问题
    public static final int TYPE_FACT = 6; // 事实类问题
    public static final int TYPE_LIST = 7; // list类型问题
    
    
	@Override
	public int getQuestionType(String[] words) {
		int[] a = new int[8];
        for (int i = 0; i < 8; i++) {
            a[i] = 0;
        }

        if ((isWordInSetence(words, "能")) && (isWordInSetence(words, "吗"))) {
            a[TYPE_BOOL] += 1;
        }
        if ((isWordInSetence(words, "有")) && (isWordInSetence(words, "吗"))) {
            a[TYPE_BOOL] += 1;
        }
        if ((isWordInSetence(words, "可以")) && (isWordInSetence(words, "吗"))) {
            a[TYPE_BOOL] += 1;
        }
        if (isWordInSetence(words, "那些")) {
            a[TYPE_LIST] += 1;
        }
        if (isWordInSetence(words, "多大")) {
            a[TYPE_NUMERIC] += 1;
            a[TYPE_FACT] += 1;
        }
        if (isWordInSetence(words, "多少")) {
        	a[TYPE_FACT] += 1;
            a[TYPE_COUNT] += 1;
        }
        if (isWordInSetence(words, "什么") || isWordInSetence(words, "什么样")) {
            a[TYPE_FACT] += 1;
        }
        if (isWordInSetence(words, "是")) {
            a[TYPE_FACT] += 1;
        }
        int target = -1;
        int v = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] > v) {
                target = i;
                v = a[i];
            }
        }
        return target;
	}
	
	 public static boolean isWordInSetence(String[] words, String target) {
	        for (String word : words) {
	            if (target.equals(word)) {
	                return true;
	            }
	        }
	        return false;
	    }
	
}
