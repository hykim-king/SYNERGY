package com.pcwk.ehr.drive;

import java.util.Date;

import com.pcwk.ehr.cmn.PLog;

/**
 * 시승 예약 정보 DTO
 */
public class DriveResDTO implements PLog {
	private int resNo;
	private String id;
	private String name;
	private String phone;
	private int carCode;
	private int retailerCode;
	private Date driveDate;
	private Date regDt;
	private String regId;
	private Date modDt;
	private String modId;

	public DriveResDTO() {
		log.debug("DriveResDTO() 생성");
	}

	public int getResNo() {
		return resNo;
	}

	public void setResNo(int resNo) {
		this.resNo = resNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getCarCode() {
		return carCode;
	}

	public void setCarCode(int carCode) {
		this.carCode = carCode;
	}

	public int getRetailerCode() {
		return retailerCode;
	}

	public void setRetailerCode(int retailerCode) {
		this.retailerCode = retailerCode;
	}

	public Date getDriveDate() {
		return driveDate;
	}

	public void setDriveDate(Date driveDate) {
		this.driveDate = driveDate;
	}

	public Date getRegDt() {
		return regDt;
	}

	public void setRegDt(Date regDt) {
		this.regDt = regDt;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public Date getModDt() {
		return modDt;
	}

	public void setModDt(Date modDt) {
		this.modDt = modDt;
	}

	public String getModId() {
		return modId;
	}

	public void setModId(String modId) {
		this.modId = modId;
	}

	@Override
	public String toString() {
		return "DriveResDTO [resNo=" + resNo + ", id=" + id + ", name=" + name + ", phone=" + phone + ", carCode="
				+ carCode + ", retailerCode=" + retailerCode + ", driveDate=" + driveDate + ", regDt=" + regDt
				+ ", regId=" + regId + ", modDt=" + modDt + ", modId=" + modId + "]";
	}

	public DriveResDTO(String id, String name, String phone, int carCode, int retailerCode, Date driveDate, Date regDt,
			 String regId, Date modDt, String modId) {
			this.id = id;
			this.name = name;
			this.phone = phone;
			this.carCode = carCode;
			this.retailerCode = retailerCode;
			this.driveDate = driveDate;
			this.regDt= regDt;
			this.regId = regId;
			this.modDt=modDt;
			this.modId = modId;
			log.debug("DriveResDTO 생성: {}", this);
		}


}