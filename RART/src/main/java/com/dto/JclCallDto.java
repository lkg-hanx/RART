package com.dto;

/**
 * 呼び出し関係情報
 * 
 * @author hanx
 *
 */
public class JclCallDto {

	// 呼出し先
	private String callTarget;

	// 実行条件
	private String condition;

	// パラメータ
	private String parameter;

	// 備考
	private String notes;

	public String getCallTarget() {
		return callTarget;
	}

	public void setCallTarget(String callTarget) {
		this.callTarget = callTarget;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	
}
