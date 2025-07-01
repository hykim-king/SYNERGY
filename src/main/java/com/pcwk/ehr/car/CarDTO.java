package com.pcwk.ehr.car;

import java.util.Date;

import com.pcwk.ehr.cmn.DTO;

public class CarDTO extends DTO {
	private int carCode;
	private String productName;
	private String carMf;
	private String cartype;
	private String orgFn;
	private String modFn;
	private String path;
	private int price;
	private String fuel;
	private double ef;
	private String engine;
	private Integer dpm;    // null 가능
	private Double battery; // null 가능
	private int mfDt;
	private java.util.Date regDt;
	private String regId;
	private java.util.Date modDt;
	private String modId;
	// + getter, setter 모두 필수!

	public CarDTO() {
	}

	/**
	 * @param carCode
	 * @param productName
	 * @param carMf
	 * @param cartype
	 * @param orgFn
	 * @param modFn
	 * @param path
	 * @param price
	 * @param fuel
	 * @param ef
	 * @param engine
	 * @param dpm
	 * @param battery
	 * @param mfDt
	 * @param regDt
	 * @param regId
	 * @param modDt
	 * @param modId
	 */
	public CarDTO(int carCode, String productName, String carMf, String cartype, String orgFn, String modFn,
			String path, int price, String fuel, double ef, String engine, Integer dpm, Double battery, int mfDt,
			Date regDt, String regId, Date modDt, String modId) {
		super();
		this.carCode = carCode;
		this.productName = productName;
		this.carMf = carMf;
		this.cartype = cartype;
		this.orgFn = orgFn;
		this.modFn = modFn;
		this.path = path;
		this.price = price;
		this.fuel = fuel;
		this.ef = ef;
		this.engine = engine;
		this.dpm = dpm;
		this.battery = battery;
		this.mfDt = mfDt;
		this.regDt = regDt;
		this.regId = regId;
		this.modDt = modDt;
		this.modId = modId;
	}

	/**
	 * @return the carCode
	 */
	public int getCarCode() {
		return carCode;
	}

	/**
	 * @param carCode the carCode to set
	 */
	public void setCarCode(int carCode) {
		this.carCode = carCode;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the carMf
	 */
	public String getCarMf() {
		return carMf;
	}

	/**
	 * @param carMf the carMf to set
	 */
	public void setCarMf(String carMf) {
		this.carMf = carMf;
	}

	/**
	 * @return the cartype
	 */
	public String getCartype() {
		return cartype;
	}

	/**
	 * @param cartype the cartype to set
	 */
	public void setCartype(String cartype) {
		this.cartype = cartype;
	}

	/**
	 * @return the orgFn
	 */
	public String getOrgFn() {
		return orgFn;
	}

	/**
	 * @param orgFn the orgFn to set
	 */
	public void setOrgFn(String orgFn) {
		this.orgFn = orgFn;
	}

	/**
	 * @return the modFn
	 */
	public String getModFn() {
		return modFn;
	}

	/**
	 * @param modFn the modFn to set
	 */
	public void setModFn(String modFn) {
		this.modFn = modFn;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * @return the fuel
	 */
	public String getFuel() {
		return fuel;
	}

	/**
	 * @param fuel the fuel to set
	 */
	public void setFuel(String fuel) {
		this.fuel = fuel;
	}

	/**
	 * @return the ef
	 */
	public double getEf() {
		return ef;
	}

	/**
	 * @param ef the ef to set
	 */
	public void setEf(double ef) {
		this.ef = ef;
	}

	/**
	 * @return the engine
	 */
	public String getEngine() {
		return engine;
	}

	/**
	 * @param engine the engine to set
	 */
	public void setEngine(String engine) {
		this.engine = engine;
	}

	/**
	 * @return the dpm
	 */
	public Integer getDpm() {
		return dpm;
	}

	/**
	 * @param dpm the dpm to set
	 */
	public void setDpm(Integer dpm) {
		this.dpm = dpm;
	}

	/**
	 * @return the battery
	 */
	public Double getBattery() {
		return battery;
	}

	/**
	 * @param battery the battery to set
	 */
	public void setBattery(Double battery) {
		this.battery = battery;
	}

	/**
	 * @return the mfDt
	 */
	public int getMfDt() {
		return mfDt;
	}

	/**
	 * @param mfDt the mfDt to set
	 */
	public void setMfDt(int mfDt) {
		this.mfDt = mfDt;
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
		return "CarDTO [carCode=" + carCode + ", productName=" + productName + ", carMf=" + carMf + ", cartype="
				+ cartype + ", orgFn=" + orgFn + ", modFn=" + modFn + ", path=" + path + ", price=" + price + ", fuel="
				+ fuel + ", ef=" + ef + ", engine=" + engine + ", dpm=" + dpm + ", battery=" + battery + ", mfDt="
				+ mfDt + ", regDt=" + regDt + ", regId=" + regId + ", modDt=" + modDt + ", modId=" + modId + "]";
	}

}