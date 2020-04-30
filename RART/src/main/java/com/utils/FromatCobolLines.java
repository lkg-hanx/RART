package com.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class FromatCobolLines {

	/**
	 * Cobol整形共通処理
	 * 
	 * @param lines
	 * @return
	 * @throws Exception
	 */
	public static List<String> format(List<String> lines, int startNum) throws Exception {

		// コメント行の削除
		lines = delComment(lines, startNum);

		// 終止符[.]接続
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
			if (!("*").equals(line.substring(startNum, startNum + 1))) {
				newLines.add(line);
			}
		}

		return newLines;
	}

	/**
	 * 終止符[.]接続
	 * 
	 * @param lines
	 * @return
	 * @throws Exception
	 */
	public static List<String> stopSignJoin(List<String> lines, int startNum) throws Exception {

		List<String> newLines = new ArrayList<String>();

		String str = "";
		for (String line : lines) {
			if (!StringUtils.isEmpty(str)) {
				str = str + " " + line.substring(startNum);
			} else {
				str = line;
			}

			if (str.indexOf(".") == -1) {
				continue;
			}

			newLines.add(str);
			str = "";
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
