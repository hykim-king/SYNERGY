/**
 * 
 */
package com.pcwk.ehr.boardmapper;

import com.pcwk.ehr.cmn.SearchDTO;

public class BoardMapperDTO extends SearchDTO {

	private int boardCode;
	private String title;
	private String nickname;
	private String regDt; // 문자열 형태의 등록일 (화면 출력용 등)

	public BoardMapperDTO() {
	}

	/**
	 * @param boardCode
	 * @param title
	 * @param nickname
	 * @param regDt
	 */
	public BoardMapperDTO(int boardCode, String title, String nickname, String regDt) {
		super();
		this.boardCode = boardCode;
		this.title = title;
		this.nickname = nickname;
		this.regDt = regDt;
	}

	/**
	 * @return the boardCode
	 */
	public int getBoardCode() {
		return boardCode;
	}

	/**
	 * @param boardCode the boardCode to set
	 */
	public void setBoardCode(int boardCode) {
		this.boardCode = boardCode;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @return the regDt
	 */
	public String getRegDt() {
		return regDt;
	}

	/**
	 * @param regDt the regDt to set
	 */
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	@Override
	public String toString() {
		return "BoardMapperDTO [boardCode=" + boardCode + ", title=" + title + ", nickname=" + nickname + ", regDt="
				+ regDt + "]";
	}

}
