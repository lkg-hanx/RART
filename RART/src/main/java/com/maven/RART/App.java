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
import com.utils.ReadFile;
import com.utils.WriterFreemarker;


/**
 * Hello world!
 *
 */
public class App {
	
	private static final Logger log = Logger.getLogger(App.class);
	
	
	public static void main(String[] args) {
		try {
			
			//配置文件读取
			InputStream in = WriterFreemarker.class.getClassLoader().getResource("resources/param.properties").openStream();
			Properties prop = new Properties();
			prop.load(in);

			String uploadFile = prop.getProperty("upload_file");
			if(StringUtils.isEmpty(uploadFile)) {
				log.error("上传文件为空");
				return;
			}
			//文件读取
			List<String> lines = ReadFile.read(uploadFile);
			if(null == lines || lines.size() == 0) {
				log.error("上传文件为空");
				return;
			}
			// TODO 整形共同处理
			
			// TODO 情报取得处理
			OutDto dto = new OutDto();
			List<OutDto> dtoList =new  ArrayList<OutDto>();
			dto.setTitle("1");
			dto.setMenu("1");
			dto.setContent("1");
			dtoList.add(dto);
			OutDto dto1 = new OutDto();
			dto1.setTitle("2");
			dto1.setMenu("2");
			dto1.setContent("2");
			dtoList.add(dto1);
			
			Map<Object, Object> dataModel = new HashMap<Object, Object>();
	        dataModel.put("OutDto", dtoList);
	        
	        // TODO 静态页生成
	        WriterFreemarker.writerFreemarker(dataModel);
	        log.info("处理成功");
		} catch (Exception e) {
			log.error("处理失败");
			e.printStackTrace();
		}
	}

}
