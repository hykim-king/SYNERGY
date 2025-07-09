/**
 * Package Name : com.pcwk.ehr.cmn <br/>
 * 파일명: DTO.java <br/>
 */
package com.pcwk.ehr.cmn;

public class DTO {
	private String searchDiv; // 검색구분 (ex: 제목, 작성자 등)
	private String searchWord; // 검색어
	private int pageSize = 10; // 한 페이지당 게시글 수
	private int pageNum = 1; // 현재 페이지 번호
	private int totalCnt;
	private int startRow;
	private int endRow;
	private int no;
	/**
	 * 
	 */
	public DTO() {}
	/**
	 * @param searchDiv
	 * @param searchWord
	 * @param pageSize
	 * @param pageNum
	 * @param totalCnt
	 * @param startRow
	 * @param endRow
	 * @param no
	 */
	public DTO(String searchDiv, String searchWord, int pageSize, int pageNum, int totalCnt, int startRow, int endRow,
			int no) {
		super();
		this.searchDiv = searchDiv;
		this.searchWord = searchWord;
		this.pageSize = pageSize;
		this.pageNum = pageNum;
		this.totalCnt = totalCnt;
		this.startRow = startRow;
		this.endRow = endRow;
		this.no = no;
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
	/**
	 * @return the totalCnt
	 */
	public int getTotalCnt() {
		return totalCnt;
	}
	/**
	 * @param totalCnt the totalCnt to set
	 */
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}
	/**
	 * @return the startRow
	 */
	public int getStartRow() {
		return startRow;
	}
	/**
	 * @param startRow the startRow to set
	 */
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	/**
	 * @return the endRow
	 */
	public int getEndRow() {
		return endRow;
	}
	/**
	 * @param endRow the endRow to set
	 */
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
	/**
	 * @return the no
	 */
	public int getNo() {
		return no;
	}
	/**
	 * @param no the no to set
	 */
	public void setNo(int no) {
		this.no = no;
	}
	@Override
	public String toString() {
		return "DTO [searchDiv=" + searchDiv + ", searchWord=" + searchWord + ", pageSize=" + pageSize + ", pageNum="
				+ pageNum + ", totalCnt=" + totalCnt + ", startRow=" + startRow + ", endRow=" + endRow + ", no=" + no
				+ ", toString()=" + super.toString() + "]";
	}
	
	
	
}