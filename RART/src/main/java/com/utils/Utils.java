package com.utils;

import java.io.File;
import java.util.List;

/**
 * 共通处理
 */
public class Utils {

	/**
	 * 空路径创建
	 * @param file 文件
	 * @throws Exception 
	 */
	public static void createFile(File file) throws Exception {
		
		// 获取父目录
		File fileParent = file.getParentFile();
		// 判断是否存在
		if (!fileParent.exists()) {
			// 创建父目录文件
			fileParent.mkdirs();
		}
		file.createNewFile();
		
	}
	
	/**
	 * 读取所有文件路径
	 * @param filepath
	 * @param fileList
	 * @throws Exception
	 */
	public static void readfile(String filepath, List<String> fileList) throws Exception {
		
		File file = new File(filepath);
		 if (!file.isDirectory()) {
			 fileList.add(file.getPath());
		 }else {
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
