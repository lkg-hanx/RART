package com.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FromatJclLines {

	/**
	 * Jcl整形共通処理
	 * 
	 * @param lines
	 * @return
	 * @throws Exception
	 */
	public static List<String> format(List<String> lines, int startNum) throws Exception {

		// コメント行の削除
		lines = delComment(lines, startNum);

		// 接続処理
		lines = stopSignJoin(lines, startNum);

		// 半角スペースをTABに換える
		lines = spaceToTab(lines);

		return lines;
	}

	/**
	 * コメント行の削除
	 * 
	 * @param lines
	 * @return
	 * @throws Exception
	 */
	public static List<String> delComment(List<String> lines, int startNum) throws Exception {

		List<String> newLines = new ArrayList<String>();

		for (String line : lines) {
			if (line.length() < startNum + 1) {
				continue;
			}
			if (!("\\*").equals(line.substring(startNum, startNum + 2))) {
				newLines.add(line.trim());
			}
		}

		return newLines;
	}

	/**
	 * 接続処理
	 * 
	 * @param lines
	 * @return
	 * @throws Exception
	 */
	public static List<String> stopSignJoin(List<String> lines, int startNum) throws Exception {

		List<String> newLines = new ArrayList<String>();

		String newLine = "";
		for(int i =0;i<lines.size();i++) {
			String line = lines.get(i);
			if (("\\").equals(line.substring(startNum, startNum + 1)) && (i+1 == lines.size() ||  ("\\").equals(lines.get(i+1).substring(startNum, startNum + 1)))) {
				newLine = line;
			}else if (("\\").equals(line.substring(startNum, startNum + 1)) && !("\\").equals(lines.get(i+1).substring(startNum, startNum + 1))) {
				newLine = line;
				continue;
			}else if (!("\\").equals(line.substring(startNum, startNum + 1)) &&(i+1 == lines.size() ||  !("\\").equals(lines.get(i+1).substring(startNum, startNum + 1)))) {
				if((",").equals(newLine.substring(newLine.length()-1))){
					newLine = newLine+ line.substring(startNum).trim();
				}else {
					newLine = newLine + " " + line.substring(startNum);
				}
				continue;
			}else if (!("\\").equals(line.substring(startNum, startNum + 1)) && ("\\").equals(lines.get(i+1).substring(startNum, startNum + 1))) {
				if((",").equals(newLine.substring(newLine.length()-1))){
					newLine = newLine+ line.substring(startNum).trim();
				}else {
					newLine = newLine + " " + line.substring(startNum);
				}
			}
				
			newLines.add(newLine);
			newLine = "";
		}

		return newLines;
	}

	/**
	 * 半角スペースをTABに換える
	 * 
	 * @param lines
	 * @return
	 * @throws Exception
	 */
	public static List<String> spaceToTab(List<String> lines) throws Exception {

		List<String> newLines = new ArrayList<String>();

		for (String line : lines) {
			Pattern p = Pattern.compile("\\s+");
			Matcher m = p.matcher(line);
			String newLine = m.replaceAll("\t");

			newLines.add(newLine);
		}

		return newLines;
	}
}
