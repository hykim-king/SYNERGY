package com.pcwk.ehr.cmn;

public class SearchDTO extends DTO {

	private String searchDiv;
	private String searchWord;
	private int pageNo; // ✅ 여기를 pageNo로 맞춰야 함
	private int pageSize;

	public SearchDTO() {
	}

	/**
	 * @param searchDiv
	 * @param searchWord
	 * @param pageNo
	 * @param pageSize
	 */
	public SearchDTO(String searchDiv, String searchWord, int pageNo, int pageSize) {
		super();
		this.searchDiv = searchDiv;
		this.searchWord = searchWord;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
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

	@Override
	public String toString() {
		return "SearchDTO [searchDiv=" + searchDiv + ", searchWord=" + searchWord + ", pageNo=" + pageNo + ", pageSize="
				+ pageSize + "]";
	}

}