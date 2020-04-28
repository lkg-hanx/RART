package com.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.dto.CallDto;
import com.dto.CobolDto;
import com.dto.IODto;
import com.utils.FromatLines;
import com.utils.ReadFile;

public class CobolService {


	/**
	 * Cobol情報の取得処理
	 * 
	 * @param path     ファイルパス
	 * @param fileList すべてのファイルパス
	 * @param startNum 無視桁数
	 * @param cobolDto
	 * @param lines
	 * @throws Exception
	 */
	public static void getCobolInfo(String path, List<String> fileList, int startNum, CobolDto cobolDto, List<String> lines) throws Exception {
		

		// PGM名 ファイル入出力
		for (String line : lines) {
			String[] split = line.split("\\t");
			for (int i = 0; i < split.length; i++) {
				String str = split[i];
				if (("PROGRAM-ID.").equals(str)) {
					if (i + 1 < split.length) {
						cobolDto.setPgmName(split[i + 1].replace(".", ""));
						cobolDto.setIoPath(split[i + 1].replace(".", "") + "_IO");
						cobolDto.setCallPath(split[i + 1].replace(".", "") + "_CALL");
					}
				}
			}

		}

		// ファイル入出力
		cobolDto.setIoList(getCobolIOInfo(lines));

		// 呼び出し関係
		List<CallDto> callList = getCobolCallInfo(lines, fileList, startNum);
		if (null != callList && callList.size() > 0) {
			cobolDto.setCallList(callList);
		}else {
			cobolDto.setCallPath(null);
		}
		
	}

	/**
	 * ファイル入出力情報の取得処理
	 * 
	 * @param lines
	 * @return
	 * @throws Exception
	 */
	public static List<IODto> getCobolIOInfo(List<String> lines) throws Exception {

		Map<String, IODto> map = new HashMap<String, IODto>();
		for (String line : lines) {
			IODto ioDto = new IODto();
			String[] split = line.split("\\t");
			for (int i = 0; i < split.length; i++) {
				String str = split[i];
				if (("SELECT").equals(str)) {
					// ファイル名
					ioDto.setFilesName(split[i + 1].replace(".", ""));
				}
				if (!StringUtils.isEmpty(ioDto.getFilesName()) && ("ASSIGN").equals(str)
						&& ("TO").equals(split[i + 1])) {
					// 種别名
					ioDto.setFileslType(split[i + 2].replace(".", ""));
				}
				if (!StringUtils.isEmpty(ioDto.getFilesName()) && ("ACCESS").equals(str)
						&& ("MODE").equals(split[i + 1]) && ("IS").equals(split[i + 2])) {
					// アクセスモード
					ioDto.setAccessMode(split[i + 3].replace(".", ""));
				}
				if (!StringUtils.isEmpty(ioDto.getFilesName()) && ("RECORD").equals(str) && ("KEY").equals(split[i + 1])
						&& ("IS").equals(split[i + 2])) {
					// レコードキー
					ioDto.setRecordKey(split[i + 3].replace(".", ""));
				}

			}
			if (!StringUtils.isEmpty(ioDto.getFilesName())) {
				map.put(ioDto.getFilesName(), ioDto);
			}
		}

		// データレコードキー
		for (String line : lines) {
			String filesName = "";
			String[] split = line.split("\\t");
			for (int i = 0; i < split.length; i++) {
				String str = split[i];
				if (("FD").equals(str)) {
					if (null != map.get(split[i + 1])) {
						filesName = split[i + 1];
					}
				}
			}
			if (!StringUtils.isEmpty(filesName)) {
				for (int i = 0; i < split.length; i++) {
					String str = split[i];
					if (("DATA").equals(str) && ("RECORD").equals(split[i + 1]) && ("IS").equals(split[i + 2])) {
						map.get(filesName).setDateRecordKey(split[i + 3].replace(".", ""));
					}
				}
			}
		}

		// 入出力
		for (String line : lines) {
			String filesName = "";
			String[] split = line.split("\\t");
			for (int i = 0; i < split.length; i++) {
				String str = split[i];
				if (("OPEN").equals(str)) {
					for(int j = 2; j+i < split.length; j++) {
						if (null != map.get(split[i + j].replace(".", ""))) {
							filesName = split[i + j].replace(".", "");
							if (("INPUT").equals(split[i + 1])) {
								map.get(filesName).setIoType("I");
							}
							if (("OUTPUT").equals(split[i + 1])) {
								map.get(filesName).setIoType("O");
							}
							if (("EXTEND").equals(split[i + 1])) {
								map.get(filesName).setIoType("O");
							}
							if (("I-O").equals(split[i + 1])) {
								map.get(filesName).setIoType("I-O");
							}
						}
					}
				}
			}
		}

		List<IODto> ioDtoList = new ArrayList<IODto>();
		for (IODto value : map.values()) {
			ioDtoList.add(value);
		}
		return ioDtoList;
	}

