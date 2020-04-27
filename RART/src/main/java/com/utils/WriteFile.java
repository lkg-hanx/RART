package com.utils;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;

/**
 * ファイルを書く
 * 
 * @param path    
 * @param content 
 */
public class WriteFile {
	private static final Logger log = Logger.getLogger(WriteFile.class);

	public static void write(String path, String content) throws Exception {

		FileOutputStream fileOutputStream = null;
		try {
			File file = new File(path);
			File fileParent = file.getParentFile();
			if (!fileParent.exists()) {
				fileParent.mkdirs();
			}
			file.createNewFile();

			fileOutputStream = new FileOutputStream(file);

			fileOutputStream.write(content.getBytes());

			fileOutputStream.close();

		} catch (Exception e) {
			log.error("ファイルの作成に失敗しました");
			e.printStackTrace();
		} finally {
			if (null != fileOutputStream) {
				fileOutputStream.close();
			}
		}
	}
}
