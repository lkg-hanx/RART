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
import com.dto.TopDto;
import com.service.CobolService;
import com.utils.Utils;
import com.utils.WriterFreemarker;

public class begin {

	private static final Logger log = Logger.getLogger(begin.class);

	/**
	 * 開始方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			// プロファイル読みだし
			InputStream in = WriterFreemarker.class.getClassLoader().getResource("resources/param.properties")
					.openStream();
			Properties prop = new Properties();
			prop.load(in);

			String uploadFile = prop.getProperty("upload_file");
			if (StringUtils.isEmpty(uploadFile)) {
				log.error("アップロードパスが空です");
				return;
			}

			// すべてのファイルパス
			List<String> fileList = new ArrayList<String>();
			Utils.readfile(uploadFile, fileList);

			// TOP情報
			TopDto dto = new TopDto();
			dto.setFilesSize(fileList.size() + "");
			int totalNum = 0;
			int validNum = 0;
			List<CobolDto> cobolList = new ArrayList<CobolDto>();

			// 同名資産checkMap
			Map<String, String> checkMap = new HashMap<String, String>();

			// 循环处理文件
			for (String path : fileList) {

				int startNum = 0;
				if (!StringUtils.isEmpty(prop.getProperty("format_start_num"))) {
					startNum = Integer.parseInt(prop.getProperty("format_start_num"));
				}

				// 情报取得处理
				CobolDto cobolDto = CobolService.getCobolInfo(path, fileList, startNum);
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
			dto.setTotalNum(totalNum + "");
			dto.setValidNum(validNum + "");
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dto.setSystemTime(sf.format(new Date()));
			dto.setUser(prop.getProperty("user"));

			Map<Object, Object> dataModel = new HashMap<Object, Object>();
			dataModel.put("OutDto", dto);

			// 静态页生成
			WriterFreemarker.writerTop(dataModel);
			WriterFreemarker.writerCobol(dataModel);
			WriterFreemarker.writerCobolsIO(dataModel);
			WriterFreemarker.writerCobolsCall(dataModel);

			log.info("处理成功");
		} catch (Exception e) {
			log.error("处理失败");
			e.printStackTrace();
		}
	}

}
