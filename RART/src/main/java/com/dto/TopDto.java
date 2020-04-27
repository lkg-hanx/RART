package com.dto;

import java.util.List;

/**
 * 作成情報
 * 
 * @author hanx
 *
 */
public class TopDto {

	// ファイル件数
	private String filesSize;

	// 総行数
	private String totalNum;

	// 有効行数
	private String validNum;

	// 作成時間
	private String systemTime;

	// 作成者
	private String user;

	// Cobol情報
	private List<CobolDto> cobolList;

	public String getFilesSize() {
		return filesSize;
	}

	public void setFilesSize(String filesSize) {
		this.filesSize = filesSize;
	}

	public String getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}

	public String getValidNum() {
		return validNum;
	}

	public void setValidNum(String validNum) {
		this.validNum = validNum;
	}

	public String getSystemTime() {
		return systemTime;
	}

	public void setSystemTime(String systemTime) {
		this.systemTime = systemTime;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public List<CobolDto> getCobolList() {
		return cobolList;
	}

	public void setCobolList(List<CobolDto> cobolList) {
		this.cobolList = cobolList;
	}
}
