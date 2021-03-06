package com.dto;

import java.util.List;

/**
 * Cobol情報
 * @author hanx
 *
 */
public class CobolDto  {
	
	// PGM名
	private String pgmName;
	
	// ライプライ名
	private String raipiraiName;
	
	// 同名資産
	private String sameName;
	
	// ファイル入出力
	private String ioPath;
	private List<CobolIODto> ioList;
	
	// 呼び出し関係
	private String callPath;
	private List<CobolCallDto> callList;
	
	// 総行数
	private String totalNum;
	
	// 有効行数
	private String validNum;
	
	// 備考
	private String notes;
	
	public String getPgmName() {
		return pgmName;
	}
	public void setPgmName(String pgmName) {
		this.pgmName = pgmName;
	}
	
	public String getRaipiraiName() {
		return raipiraiName;
	}
	public void setRaipiraiName(String raipiraiName) {
		this.raipiraiName = raipiraiName;
	}
	
	public String getSameName() {
		return sameName;
	}
	public void setSameName(String sameName) {
		this.sameName = sameName;
	}
	
	public String getIoPath() {
		return ioPath;
	}
	public void setIoPath(String ioPath) {
		this.ioPath = ioPath;
	}
	public List<CobolIODto> getIoList() {
		return ioList;
	}
	public void setIoList(List<CobolIODto> ioList) {
		this.ioList = ioList;
	}
	
	public String getCallPath() {
		return callPath;
	}
	public void setCallPath(String callPath) {
		this.callPath = callPath;
	}
	public List<CobolCallDto> getCallList() {
		return callList;
	}
	public void setCallList(List<CobolCallDto> callList) {
		this.callList = callList;
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
	
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
}
