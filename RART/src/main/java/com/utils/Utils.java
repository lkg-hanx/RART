package com.utils;

import java.io.File;
import java.util.List;

/**
 * 共通の処理
 */
public class Utils {

	/**
	 * 空のパスの作成
	 * 
	 * @param file 
	 * @throws Exception
	 */
	public static void createFile(File file) throws Exception {

		File fileParent = file.getParentFile();
		if (!fileParent.exists()) {
			fileParent.mkdirs();
		}
		file.createNewFile();

	}

	/**
	 * すべてのファイルパスを読み
	 * 
	 * @param filepath
	 * @param fileList
	 * @throws Exception
	 */
	public static void readfile(String filepath, List<String> fileList) throws Exception {

		File file = new File(filepath);
		if (!file.isDirectory()) {
			fileList.add(file.getPath());
		} else {
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				File readfile = new File(filepath + "\\" + filelist[i]);
				if (!readfile.isDirectory()) {
					fileList.add(readfile.getPath());
				} else if (readfile.isDirectory()) {
					readfile(filepath + "\\" + filelist[i], fileList);
				}
			}
		}
	}

}
