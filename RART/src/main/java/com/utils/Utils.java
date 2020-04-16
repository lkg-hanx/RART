package com.utils;

import java.io.File;

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
	
	
	
}
