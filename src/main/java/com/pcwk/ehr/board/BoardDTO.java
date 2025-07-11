package com.pcwk.ehr.board;

import java.util.Date;

import com.pcwk.ehr.cmn.DTO;

public class BoardDTO extends DTO {
	private int boardCode; // 게시글 고유 번호
	private String title; // 제목
	private String div; // 게시구분
	private String contents; // 내용
	private String id; // 작성자 ID
	private String nickname; // 작성자 닉네임
	private int readCnt; // 조회수
	private Date regDt; // 등록일
	private String regId; // 등록자 ID
	private Date modDt; // 수정일
	private String modId; // 수정자 ID

	public BoardDTO() {
	}

	/**
	 * @param boardCode
	 * @param title
	 * @param div
	 * @param contents
	 * @param id
	 * @param nickname
	 * @param readCnt
	 * @param regDt
	 * @param regId
	 * @param modDt
	 * @param modId
	 */
	public BoardDTO(int boardCode, String title, String div, String contents, String id, String nickname, int readCnt,
			Date regDt, String regId, Date modDt, String modId) {
		super();
		this.boardCode = boardCode;
		this.title = title;
		this.div = div;
		this.contents = contents;
		this.id = id;
		this.nickname = nickname;
		this.readCnt = readCnt;
		this.regDt = regDt;
		this.regId = regId;
		this.modDt = modDt;
		this.modId = modId;
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

	/**
	 * @return the contents
	 */
	public String getContents() {
		return contents;
	}

	/**
	 * @param contents the contents to set
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @return the readCnt
	 */
	public int getReadCnt() {
		return readCnt;
	}

	/**
	 * @param readCnt the readCnt to set
	 */
	public void setReadCnt(int readCnt) {
		this.readCnt = readCnt;
	}

	/**
	 * @return the regDt
	 */
	public Date getRegDt() {
		return regDt;
	}

	/**
	 * @param regDt the regDt to set
	 */
	public void setRegDt(Date regDt) {
		this.regDt = regDt;
	}

	/**
	 * @return the regId
	 */
	public String getRegId() {
		return regId;
	}

	/**
	 * @param regId the regId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}

	/**
	 * @return the modDt
	 */
	public Date getModDt() {
		return modDt;
	}

	/**
	 * @param modDt the modDt to set
	 */
	public void setModDt(Date modDt) {
		this.modDt = modDt;
	}

	/**
	 * @return the modId
	 */
	public String getModId() {
		return modId;
	}

	/**
	 * @param modId the modId to set
	 */
	public void setModId(String modId) {
		this.modId = modId;
	}

	@Override
	public String toString() {
		return "BoardDTO [boardCode=" + boardCode + ", title=" + title + ", div=" + div + ", contents=" + contents
				+ ", id=" + id + ", nickname=" + nickname + ", readCnt=" + readCnt + ", regDt=" + regDt + ", regId="
				+ regId + ", modDt=" + modDt + ", modId=" + modId + ", toString()=" + super.toString() + "]";
	}
}