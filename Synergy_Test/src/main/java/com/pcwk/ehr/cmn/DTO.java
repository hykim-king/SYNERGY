/**
 * Package Name : com.pcwk.ehr.cmn <br/>
 * 파일명: DTO.java <br/>
 */
package com.pcwk.ehr.cmn;

public class DTO {
	private int no;
	private int totalCnt;
	
	public DTO() {}

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
		return "DTO [no=" + no + ", totalCnt=" + totalCnt + "]";
	}
	
	

}
