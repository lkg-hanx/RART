package com.dto;

/**
 * 入出力情報
 * @author hanx
 *
 */
public class IODto  {
	
	// ファイル名
	private String filesName;
	
	// 種别名
	private String fileslType;
	
	// レコードキー
	private String recordKey;
	
	// データレコードキー
	private String dateRecordKey;
	
	// 入出力
	private String ioType;
	
	// アクセスモード
	private String accessMode;
	
	// 備考
	private String notes;
	
	public String getFilesName() {
		return filesName;
	}
	public void setFilesName(String filesName) {
		this.filesName = filesName;
	}
	
	public String getFileslType() {
		return fileslType;
	}
	public void setFileslType(String fileslType) {
		this.fileslType = fileslType;
	}
	
	public String getRecordKey() {
		return recordKey;
	}
	public void setRecordKey(String recordKey) {
		this.recordKey = recordKey;
	}
	
	public String getDateRecordKey() {
		return dateRecordKey;
	}
	public void setDateRecordKey(String dateRecordKey) {
		this.dateRecordKey = dateRecordKey;
	}
	
	public String getIoType() {
		return ioType;
	}
	public void setIoType(String ioType) {
		this.ioType = ioType;
	}
	
	public String getAccessMode() {
		return accessMode;
	}
	public void setAccessMode(String accessMode) {
		this.accessMode = accessMode;
	}
	
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
}
