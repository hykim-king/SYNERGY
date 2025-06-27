/**
 * Package Name : com.pcwk.ehr.cmn <br/>
 * 파일명: DTO.java <br/>
 */
package com.pcwk.ehr.cmn;

public class DTO {
	private String searchDiv; // 검색구분 (ex: 제목, 작성자 등)
	private String searchWord; // 검색어
	private int pageSize = 20; // 한 페이지당 게시글 수
	private int pageNum = 1; // 현재 페이지 번호
	// 페이징 계산용
	public int getStartRow() {
		return (pageNum - 1) * pageSize + 1;
	}
	public int getEndRow() {
		return pageNum * pageSize;
	}
	/**
	 * @return the searchDiv
	 */
	public String getSearchDiv() {
		return searchDiv;
	}
	/**
	 * @param searchDiv the searchDiv to set
	 */
	public void setSearchDiv(String searchDiv) {
		this.searchDiv = searchDiv;
	}
	/**
	 * @return the searchWord
	 */
	public String getSearchWord() {
		return searchWord;
	}
	/**
	 * @param searchWord the searchWord to set
	 */
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * @return the pageNum
	 */
	public int getPageNum() {
		return pageNum;
	}
	/**
	 * @param pageNum the pageNum to set
	 */
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	@Override
	public String toString() {
		return "DTO [searchDiv=" + searchDiv + ", searchWord=" + searchWord + ", pageSize=" + pageSize + ", pageNum="
				+ pageNum + "]";
	}
}
