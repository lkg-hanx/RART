package com.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	public static List<String> format(List<String> lines, int startNum) throws Exception {
		
		// 注释行删除
		lines = delComment(lines, startNum);
		
		// 终止符【.】接续
		lines = stopSignJoin(lines, startNum);
		
		// 半角空格换TAB
		lines = spaceToTab(lines);
		
		
		return lines;
	}
	
	/**
	 * 注释行删除
	 * @param lines
	 * @return
	 * @throws Exception
	 */
	public static List<String> delComment(List<String> lines, int startNum) throws Exception {
		
		List<String> newLines = new ArrayList<String>();
		
		for(String line:lines) {
			if(line.length()<startNum+1) {
				continue;
			}
			if(!("*").equals(line.substring(startNum, startNum+1))) {
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
	public static List<String> stopSignJoin(List<String> lines, int startNum) throws Exception {
		
		List<String> newLines = new ArrayList<String>();
		
		String str = "";
		for(String line:lines) {
			if(!StringUtils.isEmpty(str)) {
				str = str + " " + line.substring(startNum);
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
	
	/**
	 * 多个半角空格和TAB 替换成一个TAB
	 * @param lines
	 * @return
	 * @throws Exception
	 */
	public static List<String> spaceToTab(List<String> lines) throws Exception {
		
		List<String> newLines = new ArrayList<String>();
		
		for(String line:lines) {
			Pattern p = Pattern.compile("\\s+");
			Matcher m = p.matcher(line);
			String newLine = m.replaceAll("\t");
			
			newLines.add(newLine);
		}
			
		return newLines;
	}
}
