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

	// Cobolファイル件数
	private String cobolFilesSize;
		
	// Cobol総行数
	private String cobolTotalNum;

	// Cobol有効行数
	private String cobolValidNum;
	
	// Cobol情報
	private List<CobolDto> cobolList;
	
	// Jclファイル件数
	private String jclFilesSize;
		
	// Jcl総行数
	private String jclTotalNum;

	// Jcl有効行数
	private String jclValidNum;
	
	// Jcl情報
	private List<JclDto> jclList;

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
	
	public List<JclDto> getJclList() {
		return jclList;
	}

	public void setJclList(List<JclDto> jclList) {
		this.jclList = jclList;
	}

	public String getCobolFilesSize() {
		return cobolFilesSize;
	}

	public void setCobolFilesSize(String cobolFilesSize) {
		this.cobolFilesSize = cobolFilesSize;
	}

	public String getCobolTotalNum() {
		return cobolTotalNum;
	}

	public void setCobolTotalNum(String cobolTotalNum) {
		this.cobolTotalNum = cobolTotalNum;
	}

	public String getCobolValidNum() {
		return cobolValidNum;
	}

	public void setCobolValidNum(String cobolValidNum) {
		this.cobolValidNum = cobolValidNum;
	}

	public String getJclFilesSize() {
		return jclFilesSize;
	}

	public void setJclFilesSize(String jclFilesSize) {
		this.jclFilesSize = jclFilesSize;
	}

	public String getJclTotalNum() {
		return jclTotalNum;
	}

	public void setJclTotalNum(String jclTotalNum) {
		this.jclTotalNum = jclTotalNum;
	}

	public String getJclValidNum() {
		return jclValidNum;
	}

	public void setJclValidNum(String jclValidNum) {
		this.jclValidNum = jclValidNum;
	}
}
