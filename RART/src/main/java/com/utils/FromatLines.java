package com.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.maven.RART.App;

public class FromatLines  {
	private static final Logger log = Logger.getLogger(App.class); 

	/**
	 * 整形共通处理
	 * @param lines
	 * @return
	 * @throws Exception
	 */
	public static List<String> format(List<String> lines) throws Exception {
		
		// 注释行删除
		lines = delComment(lines);
		
		// TODO 终止符【.】接续
		lines = stopSignJoin(lines);
		
		// TODO 半角空格换TAB
		
		return lines;
	}
	
	/**
	 * 注释行删除
	 * @param lines
	 * @return
	 * @throws Exception
	 */
	public static List<String> delComment(List<String> lines) throws Exception {
		
		List<String> newLines = new ArrayList<String>();
		
		for(String line:lines) {
			if(line.length()<7) {
				continue;
			}
			if(!("*").equals(line.substring(6, 7))) {
				newLines.add(line);
			}
		}
		
		return newLines;
	}
	
	/**
	 * 终止符【.】接续
	 * @param lines
	 * @return
	 * @throws Exception
	 */
	public static List<String> stopSignJoin(List<String> lines) throws Exception {
		
		List<String> newLines = new ArrayList<String>();
		
		String str = "";
		for(String line:lines) {
			if(!StringUtils.isEmpty(str)) {
				str = str + " " + line.substring(6);
			}else {
				str = line;
			}
			
			
			if(str.indexOf(".") == -1) {
				continue;
			}
			
			newLines.add(str);
			str = "";
		}
			
		return newLines;
	}
	
}
