package com.pcwk.ehr.cmn;

public class SearchDTO extends DTO {

	private String searchDiv;

	private String searchWord;
	private int pageNo; // 현재 페이지 번호
	private int pageSize;
	private String div;
	/**
	 * @param searchDiv
	 * @param searchWord
	 * @param pageNo
	 * @param pageSize
	 * @param div
	 */
	public SearchDTO() {}
	public SearchDTO(String searchDiv, String searchWord, int pageNo, int pageSize, String div) {
		super();
		this.searchDiv = searchDiv;
		this.searchWord = searchWord;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.div = div;
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
	 * @return the pageNo
	 */
	public int getPageNo() {
		return pageNo;
	}
	/**
	 * @param pageNo the pageNo to set
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
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
	 * @return the div
	 */
	public String getDiv() {
		return div;
	}
	/**
	 * @param div the div to set
	 */
	public void setDiv(String div) {
		this.div = div;
	}
	@Override
	public String toString() {
		return "SearchDTO [searchDiv=" + searchDiv + ", searchWord=" + searchWord + ", pageNo=" + pageNo + ", pageSize="
				+ pageSize + ", div=" + div + ", toString()=" + super.toString() + "]";
	}
	
}