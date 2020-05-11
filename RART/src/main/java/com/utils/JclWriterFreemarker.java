package com.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.dto.JclDto;
import com.dto.TopDto;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class JclWriterFreemarker {
	private static final Logger log = Logger.getLogger(JclWriterFreemarker.class);

	/**
	 * Jclページの生成
	 * 
	 * @param dataModel 静的データ
	 * @throws Exception
	 */
	public static void writerJcl(Map<Object, Object> dataModel) throws Exception {
		Writer out = null;
		try {
			InputStream in = JclWriterFreemarker.class.getClassLoader().getResource("resources/param.properties")
					.openStream();
			Properties prop = new Properties();
			prop.load(in);

			String ftl_path = System.getProperty("user.dir") + "/src/main/webapp/WEB-INF/ftl";
			if (StringUtils.isEmpty(ftl_path)) {
				log.error("テンプレートのパスが空です");
				return;
			}

			String download_file = prop.getProperty("download_file");
			if (StringUtils.isEmpty(download_file)) {
				log.error("ファイルを生成するパスが空です。");
				return;
			}

			Configuration configuration = new Configuration(Configuration.getVersion());
			configuration.setDirectoryForTemplateLoading(new File(ftl_path));
			configuration.setDefaultEncoding("utf-8");
			Template template = configuration.getTemplate("jcl.ftl");

			File file = new File(download_file + "/jcl.html");
			Utils.createFile(file);

			out = new FileWriter(file);
			template.process(dataModel, out);
			out.close();

			log.info("Jclページの生成処理終了");
		} catch (Exception e) {
			log.error("Jclページの生成に失敗しました。");
			e.printStackTrace();
			throw e;
		} finally {
			if (null != out) {
				out.close();
			}
		}
	}

	/**
	 * Jclファイル入出力ページの生成
	 * 
	 * @param dataModel 静的データ
	 * @throws Exception
	 */
	public static void writerJclsIO(Map<Object, Object> dataModel) throws Exception {
		TopDto dto = (TopDto) dataModel.get("OutDto");
		List<JclDto> jclList = dto.getJclList();
		for (JclDto jcl : jclList) {
			if (null != jcl.getIosList() && jcl.getIosList().size() > 0) {
				writerIO(jcl);
			}
		}

		log.info("Jclファイル入出力ページの生成処理すべて終了");
	}

	/**
	 * jclファイル入出力ページの生成
	 * 
	 * @param dataModel 静的データ
	 * @throws Exception
	 */
	public static void writerIO(JclDto jcl) throws Exception {
		log.info("Jclファイル入出力ページの生成:" + jcl.getRaipiraiName() + "/" + jcl.getPgmName());

		Map<Object, Object> data = new HashMap<Object, Object>();
		data.put("jcl", jcl);
		Writer out = null;
		try {
			InputStream in = JclWriterFreemarker.class.getClassLoader().getResource("resources/param.properties")
					.openStream();
			Properties prop = new Properties();
			prop.load(in);

			String ftl_path = System.getProperty("user.dir") + "/src/main/webapp/WEB-INF/ftl";
			if (StringUtils.isEmpty(ftl_path)) {
				log.error("テンプレートのパスが空です");
				return;
			}

			String download_file = prop.getProperty("download_file");
			if (StringUtils.isEmpty(download_file)) {
				log.error("ファイルを生成するパスが空です。");
				return;
			}

			Configuration configuration = new Configuration(Configuration.getVersion());
			configuration.setDirectoryForTemplateLoading(new File(ftl_path));
			configuration.setDefaultEncoding("utf-8");
			Template template = configuration.getTemplate("jcl_IO.ftl");

			File file = new File(download_file + "/jcl" + "/" + jcl.getRaipiraiName() + "/" + jcl.getRaipiraiName()
					+ "_" + jcl.getPgmName() + "_IO.html");
			Utils.createFile(file);

			out = new FileWriter(file);
			template.process(data, out);
			out.close();

		} catch (Exception e) {
			log.error("Jclファイル入出力ページの生成失敗:" + jcl.getRaipiraiName() + "/" + jcl.getPgmName());
			e.printStackTrace();
			throw e;
		} finally {
			if (null != out) {
				out.close();
			}
		}
	}

	/**
	 * Jcl呼び出し関係ページの生成
	 * 
	 * @param dataModel 静的データ
	 * @throws Exception
	 */
	public static void writerJclsCall(Map<Object, Object> dataModel) throws Exception {
		TopDto dto = (TopDto) dataModel.get("OutDto");
		List<JclDto> jclList = dto.getJclList();
		for (JclDto jcl : jclList) {
			if (null != jcl.getCallList() && jcl.getCallList().size() > 0) {
				writerCall(jcl);
			}
		}

		log.info("Jcl呼び出し関係ページの生成処理すべて終了");
	}

	/**
	 * Cobol呼び出し関係ページの生成
	 * 
	 * @param dataModel 静的データ
	 * @throws Exception
	 */
	public static void writerCall(JclDto jcl) throws Exception {
		log.info("Jcl呼び出し関係ページの生成:" + jcl.getRaipiraiName() + "/" + jcl.getPgmName());

		Map<Object, Object> data = new HashMap<Object, Object>();
		data.put("jcl", jcl);
		Writer out = null;
		try {
			InputStream in = CobolWriterFreemarker.class.getClassLoader().getResource("resources/param.properties")
					.openStream();
			Properties prop = new Properties();
			prop.load(in);

			String ftl_path = System.getProperty("user.dir") + "/src/main/webapp/WEB-INF/ftl";
			if (StringUtils.isEmpty(ftl_path)) {
				log.error("テンプレートのパスが空です");
				return;
			}

			String download_file = prop.getProperty("download_file");
			if (StringUtils.isEmpty(download_file)) {
				log.error("ファイルを生成するパスが空です。");
				return;
			}

			Configuration configuration = new Configuration(Configuration.getVersion());
			configuration.setDirectoryForTemplateLoading(new File(ftl_path));
			configuration.setDefaultEncoding("utf-8");
			Template template = configuration.getTemplate("jcl_CALL.ftl");

			File file = new File(download_file + "/jcl" + "/" + jcl.getRaipiraiName() + "/" + jcl.getRaipiraiName()
					+ "_" + jcl.getPgmName() + "_CALL.html");
			Utils.createFile(file);

			out = new FileWriter(file);
			template.process(data, out);
			out.close();

		} catch (Exception e) {
			log.error("Jcl呼び出し関係ページの生成失敗:" + jcl.getRaipiraiName() + "/" + jcl.getPgmName());
			e.printStackTrace();
			throw e;
		} finally {
			if (null != out) {
				out.close();
			}
		}
	}
}
