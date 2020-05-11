package com.maven.RART;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.dto.CobolDto;
import com.dto.JclDto;
import com.dto.TopDto;
import com.service.CobolService;
import com.service.JclService;
import com.utils.FromatCobolLines;
import com.utils.FromatJclLines;
import com.utils.JclWriterFreemarker;
import com.utils.ReadFile;
import com.utils.Utils;
import com.utils.WriteFile;
import com.utils.CobolWriterFreemarker;

public class begin {

	private static final Logger log = Logger.getLogger(begin.class);

	/**
	 * 開始方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		log.info("処理開始");

		try {

			// プロファイル読みだし
			InputStream in = CobolWriterFreemarker.class.getClassLoader().getResource("resources/param.properties")
					.openStream();
			Properties prop = new Properties();
			prop.load(in);

			// TOP情報
			TopDto dto = new TopDto();
			dto.setFilesSize("0");
			dto.setValidNum("0");
			dto.setTotalNum("0");
			// cobolすべてのファイルパス
			List<String> cobolFileList = new ArrayList<String>();
			// jclすべてのファイルパス
			List<String> jclFileList = new ArrayList<String>();

			// cobol処理
			String cobolUploadFile = prop.getProperty("cobol_upload_file");
			if (StringUtils.isEmpty(cobolUploadFile)) {
				log.error("COBOLアップロードファイルのパスが空です");
			} else {
				Utils.readfile(cobolUploadFile, cobolFileList);
				dto.setCobolFilesSize(cobolFileList.size() + "");
				cobol(cobolFileList, prop, dto);
			}

			// jcll処理
			String jclUploadFile = prop.getProperty("jcl_upload_file");
			if (StringUtils.isEmpty(jclUploadFile)) {
				log.error("JCLアップロードファイルのパスが空です");
			} else {
				Utils.readfile(jclUploadFile, jclFileList);
				dto.setJclFilesSize(jclFileList.size() + "");
				jcl(jclFileList, prop, dto, cobolFileList);
			}

			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dto.setSystemTime(sf.format(new Date()));
			dto.setUser(prop.getProperty("user"));
			if(null != dto.getCobolList() && dto.getCobolList().size() > 0) {
				dto.setFilesSize((Integer.parseInt(dto.getFilesSize())+Integer.parseInt(dto.getCobolFilesSize()))+"");
				dto.setTotalNum((Integer.parseInt(dto.getTotalNum()) + Integer.parseInt(dto.getCobolTotalNum()))+"");
				dto.setValidNum((Integer.parseInt(dto.getValidNum()) + Integer.parseInt(dto.getCobolValidNum()))+"");
			}
			if(null != dto.getJclList() && dto.getJclList().size() > 0) {
				dto.setFilesSize((Integer.parseInt(dto.getFilesSize())+Integer.parseInt(dto.getJclFilesSize()))+"");
				dto.setTotalNum((Integer.parseInt(dto.getTotalNum()) + Integer.parseInt(dto.getJclTotalNum()))+"");
				dto.setValidNum((Integer.parseInt(dto.getValidNum()) + Integer.parseInt(dto.getJclValidNum()))+"");
			}

			// 静的なページの生成
			Map<Object, Object> dataModel = new HashMap<Object, Object>();
			dataModel.put("OutDto", dto);
			CobolWriterFreemarker.writerTop(dataModel);
			if(null != dto.getCobolList() && dto.getCobolList().size() > 0) {
				CobolWriterFreemarker.writerCobol(dataModel);
				CobolWriterFreemarker.writerCobolsIO(dataModel);
				CobolWriterFreemarker.writerCobolsCall(dataModel);
			}
			if(null != dto.getJclList() && dto.getJclList().size() > 0) {
				JclWriterFreemarker.writerJcl(dataModel);
				JclWriterFreemarker.writerJclsIO(dataModel);
				JclWriterFreemarker.writerJclsCall(dataModel);
			}

			log.info("処理が成功した");
		} catch (Exception e) {
			log.error("処理に失敗しました");
			e.printStackTrace();
		}
	}

	/**
	 * cobol処理
	 * 
	 * @param fileList
	 * @param prop
	 * @throws Exception
	 */
	private static void cobol(List<String> fileList, Properties prop, TopDto dto) throws Exception {

		int totalNum = 0;
		int validNum = 0;

		List<CobolDto> cobolList = new ArrayList<CobolDto>();

		// 同名資産checkMap
		Map<String, String> checkMap = new HashMap<String, String>();

		// 循環処理ファイル
		for (String path : fileList) {
			log.info("Cobol情報解析:" + path);

			// 無視桁数
			int startNum = 6;

			// Cobol情報の取得処理
			CobolDto cobolDto = new CobolDto();
			// ライプライ名
			String[] paths = path.split("\\\\");
			cobolDto.setRaipiraiName(paths[paths.length - 2]);
			cobolDto.setPgmName(paths[paths.length - 1].substring(0, paths[paths.length - 1].lastIndexOf(".")));
			// ファイルの内容取得
			List<String> lines = ReadFile.read(path);
			if (null == lines || lines.size() == 0) {
				log.error("ファイルが空:" + path);
				cobolList.add(cobolDto);
				String formatPath = prop.getProperty("download_file") + "\\format\\" + cobolDto.getRaipiraiName() + "\\"
						+ paths[paths.length - 1];
				WriteFile.write(formatPath, "");
				continue;
			}

			// 総行数
			cobolDto.setTotalNum(lines.size() + "");
			// 有効行数
			lines = FromatCobolLines.delComment(lines, startNum);
			cobolDto.setValidNum(lines.size() + "");
			// 整形共通处理
			lines = FromatCobolLines.stopSignJoin(lines, startNum);
			lines = FromatCobolLines.spaceToTab(lines);

			// 整形後FILE
			String content = "";
			for (String line : lines) {
				if (StringUtils.isEmpty(content)) {
					content = line;
				} else {
					content = content + "\n" + line;
				}
			}
			String formatPath = prop.getProperty("download_file") + "\\format\\" + cobolDto.getRaipiraiName() + "\\"
					+ paths[paths.length - 1];
			WriteFile.write(formatPath, content);

			// Cobol情報
			CobolService.getCobolInfo(path, fileList, startNum, cobolDto, lines);

			// 同名資産check
			if (null == checkMap.get(cobolDto.getPgmName())) {
				checkMap.put(cobolDto.getPgmName(), cobolDto.getPgmName());
			} else {
				cobolDto.setSameName("〇");
			}

			cobolList.add(cobolDto);

			totalNum = totalNum + Integer.parseInt(cobolDto.getTotalNum());
			validNum = validNum + Integer.parseInt(cobolDto.getValidNum());

		}

		dto.setCobolList(cobolList);
		dto.setCobolTotalNum(totalNum + "");
		dto.setCobolValidNum(validNum + "");

	}

