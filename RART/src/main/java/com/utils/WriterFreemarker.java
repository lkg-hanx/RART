package com.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class WriterFreemarker {
	private static final Logger log = Logger.getLogger(WriterFreemarker.class);
	
	/**
	 * 静态页生成
	 * @param dataModel 静态数据
	 * @throws Exception
	 */
	public static void writerFreemarker(Map<Object, Object> dataModel) throws Exception {
		Writer out = null;
		try {
			InputStream in = WriterFreemarker.class.getClassLoader().getResource("resources/param.properties").openStream();
			Properties prop = new Properties();
			prop.load(in);

			String ftl_path=System.getProperty("user.dir") + "/src/main/webapp/WEB-INF/ftl"; 
			if(StringUtils.isEmpty(ftl_path)) {
				log.error("模板路径为空");
				return;
			}
			
			String download_file = prop.getProperty("download_file");
			if(StringUtils.isEmpty(download_file)) {
				log.error("生成文件路径为空");
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
			File file = new File(download_file+"/index.html");
			Utils.createFile(file);
			
			out = new FileWriter(file);
			// 调用模板对象的process方法输出文件。
			template.process(dataModel, out);
			out.close();
			
			// css
			File cssFile = new File(ftl_path+"/bootstrap.css");
			File newCssFile = new File(download_file+"/css/bootstrap.css");
			Utils.createFile(newCssFile);
			CopyFile.copy(cssFile, newCssFile);
			
			// img
			File imgFile = new File(ftl_path+"/aa.png");
			File newImgFile = new File(download_file+"/img/aa.png");
			Utils.createFile(newImgFile);
			CopyFile.copy(imgFile, newImgFile);
			
			
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
