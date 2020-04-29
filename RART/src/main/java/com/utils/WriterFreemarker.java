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

import com.dto.CobolDto;
import com.dto.TopDto;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class WriterFreemarker {
	private static final Logger log = Logger.getLogger(WriterFreemarker.class);
 
	/**
	 * トップページの生成
	 * 
	 * @param dataModel 静的データ
	 * @throws Exception
	 */
	public static void writerTop(Map<Object, Object> dataModel) throws Exception {
		Writer out = null;
		try {
			InputStream in = WriterFreemarker.class.getClassLoader().getResource("resources/param.properties")
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

			// 作成Configurationオブジェクト
			Configuration configuration = new Configuration(Configuration.getVersion());
			// テンプレートファイルのパスを設定します。
			configuration.setDirectoryForTemplateLoading(new File(ftl_path));
			// テンプレートファイルで使用する文字セットを設定します。
			configuration.setDefaultEncoding("utf-8");
			// テンプレートを読み込む
			Template template = configuration.getTemplate("index.ftl");

			// 生成したファイル名を指定します。
			File file = new File(download_file + "/index.html");
			Utils.createFile(file);

			out = new FileWriter(file);
			template.process(dataModel, out);
			out.close();

			// css
			File cssFile = new File(ftl_path + "/bootstrap.css");
			File newCssFile = new File(download_file + "/css/bootstrap.css");
			Utils.createFile(newCssFile);
			CopyFile.copy(cssFile, newCssFile);

			// img
			File imgFile = new File(ftl_path + "/aa.png");
			File newImgFile = new File(download_file + "/img/aa.png");
			Utils.createFile(newImgFile);
			CopyFile.copy(imgFile, newImgFile);
			
			log.info("トップページの生成処理終了");
		} catch (Exception e) {
			log.error("静的なページの生成に失敗しました。");
			e.printStackTrace();
			throw e;
		} finally {
			if (null != out) {
				out.close();
			}
		}
	}

	/**
	 * Cobolページの生成
	 * 
	 * @param dataModel 静的データ
	 * @throws Exception
	 */
	public static void writerCobol(Map<Object, Object> dataModel) throws Exception {
		Writer out = null;
		try {
			InputStream in = WriterFreemarker.class.getClassLoader().getResource("resources/param.properties")
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
			Template template = configuration.getTemplate("cobol.ftl");

			File file = new File(download_file + "/cobol.html");
			Utils.createFile(file);

			out = new FileWriter(file);
			template.process(dataModel, out);
			out.close();
			
			log.info("Cobolページの生成処理終了");
		} catch (Exception e) {
			log.error("Cobolページの生成に失敗しました。");
			e.printStackTrace();
			throw e;
		} finally {
			if (null != out) {
				out.close();
			}
		}
	}

	/**
	 * Cobolファイル入出力ページの生成
	 * 
	 * @param dataModel 静的データ
	 * @throws Exception
	 */
	public static void writerCobolsIO(Map<Object, Object> dataModel) throws Exception {
		TopDto dto = (TopDto) dataModel.get("OutDto");
		List<CobolDto> cobolList = dto.getCobolList();
		for (CobolDto cobol : cobolList) {
			if(null != cobol.getIoList() && cobol.getIoList().size() > 0) {
				writerIO(cobol);
			}
		}
		
		log.info("Cobolファイル入出力ページの生成処理すべて終了");
	}

	/**
	 * Cobolファイル入出力ページの生成
	 * 
	 * @param dataModel 静的データ
	 * @throws Exception
	 */
	public static void writerIO(CobolDto cobol) throws Exception {
		log.info("Cobolファイル入出力ページの生成:" + cobol.getRaipiraiName()+"/"+cobol.getPgmName());
		
		Map<Object, Object> data = new HashMap<Object, Object>();
		data.put("cobol", cobol);
		Writer out = null;
		try {
			InputStream in = WriterFreemarker.class.getClassLoader().getResource("resources/param.properties")
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
			Template template = configuration.getTemplate("cobol_IO.ftl");

			File file = new File(
					download_file + "/cobol" + "/" + cobol.getRaipiraiName()  + "/" + cobol.getRaipiraiName() + "_" + cobol.getPgmName() + "_IO.html");
			Utils.createFile(file);

			out = new FileWriter(file);
			template.process(data, out);
			out.close();
			
		} catch (Exception e) {
			log.error("Cobolファイル入出力ページの生成失敗:" + cobol.getRaipiraiName()+"/"+cobol.getPgmName());
			e.printStackTrace();
			throw e;
		} finally {
			if (null != out) {
				out.close();
			}
		}
	}

	/**
	 * Cobol呼び出し関係ページの生成
	 * 
	 * @param dataModel 静的データ
	 * @throws Exception
	 */
	public static void writerCobolsCall(Map<Object, Object> dataModel) throws Exception {
		TopDto dto = (TopDto) dataModel.get("OutDto");
		List<CobolDto> cobolList = dto.getCobolList();
		for (CobolDto cobol : cobolList) {
			if (null != cobol.getCallList() && cobol.getCallList().size() > 0) {
				writerCall(cobol);
			}
		}
		
		log.info("Cobol呼び出し関係ページの生成処理すべて終了");
	}

	/**
	 * Cobol呼び出し関係ページの生成
	 * 
	 * @param dataModel 静的データ
	 * @throws Exception
	 */
	public static void writerCall(CobolDto cobol) throws Exception {
		log.info("Cobol呼び出し関係ページの生成:" + cobol.getRaipiraiName()+"/"+cobol.getPgmName());
		
		Map<Object, Object> data = new HashMap<Object, Object>();
		data.put("cobol", cobol);
		Writer out = null;
		try {
			InputStream in = WriterFreemarker.class.getClassLoader().getResource("resources/param.properties")
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
			Template template = configuration.getTemplate("cobol_CALL.ftl");

			File file = new File(
					download_file + "/cobol" + "/" + cobol.getRaipiraiName() + "/" + cobol.getRaipiraiName() + "_" + cobol.getPgmName() + "_CALL.html");
			Utils.createFile(file);

			out = new FileWriter(file);
			template.process(data, out);
			out.close();

		} catch (Exception e) {
			log.error("Cobol呼び出し関係ページの生成失敗:" + cobol.getRaipiraiName()+"/"+cobol.getPgmName());
			e.printStackTrace();
			throw e;
		} finally {
			if (null != out) {
				out.close();
			}
		}
	}
}
