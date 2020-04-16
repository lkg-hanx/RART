package com.utils;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;

/**
 * 写文件
 * @param path 文件路径
 * @param content 文件内容（\n换行）
 */
public class WriteFile  {
	private static final Logger log = Logger.getLogger(WriteFile.class); 
	
	public static void  write(String path,String content) throws Exception {
		
		FileOutputStream fileOutputStream = null;
		try {
			File file = new File(path);
			File fileParent = file.getParentFile();
			// 判断是否存在
			if (!fileParent.exists()) {
				// 创建文件目录
				fileParent.mkdirs();
			}
			file.createNewFile();
	
	        fileOutputStream = new FileOutputStream(file);
	        
	        fileOutputStream.write(content.getBytes());
	
	        fileOutputStream.close();
		
		} catch (Exception e) {
			log.error("写文件失败");
			e.printStackTrace();
		} finally {
			if (null != fileOutputStream) {
				fileOutputStream.close();
			}
		}
	}
}
