package com.pcwk.ehr.board;

import com.pcwk.ehr.cmn.DTO;

public class BoardDTO extends DTO{
	
	private int boardCode;
    private String title;
    private String contents;
    private String id;
    private String nickname;
    private int readCnt;
    private String regDt;
    private String regId;
    private String modDt;
    private String modId;
	/**
	 * @param boardCode
	 * @param title
	 * @param contents
	 * @param id
	 * @param nickname
	 * @param readCnt
	 * @param regDt
	 * @param regId
	 * @param modDt
	 * @param modId
	 */
	public BoardDTO(int boardCode, String title, String contents, String id, String nickname, int readCnt, String regDt,
			String regId, String modDt, String modId) {
		super();
		this.boardCode = boardCode;
		this.title = title;
		this.contents = contents;
		this.id = id;
		this.nickname = nickname;
		this.readCnt = readCnt;
		this.regDt = regDt;
		this.regId = regId;
		this.modDt = modDt;
		this.modId = modId;
	}
	public BoardDTO() {
		// TODO Auto-generated constructor stub
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
	public String getRegDt() {
		return regDt;
	}
	/**
	 * @param regDt the regDt to set
	 */
	public void setRegDt(String regDt) {
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
	public String getModDt() {
		return modDt;
	}
	/**
	 * @param modDt the modDt to set
	 */
	public void setModDt(String modDt) {
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
		return "BoardDTO [boardCode=" + boardCode + ", title=" + title + ", contents=" + contents + ", id=" + id
				+ ", nickname=" + nickname + ", readCnt=" + readCnt + ", regDt=" + regDt + ", regId=" + regId
				+ ", modDt=" + modDt + ", modId=" + modId + "]";
	}
    
	
}

/**
 * 
 */
package com.pcwk.ehr.board;

import java.util.Date;

public class BoardDTO {

	private int boardCode;
	private String title;
	private String contents;
	private String id;
	private String nickname;
	private int readCnt;
	private Date regDt;
	private String regId;
	private Date modDt;
	private String modId;

	/**
	 * @param boardCode
	 * @param title
	 * @param contents
	 * @param id
	 * @param nickname
	 * @param readCnt
	 * @param regDt
	 * @param regId
	 * @param modDt
	 * @param modId
	 */
	public BoardDTO(int boardCode, String title, String contents, String id, String nickname, int readCnt, Date regDt,
			String regId, Date modDt, String modId) {
		super();
		this.boardCode = boardCode;
		this.title = title;
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
		return "BoardDTO [boardCode=" + boardCode + ", title=" + title + ", contents=" + contents + ", id=" + id
				+ ", nickname=" + nickname + ", readCnt=" + readCnt + ", regDt=" + regDt + ", regId=" + regId
				+ ", modDt=" + modDt + ", modId=" + modId + "]";
	}

}

