package com.dto;

import java.util.List;

/**
 * 入出力情報
 * 
 * @author hanx
 *
 */
public class JclIOsDto {

	// ステッブ名(EX)
	private String steveName;
	
	// 入出力情報
	private List<JclIODto> ioList;
		
	public String getSteveName() {
		return steveName;
	}

	public void setSteveName(String steveName) {
		this.steveName = steveName;
	}

	public List<JclIODto> getIoList() {
		return ioList;
	}

	public void setIoList(List<JclIODto> ioList) {
		this.ioList = ioList;
	}
}
