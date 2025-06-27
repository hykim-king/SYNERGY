package com.pcwk.ehr.repair;

import com.pcwk.ehr.cmn.PLog;
import java.util.Date;

// 정비예약 정보 DTO

public class RepairResDTO implements PLog {
	private int repairNo;
	private String id;
	private String name;
	private String phone;
	private int carCode;
	private int retailerCode;
	private Date repairDate;
	private String repairDesc;
	private Date regDt;
	private String regId;
	private Date modDt;
	private String modId;

	public RepairResDTO() {
		log.debug("RepairResDTO() 생성");
	}

	public int getRepairNo() {
		return repairNo;
	}

	public void setRepairNo(int repairNo) {
		this.repairNo = repairNo;
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

	public Date getRepairDate() {
		return repairDate;
	}

	public void setRepairDate(Date repairDate) {
		this.repairDate = repairDate;
	}

	public String getRepairDesc() {
		return repairDesc;
	}

	public void setRepairDesc(String repairDesc) {
		this.repairDesc = repairDesc;
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
		return "RepairResDTO [repairNo=" + repairNo + ", id=" + id + ", name=" + name + ", phone=" + phone
				+ ", carCode=" + carCode + ", retailerCode=" + retailerCode + ", repairDate=" + repairDate
				+ ", repairDesc=" + repairDesc + ", regDt=" + regDt + ", regId=" + regId + ", modDt=" + modDt
				+ ", modId=" + modId + "]";
	}

	public RepairResDTO(String id, String name, String phone, int carCode, int retailerCode,
			Date repairDate, String repairDesc, String regId, String modId) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.carCode = carCode;
		this.retailerCode = retailerCode;
		this.repairDate = repairDate;
		this.repairDesc = repairDesc;
		this.regId = regId;
		this.modId = modId;
		log.debug("RepairResDTO 생성: {}", this);
	}

}