	/**
	 * 呼び出し関係取得
	 * 
	 * @param lines
	 * @param fileList
	 * @param startNum
	 * @return
	 * @throws Exception
	 */
	public static List<CallDto> getCobolCallInfo(List<String> lines, List<String> fileList, int startNum)
			throws Exception {

		// 階層1取得
		List<String[]> callGroup = new ArrayList<String[]>();
		for (String line : lines) {
			String[] call = new String[10];
			String[] split = line.split("\\t");
			for (int i = 0; i < split.length; i++) {
				String str = split[i];
				if (("CALL").equals(str)) {
					call[0] = split[i + 1].replace("\"", "").replace(".", "");
					callGroup.add(call);
				}
			}
		}

		// で、他の階層を取得
		if (null != callGroup && callGroup.size() > 0) {
			List<String[]> newCallGroup = updateCall(callGroup, fileList, startNum);
			for (int i = 0; i < 10; i++) {
				newCallGroup = updateCall(newCallGroup, fileList, startNum);
			}
			List<CallDto> callList = new ArrayList<CallDto>();
			for (String[] callInfo : newCallGroup) {
				CallDto calDto = new CallDto();
				for (int i = 0; i < callInfo.length; i++) {
					if (!StringUtils.isEmpty(callInfo[i])) {
						if (i == 0) {
							calDto.setStratum1(callInfo[i]);
						}
						if (i == 1) {
							calDto.setStratum2(callInfo[i]);
						}
						if (i == 2) {
							calDto.setStratum3(callInfo[i]);
						}
						if (i == 3) {
							calDto.setStratum4(callInfo[i]);
						}
						if (i == 4) {
							calDto.setStratum5(callInfo[i]);
						}
						if (i == 5) {
							calDto.setStratum6(callInfo[i]);
						}
						if (i == 6) {
							calDto.setStratum7(callInfo[i]);
						}
						if (i == 7) {
							calDto.setStratum8(callInfo[i]);
						}
						if (i == 8) {
							calDto.setStratum9(callInfo[i]);
						}
						if (i == 9) {
							calDto.setStratum10(callInfo[i]);
						}
					}
				}
				callList.add(calDto);
			}

			return callList;
		}
		return null;
	}

	/**
	 * 循環で、階層を取得
	 * 
	 * @param callGroup 原階層
	 * @param fileList  すべてのファイルパス
	 * @param startNum  無視桁数
	 * @return
	 * @throws Exception
	 */
	public static List<String[]> updateCall(List<String[]> callGroup, List<String> fileList, int startNum)
			throws Exception {
		List<String[]> newCallGroup = new ArrayList<String[]>();
		for (String[] group : callGroup) {
			for (int i = 0; i < group.length; i++) {
				if (!StringUtils.isEmpty(group[i]) && StringUtils.isEmpty(group[i + 1])) {
					for (String filePath : fileList) {
						File file = new File(filePath);
						String fileName = file.getName().substring(0, file.getName().indexOf("."));
						if (group[i].equals(fileName)) {
							boolean noHaveFlag = true;
							List<String> lines = ReadFile.read(filePath);
							lines = FromatLines.format(lines, startNum);
							for (String line : lines) {
								String[] split = line.split("\\t");
								for (int j = 0; j < split.length; j++) {
									String str = split[j];
									if (("CALL").equals(str)) {
										noHaveFlag = false;
										String[] copyGroup = group.clone();
										copyGroup[i + 1] = split[j + 1].replace("\"", "").replace(".", "");
										newCallGroup.add(copyGroup);
									}
								}
							}
							if (noHaveFlag) {
								newCallGroup.add(group);
							}
						}
					}
					break;
				}
				if (i + 1 == group.length - 1) {
					if (!StringUtils.isEmpty(group[i]) && !StringUtils.isEmpty(group[i + 1])) {
						newCallGroup.add(group);
					}
					break;
				}
			}
		}
		return newCallGroup;
	}

}
