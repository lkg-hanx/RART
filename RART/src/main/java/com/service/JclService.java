package com.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.dto.CobolDto;
import com.dto.CobolIODto;
import com.dto.JclCallDto;
import com.dto.JclDto;
import com.dto.JclIODto;
import com.dto.JclIOsDto;
import com.utils.FromatCobolLines;
import com.utils.ReadFile;
import com.utils.Utils;

public class JclService {
	private static final Logger log = Logger.getLogger(JclService.class);

	/**
	 * Jcl情報の取得処理
	 * 
	 * @param path          ファイルパス
	 * @param cobolFileList すべてのファイルパス
	 * @param startNum      無視桁数
	 * @param cobolDto
	 * @param lines
	 * @throws Exception
	 */
	public static void getJclInfo(String path, List<String> cobolFileList, int startNum, JclDto jclDto,
			List<String> lines) throws Exception {

		// PGM名 ファイル入出力
		for (String line : lines) {
			String[] split = line.split("\\t");
			for (int i = 0; i < split.length; i++) {
				String str = split[i];
				if (("JOB").equals(str)) {
					if (i + 1 < split.length) {
						jclDto.setIoPath(split[i + 1].replace(".", "") + "_IO");
						jclDto.setCallPath(split[i + 1].replace(".", "") + "_CALL");
					}
				}
			}

		}

		// 呼び出し関係
		List<JclCallDto> callList = getJclCallInfo(lines, startNum, jclDto);
		if (null != callList && callList.size() > 0) {
			jclDto.setCallList(callList);
		} else {
			jclDto.setCallPath(null);
		}

		// ファイル入出力
		List<JclIOsDto> iosList = getJclIOInfo(lines, jclDto);
		for (JclIOsDto iosDto : iosList) {
			try {
				if (!StringUtils.isEmpty(iosDto.getSteveName())) {
					boolean existFlag = false;
					for (String cobolPath : cobolFileList) {
						// Cobol情報の取得
						File file = new File(cobolPath);
						String fileName = file.getName().substring(0, file.getName().indexOf("."));
						if (iosDto.getSteveName().equals(fileName)) {
							existFlag = true;
							CobolDto cobolDto = new CobolDto();
							String[] paths = cobolPath.split("\\\\");
							cobolDto.setRaipiraiName(paths[paths.length - 2]);
							cobolDto.setPgmName(
									paths[paths.length - 1].substring(0, paths[paths.length - 1].lastIndexOf(".")));

							List<String> cobolLines = ReadFile.read(cobolPath);
							if (null != cobolLines && cobolLines.size() > 0) {
								cobolLines = FromatCobolLines.format(cobolLines, 6);
								Map<String, CobolIODto> map = CobolService.getCobolIOInfo(cobolLines, cobolDto);

								// Cobol情報からJcl呼び出し関係を取得
								if (null != iosDto.getIoList() && iosDto.getIoList().size() > 0) {
									for (JclIODto ioDto : iosDto.getIoList()) {
										if (!StringUtils.isEmpty(ioDto.getFilesName())) {
											CobolIODto cobolIODto = map.get(ioDto.getFilesName());
											if (null != cobolIODto && !StringUtils.isEmpty(ioDto.getFileslType())
													&& !StringUtils.isEmpty(cobolIODto.getFileslType())
													&& cobolIODto.getFileslType().equals(ioDto.getFileslType())) {
												ioDto.setRecordKey(!StringUtils.isEmpty(cobolIODto.getRecordKey())
														? cobolIODto.getRecordKey()
														: null);
												ioDto.setDateRecordKey(
														!StringUtils.isEmpty(cobolIODto.getDateRecordKey())
																? cobolIODto.getDateRecordKey()
																: null);
												ioDto.setIoType(!StringUtils.isEmpty(cobolIODto.getIoType())
														? cobolIODto.getIoType()
														: null);
												ioDto.setAccessMode(!StringUtils.isEmpty(cobolIODto.getAccessMode())
														? cobolIODto.getAccessMode()
														: null);
											}
										} else if (!StringUtils.isEmpty(ioDto.getFileslType())) {
											for (Map.Entry<String, CobolIODto> entry : map.entrySet()) {
												CobolIODto cobolIODto = entry.getValue();
												if (null != cobolIODto
														&& !StringUtils.isEmpty(cobolIODto.getFileslType())
														&& ioDto.getFileslType().equals(cobolIODto.getFileslType())) {
													ioDto.setRecordKey(!StringUtils.isEmpty(cobolIODto.getRecordKey())
															? cobolIODto.getRecordKey()
															: null);
													ioDto.setDateRecordKey(
															!StringUtils.isEmpty(cobolIODto.getDateRecordKey())
																	? cobolIODto.getDateRecordKey()
																	: null);
													ioDto.setIoType(!StringUtils.isEmpty(cobolIODto.getIoType())
															? cobolIODto.getIoType()
															: null);
													ioDto.setAccessMode(!StringUtils.isEmpty(cobolIODto.getAccessMode())
															? cobolIODto.getAccessMode()
															: null);
												}
											}

										}
									}
								}
							}
						}
					}
					if (!existFlag) {
						iosDto.setNotes("対象資産が存在しません。");
					}
				}
			} catch (Exception e) {
				log.error("Cobol情報からJcl呼び出し関係を取得失敗；ステッブ名(EX)：" + iosDto.getSteveName());
				e.printStackTrace();
				throw e;
			}
		}
		jclDto.setIosList(iosList);

		log.info("Jcl情報の取得処理終了");
	}

