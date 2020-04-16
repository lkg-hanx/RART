package com.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.maven.RART.App;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 
 * @author hanx
 *
 */
public class WriterFreemarker {
	private static final Logger log = Logger.getLogger(WriterFreemarker.class);
	
	public static void writerFreemarker(Map<Object, Object> dataModel) throws Exception {
		Writer out = null;
		try {
			InputStream in = WriterFreemarker.class.getClassLoader().getResource("resources/param.properties").openStream();
			Properties prop = new Properties();
			prop.load(in);

			String ftl_path = prop.getProperty("ftl_path");
			if(StringUtils.isEmpty(ftl_path)) {
				log.error("模板路径为空");
				return;
			}

			// 创建Configuration对象
			Configuration configuration = new Configuration(Configuration.getVersion());
			// 设置模板文件所在的路径
			configuration.setDirectoryForTemplateLoading(new File(ftl_path));
			// 设置模板文件使用的字符集
			configuration.setDefaultEncoding("utf-8");
			// 加载模板
			Template template = configuration.getTemplate("index.ftl");
			
			// 指定生成的文件名
			File file = new File("D:/temp/hello.html");
			// 获取父目录
			File fileParent = file.getParentFile();
			// 判断是否存在
			if (!fileParent.exists()) {
				// 创建父目录文件
				fileParent.mkdirs();
			}
			file.createNewFile();
			
			out = new FileWriter(file);
			// 调用模板对象的process方法输出文件。
			template.process(dataModel, out);
			
			out.close();
		} catch (Exception e) {
			log.error("静态页生成失败");
			e.printStackTrace();
		} finally {
			if (null != out) {
				out.close();
			}
		}
	}
}
