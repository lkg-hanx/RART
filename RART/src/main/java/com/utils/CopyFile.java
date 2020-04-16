package com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

/**
 * 整形共通处理
 * 
 * @param path 文件路径
 * @return result 文件内容
 */
public class CopyFile {
	private static final Logger log = Logger.getLogger(CopyFile.class);

	public static void copy(File source, File dest) throws Exception {
		InputStream input = null;
		OutputStream output = null;
		try {
			input = new FileInputStream(source);
			output = new FileOutputStream(dest);
			byte[] buf = new byte[1024];
			int bytesRead;
			while ((bytesRead = input.read(buf)) > 0) {
				output.write(buf, 0, bytesRead);
			}
		} catch (Exception e) {
			log.error("COPY失败");
			e.printStackTrace();
		} finally {
			if(null != input) {
				input.close();
			}
			if(null != output) {
				output.close();
			}
		}
	}
}