	/**
	 * ファイル入出力情報の取得処理
	 * 
	 * @param lines
	 * @param jclDto
	 * @return
	 * @throws Exception
	 */
	public static List<JclIOsDto> getJclIOInfo(List<String> lines, JclDto jclDto) throws Exception {
		log.info("Jcl入出力情報解析:" + jclDto.getPgmName());
		int lineNum = 0;
		try {
			List<JclIOsDto> iosList = new ArrayList<JclIOsDto>();

			// EXList取得
			List<Map<Integer, String>> EXList = new ArrayList<Map<Integer, String>>();
			for (int i = 0; i < lines.size(); i++) {
				lineNum = i + 1;
				String line = lines.get(i);
				String[] split = line.split("\\t");
				if (split.length > 2 && ("EX").equals(split[1]) && !("*").equals(split[2].substring(0, 1))) {
					Map<Integer, String> ex = new HashMap<Integer, String>();
					ex.put(i, line);
					for (int j = i + 1; j < lines.size(); j++) {
						split = lines.get(j).split("\\t");
						if (split.length > 2 && ("EX").equals(split[1])) {
							break;
						}
						ex.put(j, lines.get(j));
					}
					EXList.add(ex);
				}
			}

			for (Map<Integer, String> ex : EXList) {

				JclIOsDto iosDto = new JclIOsDto();
				List<JclIODto> ioList = new ArrayList<JclIODto>();

				boolean prglibFlag = false;
				for (Map.Entry<Integer, String> exEntry : ex.entrySet()) {
					lineNum = exEntry.getKey() + 1;

					String line = exEntry.getValue();
					String[] split = line.split("\\t");
					if (split.length > 2 && ("EX").equals(split[1])) {
						// ステッブ名(EX)
						String[] split2 = split[2].split(",");
						iosDto.setSteveName(split2[0]);
					}

					boolean useFlag = false;
					if (split.length > 2 && ("FD").equals(split[1]) && !("*").equals(split[2].substring(0, 1))) {
						JclIODto ioDto = new JclIODto();
						Map<String, String> map = Utils.stringToMap(split[2]);
						if (!StringUtils.isEmpty(map.get("PRGLIB"))) {
							prglibFlag = true;
						}
						if (!StringUtils.isEmpty(map.get("PRGLIB")) && !StringUtils.isEmpty(map.get("FILE"))) {
							continue;
						} else if (!StringUtils.isEmpty(map.get("SYSDBOUT"))) {
							continue;
						} else if (prglibFlag && !StringUtils.isEmpty(map.get("CF"))
								&& !StringUtils.isEmpty(map.get("FILE"))) {
							continue;
						} else if (!prglibFlag && !StringUtils.isEmpty(map.get("CF"))
								&& !StringUtils.isEmpty(map.get("FILE"))) {
							// ファイル名
							ioDto.setFilesName(map.get("FILE"));
							for (Map.Entry<String, String> entry : map.entrySet()) {
								if (entry.getValue().equals("DA")) {
									// 種别名
									ioDto.setFileslType(entry.getKey());
								}
							}
						} else if (!StringUtils.isEmpty(map.get("STEPCAT"))) {
							continue;
						} else if (!StringUtils.isEmpty(map.get("LIST"))) {
							continue;
						} else {
							for (Map.Entry<String, String> entry : map.entrySet()) {
								if (entry.getKey().equals("FILE")) {
									// ファイル名
									ioDto.setFilesName(entry.getValue());
									useFlag = true;
								}
								if (entry.getValue().equals("DA")) {
									// 種别名
									ioDto.setFileslType(entry.getKey());
									useFlag = true;
								}
							}
							if (useFlag) {
								ioList.add(ioDto);
							}
						}

					}

					if (split.length > 2 && ("SW").equals(split[1])) {
						JclIODto ioDto = new JclIODto();
						Map<String, String> map = Utils.stringToMap(split[2]);
						for (Map.Entry<String, String> entry : map.entrySet()) {
							if (entry.getKey().equals("FILE")) {
								// ファイル名
								ioDto.setFilesName(entry.getValue());
								useFlag = true;
							}
							if (entry.getValue().equals("DA")) {
								// 種别名
								ioDto.setFileslType(entry.getKey());
								useFlag = true;
							}
						}
						if (useFlag) {
							ioList.add(ioDto);
						}
					}
				}
				iosDto.setIoList(ioList);
				iosList.add(iosDto);
			}

			return iosList;

		} catch (Exception e) {
			log.error("Jcl入出力情報解析失敗；失敗キー：ステッブ名(EX)、ファイル名、種别名；失敗行数：" + lineNum);
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 呼び出し関係取得
	 * 
	 * @param lines
	 * @param startNum
	 * @return
	 * @throws Exception
	 */
	public static List<JclCallDto> getJclCallInfo(List<String> lines, int startNum, JclDto jclDto) throws Exception {
		log.info("Jcl呼び出し関係情報解析:" + jclDto.getPgmName());

		List<JclCallDto> callList = new ArrayList<JclCallDto>();

		try {
			for (int i = 0; i < lines.size(); i++) {
				String line = lines.get(i);
				String[] split = line.split("\\t");

				if (split.length > 2 && ("EX").equals(split[1]) && !("*").equals(split[2].substring(0, 1))) {
					JclCallDto callDto = new JclCallDto();

					String[] split2 = split[2].split(",");
					callDto.setCallTarget(split2[0]);
					if (split2.length > 1) {
						callDto.setCondition(split2[1]);
					}

					line = lines.get(i + 1);
					split = line.split("\\t");
					if (("PARA").equals(split[1])) {
						String parameter = "";
						for (int j = 2; j < split.length; j++) {
							if (StringUtils.isEmpty(parameter)) {
								parameter = split[j];
							} else {
								parameter = parameter + " " + split[j];
							}
						}
						callDto.setParameter(parameter);
					}
					callList.add(callDto);
				}
			}

			return callList;
		} catch (Exception e) {
			log.error("Jcl呼び出し関係情報解析失敗");
			e.printStackTrace();
			throw e;
		}
	}

}
