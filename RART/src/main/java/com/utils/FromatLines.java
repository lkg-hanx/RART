package com.utils;

import java.util.List;

import org.apache.log4j.Logger;

import com.maven.RART.App;

/**
 * 整形共通处理
 * @param path 文件路径
 * @return result 文件内容
 */
public class FromatLines  {
	private static final Logger log = Logger.getLogger(App.class); 

	public static List<String> format(List<String> lines) throws Exception {
		
		// 整形共通处理
		// TODO 注释行删除
		
		// TODO 终止符【.】
		
		// TODO 半角空格换TAB
		
		return lines;
	}
}
