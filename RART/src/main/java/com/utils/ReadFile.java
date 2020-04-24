package com.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * 读文件
 * @param path 文件路径
 * @return result 文件内容
 */
public class ReadFile  {
	private static final Logger log = Logger.getLogger(ReadFile.class); 


	public static List<String> read(String path) throws Exception {
		FileInputStream fileInputStream = null;
		BufferedReader bufferedReader = null;
		try {
			
			fileInputStream = new FileInputStream(path);
			
			bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
			
			List<String> result = new ArrayList<String>(); 
			String line = null;
			
			while ((line = bufferedReader.readLine()) != null) {
				result.add(line.trim());
	        }
			
			bufferedReader.close();
			fileInputStream.close();
			
			return result;
			
		} catch (Exception e) {
			log.error("文件读取失败");
			e.printStackTrace();
		} finally {
			if (null != bufferedReader) {
				bufferedReader.close();
			}
			if (null != fileInputStream) {
				fileInputStream.close();
			}
		}
		return null;
	}
}
