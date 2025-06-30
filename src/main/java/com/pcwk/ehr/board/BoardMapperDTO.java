package com.pcwk.ehr.board;

import com.pcwk.ehr.cmn.DTO;

public class BoardMapperDTO extends DTO {

	private int pageNo; // 페이지 번호
	private int pageSize; // 페이지 당 건수
	private String searchDiv; // 검색 구분 (예: 제목, 작성자 등)
	private String searchWord; // 검색어

	public BoardMapperDTO() {
	}

	public BoardMapperDTO(int pageNo, int pageSize, String searchDiv, String searchWord) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.searchDiv = searchDiv;
		this.searchWord = searchWord;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSearchDiv() {
		return searchDiv;
	}

	public void setSearchDiv(String searchDiv) {
		this.searchDiv = searchDiv;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	@Override
	public String toString() {
		return "BoardMapperDTO [pageNo=" + pageNo + ", pageSize=" + pageSize + ", searchDiv=" + searchDiv
				+ ", searchWord=" + searchWord + "]";
	}
}