	/**
	 * jcl処理
	 * 
	 * @param fileList
	 * @param prop
	 * @throws Exception
	 */
	private static void jcl(List<String> fileList, Properties prop, TopDto dto, List<String> cobolFileList) throws Exception {

		int totalNum = 0;
		int validNum = 0;

		List<JclDto> jclList = new ArrayList<JclDto>();

		// 同名資産checkMap
		Map<String, String> checkMap = new HashMap<String, String>();

		// 循環処理ファイル
		for (String path : fileList) {
			log.info("Jcl情報解析:" + path);

			// 無視桁数
			int startNum = 8;

			// Jcl情報の取得処理
			JclDto jclDto = new JclDto();
			// ライプライ名
			String[] paths = path.split("\\\\");
			jclDto.setRaipiraiName(paths[paths.length - 2]);
			jclDto.setPgmName(paths[paths.length - 1].substring(0, paths[paths.length - 1].lastIndexOf(".")));
			// ファイルの内容取得
			List<String> lines = ReadFile.read(path);
			if (null == lines || lines.size() == 0) {
				log.error("ファイルが空:" + path);
				jclList.add(jclDto);
				String formatPath = prop.getProperty("download_file") + "\\format\\" + jclDto.getRaipiraiName() + "\\"
						+ paths[paths.length - 1];
				WriteFile.write(formatPath, "");
				continue;
			}

			// 総行数
			jclDto.setTotalNum(lines.size() + "");
			// 有効行数
			lines = FromatJclLines.delComment(lines, startNum);
			jclDto.setValidNum(lines.size() + "");
			// 整形共通处理
			lines = FromatJclLines.stopSignJoin(lines, startNum);
			lines = FromatJclLines.spaceToTab(lines);

			// 整形後FILE
			String content = "";
			for (String line : lines) {
				if (StringUtils.isEmpty(content)) {
					content = line;
				} else {
					content = content + "\n" + line;
				}
			}
			String formatPath = prop.getProperty("download_file") + "\\format\\" + jclDto.getRaipiraiName() + "\\"
					+ paths[paths.length - 1];
			WriteFile.write(formatPath, content);
			
			// Jcl情報
			JclService.getJclInfo(path, cobolFileList, startNum, jclDto, lines);

			// 同名資産check
			if (null == checkMap.get(jclDto.getPgmName())) {
				checkMap.put(jclDto.getPgmName(), jclDto.getPgmName());
			} else {
				jclDto.setSameName("〇");
			}

			totalNum = totalNum + Integer.parseInt(jclDto.getTotalNum());
			validNum = validNum + Integer.parseInt(jclDto.getValidNum());

			jclList.add(jclDto);
		}

		dto.setJclList(jclList);
		dto.setJclTotalNum(totalNum + "");
		dto.setJclValidNum(validNum + "");

	}
}
