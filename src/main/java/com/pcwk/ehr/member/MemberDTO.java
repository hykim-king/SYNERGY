package com.pcwk.ehr.member;

import java.util.Date;

public class MemberDTO {
	
	private String id;
	private String pwd;
	private String nickname;
	private String name;
	private String phone;
	private String email;
	private Date regDt;
	private String regId;
	private Date modDt;
	private String modId;
	private int adminRole;
	
	public MemberDTO() {}

	/**
	 * @param id
	 * @param pwd
	 * @param nickname
	 * @param name
	 * @param phone
	 * @param email
	 * @param regDt
	 * @param regId
	 * @param modDt
	 * @param modId
	 * @param adminRole
	 */
	public MemberDTO(String id, String pwd, String nickname, String name, String phone, String email, Date regDt,
			String regId, Date modDt, String modId, int adminRole) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.nickname = nickname;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.regDt = regDt;
		this.regId = regId;
		this.modDt = modDt;
		this.modId = modId;
		this.adminRole = adminRole;
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
	 * @return the pwd
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * @param pwd the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
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

	/**
	 * @return the adminRole
	 */
	public int getAdminRole() {
		return adminRole;
	}

	/**
	 * @param adminRole the adminRole to set
	 */
	public void setAdminRole(int adminRole) {
		this.adminRole = adminRole;
	}

	@Override
	public String toString() {
		return "MemberDTO [id=" + id + ", pwd=" + pwd + ", nickname=" + nickname + ", name=" + name + ", phone=" + phone
				+ ", email=" + email + ", regDt=" + regDt + ", regId=" + regId + ", modDt=" + modDt + ", modId=" + modId
				+ ", adminRole=" + adminRole + "]";
	}
	
	

}
