package com.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class ReadFile {
	private static final Logger log = Logger.getLogger(ReadFile.class);

	/**
	 * ファイルの内容取得
	 * 
	 * @param path ファイルパス
	 * @return
	 * @throws Exception
	 */
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
			log.error("ファイルの読み込みに失敗しました");
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
