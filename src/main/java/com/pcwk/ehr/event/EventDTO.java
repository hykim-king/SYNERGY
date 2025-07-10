package com.pcwk.ehr.event;

import java.util.Date;

import com.pcwk.ehr.cmn.DTO;

public class EventDTO extends DTO {
	
	
	private String ecode;
	private String email;
	private String title;
	private String div;
	private String contents;
	private String nickname;
	private int readCnt;
	private Date regDt;
	private String regId;
	private Date modDt;
	private String modId;

	/**
	 * 
	 */
	public EventDTO() {
	}

	/**
	 * @param ecode
	 * @param email
	 * @param title
	 * @param div
	 * @param contents
	 * @param nickname
	 * @param readCnt
	 * @param regDt
	 * @param regId
	 * @param modDt
	 * @param modId
	 */
	public EventDTO(String ecode, String email, String title, String div, String contents, String nickname, int readCnt,
			Date regDt, String regId, Date modDt, String modId) {
		super();
		this.ecode = ecode;
		this.email = email;
		this.title = title;
		this.div = div;
		this.contents = contents;
		this.nickname = nickname;
		this.readCnt = readCnt;
		this.regDt = regDt;
		this.regId = regId;
		this.modDt = modDt;
		this.modId = modId;
	}

	/**
	 * @return the ecode
	 */
	public String getEcode() {
		return ecode;
	}

	/**
	 * @param ecode the ecode to set
	 */
	public void setEcode(String ecode) {
		this.ecode = ecode;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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
		return "EventDTO [ecode=" + ecode + ", email=" + email + ", title=" + title + ", div=" + div + ", contents="
				+ contents + ", nickname=" + nickname + ", readCnt=" + readCnt + ", regDt=" + regDt + ", regId=" + regId
				+ ", modDt=" + modDt + ", modId=" + modId + ", toString()=" + super.toString() + "]";
	}

}
