package com.maven.RART;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.dto.OutDto;
import com.utils.FromatLines;
import com.utils.ReadFile;
import com.utils.Utils;
import com.utils.WriterFreemarker;


public class App {
	
	private static final Logger log = Logger.getLogger(App.class);
	
	/**
	 * 启动方法
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
			//配置文件读取
			InputStream in = WriterFreemarker.class.getClassLoader().getResource("resources/param.properties").openStream();
			Properties prop = new Properties();
			prop.load(in);

			String uploadFile = prop.getProperty("upload_file");
			if(StringUtils.isEmpty(uploadFile)) {
				log.error("上传目录为空");
				return;
			}
			
			// 所有文件路径
			List<String> fileList = new ArrayList<String>();
			Utils.readfile(uploadFile, fileList);
			
			// 静态页用数据
			List<OutDto> dtoList =new  ArrayList<OutDto>();
			
			// 循环处理文件
			for(String path:fileList) {
				
				//文件读取
				List<String> lines = ReadFile.read(path);
				if(null == lines || lines.size() == 0) {
					log.error("文件为空");
					continue;
				}
				
				// 整形共通处理
				int startNum = 0;
				if(!StringUtils.isEmpty(prop.getProperty("format_start_num"))) {
					startNum = Integer.parseInt(prop.getProperty("format_start_num"));
				}
				List<String> formatLines = FromatLines.format(lines, startNum);
				if(null == formatLines || formatLines.size() == 0) {
					log.error("文件共通处理后为空");
					continue;
				}
				
				// TODO 情报取得处理
				OutDto dto = new OutDto();
				dto.setTitle("1");
				dto.setMenu("1");
				dto.setContent("1");
				dtoList.add(dto);
		        
			}
			Map<Object, Object> dataModel = new HashMap<Object, Object>();
			dataModel.put("OutDto", dtoList);
	        
	        // 静态页生成
	        WriterFreemarker.writerFreemarker(dataModel);
	        log.info("处理成功");
		} catch (Exception e) {
			log.error("处理失败");
			e.printStackTrace();
		}
	}

}
